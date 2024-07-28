package ua.com.chatter.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.chatter.demo.model.ChatterUser;

public interface UserRepository extends JpaRepository<ChatterUser, Long> {
    Optional<ChatterUser> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}
