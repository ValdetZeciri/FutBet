package at.htl.futbetdemo.model;

public class Team {
    private String name;
    private int id;

    public Team(String name, int id){
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
