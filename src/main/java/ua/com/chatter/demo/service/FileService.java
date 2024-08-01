package ua.com.chatter.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.chatter.demo.model.entity.FileEntity;
import ua.com.chatter.demo.repository.FileRepository;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void saveFileInfo(String fileName, String filePath) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(filePath);
        fileRepository.save(fileEntity);
    }
}
