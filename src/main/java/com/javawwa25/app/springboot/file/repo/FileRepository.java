package com.javawwa25.app.springboot.file.repo;

import org.springframework.data.repository.CrudRepository;

import com.javawwa25.app.springboot.file.FileData;

public interface FileRepository extends CrudRepository<FileData, Long>{

}
