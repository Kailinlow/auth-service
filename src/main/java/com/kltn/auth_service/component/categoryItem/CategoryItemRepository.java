package com.kltn.auth_service.component.categoryItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, String> {
    List<CategoryItem> findByCategory_Id(String categoryId);
    List<CategoryItem> findByProduct_Id(String productId);
}
