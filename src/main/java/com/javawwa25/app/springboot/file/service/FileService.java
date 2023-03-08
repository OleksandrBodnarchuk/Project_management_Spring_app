package com.javawwa25.app.springboot.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import com.javawwa25.app.springboot.file.FileData;
import com.javawwa25.app.springboot.user.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final UserService userService;

	@PersistenceContext
	private final EntityManager entityManager;

	@Value("${avatar.default.location}")
	private String defaultAvatarUri;
	
	@Transactional
	public FileData save(String type, MultipartFile file) {
		FileData fileData = new FileData();
		try {
			Session session = entityManager.unwrap(SessionImplementor.class);
			LobHelper lobHelper = session.getLobHelper();
			Blob blob = lobHelper.createBlob(file.getBytes());
			fileData.setName(file.getOriginalFilename());
			fileData.setType(file.getContentType());
			fileData.setData(blob);
			entityManager.persist(fileData);
			entityManager.flush();
		} catch (IOException e) {
			// TODO: personal exception
			e.printStackTrace();
		}
		
		if (type.equals("avatar")) {
			userService.updateAvatar(fileData);
		}
		
		return fileData;
	}

	public FileData getAvatar() {
		FileData photo = userService.getLoggedUser().getAccount().getPhoto();
		if (photo != null) {
			return photo;
		}
		return null;
	}

	public void setResponseFile(HttpServletResponse resp, FileData dbFile) {
		byte[] byteArray;
		try (OutputStream os = resp.getOutputStream()) {
			if (dbFile != null) {
				byteArray = dbFile.getData().getBinaryStream().readAllBytes();
				resp.setHeader("Content-Disposition", "attachment; filename=" + dbFile.getName());
			} else {
				InputStream is = new ClassPathResource(defaultAvatarUri).getInputStream();
				byteArray = is.readAllBytes();
				is.close();
				resp.setHeader("Content-Disposition", "attachment; filename=" + "default_avatar.jpg");
			}
			resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
			resp.setContentLength(byteArray.length);
			os.write(byteArray, 0, byteArray.length);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

	}

}
