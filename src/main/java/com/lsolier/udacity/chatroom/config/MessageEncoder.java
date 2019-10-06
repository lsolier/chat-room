package com.lsolier.udacity.chatroom.config;

import com.google.gson.Gson;
import com.lsolier.udacity.chatroom.model.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

  private static Gson gson = new Gson();

  @Override
  public String encode(Message message) throws EncodeException {
    return gson.toJson(message);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
    System.out.println("Starting Encode");
  }

  @Override
  public void destroy() {
    System.out.println("Ending Encode");
  }
}