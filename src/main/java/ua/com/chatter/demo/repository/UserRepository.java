package ua.com.chatter.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.chatter.demo.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

}
