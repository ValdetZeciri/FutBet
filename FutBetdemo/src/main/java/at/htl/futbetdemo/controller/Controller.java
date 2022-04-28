package at.htl.futbetdemo.controller;


import at.htl.futbetdemo.model.FutBetModel;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@org.springframework.stereotype.Controller
public class Controller {

    FutBetModel futModel;


    public Controller() throws SQLException, IOException, JSONException, ParseException, UnirestException {
        futModel = new FutBetModel();
    }

    @GetMapping("/")
    public String getIndex(Model model) throws SQLException, IOException {


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

    @GetMapping("/team.html")
    public String getTeam(){
        return "team";
    }

    @GetMapping("/login-page")
    public String getLink(){
        return "login-page";
    }

    //@RequestMapping(value = "/login-page/login", method = RequestMethod.POST)
    @PostMapping("/login-page")
    public String makePostEcho(@RequestBody String data) {

        return "login-page";
    }

}
