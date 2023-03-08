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

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/file")
@Secured({ "ADMIN", "USER" })
@RequiredArgsConstructor
public class UploadController {

	private final FileService fileService;

	@PostMapping("/upload")
	public String uploadImage(@RequestParam("type") String type, MultipartFile file) throws IOException {
		fileService.save(type, file);
		return "redirect:/user";
	}

	@GetMapping("/avatar")
	public void getPhoto(HttpServletResponse resp) throws IOException {
		FileData dbFile = fileService.getAvatar();
		fileService.setResponseFile(resp, dbFile);
	}

}