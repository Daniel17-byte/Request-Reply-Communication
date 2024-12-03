package org.sd.users.controller;

import org.sd.users.service.MessageProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://gateway:8080"})
public class MessageSenderController {

    private final MessageProducer messageProducer;

    public MessageSenderController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        messageProducer.sendMessage(message);
        return "Message sent: " + message;
    }
}