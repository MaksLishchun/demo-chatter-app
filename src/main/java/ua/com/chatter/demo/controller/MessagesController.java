package ua.com.chatter.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.model.dto.ErrorResponse;
import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.model.dto.PageWrapper;
import ua.com.chatter.demo.model.dto.message.MessageDTO;
import ua.com.chatter.demo.service.MessagessService;
import ua.com.chatter.demo.utils.exceptions.ExceptionUtils;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagessService messagessService;

    @GetMapping
    public ResponseEntity<?> getMessages(@RequestParam Long chatId, @RequestParam int page) {
        try {
            Page<MessageDTO> elems = messagessService.getMessages(chatId, page);
            PageWrapper<MessageDTO> messagesWrapper = new PageWrapper<>(elems.getContent(), elems.getNumber(), elems.getTotalPages());

            return ResponseEntity.ok(messagesWrapper);
        } catch (RuntimeException exc) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, exc.getMessage(), ExceptionUtils.getErrorType(exc, ErrorType.GET_MESSAGES)));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessage(@RequestParam Long messageId) {
        try {
            messagessService.deleteMessage(messageId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException exc) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, exc.getMessage(), ExceptionUtils.getErrorType(exc, ErrorType.DELETE_MESSAGE)));
        }

    }

}
