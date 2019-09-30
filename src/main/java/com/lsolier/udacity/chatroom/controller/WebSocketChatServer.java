package com.lsolier.udacity.chatroom.controller;

import com.lsolier.udacity.chatroom.model.Message;
import com.lsolier.udacity.chatroom.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class WebSocketChatServer {

  @RequestMapping("")
  public String redirectToLogin(User user) {
    return "login";
  }

  @RequestMapping("/singIn")
  public String redirectToChatRoom(@Valid User user, BindingResult result) {
    if (result.hasErrors()) {
      return "login";
    }
    System.out.println("User " + user.getName() + " login");
    return "chat";
  }

  @MessageMapping("/chat")
  @SendTo("/topic/chat")
  public Message getMessage(User user, String message) {
    return new Message(user.getName() + ": " + message);
  }

}
