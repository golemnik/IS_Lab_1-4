package com.golem.lab.controller;

import com.golem.lab.model.Client;
import com.golem.lab.repository.RoleRepository;
import com.golem.lab.service.SecurityService;
import com.golem.lab.service.UserService;
import com.golem.lab.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private RoleRepository roleRepository;


    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new Client());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("userForm") Client clientForm, BindingResult bindingResult, Model model) {
        userValidator.validate(clientForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("ERRORS");
            System.out.println(bindingResult.getAllErrors());
            return "registration";
        }

        clientForm.setRoles(roleRepository.findByName("USER"));
        userService.save(clientForm);

        securityService.autologin(clientForm.getUsername(), clientForm.getPasswordConfirm());

        return "redirect:/home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        return "/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }

    @RequestMapping(value = {"/","hello"}, method = RequestMethod.GET)
    public String hello(Model model) {
        return "hello";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        List<Client> clients = userService.findAllClients();
        model.addAttribute("users", clients);
        return "admin";
    }




}
