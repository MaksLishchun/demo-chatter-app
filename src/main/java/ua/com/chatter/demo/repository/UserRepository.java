package ua.com.chatter.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.chatter.demo.model.ChatterUser;

public interface UserRepository extends JpaRepository<ChatterUser, Long> {
}
