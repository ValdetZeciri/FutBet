package at.htl.futbetdemo.model;

public class Game {
    public Game(int id,Team team1, Team team2, int goals1, int goals2, int competitionId){
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.goals1 = goals1;
        this.goals2 = goals2;
        this.competitionId = competitionId;
    }
    private Team team1;
    private Team team2;
    private int id;

    private int goals1 = 0;
    private int goals2 = 0;

    private int competitionId;

    public int getCompetitionId() {
        return competitionId;
    }

    public int getGoals1() {
        return goals1;
    }

    public int getGoals2() {
        return goals2;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }


    public int getId() {
        return id;
    }
}
