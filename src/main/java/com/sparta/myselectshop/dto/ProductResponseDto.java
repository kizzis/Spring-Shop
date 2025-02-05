package com.sparta.myselectshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.ProductFolder;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
	private Long id;
	private String title;
	private String link;
	private String image;
	private int lprice;
	private int myprice;

	private List<FolderResponseDto> productFolders = new ArrayList<>();

	public ProductResponseDto(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.link = product.getLink();
		this.image = product.getImage();
		this.lprice = product.getLprice();
		this.myprice = product.getMyprice();
		//lazy loading + N : M
		for (ProductFolder productFolder : product.getProductFolders()) {
			productFolders.add(new FolderResponseDto(productFolder.getFolder()));
		}
	}
}
