package at.htl.futbetdemo.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String userName;
    private String emailAdress;
    private List<Group> groupList = new ArrayList<>();
    private String password;

    public User(){

    }

//    public User(String userName, String password, String emailAdress){
//        this.userName = userName;
//        this.password = password;
//        this.emailAdress = emailAdress;
//    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void addGroup(Group group){
        groupList.add(group);
    }

    public String getUserName() {
        return userName;
    }


    public void setId(int id){
        this.id = id;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
