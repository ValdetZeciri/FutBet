package at.htl.futbetdemo.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String userName;
    private List<Group> groupList = new ArrayList<>();
    private String password;

    public User(){

    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

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
}
