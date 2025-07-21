package com.songjae.modenshop.product.dto;

import org.springframework.web.multipart.MultipartFile;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductModifyRequestDto {
	private long productId;
	private String productName;
	private String productCategory;
	private int productRegularPrice;
	private int productSalesPrice;
	private int productDeliveryCost;
	private String summerNoteContentHtml;
	private MultipartFile productMainImgFile;
	
	private long productSizeQuantitySId;
	private long productSizeQuantityMId;
	private long productSizeQuantityLId;
	private int sSizeCount;
	private int mSizeCount;
	private int lSizeCount;
}
