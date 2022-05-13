package at.htl.futbetdemo.controller;

import at.htl.futbetdemo.model.*;
import at.htl.futbetdemo.model.Competition;
import at.htl.futbetdemo.model.User;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
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
       // futModel.getDataFromApi();
    }


    @GetMapping("/main")
    public String getMainPage(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }

        return "index";
    }

    @GetMapping("/")
    public String getLogin(HttpServletRequest request, Model model) throws SQLException, IOException {

        HttpSession session = request.getSession();

        System.out.println(session.getId());

        model.addAttribute("user", new User());
        model.addAttribute("message1", "");
        model.addAttribute("message2", "");


        return "loginPage";
    }

    @GetMapping("/about.html")
    public String getAbout(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }


        return "about";
    }

    @GetMapping("/contact.html")
    public String getContact(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }
        return "contact";
    }

    @GetMapping("/news.html")
    public String getNews(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }
        return "news";
    }

    @GetMapping("/team.html")
    public String getTeam(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }

        return "team";
    }

    @GetMapping("/login-page")
    public String getLogin(Model model){


        return "loginPage";
    }

    @PostMapping("/login-page")
    public String postLogin(HttpServletRequest request, @ModelAttribute User user, Model model) throws SQLException {

        String message = futModel.checkForCorrectLogin(user);

        HttpSession session = request.getSession();

        System.out.println(message);




        if (message == "correct"){

            user.setUserName(futModel.getUserWithEmail(user.getEmailAdress()));

            session.setAttribute("user", user);
            model.addAttribute("userName", user.getUserName());

            return "index";
        }

        model.addAttribute("message1", message);

        return "loginPage";
    }

    @PostMapping("/register-page")
    public String postRegister(HttpServletRequest request, @ModelAttribute User user, Model model) throws SQLException {

        int id=0;

        String message = futModel.checkForRightPassword(user.getPassword());

        HttpSession session = request.getSession();

        System.out.println(message);

        if (message.equals("correct")){
            id = futModel.addUser(user);
            if (id != -1) {

                session.setAttribute("user", user);
                model.addAttribute("userName", user.getUserName());

                return "index";
            }
        }
        else{
            model.addAttribute("message2", message);
        }


        if (id == -1)
            model.addAttribute("message2", "User oder email gibt es schon");

        return "loginPage";
    }

    @GetMapping("logOut")
    public String logOut(HttpServletRequest request, Model model) throws ServletException {

        HttpSession session = request.getSession();

        session.removeAttribute("user");

        model.addAttribute("user", new User());
        model.addAttribute("message1", "");
        model.addAttribute("message2", "");

        return "loginPage";
    }


}
