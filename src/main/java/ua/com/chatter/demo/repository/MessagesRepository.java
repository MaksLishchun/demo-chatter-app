package ua.com.chatter.demo.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.chatter.demo.model.entity.ChatterMessageEntity;

public interface MessagesRepository extends JpaRepository<ChatterMessageEntity, Long> {

    Optional<Page<ChatterMessageEntity>> findByOwnerIdAndRecipientId(Long ownerId, Long recipientId, Pageable pageable);
}
