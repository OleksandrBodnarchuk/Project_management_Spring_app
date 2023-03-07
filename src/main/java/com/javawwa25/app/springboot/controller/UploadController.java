package com.javawwa25.app.springboot.controller;

import java.io.IOException;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/file")
@Secured({ "ADMIN", "USER" })
@RequiredArgsConstructor
public class UploadController {

	@PostMapping("/upload")
	public String uploadImage(MultipartFile file) throws IOException {
		// TODO: save file
		return "redirect:/user";
	}
	
	@GetMapping("/photo")
	public void getPhoto( HttpServletResponse resp) throws IOException {
		/* TODO: get photo via OcetStream
		 * https://stackoverflow.com/questions/63866765/display-files-pdf-doc-ppt-from-mysql-to-thymeleaf-with-spring-boot
		 */
	}
}