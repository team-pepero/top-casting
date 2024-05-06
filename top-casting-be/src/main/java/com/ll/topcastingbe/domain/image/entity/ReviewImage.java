package com.ll.topcastingbe.domain.image.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("REVIEW")
@Getter
public class ReviewImage extends Image{

    public ReviewImage() {
    }

    @Builder
    public ReviewImage(Long id, String path, String imageName, String fullName, LocalDateTime createdDate) {
        super(id, path, imageName, fullName, createdDate);
    }
}
