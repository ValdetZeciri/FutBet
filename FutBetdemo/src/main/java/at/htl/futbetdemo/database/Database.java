package at.htl.futbetdemo.database;

import java.sql.*;

public class Database {
    private static Database instance = null;
    private Connection connection;
    public static synchronized Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    private Database() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(
                    "jdbc:oracle:thin:@delphi.htl-leonding.ac.at:1521:delphidb","if190105","oracle");


        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Verbindung zur Datenbank nicht moeglich " + ex +
                    "");
            System.exit(1);
        }

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Persons( PersonId , Lastname) VALUES (1, 'STANGL')"
        );

        preparedStatement.execute();
        preparedStatement.close();
    }
}
