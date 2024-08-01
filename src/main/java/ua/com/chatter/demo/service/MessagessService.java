package ua.com.chatter.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.dto.ChatterDefaultResponse;
import ua.com.chatter.demo.model.dto.message.MessageDTO;
import ua.com.chatter.demo.model.dto.request.MessageRequest;
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

    public MessageDTO saveMessage(MessageRequest messageRequest) {
        UserEntity user = userRepository.findById(messageRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ChatEntity chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new ChatNotFoundException("Chat was removed, please create chat and try agaite"));

        boolean isMessageExist = messageRequest.getId() == null ? false : messagesRepository.existsById(messageRequest.getId());
        if (isMessageExist) {
            messagesRepository.updateMessage(messageRequest.getId(), messageRequest.getContent());

            MessageEntity newMessage = messageRequest.getId() != null ? messagesRepository
                    .findByMessageId(messageRequest.getId()).orElse(null) : null;
            return mapEntityToDTO(newMessage);
        } else {
            MessageEntity messageToSave = new MessageEntity(messageRequest.getCreatedAt(),
                    messageRequest.getContent(),
                    chat,
                    user,
                    false);

            MessageEntity entity = messagesRepository.save(messageToSave);

            return mapEntityToDTO(entity);
        }
    }

    public ChatterDefaultResponse deleteMessage(Long messageId) {
        messagesRepository.deleteByMessageId(messageId);
        return new ChatterDefaultResponse(200, "Message deleted");
    }

    private MessageDTO mapEntityToDTO(MessageEntity elem) {
        return new MessageDTO(elem.getMessageId(),
                elem.getCreatedAt(),
                elem.getContent(),
                elem.isEdited());
    }
}
