package model.dao;

import model.entity.audioWord.WordEnd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static model.dao.SQLQuery.INSERT_WORD_END;
import static model.dao.SQLQuery.SELECT_WORD_ENDS_BY_LANGUAGE;

public class WordEndDAO extends AbstractDAO {
    public void insertWordEnds(List<WordEnd> wordEnds) {
        List<ResultSet> sets = new ArrayList<>();
        try {
            for (WordEnd wordEnd : wordEnds) {
                preparedStatement = connection.prepareStatement(INSERT_WORD_END, Statement.RETURN_GENERATED_KEYS);

                mapWordEndInsert(wordEnd, preparedStatement);

                preparedStatement.executeUpdate();
                sets.add(preparedStatement.getGeneratedKeys());
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

    public List<WordEnd> getAudioWords(String language) {
        List<WordEnd> wordEnds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_WORD_ENDS_BY_LANGUAGE);
            preparedStatement.setString(1, language);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WordEnd end = new WordEnd();
                mapWordEnd(end, resultSet);
                wordEnds.add(end);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        System.out.println(wordEnds);
        return wordEnds;
    }

    private void mapWordEndInsert(WordEnd audioWord,  PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, audioWord.getName());
        preparedStatement.setString(2, audioWord.getLanguage());
        preparedStatement.setBlob(3, audioWord.getEndStream());
    }

    private void mapWordEnd(WordEnd wordEnd, ResultSet resultSet) throws SQLException {
        wordEnd.setId(resultSet.getInt("id"));
        wordEnd.setEndBlob(resultSet.getBlob("end_blob"));
        wordEnd.setLanguage(resultSet.getString("language"));
        wordEnd.setName(resultSet.getString("name"));
    }
}
