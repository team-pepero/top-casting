package com.ll.topcastingbe.domain.item.entity;

import com.ll.topcastingbe.domain.category.entity.MainCategory;
import com.ll.topcastingbe.domain.category.entity.SubCategory;
import com.ll.topcastingbe.domain.image.entity.DetailedImage;
import com.ll.topcastingbe.domain.image.entity.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

	private String filename; //이미지 파일 이름
	private String filepath; //이미지 파일 경로

	@ManyToOne(fetch = FetchType.LAZY)
	private MainCategory mainCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	private SubCategory subCategory;

	public void setFilename(String fileName) {
		return;
	}

	public void setFilepath(String filepath) {
		return;
	}

	public void setItemName(String itemName) {
	}

	public void setItemPrice(BigDecimal itemPrice) {
	}
}
