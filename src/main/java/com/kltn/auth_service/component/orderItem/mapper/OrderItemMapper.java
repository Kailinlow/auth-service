package com.kltn.auth_service.component.orderItem.mapper;

import com.kltn.auth_service.component.orderItem.OrderItem;
import com.kltn.auth_service.component.orderItem.dto.request.CreateOrderItemRequest;
import com.kltn.auth_service.component.orderItem.dto.request.OrderItemRequest;
import com.kltn.auth_service.component.orderItem.dto.response.OrderItemResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toOrderItem(OrderItemRequest request);
    OrderItem toOrderItem(CreateOrderItemRequest request);

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

    List<OrderItemResponse> toResponses(List<OrderItem> orderItemList);
}
