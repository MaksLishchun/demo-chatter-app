package ua.com.chatter.demo.model.dto;

public enum ErrorType {
    //auth
    REGISTRATION,
    USER_EMAIL_IS_EXIST,
    USER_PHONE_IS_EXIST,

    LOGIN,
    JWT_GENERATION,

    //chats
    GET_CHATS,
    DELETE_CHAT,
    CREATE_EMPTY_CHAT,
    CREATE_CHAT,
    
    //messages
    DELETE_MESSAGE,
    GET_MESSAGES,

    //user
    DELETE_USER,

    ENTITY_NOT_FOUND,
    ENTITY_IS_EXIST,
    ENTITY_IS_NULL,
}
