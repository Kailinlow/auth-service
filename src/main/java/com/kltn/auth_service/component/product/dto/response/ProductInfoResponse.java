package com.kltn.auth_service.component.product.dto.response;

import com.kltn.auth_service.component.productAttributes.dto.response.ProductAttributesResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoResponse {
    private String id;
    private String name;
    private String sku;
    private String imageUrl;
    private String description;
    private BigDecimal cost;
    private BigDecimal price;
    private BigDecimal marketPrice;
    private Long stockQuantity;
    private ProductResponse.BrandResponse brand;
    private List<ProductAttributesResponse> productAttributesList;
}
