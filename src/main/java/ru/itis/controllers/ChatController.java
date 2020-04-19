package ru.itis.controllers;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.handlers.WebSocketMessageHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private WebSocketMessageHandler handler;

    @SneakyThrows
    @GetMapping("/room/{roomId:.+}")
    public ModelAndView getChatPage(@PathVariable("roomId") String roomId,
                                    HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String login = (String) request.getSession().getAttribute("login");

        if (login == null) {
            return new ModelAndView("redirect:/signIn");
        }

        modelAndView.addObject("login", login);
        modelAndView.addObject("roomId", roomId);
        modelAndView.setViewName("chat");
        return modelAndView;
    }

    @SneakyThrows
    @GetMapping("/rooms")
    public ModelAndView getRooms(HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        System.out.println(login);

        if (login == null) {
            return new ModelAndView("redirect:/signIn");
        }

        ModelAndView modelAndView = new ModelAndView();
        Set<String> roomsId = handler.getRoomsId();
            modelAndView.addObject("roomsId", roomsId);
            modelAndView.addObject("login", login);
            modelAndView.setViewName("rooms");
        return modelAndView;
    }

}
