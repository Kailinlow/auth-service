package com.kltn.auth_service.component.category.mapper;

import com.kltn.auth_service.component.category.Category;
import com.kltn.auth_service.component.category.dto.request.CategoryRequest;
import com.kltn.auth_service.component.category.dto.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> categoriesToResponses(List<Category> categories);

    void updateCategoryFromRequest(CategoryRequest request, @MappingTarget Category category);
}
