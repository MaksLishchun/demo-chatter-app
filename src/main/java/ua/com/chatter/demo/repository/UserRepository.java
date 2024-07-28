package ua.com.chatter.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.chatter.demo.model.entity.ChatterUserEntity;

public interface UserRepository extends JpaRepository<ChatterUserEntity, Long> {
    Optional<ChatterUserEntity> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}
