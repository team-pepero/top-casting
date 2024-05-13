package com.ll.topcastingbe.domain.image.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Builder;

@Entity
@DiscriminatorValue("MAIN")
public class MainImage extends Image{
    public MainImage() {
    }

    @Builder
    public MainImage(Long id, String path, String imageName, String fullName, LocalDateTime createdDate) {
        super(id, path, imageName, fullName, createdDate);
    }
}
