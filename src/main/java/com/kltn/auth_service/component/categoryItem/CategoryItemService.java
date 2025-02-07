package com.kltn.auth_service.component.categoryItem;

import com.kltn.auth_service.component.category.Category;
import com.kltn.auth_service.component.category.CategoryRepository;
import com.kltn.auth_service.component.category.mapper.CategoryMapper;
import com.kltn.auth_service.component.categoryItem.dto.request.CategoryItemRequest;
import com.kltn.auth_service.component.categoryItem.dto.response.CategoryItemListResponse;
import com.kltn.auth_service.component.categoryItem.dto.response.CategoryItemResponse;
import com.kltn.auth_service.component.categoryItem.mapper.CategoryItemMapper;
import com.kltn.auth_service.component.product.Product;
import com.kltn.auth_service.component.product.ProductRepository;
import com.kltn.auth_service.component.product.dto.response.ProductResponse;
import com.kltn.auth_service.component.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryItemService {
    private final CategoryItemRepository repository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemMapper mapper;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    public CategoryItemResponse create(CategoryItemRequest request) {
        Product existingProduct = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product is not existed."));

        Category existingCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category is not existed."));

        CategoryItem categoryItem = new CategoryItem();
        categoryItem.setCategory(existingCategory);
        categoryItem.setProduct(existingProduct);

        repository.save(categoryItem);

        return toCategoryItemResponse(categoryItem);
    }

    public CategoryItemListResponse findByCategoryId(String categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category is not existed."));

        List<CategoryItem> categoryItemList = repository.findByCategory_Id(categoryId);

        CategoryItemListResponse categoryItemListResponse = new CategoryItemListResponse();

        List<ProductResponse> productResponsesList = new ArrayList<>();

        for (CategoryItem categoryItem : categoryItemList) {
            productResponsesList.add(productMapper.toProductResponse(categoryItem.getProduct()));
        }

        categoryItemListResponse.setCategory(categoryMapper.toCategoryResponse(existingCategory));
        categoryItemListResponse.setProductList(productResponsesList);

        return categoryItemListResponse;
    }


    private CategoryItemResponse toCategoryItemResponse(CategoryItem categoryItem) {
        CategoryItemResponse categoryItemResponse = new CategoryItemResponse();

        categoryItemResponse.setProduct(productMapper.toProductResponse(categoryItem.getProduct()));
        categoryItemResponse.setCategory(categoryMapper.toCategoryResponse(categoryItem.getCategory()));

        return categoryItemResponse;
    }
}
