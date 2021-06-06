package model.dao;

import model.entity.ConversionRecord;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static model.dao.SQLQuery.*;

public class ConversionDAO extends AbstractDAO{
    public ConversionRecord getConversion(int id) {
        ConversionRecord conversion = new ConversionRecord();
        try {
            preparedStatement = connection.prepareStatement(SELECT_CONVERSION_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapConversion(conversion, resultSet);
            } else {
                throw new SQLException("Conversion not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return conversion;
    }

    public void insertConversion(ConversionRecord conversion) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_CONVERSION, new String[] { "id" });

            preparedStatement.setString(1, conversion.getFileName());
            preparedStatement.setString(2, conversion.getConversionSourceType());
            preparedStatement.setString(3, conversion.getConversionDestinationType());
            preparedStatement.setBoolean(4, conversion.getConverted());
            preparedStatement.setBoolean(5, conversion.getError());
            preparedStatement.setInt(6, conversion.getCreatedBy());
            preparedStatement.setBlob(7, conversion.getSourceFileStream());
            preparedStatement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

            preparedStatement.execute();
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
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
            close(preparedStatement);
        }
    }

    public void deleteConversion(int conversionId) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_CONVERSION_BY_ID);

            preparedStatement.setInt(1, conversionId);

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

    public void deleteConversion(Timestamp createdDate) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_CONVERSIONS_BY_CREATE_DATE);

            preparedStatement.setTimestamp(1, createdDate);

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

    public List<ConversionRecord> getConversions(int userId) {
        List<ConversionRecord> conversionRecords = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_CONVERSION_BY_USER);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ConversionRecord record = new ConversionRecord();
                mapConversion(record, resultSet);
                conversionRecords.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return conversionRecords;
    }

    public void updateConversion(int conversionId, boolean isConverted, boolean isError) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CONVERSION_BY_ID);
            preparedStatement.setBoolean(1, isConverted);
            preparedStatement.setBoolean(2, isError);
            preparedStatement.setInt(3, conversionId);

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

    public void updateConversionDestinationFile(int conversionId, InputStream inputStream) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CONVERSION_DESTINATION_BLOB_BY_ID);

            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setInt(2, conversionId);

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

    private void mapConversion(ConversionRecord conversion, ResultSet resultSet) throws SQLException {
        conversion.setId(resultSet.getInt("id"));
        conversion.setConversionSourceType(resultSet.getString("conversion_source_type"));
        conversion.setConversionDestinationType(resultSet.getString("conversion_destination_type"));
        conversion.setFileName(resultSet.getString("file_name"));
        conversion.setCreatedDate(resultSet.getTimestamp("created_date"));
        conversion.setCreatedBy(resultSet.getInt("created_by"));
        conversion.setError(resultSet.getBoolean("is_error"));
        conversion.setConverted(resultSet.getBoolean("is_converted"));
        conversion.setDestinationFileBlob(resultSet.getBlob("destination_file_blob"));
    }
}
