package com.songjae.modenshop.common.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;

public class FileManager {

	private static final String PRODUCT_IMG_FILE_UPLOAD_PATH = "C:\\modenShop\\product";

	// -----------------------제품 폴더 생성-------------------------------
	public static boolean createProductDirectory(long productId) {
		String[] subDirs = { "", "mainImg", "summerNoteImg" };

		for (String sub : subDirs) {
			// paths.get(가변인자) => get()메소드에 절대 경로 문자열을 넣어주면 Path객체를 만들어주는데 가변인자라 여러개의 경로
			// 문자열을
			// 넣어주면 os \/를 구분해서 이어 붙여줌
			Path dirPath = Paths.get(PRODUCT_IMG_FILE_UPLOAD_PATH, String.valueOf(productId));
			// Path객체.resolve()는 인자로 문자열 또는 Path객체를 받아 Path객체에 이어 붙여준다 (빈 문자열일 떄는 아무것도 붙이지
			// 않는다)
			dirPath = dirPath.resolve(sub);

			try {
				// Files.createDirectories(Path객체) => Path객체를 인자로 받아 폴더를 생성해준다
				// 해당 폴더가 이미 있는 경우 아무것도 안함, 없는 경우 생성(만약 상위 디렉토리도 없는경우 상위 디렉토리까지 생성)
				Files.createDirectories(dirPath);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	// ------------------------------메인 이미지 사진
	// 저장---------------------------------------
	public static String saveProductMainImg(long productId, MultipartFile file) {
		String fileName = file.getOriginalFilename();
		Path filePath = Paths.get(PRODUCT_IMG_FILE_UPLOAD_PATH, String.valueOf(productId), "mainImg", fileName);
		try {
			byte[] fileBytes = file.getBytes();
			saveFile(filePath, fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		// 클라이언트에서 사용할 url경로 설정 한다 => 내맘대로 지정해도됨 => 파일명 뺴고
		return "/product/" + productId + "/mainImg/" + fileName;
	}
	
	// 메인 이미지 폴더안에 내용물 삭제
	public static boolean deleteProductMainImg(long productId) {
		Path dirPath = Paths.get(PRODUCT_IMG_FILE_UPLOAD_PATH, String.valueOf(productId), "mainImg");
		try {
	        Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
	            @Override
	            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	                Files.delete(file);
	                return FileVisitResult.CONTINUE;
	            }
	            @Override
	            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
	                Files.delete(dir);
	                return FileVisitResult.CONTINUE;
	            }
	        });
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace(); 
	        return false;
	    }
	}
	
	// 모두 삭제
	public static boolean deleteProductAllImg(long productId) {
	    Path dirPath = Paths.get(PRODUCT_IMG_FILE_UPLOAD_PATH, String.valueOf(productId));
	    try {
	        Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
	            @Override
	            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	                Files.delete(file);
	                return FileVisitResult.CONTINUE;
	            }

	            @Override
	            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
	                Files.delete(dir);
	                return FileVisitResult.CONTINUE;
	            }
	        });
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace(); 
	        return false;
	    }
	}

	// ---------- 썸머노트 이미지 임시 폴더에 저장 -----------------------
	public static String saveTempSummerNoteImg(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		Path filePath = Paths.get(PRODUCT_IMG_FILE_UPLOAD_PATH, "summernote_Img_temp", fileName);
		try {
			byte[] fileBytes = file.getBytes();
			saveFile(filePath, fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return "/product" + "/summernote_Img_temp/" + fileName;
	}

	private static boolean saveFile(Path filePath, byte[] bytes) {
		try {
			// 파일을 담는 폴더 생성(있으면 아무것도X, 없으면 생성(상위디렉토리도 없으면 같이 생성해줌)
			// getParent()는 경로에서 /기준으로 마지막을 제거한 경로를 반환한다 즉, 여기서는 파일을 담는 폴더 경로를 반환함
			Files.createDirectories(filePath.getParent());
			Files.write(filePath, bytes); // 파일 저장
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	// ----------------------summerNote임시폴더 에서 사용자가 확정한 이미지 파일을 제회한 파일
	// 삭제-----------------
	public static boolean removeTmepSummerNoteImgExceptCommitImg(String html) {
		// Jsoup으로 html을 파싱해서 파일 이름만 뽑아내 set에 저장
		Set<String> fileNameSet = new HashSet<>();
		Document document = Jsoup.parse(html);
		Elements imgTags = document.select("img");
		for (Element img : imgTags) {
			String src = img.attr("src");
			String[] temp = src.split("/");
			int lastIndex = temp.length - 1;
			fileNameSet.add(temp[lastIndex]);
		}
		String dirPath = PRODUCT_IMG_FILE_UPLOAD_PATH + "/summernote_Img_temp";
		Path dir = Paths.get(dirPath);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path filePath : stream) { // 지정한 해당 폴더를 바로 아래 항목들만 순회
				if (Files.isRegularFile(filePath)) { // 경로가 일반 파일인지 판단(일반 파일이면 true)
					String name = filePath.getFileName().toString(); // 주어진 경로에서 파일 이름 가져오기
					if (!fileNameSet.contains(name)) { // 해당 파일이 set에 포함되어있지 않으면
						Files.delete(filePath); // 해당 파일 삭제
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// ----------------------제품폴더의 summerNote폴더에서 사용자가 확정한 이미지 파일을 제회한 파일
	// 삭제-----------------
	public static boolean removeProductSummerNoteImgExceptCommitImg(String html, long productNumber) {
		// Jsoup으로 html을 파싱해서 파일 이름만 뽑아내 set에 저장
		Set<String> fileNameSet = new HashSet<>();
		Document document = Jsoup.parse(html);
		Elements imgTags = document.select("img");
		for (Element img : imgTags) {
			String src = img.attr("src");
			String[] temp = src.split("/");
			int lastIndex = temp.length - 1;
			fileNameSet.add(temp[lastIndex]);
		}
		Path dir = Paths.get(PRODUCT_IMG_FILE_UPLOAD_PATH, String.valueOf(productNumber), "summerNoteImg");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path filePath : stream) { // 지정한 해당 폴더를 바로 아래 항목들만 순회
				if (Files.isRegularFile(filePath)) { // 경로가 일반 파일인지 판단(일반 파일이면 true)
					String name = filePath.getFileName().toString(); // 주어진 경로에서 파일 이름 가져오기
					if (!fileNameSet.contains(name)) { // 해당 파일이 set에 포함되어있지 않으면
						Files.delete(filePath); // 해당 파일 삭제
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// -----------------------임시 폴더에 있는 summerNote이미지 파일들을 제품 폴더로 옮기면서 src url
	// 변경-------------
	public static String moveAndUrlChangeSummerNoteImg(long productNumber, String html) {
		String tempSummernoteImgDir = PRODUCT_IMG_FILE_UPLOAD_PATH + "/summernote_Img_temp";
		String targetDir = PRODUCT_IMG_FILE_UPLOAD_PATH + "/" + productNumber + "/summerNoteImg";
		String newUrl = "/product/" + productNumber + "/summerNoteImg/";
		Path summernoteImgTempDirPath = Paths.get(tempSummernoteImgDir);
		Path targetDirPath = Paths.get(targetDir);

		Document doc = Jsoup.parse(html);
		Elements imgTags = doc.select("img");
		for (Element img : imgTags) {
			String src = img.attr("src");
			String fileName = Paths.get(src).getFileName().toString();
			// Path객체.resolve(Path객체) => 두개의 Path를 이어 붙어준다 => 그냥 경로 문자열 두개를 이어붙인것 같은 결과가
			// 나오지만 os별로 \/를 구분해서 붙여줘서 안전하다
			Path tempSummernoteImgFilePath = summernoteImgTempDirPath.resolve(fileName);
			Path targetFilePath = targetDirPath.resolve(fileName);

			try {
				// createDirectories(Path객체) => 해당 위치에 디렉토리가 있으면 아무것도X, 없으면 생성(만약 상위 폴더도 없으면 상위
				// 폴더 까지 생성 해줌)
				Files.createDirectories(targetDirPath);
				if (Files.exists(tempSummernoteImgFilePath)) {	// Path객체를 인자로 받아 파일또는 폴더가 존재하는지
					// 파일 이동 (옮길 파일Path객체, 목적지 Path_파일 명까지 포함해서, 옵션)
					// StandardCopyOption.REPLACE_EXISTING => 목적지에 같은 파일이 있으면 덮이씌움
					Files.move(tempSummernoteImgFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
					img.attr("src", newUrl + fileName); // 새로운 url 삽입
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return doc.body().html(); // 변경한 html문자열 반환
	}

}
