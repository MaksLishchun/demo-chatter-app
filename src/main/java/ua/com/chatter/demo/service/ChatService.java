package ua.com.chatter.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.ChatType;
import ua.com.chatter.demo.model.dto.chat.ChatDTO;
import ua.com.chatter.demo.model.dto.request.ChatCreateRequest;
import ua.com.chatter.demo.model.entity.ChatEntity;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.ChatRepository;
import ua.com.chatter.demo.repository.MessagesRepository;
import ua.com.chatter.demo.repository.UserRepository;
import ua.com.chatter.demo.utils.ChatterConstants;
import ua.com.chatter.demo.utils.exceptions.EntityIsExistException;
import ua.com.chatter.demo.utils.exceptions.EntityIsNullException;
import ua.com.chatter.demo.utils.exceptions.EntityNotFoundException;

@Service
public class ChatService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    public Page<ChatDTO> getChats(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, ChatterConstants.DEF_PAGE_SIZE);
        Page<ChatEntity> entity = chatRepository.findByUserId(userId, pageable)
                .orElseThrow(() -> new EntityNotFoundException("Chats not found"));

        return entity.map(converter -> mapEntityToDTO(converter));
    }

    public ChatDTO createEmptyChat(Long userId, ChatDTO chat) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ChatEntity newChat = mapDTOtoEntity(chat);
        newChat.addUser(user);

        try {
            ChatEntity savedChat = chatRepository.save(newChat);
            return mapEntityToDTO(savedChat);
        } catch (IllegalArgumentException e) {
            throw new EntityIsNullException("Chat can not be null");
        } catch (OptimisticLockingFailureException e) {
            throw new EntityIsExistException("Chat is exist");
        }
    }

    public ChatDTO createChat(ChatCreateRequest chatParams) {
        boolean hasAccount = userRepository.existsById(chatParams.getCreatedBy());

        if (!hasAccount) {
            throw new EntityNotFoundException("You don't have account");
        }

        if (chatParams.getType() == ChatType.PRIVATE_SIMPLE) {
            boolean isChatExist = chatRepository.hasPrivateSimpleChat(chatParams.getCreatedBy(), chatParams.getIds().get(0));

            if (isChatExist) {
                throw new EntityIsExistException("Chat creation: PRIVATE_SIMPLE is exist");
            }
        }
        List<UserEntity> chatUsers = new ArrayList<>();
        try {
            List<Long> chatUserIds = chatParams.getIds();
            chatUserIds.add(chatParams.getCreatedBy());
            chatUsers = userRepository.findAllById(chatUserIds);
        } catch (IllegalArgumentException exp) {
            throw new EntityIsNullException("Users not found for creating new chat");
        }

        try {
            ChatEntity newChat = new ChatEntity();
            newChat.setChatName(chatParams.getChatName());
            newChat.setType(chatParams.getType());
            newChat.setCreatedBy(chatParams.getCreatedBy());
            newChat.addUsers(chatUsers);

            ChatEntity savedChat = chatRepository.save(newChat);
            return mapEntityToDTO(savedChat);

        } catch (IllegalArgumentException exp) {
            throw new EntityIsNullException("Chat can not be null");
        } catch (OptimisticLockingFailureException e) {
            throw new EntityIsExistException("Chat is exist");
        }
    }

    public ChatEntity getChat(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat not found"));
    }

    public void deleteChat(Long chatId) {
        try {
            messagesRepository.deleteByChatId(chatId);
        } catch (IllegalArgumentException exp) {
            throw new EntityIsNullException("Messages ids can not be null");
        } catch (OptimisticLockingFailureException e) {
            throw new EntityIsExistException("Messages can not be removed");
        }
        try {
            chatRepository.deleteByChatId(chatId);
        } catch (IllegalArgumentException exp) {
            throw new EntityIsNullException("Chat can not be null");
        } catch (OptimisticLockingFailureException e) {
            throw new EntityIsExistException("Chat can not removed");
        }
    }

    private ChatDTO mapEntityToDTO(ChatEntity elem) {
        return new ChatDTO(elem.getId(), elem.getChatName(), elem.getChatLogo(), elem.getType(), elem.getLastMessageTime(), elem.getCreatedBy());
    }

    private ChatEntity mapDTOtoEntity(ChatDTO elem) {
        return new ChatEntity(elem.getChatId(), elem.getChatName(), elem.getChatLogo(), elem.getType(), elem.getLastMessageTime(), elem.getCreatedBy());
    }

}
