package com.kltn.auth_service.component.categoryItem.mapper;

import com.kltn.auth_service.component.categoryItem.CategoryItem;
import com.kltn.auth_service.component.categoryItem.dto.request.CategoryItemRequest;
import com.kltn.auth_service.component.categoryItem.dto.response.CategoryItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryItemMapper {
    CategoryItem toCategoryItem(CategoryItemRequest request);
    CategoryItemResponse toCategoryItemResponse(CategoryItem categoryItem);
}
