package com.kltn.auth_service.component.brand.mapper;

import com.kltn.auth_service.component.brand.Brand;
import com.kltn.auth_service.component.brand.dto.request.BrandRequest;
import com.kltn.auth_service.component.brand.dto.response.BrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandRequest request);
    BrandResponse toBrandResponse(Brand brand);
    List<BrandResponse> toResponses(List<Brand> brandList);

    void updateBrandFromRequest(BrandRequest request, @MappingTarget Brand brand);
}
