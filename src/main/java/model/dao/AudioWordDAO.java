package model.dao;

import model.entity.audioWord.AudioWord;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static model.DBManager.getInstance;
import static model.dao.SQLQuery.*;

public class AudioWordDAO extends AbstractDAO{

    public AudioWord getAudioWord(int id) {
        AudioWord audioWord = new AudioWord();
        try {
            preparedStatement = connection.prepareStatement(SELECT_AUDIO_WORD_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapAudioWord(audioWord, resultSet);
            } else {
                audioWord = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return audioWord;
    }

    public AudioWord getStandardAudioWord(String wordString) {
        AudioWord audioWord = new AudioWord();
        try {
            preparedStatement = connection.prepareStatement(SELECT_AUDIO_WORD_BY_STANDARD);
            preparedStatement.setString(1, wordString);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapAudioWord(audioWord, resultSet);
            } else {
                audioWord = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(resultSet);
            close(preparedStatement);
        }
        return audioWord;
    }

    public AudioWord getAudioWord(String wordString, int createdBy) {
        AudioWord audioWord = new AudioWord();
        try {
            preparedStatement = connection.prepareStatement(SELECT_AUDIO_WORD_BY_USER);
            preparedStatement.setString(1, wordString);
            preparedStatement.setInt(2, createdBy);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapAudioWord(audioWord, resultSet);
            } else {
                audioWord = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return audioWord;
    }

    public static void main(String[] args) {
        AudioWord audioWord = new AudioWord();
        audioWord.setLanguage("English");
        audioWord.setExtension(".mp3");
        audioWord.setWordString("aaaa");
        AudioWordDAO audioWordDAO = new AudioWordDAO();
        audioWordDAO.insertAudioWord(audioWord);
        audioWordDAO.close();
    }

    public List<AudioWord> getAudioWords(List<String> words) {
        List<AudioWord> wordList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_AUDIO_WORDS_BY_WORD_STRING
                    .replace("?", "(\"" + String.join("\",\"", words) + "\")"));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AudioWord word = new AudioWord();
                mapAudioWord(word, resultSet);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        System.out.println(wordList);
        return wordList;
    }

    public List<AudioWord> getAudioWords(int created_by, List<String> words, boolean addStandard) {
        List<AudioWord> wordList = new ArrayList<>();
        try {
        String query = addStandard ? SELECT_AUDIO_WORDS_BY_WORD_STRING_AND_USER_OR_STANDARD :
                SELECT_AUDIO_WORDS_BY_WORD_STRING_AND_USER;
            preparedStatement = connection.prepareStatement(query
                    .replace("(?)", "(\"" + String.join("\",\"", words) + "\")"));
            preparedStatement.setInt(1, created_by);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AudioWord word = new AudioWord();
                mapAudioWord(word, resultSet);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        System.out.println(wordList);
        return wordList;
    }

    public List<AudioWord> getAudioWords() {
        List<AudioWord> wordList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_AUDIO_WORDS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AudioWord word = new AudioWord();
                mapAudioWord(word, resultSet);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        System.out.println(wordList);
        return wordList;
    }

    public List<AudioWord> getAudioWords(int createdBy) {
        List<AudioWord> wordList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_AUDIO_WORDS_BY_USER);
            preparedStatement.setInt(1, createdBy);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AudioWord word = new AudioWord();
                mapAudioWord(word, resultSet);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        System.out.println(wordList);
        return wordList;
    }

    public List<AudioWord> getStandardAudioWords() {
        List<AudioWord> wordList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_AUDIO_WORDS_BY_STANDARD);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AudioWord word = new AudioWord();
                mapAudioWord(word, resultSet);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        System.out.println(wordList);
        return wordList;
    }

    public void insertAudioWords(List<AudioWord> audioWords) {
        List<ResultSet> sets = new ArrayList<>();
        try {
            for (AudioWord audioWord : audioWords) {
                preparedStatement = connection.prepareStatement(INSERT_AUDIO_WORD, Statement.RETURN_GENERATED_KEYS);

                mapAudioWordInsert(audioWord, preparedStatement);

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

    public void insertAudioWord(AudioWord audioWord) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_AUDIO_WORD, Statement.RETURN_GENERATED_KEYS);

            mapAudioWordInsert(audioWord, preparedStatement);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
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
            close(resultSet);
            close(preparedStatement);
        }
    }

    public void updateAudioWord(int audioWordId, InputStream inputStream) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_AUDIO_WORD_BY_ID);

            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setInt(2, audioWordId);

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
            close(connection);
        }
    }

    public void deleteAudioWord(int audioWord) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_AUDIO_WORD_BY_ID);

            preparedStatement.setInt(1, audioWord);

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

    private void mapAudioWordInsert(AudioWord audioWord,  PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, audioWord.getWordString());
        preparedStatement.setString(2, audioWord.getLanguage());
        preparedStatement.setString(3, audioWord.getExtension());
        preparedStatement.setBlob(4, audioWord.getAudioWordStream());
        preparedStatement.setBoolean(5, audioWord.getStandard());
        preparedStatement.setInt(6, audioWord.getCreatedBy());
    }

    private void mapAudioWord(AudioWord audioWord, ResultSet resultSet) throws SQLException {
        audioWord.setId(resultSet.getInt("id"));
        audioWord.setWordString(resultSet.getString("word_string"));
        audioWord.setLanguage(resultSet.getString("language"));
        audioWord.setExtension(resultSet.getString("extension"));
        audioWord.setAudioWordBlob(resultSet.getBlob("audio_word_blob"));
        audioWord.setStandard(resultSet.getBoolean("is_standard"));
    }

    public AudioWordDAO() {
    }
}
