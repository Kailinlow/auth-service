package com.kltn.auth_service.component.brand;

import com.kltn.auth_service.component.brand.dto.request.BrandRequest;
import com.kltn.auth_service.component.brand.dto.response.BrandResponse;
import com.kltn.auth_service.component.brand.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository repository;
    private final BrandMapper mapper;

    public BrandResponse create(BrandRequest request) {
        Brand brand = mapper.toBrand(request);

        repository.save(brand);

        return mapper.toBrandResponse(brand);
    }

    public List<BrandResponse> findAll() {
        List<Brand> brandList = repository.findAll();

        return mapper.toResponses(brandList);
    }

    public BrandResponse findById(String id) {
        Brand existingBrand = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand is not existed"));

        return mapper.toBrandResponse(existingBrand);
    }

    public BrandResponse update(BrandRequest request, String id) {
        Brand existingBrand = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Brand is not existed"));

        mapper.updateBrandFromRequest(request, existingBrand);

        Brand brand = repository.save(existingBrand);

        return mapper.toBrandResponse(brand);
    }

    public void delete(String id) {
        Brand existingBrand = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Brand is not existed"));

        repository.delete(existingBrand);
    }
}
