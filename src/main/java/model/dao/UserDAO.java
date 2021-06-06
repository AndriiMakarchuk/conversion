package model.dao;

import model.entity.user.Profile;
import model.entity.user.User;
import model.entity.user.UserDetails;

import java.sql.*;

import static model.dao.SQLQuery.*;

public class UserDAO extends AbstractDAO{

    public void insertUser(User user) {
        try {

            preparedStatement = connection.prepareStatement(INSERT_USER_DETAILS, Statement.RETURN_GENERATED_KEYS);

            UserDetails details = user.getDetails();
            preparedStatement.setString(1, details.getFirstName());
            preparedStatement.setString(2, details.getLastName());
            preparedStatement.setString(3, details.getEmail());
            preparedStatement.setString(4, details.getPhone());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getProfile().getId());

            if (resultSet.next()) {
                preparedStatement.setInt(4, resultSet.getInt(1));
            } else {
                throw new SQLException();
            }
            preparedStatement.executeUpdate();
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
            close(preparedStatement);
        }
    }

    public User getUser(String login) throws SQLException {
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapUser(user, resultSet);
            } else {
                throw new SQLException("User not found");
            }
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return user;
    }

    public User getUser(int id) {
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapUser(user, resultSet);
            } else {
                throw new SQLException("User not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return user;
    }

    private void mapUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password1"));
        int profileId = resultSet.getInt("profile_id");
        ProfileDAO profileDAO = new ProfileDAO();
        Profile profile = profileDAO.getProfile(profileId);
        profileDAO.close();
        user.setProfile(profile);
        user.setUserDetailsId(resultSet.getInt("user_details_id"));
    }

    public void getUserDetails(User user) {
        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_DETAILS_BY_ID);
            preparedStatement.setInt(1, user.getUserDetailsId());
            resultSet = preparedStatement.executeQuery();
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
            close(preparedStatement);
        }
    }

}