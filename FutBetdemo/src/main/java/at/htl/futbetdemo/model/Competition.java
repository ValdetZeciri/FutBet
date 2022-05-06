package at.htl.futbetdemo.model;

import java.util.ArrayList;
import java.util.List;

public class Competition {
    int id;
    String name;
    private List<Team> list = new ArrayList<>();
    private List<Game> gameList = new ArrayList<>();


    public Competition(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
