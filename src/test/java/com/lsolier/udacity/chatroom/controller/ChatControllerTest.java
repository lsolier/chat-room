package com.lsolier.udacity.chatroom.controller;

import com.lsolier.udacity.chatroom.model.User;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

public class ChatControllerTest {

  private static ChatController controller;

  @BeforeClass
  public static void setUp(){
    controller = new ChatController();
  }

  @Test
  public void redirectToLoginTest() {

    ModelAndView response = controller.redirectToLogin(new User(), new ModelAndView());
    Assert.assertEquals("login", response.getViewName());

  }

  @Test
  public void redirectToChatRoomWithUser() {
    User user = new User();
    user.setName("Luis");
    BeanPropertyBindingResult result = new BeanPropertyBindingResult(user,"User");
    ModelAndView response = controller.redirectToChatRoom(user, result, new ModelAndView());
    Assert.assertEquals("chat", response.getViewName());
  }

  @Test
  public void redirectToChatRoomWithoutUser() {
    User user = new User();
    ObjectError error = new ObjectError("name","User name can not be empty.");
    BindException ex = new BindException(user,"User");
    ex.addError(error);
    ModelAndView response = controller.redirectToChatRoom(user, ex, new ModelAndView());
    Assert.assertEquals("login", response.getViewName());
  }

}