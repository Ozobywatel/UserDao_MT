package pl.coderslab.entity;

import pl.coderslab.BCrypt;
import pl.coderslab.UserDAO_MT.DbUtil;
import pl.coderslab.entity.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    //HASHPASSWORD: CHECK OK
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    //CREATE USER - CHECK OK

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(email, username, password) VALUES (?, ?, ?)";

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

    //UPDATE - CHECK OK
    private static final String

            UPDATE_QUERY =
            "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?;";

    public void updateUsersData(User user) {
        try (Connection connUpdate = DbUtil.connect()) {
            PreparedStatement statement =
                    connUpdate.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());

            statement.executeUpdate();
            System.out.println("Users data successfully updated! \n Updated user id: " + user.getId() + "\n" +
                    "New updated user name: " + user.getUsername() + "\n" +
                    "New updated users email: " + user.getEmail() + "\n" +
                    "New password has been set.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //SELECT USER BY ID AND PRINTOUT - CHECK OK
    private static final String SELECT_BY_ID = "SELECT * from users where id = ?;";

    public User getUserById(int id) {
        User user = null;
        try (Connection connRead = DbUtil.connect()) {
            PreparedStatement preparedStatement = connRead.prepareStatement(SELECT_BY_ID);
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

    //DELETE USER BY ID CHECK OK
    private static final String DELETE_BY_ID = "DELETE from users where id = ?;";

    public void deleteUser(int userId) {
        try (Connection connDelete = DbUtil.connect()) {
            PreparedStatement statement =
                    connDelete.prepareStatement(DELETE_BY_ID);
            statement.setInt(1, userId);

            statement.executeUpdate();
            System.out.println("User with id: " + userId + " has been deleted.");
        } catch (SQLException e2) {
            System.out.println(e2);
        }
    }

    // PRINTOUT ALL USERS
    private static final String SHOW_ALL_USERS = "SELECT * FROM users";

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;

        return tmpUsers;
    }

    public User[] showAll() {
        try (Connection connShowAll = DbUtil.connect()) {
            User[] users = new User[0];
            PreparedStatement statement =
                    connShowAll.prepareStatement(SHOW_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e3) {
            e3.printStackTrace();
            return null;
        }


    }

}
