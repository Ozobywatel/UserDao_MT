package pl.coderslab.entity;

import pl.coderslab.BCrypt;
import pl.coderslab.UserDAO_MT.DbUtil;
import pl.coderslab.entity.User;

import java.sql.*;

public class UserDao {

    //HASHPASSWORD: CHECK OK
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //SELECT USER BY ID AND PRINTOUT - CHECK OK
    private static final String SELECT_BY_ID = "SELECT * from users where id = ?;";

    public User getUserById(int id) {
        User user = null;
        try (Connection connect = DbUtil.connect()) {
            PreparedStatement preparedStatement = connect.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password")

                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

// PRINTOUT ALL USERS
    public User[] getAllUsers() {
        return new User[0];
    }
    //CREATE USER - CHECK OK
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    public User create(User user) {

        try (Connection connCreate = DbUtil.connect()) {
            PreparedStatement statement =
                    connCreate.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            System.out.println("New user successfully added! \n New user id: " + user.getId() + "\n" +
                    "New user name: " + user.getUsername() + "\n" +
                    "New users email: " + user.getEmail());
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    //UPDATE
    private static final String
            UPDATE_QUERY =
            "UPDATE tableName SET email = ?, username = ? WHERE id = ?;";

    public void updateUsersData(User user) {
        try (Connection connUpdate = DbUtil.connect()) {
            PreparedStatement statement =
                    connUpdate.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println();
    }
}
