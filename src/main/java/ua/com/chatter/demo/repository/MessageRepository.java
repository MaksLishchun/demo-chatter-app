package ua.com.chatter.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.chatter.demo.model.ChatterMessageEntity;

public interface  MessageRepository extends JpaRepository<ChatterMessageEntity, Long> {

}
