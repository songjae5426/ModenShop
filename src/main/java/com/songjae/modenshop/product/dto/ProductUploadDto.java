package com.songjae.modenshop.product.dto;


import org.springframework.web.multipart.MultipartFile;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductUploadDto {
	private String summerNoteContentHtml;
	private String productName;
	private String productCategory;
	private int productRegularPrice;
	private int productSalesPrice;
	private int productDeliveryCost;
	private int sSizeCount;
	private int mSizeCount;
	private int lSizeCount;
	private MultipartFile productMainImgFile;
}
