package org.sd.chat.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {
    private String username;
    private String message;
}