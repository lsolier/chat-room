package com.lsolier.udacity.chatroom.component;

import com.lsolier.udacity.chatroom.config.MessageDecoder;
import com.lsolier.udacity.chatroom.config.MessageEncoder;
import com.lsolier.udacity.chatroom.model.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value = "/chat/{username}",
    encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class WebSocketChatServer {

  /**
   * All chat sessions.
   */
  private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
  private static HashMap<String, String> users = new HashMap<>();

  private static void sendMessageToAll(Message message) {
    System.out.println("Object that will be send to each user: " + message.toString());
    onlineSessions.forEach((id, session) -> {
      try {
        session.getBasicRemote().sendObject(message);
      } catch (IOException | EncodeException e) {
        e.printStackTrace();
      }
    });
    //TODO: add send message method.
  }

  /**
   * Open connection, 1) add session, 2) add user.
   */
  @OnOpen
  public void onOpen(Session session, @PathParam("username") String username) {
    System.out.printf("Session opened, id: %s%n", session.getId());
    onlineSessions.put(session.getId(), session);
    users.put(session.getId(), username);
    Message message = new Message();
    message.setUserName(username);
    message.setMsg("Hello everyone");
    message.setOnlineCount(onlineSessions.size());
    message.setType("SPEAK");
    System.out.println(username + " are connected!");
    sendMessageToAll(message);
  }

  /**
   * Send message, 1) get username and session, 2) send message to all.
   */
  @OnMessage
  public void onMessage(Session session, Message message) {
    System.out.println("session recibido: " + session.getId());
    System.out.println("mensaje recibido: " + message.toString());
    message.setType("SPEAK");
    sendMessageToAll(message);
  }

  /**
   * Close connection, 1) remove session, 2) update user.
   */
  @OnClose
  public void onClose(Session session) {
    onlineSessions.remove(session.getId());
    Message message = new Message();
    message.setUserName("System");
    message.setMsg(users.get(session.getId()) + " was Disconnected!");
    message.setOnlineCount(onlineSessions.size());
    message.setType("SPEAK");
    users.remove(session.getId());
    sendMessageToAll(message);
    //TODO: add close connection.
  }

  /**
   * Print exception.
   */
  @OnError
  public void onError(Session session, Throwable error) {
    error.printStackTrace();
  }

}

