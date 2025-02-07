package com.kltn.auth_service.component.productAttributes.dto.response;

public record ProductAttributesResponse(
        String id,
        String attributeName,
        String attributeValue,
        Integer oderPoint,
        ProductInfor productResponse
) {
    public record ProductInfor (
      String id,
      String name
    ){}
}
