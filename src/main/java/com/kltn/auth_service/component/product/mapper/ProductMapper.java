package com.kltn.auth_service.component.product.mapper;

import com.kltn.auth_service.component.product.Product;
import com.kltn.auth_service.component.product.dto.request.CreateProductRequest;
import com.kltn.auth_service.component.product.dto.request.UpdateProductRequest;
import com.kltn.auth_service.component.product.dto.response.ProductInfoResponse;
import com.kltn.auth_service.component.product.dto.response.ProductResponse;
import com.kltn.auth_service.component.product.dto.response.ProductShortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(CreateProductRequest request);
    ProductResponse toProductResponse(Product product);
    ProductInfoResponse toProductInfoResponse(Product product);
    List<ProductResponse> toResponses(List<Product> products);

    ProductShortResponse toProductShortResponse(Product product);

    void updateProductFromRequest(UpdateProductRequest request, @MappingTarget Product product);

}
