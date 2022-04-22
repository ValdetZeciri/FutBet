package at.htl.futbetdemo.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("serverTime", "Hallo");
        return "index";
    }

    @GetMapping("/about.html")
    public String getAbout(){
        return "about";
    }

    @GetMapping("/contact.html")
    public String getContact(){
        return "contact";
    }

    @GetMapping("/news.html")
    public String getNews(){
        return "news";
    }

}
