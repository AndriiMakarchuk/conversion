package model.dao;

import model.entity.user.Profile;
import model.entity.user.User;
import model.entity.user.UserDetails;

import java.sql.*;

import static model.DBManager.*;
import static model.dao.SQLQuery.*;

public class UserDAO {

    public void insertUser(User user) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);

            pStatement = connection.prepareStatement(INSERT_USER_DETAILS, Statement.RETURN_GENERATED_KEYS);

            UserDetails details = user.getDetails();
            pStatement.setString(1, details.getFirstName());
            pStatement.setString(2, details.getLastName());
            pStatement.setString(3, details.getEmail());
            pStatement.setString(4, details.getPhone());
            pStatement.executeUpdate();

            resultSet = pStatement.getGeneratedKeys();
            pStatement = connection.prepareStatement(INSERT_USER);
            pStatement.setString(1, user.getLogin());
            pStatement.setString(2, user.getPassword());
            pStatement.setInt(3, user.getProfile().getId());

            if (resultSet.next()) {
                pStatement.setInt(4, resultSet.getInt(1));
            } else {
                throw new SQLException();
            }
            pStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public User getUser(String login) throws SQLException {
        User user = new User();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            pStatement.setString(1, login);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapUser(user, resultSet);
            } else {
                throw new SQLException("User not found");
            }
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return user;
    }

    public User getUser(int id) {
        User user = new User();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapUser(user, resultSet);
            } else {
                throw new SQLException("User not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return user;
    }

    private void mapUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password1"));
        int profileId = resultSet.getInt("profile_id");
        Profile profile = new ProfileDAO().getProfile(profileId);
        user.setProfile(profile);
        user.setUserDetailsId(resultSet.getInt("user_details_id"));
    }

    public void getUserDetails(User user) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER_DETAILS_BY_ID);
            pStatement.setInt(1, user.getUserDetailsId());
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                UserDetails details = new UserDetails();
                details.setFirstName(resultSet.getString("first_name"));
                details.setLastName(resultSet.getString("last_name"));
                details.setEmail(resultSet.getString("email"));
                details.setPhone(resultSet.getString("phone"));
                user.setDetails(details);
            } else {
                throw new SQLException("User not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
    }

    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}