package ua.com.chatter.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import ua.com.chatter.demo.model.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("SELECT c FROM ChatEntity c JOIN c.users u WHERE u.id = :userId")
    Optional<Page<ChatEntity>> findByUserId(Long userId, Pageable pageable);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END "
            + "FROM ChatEntity c "
            + "JOIN c.users u1 "
            + "JOIN c.users u2 "
            + "WHERE u1.id = :userId1 "
            + "AND u2.id = :userId2 "
            + "AND SIZE(c.users) = 2")
    boolean existsChatWithTwoUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatEntity c WHERE c.id = :chatId")
    void deleteByChatId(@Param("chatId") Long chatId);

}
