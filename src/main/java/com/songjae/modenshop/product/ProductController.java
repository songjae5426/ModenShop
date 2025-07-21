package com.songjae.modenshop.product;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.songjae.modenshop.product.domain.Product;
import com.songjae.modenshop.product.dto.ProductCategoryPageDto;
import com.songjae.modenshop.product.dto.ProductDetailPageDto;
import com.songjae.modenshop.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/moden-shop")
@Controller
public class ProductController {
	private final ProductService productService;

	// 카테고리 페이지 
	@GetMapping("/category")
	public String showCategoryPage(
			@RequestParam("productCategory") Product.ProductCategory productCategory,
			@PageableDefault(page = 0, size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		ProductCategoryPageDto ProductCategoryPageDto = productService.getProductCategoryPageDtoByCategory(productCategory, pageable);
		model.addAttribute("pageType", "category");
		model.addAttribute("productCategoryPageDto", ProductCategoryPageDto);
		return "pages/basic/category";
	}
	
	@GetMapping("/detail")
	public String showDetailPage(
			Model model,
			@RequestParam("productId") long productId) {
		ProductDetailPageDto productDetailPageDto = productService.getProductDetailPageDto(productId);
		model.addAttribute("productDetailPageDto", productDetailPageDto);
		model.addAttribute("pageType", "detail");
		return "pages/basic/detailPage";
	}
	
	
	
}
