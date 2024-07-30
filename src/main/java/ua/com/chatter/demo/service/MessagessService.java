package ua.com.chatter.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ua.com.chatter.demo.model.dto.ChatterDefaultResponse;
import ua.com.chatter.demo.model.dto.MessageDTO;
import ua.com.chatter.demo.model.dto.MessageRequest;
import ua.com.chatter.demo.model.entity.ChatEntity;
import ua.com.chatter.demo.model.entity.MessageEntity;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.ChatRepository;
import ua.com.chatter.demo.repository.MessagesRepository;
import ua.com.chatter.demo.repository.UserRepository;
import ua.com.chatter.demo.utils.ChatterConstants;
import ua.com.chatter.demo.utils.exceptions.ChatNotFoundException;
import ua.com.chatter.demo.utils.exceptions.MessagesNotFoundException;
import ua.com.chatter.demo.utils.exceptions.UserNotFoundException;

@Service
@Slf4j
public class MessagessService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;


    public Page<MessageDTO> getMessages(Long chatId, int page) throws MessagesNotFoundException {
        Pageable pageable = PageRequest.of(page, ChatterConstants.DEF_PAGE_SIZE);
        Page<MessageEntity> messagesPade = messagesRepository.findByChatId(chatId, pageable)
                .orElseThrow(() -> new MessagesNotFoundException("You don't have any messages"));
        return messagesPade.map(content -> mapEntityToDTO(content));
    }

    public MessageDTO saveMessage(MessageRequest chatterMessageDTO, Long userId, Long chatId) {
        log.info("Message saving started chatId -- " + chatId + " userId -- " + userId);
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("Message user data getted");
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat was removed, please create chat and try agaite"));
        log.info("Message chat data getted");

        MessageEntity messageToSave = mapDTOtoEntity(chatterMessageDTO);
        messageToSave.setUser(user);
        messageToSave.setChat(chat);

        log.info("Message messageToSave created");
        MessageEntity entity = messagesRepository.save(messageToSave);

        log.info("Message saveMessage done saving");
        return mapEntityToDTO(entity);
    }

    public ChatterDefaultResponse deleteMessage(Long messageId) {
        messagesRepository.deleteByMessageId(messageId);
        return new ChatterDefaultResponse(200, "Message deleted");
    }

    private List<MessageDTO> mapEntityToDTO(List<MessageEntity> elems) {
        List<MessageDTO> mappedMessages = new ArrayList<>();
        for (MessageEntity elem : elems) {
            mappedMessages.add(mapEntityToDTO(elem));
        }

        return mappedMessages;
    }

    private MessageDTO mapEntityToDTO(MessageEntity elem) {
        return new MessageDTO(elem.getMessageId(),
                elem.getCreatedAt(),
                elem.getContent());
    }

    private MessageEntity mapDTOtoEntity(MessageRequest elem) {
        return new MessageEntity(
                elem.getCreatedAt(),
                elem.getContent());
    }
}
