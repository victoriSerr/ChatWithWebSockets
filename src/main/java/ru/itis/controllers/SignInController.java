package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignInDto;
import ru.itis.models.AppUser;
import ru.itis.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/signIn")
    public ModelAndView getSignInPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign_in");
        return modelAndView;
    }

    @PostMapping("/signIn")
    public String signIn(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        SignInDto signInDto = SignInDto.builder().login(login).password(password).build();

        AppUser user = userService.signIn(signInDto);

        System.out.println(user);
        request.getSession().setAttribute("login", user.getLogin());

        System.out.println(request.getSession().getAttribute("login"));

        return "redirect:/rooms";

    }
}
