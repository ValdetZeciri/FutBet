package at.htl.futbetdemo.database;

import at.htl.futbetdemo.model.FutBetModel;
import at.htl.futbetdemo.model.Leagues;
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

    public void addUser(String userName, String password) throws SQLException {

         PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO USER_ (name, password) VALUES (?,?)"
         );

         preparedStatement.setString(1, userName);
         preparedStatement.setString(2, password);

         preparedStatement.execute();
         preparedStatement.close();
    }

    public void createUserGroup(int userId, String groupName, Leagues league) throws SQLException, UnirestException {
        int id = model.getLeagueId(league);

        PreparedStatement preparedStatement = connection.prepareStatement(
                ""
        );

    }
}
