package ua.com.chatter.demo.utils;

import java.util.UUID;

public class ChatKeysGenerator {
    
    public static String generateUniqueKey() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }
    
}
