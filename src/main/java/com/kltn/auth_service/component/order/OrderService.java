package com.kltn.auth_service.component.order;

import com.kltn.auth_service.component.coupon.Coupon;
import com.kltn.auth_service.component.coupon.CouponRepository;
import com.kltn.auth_service.component.coupon.mapper.CouponMapper;
import com.kltn.auth_service.component.order.dto.request.CreateOrderRequest;
import com.kltn.auth_service.component.order.dto.request.OrderRequest;
import com.kltn.auth_service.component.order.dto.response.OrderResponse;
import com.kltn.auth_service.component.order.mapper.OrderMapper;
import com.kltn.auth_service.component.orderItem.OrderItem;
import com.kltn.auth_service.component.orderItem.OrderItemRepository;
import com.kltn.auth_service.component.orderItem.dto.request.CreateOrderItemRequest;
import com.kltn.auth_service.component.orderItem.dto.request.OrderItemRequest;
import com.kltn.auth_service.component.orderItem.dto.response.OrderItemResponse;
import com.kltn.auth_service.component.orderItem.mapper.OrderItemMapper;
import com.kltn.auth_service.component.product.Product;
import com.kltn.auth_service.component.product.ProductRepository;
import com.kltn.auth_service.component.product.dto.response.ProductShortResponse;
import com.kltn.auth_service.component.product.mapper.ProductMapper;
import com.kltn.auth_service.dto.response.UserResponse;
import com.kltn.auth_service.entity.User;
import com.kltn.auth_service.ghn.GhnApiService;
import com.kltn.auth_service.ghn.dto.GhnOrderRequest;
import com.kltn.auth_service.ghn.dto.GhnOrderResponse;
import com.kltn.auth_service.ghn.dto.GhnTokenResponse;
import com.kltn.auth_service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    private final UserRepository userRepository;

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final GhnApiService ghnApiService;


    public UserResponse findUserById(String id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not existed."));

        return UserResponse.builder()
                .id(existingUser.getId())
                .firstname(existingUser.getFirstname())
                .lastname(existingUser.getLastname())
                .email(existingUser.getEmail())
                .phoneNumber(existingUser.getPhoneNumber())
                .build();
    }

    private ProductShortResponse findProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow();

        return productMapper.toProductShortResponse(product);
    }


    public OrderResponse create(CreateOrderRequest request) {
        UserResponse user = findUserById(request.getUserId());

        Order order = mapper.toOrder(request);
        order.setStatus("processing");

        repository.save(order);

        OrderResponse orderResponse = mapper.toOrderResponse(order);
        orderResponse.setUser(user);

        return orderResponse;
    }

    public OrderResponse createNewOrder(CreateOrderRequest request) {
        OrderRequest orderRequest = new OrderRequest(request.getUserId(), request.getOrderDate());
        Order order = mapper.toOrder(orderRequest);
        order.setStatus("processing");

        Order newOrder = repository.save(order);

        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);

        OrderResponse orderResponse = mapper.toOrderResponse(newOrder);
        List<OrderItemResponse> orderItemResponsesList = new ArrayList<>();

        request.getOrderItemList().stream()
                .forEach(orderItemRequest -> {
                    OrderItemResponse orderItemResponse = createOrderItem(orderItemRequest, newOrder.getId(), newOrder);
                    orderItemResponsesList.add(orderItemResponse);
                    totalAmount.set(totalAmount.get().add(
                            orderItemResponse.getPrice().multiply(BigDecimal.valueOf(orderItemResponse.getQuantity()))
                    ));
                });


        BigDecimal discount = BigDecimal.ZERO;
        try {
            UserResponse user = findUserById(request.getUserId());
            orderResponse.setUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user details", e);
        }

        if (!request.getCouponId().isEmpty()) {
            try {
                Coupon coupon = couponRepository.findById(request.getCouponId()).orElseThrow();
                orderResponse.setCoupon(couponMapper.toCouponResponse(coupon));

                if (coupon.getDiscount() != null) {
                    discount = totalAmount.get().multiply(coupon.getDiscount().divide(BigDecimal.valueOf(100)));
                    totalAmount.set(totalAmount.get().subtract(discount));
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch coupon details", e);
            }
        }

        orderResponse.setOrderItemList(orderItemResponsesList);
        orderResponse.setTotalAmount(totalAmount.get());

        return orderResponse;
    }

    private OrderItemResponse createOrderItem(CreateOrderItemRequest request, String orderId, Order order){
        OrderItemRequest orderItemRequest = new OrderItemRequest(request.getQuantity(), orderId, request.getProductId());

        OrderItem newOrderItem = orderItemMapper.toOrderItem(orderItemRequest);
        newOrderItem.setOrder(order);

        ProductShortResponse productDTO = findProductById(request.getProductId());

        newOrderItem.setPrice(productDTO.getPrice());
        orderItemRepository.save(newOrderItem);

        OrderItemResponse orderItemResponse = orderItemMapper.toOrderItemResponse(newOrderItem);
        orderItemResponse.setProduct(productDTO);

        return orderItemResponse;
    }

    public OrderResponse findById(String id) {
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not existing."));

        return mapper.toOrderResponse(existingOrder);
    }

    public OrderResponse updateStatusOrder(String id) {
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not existing."));

        existingOrder.setStatus("proved");
        repository.save(existingOrder);

        return mapper.toOrderResponse(existingOrder);
    }

    public List<OrderResponse> findAll() {
        List<Order> orderList = repository.findAll();

        return mapper.toResponses(orderList);
    }

    public List<OrderResponse> findByUserId(String userId) {
        List<Order> orderList = repository.findByUserId(userId);

        return mapper.toResponses(orderList);
    }


    public String printA5ShippingLabel(String id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Order Id")
        );

        if (order.getShippingCode() == null) {
            throw new IllegalStateException("Couldn't print the shipping label because this order hasn't been initiated yet");
        }

        GhnTokenResponse tokenResponse = ghnApiService.generatePrintOrderToken(List.of(order.getShippingCode()));

        if (tokenResponse.getCode() != 200) {
            throw new RuntimeException(tokenResponse.getCode()+": " + tokenResponse.getMessage());
        }

        String token = tokenResponse.getData().getToken();
        return "https://dev-online-gateway.ghn.vn/a5/public-api/printA5?token=" + token;
    }

    public void deleteOrder(String orderId) {
        Optional<Order> orderOptional = repository.findById(orderId);
        if (orderOptional.isPresent()) {
            repository.delete(orderOptional.get());
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }
}
