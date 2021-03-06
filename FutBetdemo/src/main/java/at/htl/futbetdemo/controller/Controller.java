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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    FutBetModel futModel;
    List<User> userForGroup;
    Group currentGroup;

    public Controller() throws SQLException, IOException, JSONException, ParseException, UnirestException {
        futModel = new FutBetModel();

       // futModel.testMethod();

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

    @PostMapping("/sendRequest")
    public String sendRequest(HttpServletRequest request, Model model, @ModelAttribute User friend){
        HttpSession session = request.getSession();
        List<User> searchedFriends;
        List<Group> list;
        List<User> friendsList;

        try {


            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

            user.setId(futModel.getIdForUser(user));
            friend.setId(futModel.getIdForUser(friend));

            if (!(friend.getUserName().equals("cancel")))
            {
                futModel.addFriend(user, friend);

            }

            model.addAttribute("usersOfGroup", userForGroup);

            model.addAttribute("searchedFriends", new ArrayList<User>());

            friendsList = futModel.getFriendsForUser(futModel.getIdForUser(user));

            list = futModel.getGroupsForUser(futModel.getIdForUser(user));

            model.addAttribute("groups", list);

            model.addAttribute("friends", friendsList);
            model.addAttribute("group", new Group());
            model.addAttribute("friend", new User());



        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }

        return "groups";
    }

    @PostMapping("/friend")
    public String searchForFriends(HttpServletRequest request, Model model, @ModelAttribute User friend){
        HttpSession session = request.getSession();
        List<User> searchedFriends;
        List<Group> list;
        List<User> friendsList;

        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

            friendsList = futModel.getFriendsForUser(futModel.getIdForUser(user));
            list = futModel.getGroupsForUser(futModel.getIdForUser(user));

            model.addAttribute("groups", list);

            model.addAttribute("friends", friendsList);
            model.addAttribute("group", new Group());
            model.addAttribute("friend", new User());


            user.setId(futModel.getIdForUser(user));

            searchedFriends = futModel.getFriendsSearched(user, friend);

            model.addAttribute("usersOfGroup", userForGroup);

            model.addAttribute("searchedFriends", searchedFriends);
        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }

        return "groups";

    }

    @PostMapping("/groups")
        public String postGroups (HttpServletRequest request, Model model, @ModelAttribute Group group){
            HttpSession session = request.getSession();
            List<Group> list;
        List<User> friendsList;


        String message;
            try {
                User user = (User) session.getAttribute("user");
                model.addAttribute("userName", user.getUserName());

                message = futModel.checkForRightGroupName(group.getName());

                model.addAttribute("group", new Group());
                model.addAttribute("friend", new User());
                model.addAttribute("message", message);

                if (message == ""){
                    int groupId = futModel.createGroup(group, futModel.getIdForUser(user));

                    futModel.addUserToGroup(futModel.getIdForUser(user), groupId);
                }

                list = futModel.getGroupsForUser(futModel.getIdForUser(user));
                model.addAttribute("groups", list);

                friendsList = futModel.getFriendsForUser(futModel.getIdForUser(user));
                model.addAttribute("friends", friendsList);

                model.addAttribute("searchedFriends", new ArrayList<User>());

                model.addAttribute("usersOfGroup", userForGroup);

            } catch (Exception e) {
                model.addAttribute("user", new User());
                model.addAttribute("message1", "");
                model.addAttribute("message2", "");
                return "loginPage";
            }
            return "groups";
    }

    @PostMapping("/addUserToGroup")
    public String addUserToGroup(HttpServletRequest request, Model model, @ModelAttribute User userToAdd){
        HttpSession session = request.getSession();
        List<Group> list;
        List<User> friendsList;

        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

            friendsList = futModel.getFriendsForUser(futModel.getIdForUser(user));
            list = futModel.getGroupsForUser(futModel.getIdForUser(user));

            model.addAttribute("groups", list);

            model.addAttribute("friends", friendsList);
            model.addAttribute("group", new Group());
            model.addAttribute("friend", new User());

            user.setId(futModel.getIdForUser(user));

            futModel.addUserToGroup(futModel.getIdForUser(userToAdd), futModel.getIdForGroup(currentGroup));

            currentGroup.setId(futModel.getIdForGroup(currentGroup));

            userForGroup = futModel.getUsersForGroup(currentGroup);

            model.addAttribute("usersOfGroup", userForGroup);


        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }

        return "groups";

    }

    @PostMapping("/getUsersForGroup")
    public String getUsersForGroup(HttpServletRequest request, Model model, @ModelAttribute Group group){
        HttpSession session = request.getSession();
        List<Group> list;
        List<User> friendsList;

        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

            friendsList = futModel.getFriendsForUser(futModel.getIdForUser(user));
            list = futModel.getGroupsForUser(futModel.getIdForUser(user));

            model.addAttribute("groups", list);

            model.addAttribute("friends", friendsList);
            model.addAttribute("group", new Group());
            model.addAttribute("friend", new User());


            user.setId(futModel.getIdForUser(user));

            group.setCreatorId(futModel.getIdForUser(new User(group.getCreatorName())));

            group.setId(futModel.getIdForGroup(group));

            userForGroup = futModel.getUsersForGroup(group);

            this.currentGroup=group;

            model.addAttribute("usersOfGroup", userForGroup);


        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }

        return "groups";
    }

    @GetMapping("/groups")
    public String getGroups(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        List<Group> list;
        List<User> friendsList;
        userForGroup= new ArrayList<>();

        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

            list = futModel.getGroupsForUser(futModel.getIdForUser(user));
            model.addAttribute("groups", list);
            model.addAttribute("group", new Group());

            friendsList = futModel.getFriendsForUser(futModel.getIdForUser(user));
            model.addAttribute("friends", friendsList);
            model.addAttribute("friend", new User());
            model.addAttribute("searchedFriends", new ArrayList<User>());


            model.addAttribute("usersOfGroup", userForGroup);
            model.addAttribute("message", "");

        }catch (Exception e){
            System.out.println(e.getStackTrace());
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }
        return "groups";
    }

    @PostMapping("/showGroupBet")
    public String showGroupBet(HttpServletRequest request, Model model, @ModelAttribute Group group){
        HttpSession session = request.getSession();
        List<Group> list;
        List<User> userList;

        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

            model.addAttribute("group", new Group());

            group.setCreatorId(futModel.getIdForUser(new User(group.getCreatorName())));

            group.setId(futModel.getIdForGroup(group));

            userList = futModel.getUsersForGroup(group);
            model.addAttribute("users", userList);

            list = futModel.getGroupsForUser(futModel.getIdForUser(user));
            model.addAttribute("groups", list);

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }
        return "betting";
    }

    @GetMapping("/bettingHome")
    public String getBettingHome(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        List<Group> list;

        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());

            model.addAttribute("group", new Group());


            list = futModel.getGroupsForUser(futModel.getIdForUser(user));
            model.addAttribute("groups", list);

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }
        return "betting";
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

    @PostMapping("profile")
    public String postprofile(Model model, HttpServletRequest request, @ModelAttribute User user) throws SQLException {
        HttpSession session = request.getSession();

        User sessionUser = (User) session.getAttribute("user");

        user.setId(futModel.getIdForUser(sessionUser));

        String message = futModel.updateUser(user);

        sessionUser.setUserName(user.getUserName());
        sessionUser.setEmailAdress(user.getEmailAdress());
        sessionUser.setInfo(user.getInfo());

        if (message == "successful"){
            session.setAttribute("user", sessionUser);
        }

        model.addAttribute("message", message);
        return "profile";
    }

    @GetMapping("profile")
    public String profileCard(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();

        try {
            User user = (User) session.getAttribute("user");
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("user", user);

        }catch (Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("message1", "");
            model.addAttribute("message2", "");
            return "loginPage";
        }

        return "profile";
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
