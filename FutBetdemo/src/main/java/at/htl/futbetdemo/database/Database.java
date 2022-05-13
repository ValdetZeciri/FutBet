package at.htl.futbetdemo.database;

import at.htl.futbetdemo.model.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import oracle.jdbc.OracleDatabaseException;

import java.sql.*;

public class Database {
    private static Database instance = null;
    private Connection connection;
    FutBetModel model;

    public static synchronized Database getInstance(FutBetModel model) throws SQLException {
        if (instance == null) {
            instance = new Database(model);
        }
        return instance;
    }

    // INSERT INTO USER_ (name, password) VALUES ("Raphael", Lollol12)

    public Database(FutBetModel model) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(
                    "jdbc:oracle:thin:@delphi.htl-leonding.ac.at:1521:delphidb","if190105","oracle");


        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Verbindung zur Datenbank nicht moeglich " + ex +
                    "");
            System.exit(1);
        }
    }

    public int addUser(User user) throws SQLException {

        try{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO USER_(name, password, emailAdress) VALUES(?, ?, ?)"
        );
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmailAdress());


            preparedStatement.execute();
            preparedStatement.close();
        }catch(Exception e) {

            System.out.println(e.getMessage());
            return -1;
        }

        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT id FROM USER_ WHERE name = ?"
        );
        preparedStatement1.setString(1, user.getUserName());
        ResultSet resultSet = preparedStatement1.executeQuery();

        while(resultSet.next()){
            return resultSet.getInt(1);
        }

        resultSet.close();
        preparedStatement1.execute();
        preparedStatement1.close();
        return 0;
    }

    public void createGroup(String groupName, Leagues league) throws SQLException, UnirestException {
        int id = model.getLeagueId(league);

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO GROUP_(name, competition) VALUES(?, ?)"
        );

        preparedStatement.setString(1,groupName);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void addUserToGroup(int userId, int groupId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO USER_GROUP(userId, groupId) VALUES (?,?)"
        );

        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, groupId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void insertGame(Game game) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO GAME(team1Id, team2Id, toreHeim, toreAuswaerts, competitionId) Values (?,?,?,?,?)"
        );

        preparedStatement.setInt(1, game.getTeam1().getId());
        preparedStatement.setInt(2,game.getTeam2().getId());
        preparedStatement.setInt(3, game.getGoals1());
        preparedStatement.setInt(4,game.getGoals2());
        preparedStatement.setInt(5,game.getCompetitionId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void addBet(User user, Game game, int tore1, int tore2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "Insert INTO BET(idOfUser, gameId, toreHeim, toreAuswaerts)"
        );

        //preparedStatement.setInt(1, user.getId());
        preparedStatement.setInt(2,game.getId());
        preparedStatement.setInt(3,tore1);
        preparedStatement.setInt(4,tore2);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void insertCompetition(Competition competition) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "Insert INTO COMPETITION(name) VALUES (?)"
        );

        preparedStatement.setString(1, competition.getName());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void createTeam(Team team) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO TEAM(name) VALUES (?)"
        );
        preparedStatement.setString(1, team.getName());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void addTeamToCompetition(Competition competition, Team team) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO COMPETITION_TEAM(comId, teamId) VALUES (?,?)"
        );

        preparedStatement.setInt(1, competition.getId());
        preparedStatement.setInt(2, team.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public boolean checkUserLogin(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT emailAdress, password FROM USER_"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            if(user.getEmailAdress().equals(resultSet.getString(1))&&user.getPassword().equals(resultSet.getString(2))){
                return true;
            }
        }

        return false;
    }

    public String getUserWithEmail(String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT name FROM USER_ WHERE emailadress = ?"
        );

        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return resultSet.getString(1);
    }
}
