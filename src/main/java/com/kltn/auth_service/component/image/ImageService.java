package com.kltn.auth_service.component.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;

    public List<Image> findByProductId(String id){
        List<Image> imageList = repository.findByProductId(id);

        return imageList;
    }
}
