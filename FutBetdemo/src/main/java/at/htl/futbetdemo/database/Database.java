package at.htl.futbetdemo.database;

import at.htl.futbetdemo.model.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import oracle.jdbc.OracleDatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            this.model = model;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@delphi.htl-leonding.ac.at:1521:delphidb", "if190105", "oracle");


        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Verbindung zur Datenbank nicht moeglich " + ex +
                    "");
            System.exit(1);
        }
    }

    public int addUser(User user) throws SQLException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO USER_(name, password, emailAdress) VALUES(?, ?, ?)"
            );
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmailAdress());


            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return -1;
        }

        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT id FROM USER_ WHERE name = ?"
        );
        preparedStatement1.setString(1, user.getUserName());
        ResultSet resultSet = preparedStatement1.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }

        resultSet.close();
        preparedStatement1.execute();
        preparedStatement1.close();
        return 0;
    }


    public int getCompIdForApiId(int apiId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT competitionid From Competition WHERE apiid = ?"
        );

        preparedStatement.setInt(1, apiId);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return resultSet.getInt("competitionId");
    }

    public int getApiIdForCompId(int compId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT apiid From Competition WHERE competitionid = ?"
        );

        preparedStatement.setInt(1, compId);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return resultSet.getInt("apiid");
    }


    public int createGroup(Group group, int creatorId) throws SQLException, UnirestException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO GROUP_(name, competitionid, creatorId) VALUES(?, ?, ?)"
        );

        preparedStatement.setString(1, group.getName());
        preparedStatement.setInt(2, getCompIdForApiId(Integer.valueOf(group.getCompetitionName())));
        preparedStatement.setInt(3, creatorId);

        preparedStatement.execute();
        preparedStatement.close();

        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT id FROM GROUP_ WHERE name = ? AND competitionId = ? AND creatorId = ?"
        );

        preparedStatement1.setString(1, group.getName());
        preparedStatement1.setInt(2, getCompIdForApiId(Integer.valueOf(group.getCompetitionName())));
        preparedStatement1.setInt(3, creatorId);

        ResultSet resultSet = preparedStatement1.executeQuery();

        resultSet.next();

        return resultSet.getInt("id");

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
        preparedStatement.setInt(2, game.getTeam2().getId());
        preparedStatement.setInt(3, game.getGoals1());
        preparedStatement.setInt(4, game.getGoals2());
        preparedStatement.setInt(5, game.getCompetitionId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void addBet(User user, Game game, int tore1, int tore2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "Insert INTO BET(idOfUser, gameId, toreHeim, toreAuswaerts)"
        );

        //preparedStatement.setInt(1, user.getId());
        preparedStatement.setInt(2, game.getId());
        preparedStatement.setInt(3, tore1);
        preparedStatement.setInt(4, tore2);
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

        while (resultSet.next()) {
            if (user.getEmailAdress().equals(resultSet.getString(1)) && user.getPassword().equals(resultSet.getString(2))) {
               resultSet.close();
                return true;
            }
        }

        resultSet.close();
        return false;
    }

    public String getUserWithEmail(String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT name FROM USER_ WHERE emailadress = ?"
        );

        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        String string = resultSet.getString(1);

        resultSet.close();

        return string;
    }

    public String getUserNameForId(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "Select name from User_ WHERE id = ?"
        );

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return resultSet.getString("name");
    }

    public String updateUser(User user) throws SQLException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE USER_ SET name = ?, emailadress = ?, info = ? WHERE id = ?"
            );
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmailAdress());
            preparedStatement.setString(3, user.getInfo());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return "User oder Email existiert schon";
        }

        return "successful";
    }

    public int getIdForUser(User user) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM USER_ WHERE name = ? AND emailadress = ?"
        );

        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getEmailAdress());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int i = resultSet.getInt(1);

        resultSet.close();

        return i;
    }

    public List<Group> getGroupForUser(int id) throws SQLException {
        List<Group> groupList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, name, competitionid, creatorId FROM GROUP_ WHERE id IN(SELECT groupId FROM USER_GROUP WHERE userId = ?)"
        );

        preparedStatement.setInt(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            groupList.add(new Group(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("competitionId"), model.getLeagueForId(getApiIdForCompId(resultSet.getInt("competitionId"))).name(), resultSet.getInt("creatorId"), getUserNameForId(resultSet.getInt("creatorId"))));
        };

        resultSet.close();

        return groupList;
    }
}
