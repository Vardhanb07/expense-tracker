package db;

import java.sql.*;
import java.util.ArrayList;

public class Query {
    private String url, name, password;

    public Query(String url, String name, String password) {
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public ArrayList<ArrayList<String>> getData() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url, this.name, this.password)) {
            String query = "SELECT * FROM expenses;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ArrayList<String> t = new ArrayList<>();
                t.add(Integer.toString(resultSet.getInt("id")));
                t.add(resultSet.getString("title"));
                t.add(Integer.toString(resultSet.getInt("amount")));
                t.add(resultSet.getString("description"));
                data.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return data;
    }

    public void insertData(String title, String amount, String description) {
        try (Connection connection = DriverManager.getConnection(this.url, this.name, this.password)) {
            String query = "INSERT INTO expenses (title, amount, description) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, amount);
            statement.setString(3, description);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public ArrayList<String> getData(int id) {
        ArrayList<String> data = new ArrayList<String>();
        try (Connection connection = DriverManager.getConnection(this.url, this.name, this.password)) {
            String query = "SELECT * FROM expenses WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                data.add(Integer.toString(resultSet.getInt("id")));
                data.add(resultSet.getString("title"));
                data.add(Integer.toString(resultSet.getInt("amount")));
                data.add(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return data;
    }

    public void removeData(int id) {
        try (Connection connection = DriverManager.getConnection(this.url, this.name, this.password)) {
            String query = "DELETE FROM expenses WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(id));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
