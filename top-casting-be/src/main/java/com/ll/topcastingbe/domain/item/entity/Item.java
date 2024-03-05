package com.ll.topcastingbe.domain.item.domain;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

import com.ll.topcastingbe.domain.category.entity.MainCategory;
import com.ll.topcastingbe.domain.category.entity.SubCategory;
import com.ll.topcastingbe.domain.image.entity.DetailedImage;
import com.ll.topcastingbe.domain.image.entity.Image;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String itemName;
	private BigDecimal itemPrice;

	@OneToOne(fetch = FetchType.LAZY)
	private Image image;
	@OneToOne(fetch = FetchType.LAZY)
	private DetailedImage detailedImage;
	@ManyToOne(fetch = FetchType.LAZY)
	private MainCategory mainCategory;
	@ManyToOne(fetch = FetchType.LAZY)
	private SubCategory subCategory;

}
