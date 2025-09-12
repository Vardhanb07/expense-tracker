package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Init {
    private String url, name, password;

    public Init(String url, String name, String password) {
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public void populateDB() {
        try (Connection connection = DriverManager.getConnection(this.url, this.name, this.password)) {
            System.out.println("Connected to db");
            Statement statement = connection.createStatement();
            String SQL = """
                    CREATE TABLE IF NOT EXISTS expenses (
                        id INT AUTO_INCREMENT,
                        title TEXT,
                        amount int,
                        description TEXT,
                        PRIMARY KEY(id)
                    );
                    INSERT INTO expenses (title, amount, description) VALUES ( 'a', 1, 'b');
                    INSERT INTO expenses (title, amount, description) VALUES ('c', 2, 'd');
                    INSERT INTO expenses (title, amount, description) VALUES ('e', 3, 'f');
                    """;
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}