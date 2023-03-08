package com.javawwa25.app.springboot.file.service;

import java.io.IOException;
import java.sql.Blob;

import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javawwa25.app.springboot.file.FileData;
import com.javawwa25.app.springboot.user.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final UserService userService; 
	
	@PersistenceContext
	private final EntityManager entityManager;

	@Transactional
	public FileData save(MultipartFile file) {
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

		return fileData;
	}

	public FileData getAvatar() {
		FileData photo = userService.getLoggedUser().getAccount().getPhoto();
		if(photo!=null) {
			return photo;
		}
		return null;
	}

}
