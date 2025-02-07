package com.kltn.auth_service.component.product;

import com.kltn.auth_service.component.brand.Brand;
import com.kltn.auth_service.component.categoryItem.CategoryItem;
import com.kltn.auth_service.component.productAttributes.ProductAttributes;
import com.kltn.auth_service.component.review.Review;
import com.kltn.auth_service.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    private String sku;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "marketPrice")
    private BigDecimal marketPrice;

    @Column(name = "stock_quantity")
    private Long stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItemList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductAttributes> productAttributesList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviewList;
}
