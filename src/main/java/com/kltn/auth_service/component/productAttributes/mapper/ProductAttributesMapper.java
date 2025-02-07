package com.kltn.auth_service.component.productAttributes.mapper;

import com.kltn.auth_service.component.productAttributes.ProductAttributes;
import com.kltn.auth_service.component.productAttributes.dto.request.CreateProductAttributesRequest;
import com.kltn.auth_service.component.productAttributes.dto.request.UpdateProductAttributesRequest;
import com.kltn.auth_service.component.productAttributes.dto.response.ProductAttributesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductAttributesMapper {
    ProductAttributes toProductAttributes(CreateProductAttributesRequest request);
    ProductAttributesResponse toProductAttributesResponse(ProductAttributes productAttributes);
    List<ProductAttributesResponse> toResponses(List<ProductAttributes> productAttributesList);

    void updateProductAttributesFromRequest(UpdateProductAttributesRequest request, @MappingTarget ProductAttributes productAttributes);
}
