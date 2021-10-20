package com.ebeauty;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.apache.commons.io.IOUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File.*;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import java.nio.channels.Channels;
import java.nio.file.Files;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;



@Service
public class FirebaseInitializer implements StorageStrategy {
	
	 private StorageOptions storageOptions;
	 private String bucketName = "ebeauty-csis4495.appspot.com";
	 private String projectId = "ebeauty-csis4495";
	
	
	 @PostConstruct
	    public void initialize() {
	        try {
	            FileInputStream serviceAccount =
	                    new FileInputStream("C:\\Users\\Sphurti\\Downloads\\firebase.json");

	            this.storageOptions = StorageOptions.newBuilder()
	            		.setProjectId("ebeauty-csis4495")
	                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                    .build();

	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.print("Excepton in Ini .");
	        }
	        System.out.print("Here in ini.");

	    }
	
	@Override
	public String[] uploadFile(MultipartFile multipartFile) throws Exception {
		 System.out.println("bucket name====" + bucketName);
	        File file = convertMultiPartToFile(multipartFile);
	        Path filePath = file.toPath();
	        String objectName = generateFileName(multipartFile);
	        System.out.print("Here in upload.");
	        Storage storage = storageOptions.getService();

	        BlobId blobId = BlobId.of(bucketName, objectName);
	        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
	        Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));

	        
	        return new String[]{"fileUrl", objectName};
	}
	
	 private String generateFileName(MultipartFile multiPart) {
	        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
	    }

	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }
	
	public ResponseEntity<Object> downloadFile(String fileName, HttpServletRequest request) throws Exception {
	        Storage storage = storageOptions.getService();

	        Blob blob = storage.get(BlobId.of(bucketName, fileName));
	        ReadChannel reader = blob.reader();
	        InputStream inputStream = Channels.newInputStream(reader);

	        byte[] content = null;
	        
	        content = IOUtils.toByteArray(inputStream);

	        final ByteArrayResource byteArrayResource = new ByteArrayResource(content);

	        return ResponseEntity
	                .ok()
	                .contentLength(content.length)
	                .header("Content-type", "application/octet-stream")
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
	                .body(byteArrayResource);

	    }
	
	


}
