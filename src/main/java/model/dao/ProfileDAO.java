package model.dao;

import static model.dao.SQLQuery.*;

import model.entity.user.Profile;

import java.sql.SQLException;

public class ProfileDAO extends AbstractDAO{
    public Profile getProfile(int id) {
        Profile profile = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_PROFILE_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                profile = new Profile();
                profile.setId(resultSet.getInt("id"));
                profile.setProfileName(resultSet.getString("profile_name"));
                profile.setDeveloperName(resultSet.getString("developer_name"));
            } else {
                throw new SQLException("Profile not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return profile;
    }

    public Profile getProfile(String developerName) {
        Profile profile = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_PROFILE_BY_DEVELOPER_NAME);
            preparedStatement.setString(1, developerName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                profile = new Profile();
                profile.setId(resultSet.getInt("id"));
                profile.setProfileName(resultSet.getString("profile_name"));
                profile.setDeveloperName(resultSet.getString("developer_name"));
            } else {
                throw new SQLException("Profile not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return profile;
    }

}
