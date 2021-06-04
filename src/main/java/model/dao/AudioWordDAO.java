package model.dao;

import model.entity.audioWord.AudioWord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static model.DBManager.getInstance;
import static model.dao.SQLQuery.*;

public class AudioWordDAO {
    public AudioWord getAudioWord(int id) {
        AudioWord audioWord = new AudioWord();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AUDIO_WORD_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapAudioWord(audioWord, resultSet);
            } else {
                audioWord = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return audioWord;
    }

    public AudioWord getAudioWord(String wordString) {
        AudioWord audioWord = new AudioWord();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AUDIO_WORD_BY_WORD_STRING);
            pStatement.setString(1, wordString);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapAudioWord(audioWord, resultSet);
            } else {
                audioWord = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return audioWord;
    }

    public static void main(String[] args) {
        AudioWord audioWord = new AudioWord();
        audioWord.setLanguage("English");
        audioWord.setExtension(".mp3");
        audioWord.setWordString("aaaa");
        new AudioWordDAO().insertAudioWord(audioWord);
    }

    public List<AudioWord> getAudioWords(List<String> words) {
        List<AudioWord> wordList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AUDIO_WORDS_BY_WORD_STRING.replace("?", "(\"" + String.join("\",\"", words) + "\")"));
            System.out.println(SELECT_AUDIO_WORDS_BY_WORD_STRING.replace("?", "\"" + String.join("\",\"", words) + "\""));

            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                AudioWord word = new AudioWord();
                mapAudioWord(word, resultSet);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        System.out.println(wordList);
        return wordList;
    }

    public List<AudioWord> getAudioWords() {
        List<AudioWord> wordList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AUDIO_WORDS);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                AudioWord word = new AudioWord();
                mapAudioWord(word, resultSet);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        System.out.println(wordList);
        return wordList;
    }

    public List<ResultSet> insertAudioWords(List<AudioWord> audioWords) {
        List<ResultSet> sets = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);
            for (AudioWord audioWord : audioWords) {
                pStatement = connection.prepareStatement(INSERT_AUDIO_WORD, Statement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, audioWord.getWordString());
                pStatement.setString(2, audioWord.getLanguage());
                pStatement.setString(3, audioWord.getExtension());
                pStatement.setBlob(4, audioWord.getAudioWordStream());
                pStatement.executeUpdate();
                sets.add(pStatement.getGeneratedKeys());
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
        return sets;
    }

    public ResultSet insertAudioWord(AudioWord audioWord) {
        ResultSet set = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);
            pStatement = connection.prepareStatement(INSERT_AUDIO_WORD, Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, audioWord.getWordString());
            pStatement.setString(2, audioWord.getLanguage());
            pStatement.setString(3, audioWord.getExtension());
            pStatement.setBlob(4, audioWord.getAudioWordStream());
            pStatement.executeUpdate();
            set = pStatement.getGeneratedKeys();
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
        return set;
    }

    public void deleteAudioWord(int audioWord) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);

            pStatement = connection.prepareStatement(DELETE_AUDIO_WORD_BY_ID);

            pStatement.setInt(1, audioWord);

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

    private void mapAudioWord(AudioWord audioWord, ResultSet resultSet) throws SQLException {
        audioWord.setId(resultSet.getInt("id"));
        audioWord.setWordString(resultSet.getString("word_string"));
        audioWord.setLanguage(resultSet.getString("language"));
        audioWord.setExtension(resultSet.getString("extension"));
        audioWord.setAudioWordBlob(resultSet.getBlob("audio_word_blob"));
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
