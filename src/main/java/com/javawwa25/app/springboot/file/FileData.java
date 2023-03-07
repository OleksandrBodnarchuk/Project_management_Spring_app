package com.javawwa25.app.springboot.file;

import java.sql.Blob;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileData extends BaseEntity {

	private String name;

	private String type;

	@Lob
	private Blob data;
}
