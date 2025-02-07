package com.kltn.auth_service.component.categoryItem.dto.response;

import com.kltn.auth_service.component.category.dto.response.CategoryResponse;
import com.kltn.auth_service.component.product.dto.response.ProductResponse;
import lombok.*;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemResponse {
    private ProductResponse product;

    private CategoryResponse category;
}
