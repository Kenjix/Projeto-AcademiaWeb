package com.example.academia.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MVCController {
    private static final Logger LOG = LoggerFactory.getLogger(MVCController.class);

    @GetMapping(value = "/login")
    public String login() {
        //LOG.info("/login");
        //LOG.info("Return login");
        return "login";
    }
    @GetMapping(value = "/")
    public String home() {
        return "home";
    }
    @GetMapping(value = "/error400")
    public String error400() {
        return "error400";
    }

    @GetMapping(value = "/error403")
    public String error403() {
        return "error403";
    }

    @GetMapping(value = "/error404")
    public String error404() {
        return "error404";
    }

    @GetMapping(value = "/error500")
    public String error500() {
        return "error500";
    }
}
