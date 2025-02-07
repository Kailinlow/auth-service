package com.kltn.auth_service.component.product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kltn.auth_service.component.brand.Brand;
import com.kltn.auth_service.component.brand.BrandRepository;
import com.kltn.auth_service.component.product.Product;
import com.kltn.auth_service.component.product.ProductRepository;
import com.kltn.auth_service.component.product.dto.request.CreateProductRequest;
import com.kltn.auth_service.component.product.dto.request.UpdateProductRequest;
import com.kltn.auth_service.component.product.dto.response.ProductInfoResponse;
import com.kltn.auth_service.component.product.dto.response.ProductResponse;
import com.kltn.auth_service.component.product.dto.response.ProductShortResponse;
import com.kltn.auth_service.component.product.mapper.ProductMapper;
import com.kltn.auth_service.component.productAttributes.ProductAttributes;
import com.kltn.auth_service.component.productAttributes.dto.response.ProductAttributesResponse;
import com.kltn.auth_service.component.productAttributes.mapper.ProductAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final BrandRepository brandRepository;
    private final ProductAttributesMapper productAttributesMapper;

    public ProductResponse create(CreateProductRequest request) {
        Brand existingBrand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new RuntimeException("Brand is not existed"));

        Product product = mapper.toProduct(request);
        product.setBrand(existingBrand);

        productRepository.save(product);

        return mapper.toProductResponse(product);
    }

    public List<ProductResponse> findAll() {
        List<Product> productList = productRepository.findAll();

        return mapper.toResponses(productList);
    }

    public ProductInfoResponse findById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product isn't existed");
        }

        return toResponse(productOptional.get());
    }

    public ProductResponse updateProduct(UpdateProductRequest request, String id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product is not existed"));

        mapper.updateProductFromRequest(request, existingProduct);

        Product updatedProduct = productRepository.save(existingProduct);

        return mapper.toProductResponse(updatedProduct);
    }

    public ProductShortResponse getShortInformation(String id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not existed."));

        return mapper.toProductShortResponse(existingProduct);
    }

    private ProductInfoResponse toResponse(Product product) {

        List<ProductAttributes> productAttributesList = product.getProductAttributesList();

        List<ProductAttributesResponse> productAttributesResponseList = productAttributesMapper.toResponses(productAttributesList);

        ProductInfoResponse productInfoResponse = mapper.toProductInfoResponse(product);

        productInfoResponse.setProductAttributesList(productAttributesResponseList);

        return productInfoResponse;
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    public void createProductsFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file and map it to a list of CreateProductRequest
            List<CreateProductRequest> products = objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<List<CreateProductRequest>>() {}
            );

            // Loop through each product and create it using the create method
            for (CreateProductRequest request : products) {
                create(request);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + e.getMessage(), e);
        }
    }
    public void deleteProduct(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }
}
