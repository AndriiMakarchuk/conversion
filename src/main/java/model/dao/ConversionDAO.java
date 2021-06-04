package model.dao;

import model.entity.ConversionRecord;
import model.entity.audioWord.AudioWord;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static model.DBManager.getInstance;
import static model.dao.SQLQuery.*;

public class ConversionDAO {
    public ConversionRecord getConversion(int id) {
        ConversionRecord conversion = new ConversionRecord();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_CONVERSION_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapConversion(conversion, resultSet);
            } else {
                throw new SQLException("Conversion not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return conversion;
    }

    public void insertConversion(ConversionRecord conversion) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);
            System.out.println(connection);
            pStatement = connection.prepareStatement(INSERT_CONVERSION, new String[] { "id" });

            pStatement.setString(1, conversion.getFileName());
            pStatement.setString(2, conversion.getConversionSourceType());
            pStatement.setString(3, conversion.getConversionDestinationType());
            pStatement.setDate(4, conversion.getCreatedDate());
            pStatement.setBoolean(5, conversion.getConverted());
            pStatement.setBoolean(6, conversion.getError());
            pStatement.setInt(7, conversion.getCreatedBy());
            pStatement.setBlob(8, conversion.getSourceFileStream());

            int affectedRows = pStatement.executeUpdate();
            System.out.println(connection);
            System.out.println(affectedRows);
            try (ResultSet keys = pStatement.getGeneratedKeys()) {
                keys.next();
                conversion.setId(keys.getInt(1));
            }
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

    public static void main(String[] args) {
        ConversionRecord conversionRecord = new ConversionRecord();
        conversionRecord.setCreatedDate(Date.valueOf(LocalDate.now()));
        conversionRecord.setConverted(false);
        conversionRecord.setError(false);
        conversionRecord.setCreatedBy(2);
        conversionRecord.setFileName("test");
        conversionRecord.setConversionSourceType("txt");
        conversionRecord.setConversionDestinationType("mp3");
        new ConversionDAO().insertConversion(conversionRecord);
    }

    public void deleteConversion(int conversionId) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);

            pStatement = connection.prepareStatement(DELETE_CONVERSION_BY_ID);

            pStatement.setInt(1, conversionId);

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

    public List<ConversionRecord> getConversions(int userId) {
        List<ConversionRecord> conversionRecords = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_CONVERSION_BY_USER);
            pStatement.setInt(1, userId);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                ConversionRecord record = new ConversionRecord();
                mapConversion(record, resultSet);
                conversionRecords.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        System.out.println(conversionRecords);
        return conversionRecords;
    }

    public void updateConversion(int conversionId, boolean isConverted, boolean isError) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);

            pStatement = connection.prepareStatement(UPDATE_CONVERSION_BY_ID);

            pStatement.setBoolean(1, isConverted);
            pStatement.setBoolean(2, isError);
            pStatement.setInt(3, conversionId);

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

    public void updateConversionDestinationFile(int conversionId, InputStream inputStream) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);

            pStatement = connection.prepareStatement(UPDATE_CONVERSION_DESTINATION_BLOB_BY_ID);

            pStatement.setBlob(1, inputStream);
            pStatement.setInt(2, conversionId);

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

    private void mapConversion(ConversionRecord conversion, ResultSet resultSet) throws SQLException {
        conversion.setId(resultSet.getInt("id"));
        conversion.setConversionSourceType(resultSet.getString("conversion_source_type"));
        conversion.setConversionDestinationType(resultSet.getString("conversion_destination_type"));
        conversion.setFileName(resultSet.getString("file_name"));
        conversion.setCreatedDate(resultSet.getDate("created_date"));
        conversion.setCreatedBy(resultSet.getInt("created_by"));
        conversion.setError(resultSet.getBoolean("is_error"));
        conversion.setConverted(resultSet.getBoolean("is_converted"));
        conversion.setDestinationFileBlob(resultSet.getBlob("destination_file_blob"));
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
