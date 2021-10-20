package com.ebeauty;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageStrategy {
	
	String[] uploadFile(MultipartFile multipartFile) throws Exception;

	ResponseEntity<Object> downloadFile(String fileName, HttpServletRequest request) throws Exception;

}
