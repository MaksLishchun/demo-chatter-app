package ua.com.chatter.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.dto.ChatDTO;
import ua.com.chatter.demo.model.dto.ChatterDefaultResponse;
import ua.com.chatter.demo.model.entity.ChatEntity;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.ChatRepository;
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

    public Page<ChatDTO> getChats(Long userId, int page) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("You are not authorized"));

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

    public ChatDTO createChat(Long userId, Long invitedUserId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("You are not authorized"));
        UserEntity invitedUser = userRepository.findById(invitedUserId).orElseThrow(() -> new UserNotFoundException("Invited user not found authorized"));

        boolean isChatExist = checkIfTwoUsersHaveSingleChat(userId, invitedUserId);

        if (isChatExist) {
            throw new ChatIsExistException("Chat is exist");
        }

        ChatEntity newChat = new ChatEntity();
        newChat.addUser(user);
        newChat.addUser(invitedUser);

        ChatEntity savedChat = chatRepository.save(newChat);
        return mapEntityToDTO(savedChat);
    }

    public ChatterDefaultResponse deleteChat(Long chatId) {
        chatRepository.deleteByChatId(chatId);
        return new ChatterDefaultResponse(200, "Chat deleted");
    }

    private boolean checkIfTwoUsersHaveSingleChat(Long userId1, Long userId2) {
        return chatRepository.existsChatWithTwoUsers(userId1, userId2);
    }

    private Set<ChatDTO> mapEntityToDTO(Set<ChatEntity> elems) {
        Set<ChatDTO> chatDTOs = new HashSet<>();
        for (ChatEntity elem : elems) {
            chatDTOs.add(mapEntityToDTO(elem));
        }
        return chatDTOs;
    }

    private ChatDTO mapEntityToDTO(ChatEntity elem) {
        return new ChatDTO(elem.getId(), elem.getChatName(), elem.getChatLogo());
    }

    private ChatEntity mapDTOtoEntity(ChatDTO elem) {
        return new ChatEntity(elem.getChatId(), elem.getChatName(), elem.getChatLogo());
    }

}
