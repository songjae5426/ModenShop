package com.songjae.modenshop.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.songjae.modenshop.common.util.FileManager;
import com.songjae.modenshop.product.domain.Product;
import com.songjae.modenshop.product.domain.Product.ProductCategory;
import com.songjae.modenshop.product.domain.ProductSizeQuantity;
import com.songjae.modenshop.product.domain.ProductSizeQuantity.Size;
import com.songjae.modenshop.product.dto.ProductCategoryPageDto;
import com.songjae.modenshop.product.dto.ProductCategoryPageItemDto;
import com.songjae.modenshop.product.dto.ProductDetailPageDto;
import com.songjae.modenshop.product.dto.ProductDetailPageItemDto;
import com.songjae.modenshop.product.dto.ProductManageCardItemDto;
import com.songjae.modenshop.product.dto.ProductManageListItemDto;
import com.songjae.modenshop.product.dto.ProductManageModifyExistingDataDto;
import com.songjae.modenshop.product.dto.ProductManageModifyExistingDataItemDto;
import com.songjae.modenshop.product.dto.ProductMangerCardDto;
import com.songjae.modenshop.product.dto.ProductModifyRequestDto;
import com.songjae.modenshop.product.dto.ProductSizeQuantityDetailPageItemDto;
import com.songjae.modenshop.product.dto.ProductSizeQuantityManageItemDto;
import com.songjae.modenshop.product.dto.ProductUploadDto;
import com.songjae.modenshop.product.repository.ProductRepositoryByJpa;
import com.songjae.modenshop.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepositoryByJpa productRepositoryByJpa;
	private final ProductSizeQuantityService productSizeQuantityService;
	private final ReviewService reviewService;


	// summerNoteImg 파일 임시 저장후 클라이언트 url반환
	public String saveTempSummerNoteImg(MultipartFile file) {
		return FileManager.saveTempSummerNoteImg(file);
	}

	// 제품 등록
	@Transactional
	public boolean addProduct(ProductUploadDto productUploadDto) {
		Product product = Product.builder()
				.productCategory(ProductCategory.valueOf(productUploadDto.getProductCategory()))
				.productName(productUploadDto.getProductName())
				.regularPrice(productUploadDto.getProductRegularPrice())
				.salesPrice(productUploadDto.getProductSalesPrice())
				.deliveryCost(productUploadDto.getProductDeliveryCost())
				.productMainImg("temp")
				.build();
		product = productRepositoryByJpa.save(product);
		if(product != null) {
			long productId = product.getId();
			// 썸머 노트 임시폴더에서 사용하지 않은 이미지들 제거
			if (FileManager.removeTmepSummerNoteImgExceptCommitImg(productUploadDto.getSummerNoteContentHtml())) {
				// 제품 폴더로 옮긴후 html의 이미지 url수정후 반환
				String newHtml = FileManager.moveAndUrlChangeSummerNoteImg(productId, productUploadDto.getSummerNoteContentHtml());
				if (newHtml != null) {
					// 메인이미지 업로드후 url받기
					String mainImgUrl = FileManager.saveProductMainImg(productId, productUploadDto.getProductMainImgFile());
					if (mainImgUrl != null) {
						product = product.toBuilder()
								.productMainImg(mainImgUrl)
								.productExplanationHtml(newHtml)
								.build();
						product = productRepositoryByJpa.save(product);
						if (product != null) {
							Size[] size = { Size.S, Size.M, Size.L };
							int[] sizeCount = { productUploadDto.getSSizeCount(), productUploadDto.getMSizeCount(), productUploadDto.getLSizeCount() };
							for (int i = 0; i < size.length; i++) {
								ProductSizeQuantity productSizeQuantity = ProductSizeQuantity.builder()
										.productId(productId)
										.size(size[i])
										.quantity(sizeCount[i])
										.build();
								productSizeQuantityService.addProductSizeQuantity(productSizeQuantity);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// 카테고리를 입력받아 해당 카테고리의 제품정보를 업데이트를 기준으로최신순으로 가져오기(카테고리)
	public ProductCategoryPageDto getProductCategoryPageDtoByCategory(
			Product.ProductCategory productCategory, Pageable pageable) {
		Page<ProductCategoryPageItemDto> productCategoryItemDtoPage = productRepositoryByJpa
				.findProductCategoryItemDtoPageByProductCategoryOrderByUpdatedAt(productCategory, pageable);
		List<ProductCategoryPageItemDto> productCategoryItemDtoList = productCategoryItemDtoPage.getContent();
		boolean hasProduct = !productCategoryItemDtoList.isEmpty();
		return ProductCategoryPageDto.builder()
				.productCategoryItemDtoList(productCategoryItemDtoList)
				.hasProduct(hasProduct)
				.category(productCategory.toString()).build();
	}

	// 무한 스크롤시에 카테고리 리스트 반환(카테고리)
	public List<ProductCategoryPageItemDto> getProductCategoryPageItemDtoList(Product.ProductCategory productCategory,
			Pageable pageable) {
		Page<ProductCategoryPageItemDto> productCategoryListItemDtoByPage = productRepositoryByJpa
				.findProductCategoryItemDtoPageByProductCategoryOrderByUpdatedAt(productCategory, pageable);
		return productCategoryListItemDtoByPage.getContent();
	}

	// 관리자 페이지 제품 검색 리스트 가져오기(관리자)
	public List<ProductManageListItemDto> getProductManageListSearchResult(String productName) {
		List<ProductManageListItemDto> productManageListItemDtoLsit = productRepositoryByJpa.findProductManageListItemDtoByProductName(productName);
		return productManageListItemDtoLsit;
	}

	// 모든 상품 리스트 가져오기(관리자)
	public List<ProductManageListItemDto> getAllProductManageListDto() {
		List<ProductManageListItemDto> productManageListItemDtoList = productRepositoryByJpa
				.findProductManageListItemDtoList();
		return productManageListItemDtoList;
	}

	// 제품id를 입력받아 해당 제품의 product와 ProductSizeQuantity 모든 정보 가져오기(상세페이지)
	@Transactional(readOnly = true)
	public ProductDetailPageDto getProductDetailPageDto(long productId) {
		ProductDetailPageItemDto productDetailPageItemDto = productRepositoryByJpa
				.findProductDetailPageItemDtoByProductId(productId);
		List<ProductSizeQuantityDetailPageItemDto> productSizeQuantityDetailPageItemDtoList = productSizeQuantityService
				.getProductSizeQuantityDetailPageItemDtoByProductId(productId);
		return ProductDetailPageDto.builder().productDetailPageItemDto(productDetailPageItemDto)
				.productSizeQuantityDetailPageItemDtoList(productSizeQuantityDetailPageItemDtoList).build();
	}

	// 관리자 페이지 제품정보카드(관리자)
	@Transactional(readOnly = true)
	public ProductMangerCardDto getProductMangerCardDto(long productId) {
		ProductManageCardItemDto productManageCardItemDto = productRepositoryByJpa
				.findProductManageCardItemDtoByProductId(productId);
		List<ProductSizeQuantityManageItemDto> productSizeQuantityManageItemDtos = productSizeQuantityService
				.getProductSizeQuantityManageItemDtoByProductId(productId);
		return ProductMangerCardDto.builder().productManageCardItemDto(productManageCardItemDto)
				.productSizeQuantityManageItemDtoList(productSizeQuantityManageItemDtos).build();
	}

	// 제품 정보 수정
	@Transactional
	public boolean modifyProduct(ProductModifyRequestDto productModifyRequestDto) {
		Optional<Product> optionalProdcut = productRepositoryByJpa.findById(productModifyRequestDto.getProductId());
		if (optionalProdcut.isPresent()) {
			Product product = optionalProdcut.get();
			long productId = product.getId();
			// 썸머 노트 임시폴더에서 사용하지 않은 이미지들 제거
			if (FileManager.removeTmepSummerNoteImgExceptCommitImg(productModifyRequestDto.getSummerNoteContentHtml())) {
				// 제품 폴더로 옮긴후 html의 이미지 url수정후 반환
				String newHtml = FileManager.moveAndUrlChangeSummerNoteImg(productId,
						productModifyRequestDto.getSummerNoteContentHtml());
				if (newHtml != null) {
					// 제품 폴더에서 사용자가 확정한 이미지 파일을 제외한 파일을 제거
					if (FileManager.removeProductSummerNoteImgExceptCommitImg(newHtml, productId)) {
						// 메인이미지를 수정했다면
						if (!(productModifyRequestDto.getProductMainImgFile().isEmpty())) {
							// 메인 이미지 폴더 비우기
							if (FileManager.deleteProductMainImg(productId)) {
								// 메인이미지 업로드후 url받기
								String mainImgUrl = FileManager.saveProductMainImg(productId,
										productModifyRequestDto.getProductMainImgFile());
								if (mainImgUrl != null) {
									product = product.toBuilder()
											.id(productModifyRequestDto.getProductId())
											.productName(productModifyRequestDto.getProductName())
											.productCategory(ProductCategory.valueOf(productModifyRequestDto.getProductCategory().toUpperCase()))
											.regularPrice(productModifyRequestDto.getProductRegularPrice())
											.salesPrice(productModifyRequestDto.getProductSalesPrice())
											.deliveryCost(productModifyRequestDto.getProductDeliveryCost())
											.productMainImg(mainImgUrl)
											.productExplanationHtml(newHtml)
											.build();
									product = productRepositoryByJpa.save(product); // 업데이트
									if (product != null) {
										Size[] size = { Size.S, Size.M, Size.L };
										int[] sizeCount = { productModifyRequestDto.getSSizeCount(),
												productModifyRequestDto.getMSizeCount(),
												productModifyRequestDto.getLSizeCount() };
										long[] productSizeQuantityId = {
												productModifyRequestDto.getProductSizeQuantitySId(),
												productModifyRequestDto.getProductSizeQuantityMId(),
												productModifyRequestDto.getProductSizeQuantityLId() };
										for (int i = 0; i < size.length; i++) {
											ProductSizeQuantity productSizeQuantity = ProductSizeQuantity.builder()
													.id(productSizeQuantityId[i]).productId(productId).size(size[i])
													.quantity(sizeCount[i]).build();
											productSizeQuantityService.updateProductSizeQuantity(productSizeQuantity); // 업데이트
										}
										return true;
									}
								}
							}
						} else {
							product = product.toBuilder()
									.id(productModifyRequestDto.getProductId())
									.productName(productModifyRequestDto.getProductName())
									.productCategory(ProductCategory.valueOf(productModifyRequestDto.getProductCategory().toUpperCase()))
									.regularPrice(productModifyRequestDto.getProductRegularPrice())
									.salesPrice(productModifyRequestDto.getProductSalesPrice())
									.deliveryCost(productModifyRequestDto.getProductDeliveryCost())
									.productExplanationHtml(newHtml)
									.build();
							product = productRepositoryByJpa.save(product); // 업데이트
							if (product != null) {
								Size[] size = { Size.S, Size.M, Size.L };
								int[] sizeCount = { productModifyRequestDto.getSSizeCount(),
										productModifyRequestDto.getMSizeCount(),
										productModifyRequestDto.getLSizeCount() };
								long[] productSizeQuantityId = { productModifyRequestDto.getProductSizeQuantitySId(),
										productModifyRequestDto.getProductSizeQuantityMId(),
										productModifyRequestDto.getProductSizeQuantityLId() };
								for (int i = 0; i < size.length; i++) {
									ProductSizeQuantity productSizeQuantity = ProductSizeQuantity.builder()
											.id(productSizeQuantityId[i]).productId(productId).size(size[i])
											.quantity(sizeCount[i]).build();
									productSizeQuantityService.updateProductSizeQuantity(productSizeQuantity); // 업데이트
								}
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	// 제품 정보 수정전 기존 정보 조회
	@Transactional(readOnly = true)
	public ProductManageModifyExistingDataDto getProductManageModifyExistingDataDto(long productId) {
		ProductManageModifyExistingDataItemDto productManageModifyExistingDataItemDto = productRepositoryByJpa
				.findProductManageModifyExistingDataItemDtoByProductId(productId);
		List<ProductSizeQuantityManageItemDto> productSizeQuantityManageItemDtos = productSizeQuantityService
				.getProductSizeQuantityManageItemDtoByProductId(productId);
		return ProductManageModifyExistingDataDto.builder().productManageModifyExistingDataItemDto(productManageModifyExistingDataItemDto)
				.productSizeQuantityManageItemDtoList(productSizeQuantityManageItemDtos).build();
	}
	
	// 제품 삭제
	@Transactional
	public boolean deleteProduct(long productId) {
		productRepositoryByJpa.deleteById(productId);
		boolean exists = productRepositoryByJpa.existsById(productId);
		if(!exists) {
			boolean sizeQuantityDeleteSuccess = productSizeQuantityService.deleteProductSizeQuantityByPorductId(productId);
			if(sizeQuantityDeleteSuccess) {
				if(reviewService.deleteReviewByProductId(productId)) {
					boolean fileDeleteSuccess = FileManager.deleteProductAllImg(productId);
					return fileDeleteSuccess;
				}
			}
		}
		return false;
		
	}

}
