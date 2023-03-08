package com.javawwa25.app.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ResourceUtils;
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
	public String uploadImage(@RequestParam("type") String type, MultipartFile file) throws IOException {
		FileData fileData = fileService.save(file);
		if (type.equals("avatar")) {
			userService.updateAvatar(fileData);
		}
		return "redirect:/user";
	}

	@GetMapping("/avatar")
	public void getPhoto(HttpServletResponse resp) throws IOException {
		FileData dbFile = fileService.getAvatar();
		setResponseFile(resp, dbFile);
	}

	private void setResponseFile(HttpServletResponse resp, FileData dbFile) {
		byte[] byteArray;
		try (OutputStream os = resp.getOutputStream()) {
			if (dbFile != null) {
				byteArray = dbFile.getData().getBinaryStream().readAllBytes();
				resp.setHeader("Content-Disposition", "attachment; filename=" + dbFile.getName());
			} else {
				InputStream is = new ClassPathResource("/static/images/avatar/default_avatar.jpg").getInputStream();
				byteArray = is.readAllBytes();
				is.close();
				resp.setHeader("Content-Disposition", "attachment; filename=" + "default_avatar.jpg");
			}
			resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
			resp.setContentLength(byteArray.length);
			os.write(byteArray, 0, byteArray.length);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}