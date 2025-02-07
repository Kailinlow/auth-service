package com.kltn.auth_service.component.order.mapper;

import com.kltn.auth_service.component.order.Order;
import com.kltn.auth_service.component.order.dto.request.CreateOrderRequest;
import com.kltn.auth_service.component.order.dto.request.OrderRequest;
import com.kltn.auth_service.component.order.dto.response.OrderResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderRequest request);

    Order toOrder(CreateOrderRequest request);

    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toResponses(List<Order> orders);
}
