package com.songjae.modenshop.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.songjae.modenshop.product.domain.Product;
import com.songjae.modenshop.product.dto.ProductCategoryPageItemDto;
import com.songjae.modenshop.product.dto.ProductManageListItemDto;
import com.songjae.modenshop.product.dto.ProductManageListResponseDto;
import com.songjae.modenshop.product.dto.ProductManageListSearchResultResponseDto;
import com.songjae.modenshop.product.dto.ProductManageModifyExistingDataDto;
import com.songjae.modenshop.product.dto.ProductMangerCardDto;
import com.songjae.modenshop.product.dto.ProductModifyRequestDto;
import com.songjae.modenshop.product.dto.ProductUploadDto;
import com.songjae.modenshop.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/moden-shop")
@RestController
public class ProductRestController {
	private final ProductService productService;
	
	
	// 임시 폴더에 이미지 추가
	@PostMapping("/manager/summer-note/img/temp/upload/session/check")
	public Map<String, String> summerNoteImgUpload(@RequestParam("imgFile") MultipartFile imgFile){
		Map<String, String> resultMap = new HashMap<>();
		String url = productService.saveTempSummerNoteImg(imgFile);
		resultMap.put("url", url);
		return resultMap;
	}
	
	
	
	// 제품 추가
	@PostMapping("/manager/product/upload/session/check")
	public Map<String, Object> addProduct(@ModelAttribute ProductUploadDto productUploadDto){
		Map<String, Object> resultMap = new HashMap<>();
		boolean result = productService.addProduct(productUploadDto);
		resultMap.put("success", result);
		return resultMap;
	}
	
	// 상품 리스트 가져오기(관리자)
	@GetMapping("/manager/product/list/session/check")
	public ProductManageListResponseDto getProductManageListResponseDto(){
		List<ProductManageListItemDto> productManageListItemDtoList = productService.getAllProductManageListDto();
		boolean isEmptyList = productManageListItemDtoList.isEmpty();
		return ProductManageListResponseDto.builder().isEmptyList(isEmptyList)
				.productManageListItemDtoList(productManageListItemDtoList).build();
	}
	
	// 제품 검색(관리자)
	@GetMapping("/manager/product/search/session/check")
	public ProductManageListSearchResultResponseDto getProductManageListSearchResultResponseDto(@RequestParam("productName") String productName) {
		List<ProductManageListItemDto> productManageListItemDtoList = productService.getProductManageListSearchResult(productName);
		boolean searchRrsultEmpty =productManageListItemDtoList.isEmpty();
		return ProductManageListSearchResultResponseDto.builder()
				.serachResultEmpty(searchRrsultEmpty)
				.productManageListItemDtoList(productManageListItemDtoList)
				.build();
	}
	
	// 관리자 페이지 제품정보카드(상품정보)
	@GetMapping("/manager/product/card/info/session/check")
	public ProductMangerCardDto getProductMangerCardDto(@RequestParam("productId") long productId){
		ProductMangerCardDto productMangerCardDto = productService.getProductMangerCardDto(productId);
		return productMangerCardDto;
	}

	// 카테고리 페이지 제품 리스트 무한스크롤(카테고리)
	@GetMapping("/category/product/page-list")
	public List<ProductCategoryPageItemDto> getProductCategoryPageItemDtoList(
			@RequestParam("productCategory") Product.ProductCategory productCategory,
			@PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		return productService.getProductCategoryPageItemDtoList(productCategory, pageable);
	}
	
	// 제품 수정 수정전 내용(관리자)
	@GetMapping("/manager/product/modify/info/session/check")
	public ProductManageModifyExistingDataDto getProductManageModifyExistingDataDto(@RequestParam("productId") long productId) {
		ProductManageModifyExistingDataDto productManageModifyExistingDataDto = productService.getProductManageModifyExistingDataDto(productId);
		return productManageModifyExistingDataDto;
	}
	
	// 제품 수정
	@PostMapping("/manager/product/modify/session/check")
	public Map<String, Boolean> modifyProduct(@ModelAttribute ProductModifyRequestDto productModifyUploadDto){
		boolean modifySuccess = productService.modifyProduct(productModifyUploadDto);
		Map<String, Boolean> resultMap = new HashMap<>();
		resultMap.put("success", modifySuccess);
		return resultMap;
	}
	
	// 제품 삭제
	@GetMapping("/manager/product/delete/session/check")
	public Map<String, Boolean> deleteProduct(@RequestParam("productId") long productId){
		boolean success = productService.deleteProduct(productId);
		Map<String, Boolean> resultMap = new HashMap<>();
		resultMap.put("success", success);
		return resultMap;
	}
	
	
	
	
}
