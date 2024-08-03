package ua.com.chatter.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.model.dto.ErrorResponse;
import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.model.dto.PageWrapper;
import ua.com.chatter.demo.model.dto.chat.ChatDTO;
import ua.com.chatter.demo.model.dto.request.ChatCreateRequest;
import ua.com.chatter.demo.service.ChatService;
import ua.com.chatter.demo.utils.exceptions.ExceptionUtils;

@RestController
@RequestMapping("/api/chats")
public class ChatsController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/empty")
    public ResponseEntity<?> createEmptyChat(@RequestParam Long userId) {
        try {
            ChatDTO chat = chatService.createEmptyChat(userId, new ChatDTO());
            return ResponseEntity.ok(chat);
        } catch (RuntimeException exc) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, exc.getMessage(), ExceptionUtils.getErrorType(exc, ErrorType.CREATE_EMPTY_CHAT)));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChat(@RequestBody ChatCreateRequest chatParams) {
        try {
            ChatDTO chat = chatService.createChat(chatParams);
            return ResponseEntity.ok(chat);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage(), ExceptionUtils.getErrorType(e, ErrorType.CREATE_CHAT)));
        }
    }

    @GetMapping
    public ResponseEntity<?> getChats(@RequestParam Long userId, int page) {
        try {
            Page<ChatDTO> chatsPage = chatService.getChats(userId, page);
            PageWrapper<ChatDTO> chatWrapper = new PageWrapper<>(chatsPage.getContent(), page, chatsPage.getTotalPages());

            return ResponseEntity.ok(chatWrapper);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage(), ExceptionUtils.getErrorType(e, ErrorType.GET_CHATS)));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteChat(@RequestParam Long chatId) {
        try {
            chatService.deleteChat(chatId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage(), ExceptionUtils.getErrorType(e, ErrorType.DELETE_CHAT)));
        }
    }

}
