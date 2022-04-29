package at.htl.futbetdemo.database;

import at.htl.futbetdemo.model.FutBetModel;
import at.htl.futbetdemo.model.Leagues;
import at.htl.futbetdemo.model.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

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

    private Database(FutBetModel model) throws SQLException {
        this.model = model;
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
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO USER_(name, password) VALUES(?, ?)"
        );
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.execute();
        preparedStatement.close();

        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT id FROM USER_ WHERE name = ?"
        );
        preparedStatement1.setString(1, user.getUserName());
        ResultSet resultSet = preparedStatement.executeQuery();

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
}
