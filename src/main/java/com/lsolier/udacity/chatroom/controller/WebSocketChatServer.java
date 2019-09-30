package com.lsolier.udacity.chatroom.controller;

import com.lsolier.udacity.chatroom.model.Message;
import com.lsolier.udacity.chatroom.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketChatServer {

  @MessageMapping("/chat")
  @SendTo("/topic/chat")
  public Message getMessage(User user, String message) {
    return new Message(user.getName() + ": " + message);
  }

  @RequestMapping("/login")
  public String redirectToLogin() {
    return "login";
  }
}
