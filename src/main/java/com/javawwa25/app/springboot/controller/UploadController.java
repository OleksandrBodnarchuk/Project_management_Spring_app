package com.javawwa25.app.springboot.controller;

import java.io.IOException;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javawwa25.app.springboot.file.FileData;
import com.javawwa25.app.springboot.file.service.FileService;
import com.javawwa25.app.springboot.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/file")
@Secured({ "ADMIN", "USER" })
@RequiredArgsConstructor
public class UploadController {
	
	private final FileService fileService;
	private final UserService userService;
	
	@PostMapping("/upload")
	public String uploadImage(@RequestParam("type")String type, MultipartFile file) throws IOException {
		FileData fileData = fileService.save(file);
		if(type.equals("avatar")) {
			userService.updateAvatar(fileData);
		}
		return "redirect:/user";
	}
	
	@GetMapping("/photo")
	public void getPhoto( HttpServletResponse resp) throws IOException {
		/* TODO: get photo via OcetStream
		 * https://stackoverflow.com/questions/63866765/display-files-pdf-doc-ppt-from-mysql-to-thymeleaf-with-spring-boot
		 */
	}
}