package ua.com.chatter.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.ChatType;
import ua.com.chatter.demo.model.dto.ChatterDefaultResponse;
import ua.com.chatter.demo.model.dto.chat.ChatCreateRequest;
import ua.com.chatter.demo.model.dto.chat.ChatDTO;
import ua.com.chatter.demo.model.entity.ChatEntity;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.ChatRepository;
import ua.com.chatter.demo.repository.MessagesRepository;
import ua.com.chatter.demo.repository.UserRepository;
import ua.com.chatter.demo.utils.ChatterConstants;
import ua.com.chatter.demo.utils.exceptions.ChatIsExistException;
import ua.com.chatter.demo.utils.exceptions.ChatNotFoundException;
import ua.com.chatter.demo.utils.exceptions.UserNotFoundException;

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
                .orElseThrow(() -> new ChatNotFoundException("You don't have any chats"));

        return entity.map(converter -> mapEntityToDTO(converter));
    }

    public ChatDTO createEmptyChat(Long userId, ChatDTO chat) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("You are not authorized"));

        ChatEntity newChat = mapDTOtoEntity(chat);
        newChat.addUser(user);

        ChatEntity savedChat = chatRepository.save(newChat);
        return mapEntityToDTO(savedChat);
    }

    public ChatDTO createChat(ChatCreateRequest chatParams) {
        boolean hasAccount = userRepository.existsById(chatParams.getCreatedBy());

        if (!hasAccount) {
            throw new UserNotFoundException("You don't have account");
        }

        if (chatParams.getType() == ChatType.PRIVATE_SIMPLE) {
            boolean isChatExist = chatRepository.hasPrivateSimpleChat(chatParams.getCreatedBy(), chatParams.getIds().get(0));

            if (isChatExist) {
                throw new ChatIsExistException("Chat creation: PRIVATE_SIMPLE is exist");
            }
        }

        List<Long> chatUserIds = chatParams.getIds();
        chatUserIds.add(chatParams.getCreatedBy());
        List<UserEntity> chatUsers = userRepository.findAllById(chatUserIds);

        ChatEntity newChat = new ChatEntity();
        newChat.setChatName(chatParams.getChatName());
        newChat.setType(chatParams.getType());
        newChat.setCreatedBy(chatParams.getCreatedBy());
        newChat.addUsers(chatUsers);

        ChatEntity savedChat = chatRepository.save(newChat);
        return mapEntityToDTO(savedChat);
    }

    public ChatEntity getChat(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new ChatNotFoundException("Chat not found"));
    }

    public ChatterDefaultResponse deleteChat(Long chatId) {
        messagesRepository.deleteByChatId(chatId);
        chatRepository.deleteByChatId(chatId);
        return new ChatterDefaultResponse(200, "Chat deleted");
    }

    private ChatDTO mapEntityToDTO(ChatEntity elem) {
        return new ChatDTO(elem.getId(), elem.getChatName(), elem.getChatLogo(), elem.getType(), elem.getLastMessageTime(), elem.getCreatedBy());
    }

    private ChatEntity mapDTOtoEntity(ChatDTO elem) {
        return new ChatEntity(elem.getChatId(), elem.getChatName(), elem.getChatLogo(), elem.getType(), elem.getLastMessageTime(), elem.getCreatedBy());
    }

}
