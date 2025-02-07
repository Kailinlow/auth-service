package com.kltn.auth_service.component.productAttributes;

import com.kltn.auth_service.component.product.Product;
import com.kltn.auth_service.component.product.ProductRepository;
import com.kltn.auth_service.component.productAttributes.dto.request.CreateProductAttributesRequest;
import com.kltn.auth_service.component.productAttributes.dto.request.UpdateProductAttributesRequest;
import com.kltn.auth_service.component.productAttributes.dto.response.ProductAttributesResponse;
import com.kltn.auth_service.component.productAttributes.mapper.ProductAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributesService {
    private final ProductAttributesRepository repository;
    private final ProductAttributesMapper mapper;
    private final ProductRepository productRepository;

    public ProductAttributesResponse create(CreateProductAttributesRequest request) {
        Product existingProduct = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product is not existed."));

        ProductAttributes productAttributes = mapper.toProductAttributes(request);
        productAttributes.setProduct(existingProduct);

        repository.save(productAttributes);

        return mapper.toProductAttributesResponse(productAttributes);
    }

    public ProductAttributesResponse findById(String id) {
        ProductAttributes existingProductAttributes = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Attributes is not existed"));

        return mapper.toProductAttributesResponse(existingProductAttributes);
    }

    public List<ProductAttributesResponse> findAll() {
        List<ProductAttributes> productAttributesList = repository.findAll();

        return mapper.toResponses(productAttributesList);
    }

    public ProductAttributesResponse update(UpdateProductAttributesRequest request, String id) {
        ProductAttributes existingProductAttributes = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Attributes is not existed"));

        mapper.updateProductAttributesFromRequest(request, existingProductAttributes);

        ProductAttributes updatedProductAttributes = repository.save(existingProductAttributes);

        return mapper.toProductAttributesResponse(updatedProductAttributes);
    }
}
