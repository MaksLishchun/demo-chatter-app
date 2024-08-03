package ua.com.chatter.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import ua.com.chatter.demo.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.isActive = :isActive WHERE u.id = :userId")
    void chageUserActiveStatus(@Param("userId") Long userId, @Param("isActive") Boolean isActive);

}
