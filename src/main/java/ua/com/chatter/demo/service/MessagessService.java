package ua.com.chatter.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.dto.ChatterMessageDTO;
import ua.com.chatter.demo.model.entity.ChatterMessageEntity;
import ua.com.chatter.demo.repository.MessagesRepository;
import ua.com.chatter.demo.utils.exceptions.MessagesNotFoundException;

@Service
public class MessagessService {

    private final static int DEF_PAGE_SIZE = 20;

    @Autowired
    private MessagesRepository messagesRepository;

    public Page<ChatterMessageDTO> getMessagesByOwnerRecipientIds(Long ownerId, Long recipientId, int page) throws MessagesNotFoundException {
        Pageable pageable = PageRequest.of(page, DEF_PAGE_SIZE);

        Page<ChatterMessageEntity> messagesPade = messagesRepository.findByOwnerIdAndRecipientId(ownerId, recipientId, pageable)
                .orElseThrow(() -> new MessagesNotFoundException("You don't have any messages"));
        
        return messagesPade.map(content -> mapEntityToDTO(content));
    }

    public ChatterMessageDTO saveMessage(ChatterMessageDTO chatterMessageDTO) {
        ChatterMessageEntity entity = messagesRepository.save(mapDTOtoEntity(chatterMessageDTO));
        return mapEntityToDTO(entity);
    }

    private List<ChatterMessageDTO> mapEntityToDTO(List<ChatterMessageEntity> elems) {
        List<ChatterMessageDTO> mappedMessages = new ArrayList<>();
        for (ChatterMessageEntity elem : elems) {
            mappedMessages.add(mapEntityToDTO(elem));
        }

        return mappedMessages;
    }

    private ChatterMessageDTO mapEntityToDTO(ChatterMessageEntity elem) {
        return new ChatterMessageDTO(elem.getOwnerId(),
                elem.getRecipientId(),
                elem.getDate(),
                elem.getSender(),
                elem.getContent());
    }

    private ChatterMessageEntity mapDTOtoEntity(ChatterMessageDTO elem) {
        return new ChatterMessageEntity(elem.getOwnerId(),
                elem.getRecipientId(),
                elem.getDate(),
                elem.getSender(),
                elem.getContent());
    }
}
