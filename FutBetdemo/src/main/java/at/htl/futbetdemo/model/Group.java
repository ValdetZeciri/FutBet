package at.htl.futbetdemo.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private String name;
    private int competitionId;
    private String competitionName;
    private int creatorId;
    private String creatorName;

    public Group(){

    }

    public Group(int id, String name, int competitionId) {
        this.id = id;
        this.name = name;
        this.competitionId = competitionId;
    }
    public Group(int id, String name, int competitionId, String competitionName, int creatorId, String creatorName) {
        this.id = id;
        this.name = name;
        this.competitionId = competitionId;
        this.competitionName = competitionName;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }
}
