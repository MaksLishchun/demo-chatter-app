package ua.com.chatter.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.chatter.demo.model.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
