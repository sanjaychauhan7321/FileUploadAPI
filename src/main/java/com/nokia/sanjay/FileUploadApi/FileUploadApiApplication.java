package com.nokia.sanjay.FileUploadApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
@RestController
public class FileUploadApiApplication extends SpringBootServletInitializer {

	// By making required true Spring loads the bean at application startup
	@Autowired(required = true)
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(FileUploadApiApplication.class, args);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/uploadFile", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam(name = "file") MultipartFile uploadedFile,
			HttpServletRequest request) {

		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>("File Uploaded Successfully", HttpStatus.OK);

		new File(System.getProperty("user.dir") + "//Uploads//").mkdirs();
		File newFile = new File(
				System.getProperty("user.dir") + "//Uploads//" + String.valueOf(LocalDate.now()).replaceAll("-", "_")
						+ "_" + String.valueOf(LocalTime.now()).replaceAll("[:]", ".") + "__"
						+ request.getSession().getId() + "__" + uploadedFile.getOriginalFilename());

		System.out.println("Session id : " + request.getSession().getId());

		System.out.println("value of the property : " + env.getProperty("sanjay"));
		try {
			newFile.createNewFile();
			System.out.println("file created!");
		} catch (IOException e) {

			responseEntity = new ResponseEntity<Object>(
					"Uhh ow! Issue occured while new file creation. Reason : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

		System.out.println();
		System.out.println("-------- Information about Upload --------------------");
		System.out.println("Upload Date and time : " + LocalDate.now() + " " + LocalTime.now());
		System.out.println("Original file name : " + uploadedFile.getOriginalFilename());
		System.out.println("Content-type : " + uploadedFile.getContentType());
		System.out.println("File size is : " + uploadedFile.getSize() + " bytes");
		System.out.println("Uploaded file location : " + newFile.getAbsolutePath());
		System.out.println("------------------------------------------------------");
		System.out.println();

		FileOutputStream fileOutputStreamForNewFile;
		try {
			fileOutputStreamForNewFile = new FileOutputStream(newFile);
			try {
				fileOutputStreamForNewFile.write(uploadedFile.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fileOutputStreamForNewFile.close();
				fileOutputStreamForNewFile.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Invalidate session for new session id
		request.getSession().invalidate();

		return responseEntity;
	}

}
