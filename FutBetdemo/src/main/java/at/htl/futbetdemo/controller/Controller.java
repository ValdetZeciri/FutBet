package at.htl.futbetdemo.controller;


import at.htl.futbetdemo.model.FutBetModel;
import at.htl.futbetdemo.model.User;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String getIndex(HttpServletRequest request, Model model) throws SQLException, IOException {

        HttpSession session = request.getSession();

        System.out.println(session.getId());


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
    public String getLogin(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("message", "");
        return "loginPage";
    }

    @PostMapping("/login-page")
    public String postLogin(HttpServletRequest request, @ModelAttribute User user, Model model) {
        //String message = futModel.checkForCorrectLogin(user);

        HttpSession session = request.getSession();

        System.out.println(session.getId());

//        if (message == "correct"){
//            return "index";
//        }
//
//        model.addAttribute("message", message);

        return "loginPage";
    }

    @GetMapping("/register-page")
    public String getRegister(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("message", "");

        return "register-page";
    }

    @PostMapping("/register-page")
    public String postRegister(@ModelAttribute User user, Model model) {

        System.out.println(user.getUserName() + " " + user.getId());

//        String message = futModel.checkForRightPassword(user.getId());
//
//        model.addAttribute("message", message);

        return "register-page";
    }


}
