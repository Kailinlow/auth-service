package com.kltn.auth_service.component.productAttributes.dto.request;

public record CreateProductAttributesRequest(
        String attributeName,
        String attributeValue,
        Integer oderPoint,
        String productId
) {
}
