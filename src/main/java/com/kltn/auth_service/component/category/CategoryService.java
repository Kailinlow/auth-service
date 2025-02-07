package com.kltn.auth_service.component.category;

import com.kltn.auth_service.component.category.dto.request.CategoryRequest;
import com.kltn.auth_service.component.category.dto.response.CategoryResponse;
import com.kltn.auth_service.component.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryResponse create(CategoryRequest request){
        Category category = mapper.toCategory(request);

        repository.save(category);

        return mapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> findAll() {
        List<Category> categoryList = repository.findAll();

        return mapper.categoriesToResponses(categoryList);
    }

    public CategoryResponse findById(String id) {
        Optional<Category> categoryOptional = repository.findById(id);

        if (categoryOptional.isEmpty()) {
            throw new RuntimeException("Category is not existed");
        }

        return mapper.toCategoryResponse(categoryOptional.get());
    }

    public CategoryResponse update(CategoryRequest request, String id) {
        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        mapper.updateCategoryFromRequest(request, existingCategory);

        Category updatedCategory = repository.save(existingCategory);

        return mapper.toCategoryResponse(updatedCategory);
    }


}
