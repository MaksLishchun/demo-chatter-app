package ua.com.chatter.demo.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import ua.com.chatter.demo.model.entity.MessageEntity;

public interface MessagesRepository extends JpaRepository<MessageEntity, Long> {

    Optional<Page<MessageEntity>> findByChatId(Long chatId, Pageable pageable);

    Optional<Page<MessageEntity>> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM MessageEntity m WHERE m.messageId = :messageId")
    void deleteByMessageId(@Param("messageId") Long messageId);

}
