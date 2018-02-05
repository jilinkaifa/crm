package com.pandawork.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexController
 *
 * @author: zhangteng
 * @time: 2017/07/19 19:48
 **/
@Controller
@RequestMapping(value = "")
public class IndexController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String toIndex() {
        return "redirect:/admin";
    }
}

