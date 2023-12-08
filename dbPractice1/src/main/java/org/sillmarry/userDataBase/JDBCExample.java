package org.sillmarry.userDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {

    private static final String GET_USERS_SQL = "SELECT * FROM users";
    public static final String SELECT_FROM_USERS_WHERE_ID_SQL = "SELECT * FROM users WHERE id = ?";
//    private static final String INSERT_USER_SQL =
//            "INSERT INTO users(name, email, age)" +
//                    "VALUES (?, ?, ?)";

    public static void main(String[] args) throws SQLException {
        Connection connection = jdbcConnection();
        if(connection != null) {
            List<UserDTO> userList = findAllUsers(connection);
            System.out.println(userList);
            UserDTO userById = findUserById(connection, 5);
            System.out.println(userById);
        }
    }

//    public static void save(Connection connection, UserDTO user) throws SQLException {
//        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setString(2, user.getEmail());
//            preparedStatement.setInt(3, user.getAge());
//            System.out.println(preparedStatement);
//            preparedStatement.executeUpdate();
//        }
//    }

    public static List<UserDTO> findAllUsers(Connection connection) throws SQLException {
        ArrayList<UserDTO> result = new ArrayList<>();
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_SQL)) {
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                int fragCount = resultSet.getInt("frag_count");

                UserDTO user = UserDTO.builder()
                        .name(name)
                        .email(email)
                        .age(age)
                        .fragCount(fragCount)
                        .build();
                result.add(user);
            }
        }
        result.forEach(System.out::println);
        return result;
    }

    public static UserDTO findUserById(Connection connection, int id) throws SQLException {
        ResultSet resultSet;
        UserDTO user = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_USERS_WHERE_ID_SQL)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                int fragCount = resultSet.getInt("frag_count");

                user = UserDTO.builder()
                        .name(name)
                        .email(email)
                        .age(age)
                        .fragCount(fragCount)
                        .build();
            }
        }
        return user;
    }

    public static Connection jdbcConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "1488"
            );

            if(connection != null) {
                System.out.println("Connected to the database!");
                return connection;
            } else {
                System.out.println("Connection failed.");
            }
        } catch(SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch(Exception e) {
            System.out.println("Oh no! " + e.getMessage());
        }
        return null;
    }
}
