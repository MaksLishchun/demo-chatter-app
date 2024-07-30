-- Міграція для UserEntity
ALTER TABLE users ADD COLUMN status VARCHAR(255);

-- Міграція для ChatEntity
ALTER TABLE chats ADD COLUMN type VARCHAR(255);
ALTER TABLE chats ADD COLUMN last_message_time TIMESTAMP;
ALTER TABLE chats ADD COLUMN created_by BIGINT;

-- Міграція для MessageEntity
ALTER TABLE messages ADD COLUMN edited BOOLEAN;
