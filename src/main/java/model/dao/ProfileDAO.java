package model.dao;

import static model.DBManager.*;
import static model.dao.SQLQuery.*;

import model.entity.user.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO {
    public Profile getProfile(int id) {
        Profile profile = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_PROFILE_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
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
            close(pStatement);
            close(connection);
        }
        return profile;
    }

    public Profile getProfile(String developerName) {
        Profile profile = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_PROFILE_BY_DEVELOPER_NAME);
            pStatement.setString(1, developerName);
            resultSet = pStatement.executeQuery();
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
            close(pStatement);
            close(connection);
        }
        return profile;
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
