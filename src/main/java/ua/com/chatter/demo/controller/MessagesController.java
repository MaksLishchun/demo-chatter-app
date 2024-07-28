package ua.com.chatter.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.model.dto.ChatterDefaultResponse;
import ua.com.chatter.demo.model.dto.ChatterMessageDTO;
import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.service.MessagessService;
import ua.com.chatter.demo.utils.exceptions.MessagesNotFoundException;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagessService messagessService;

    private final static int DEF_PAGE_SIZE = 20;

    @GetMapping("list")
    public ResponseEntity<?> getMessages(@RequestParam Long ownerId, @RequestParam Long recipientId, @RequestParam String key, Integer page) {
        if (!isKeyValid(key)) {
            return ResponseEntity.badRequest()
                    .body(new ChatterDefaultResponse(400,
                            "Sorry, you don't have permission, chack please you data or try againe",
                            ErrorType.MESSAGES_CHAT_KEY_IS_NOT_VALID));
        }

        try {
            Page<ChatterMessageDTO> elems = messagessService.getMessagesByOwnerRecipientIds(ownerId, recipientId, page);

            return ResponseEntity.ok(elems);
        } catch (MessagesNotFoundException exc) {
            return ResponseEntity.badRequest()
                    .body(new ChatterDefaultResponse(400,
                            exc.getMessage(),
                            ErrorType.CHAT_IS_EMPTY));
        }

    }

    private boolean isKeyValid(String key) {
        //TODO: key validation
        return true;
    }

}
