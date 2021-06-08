package daoTests;

import model.DBManager;
import model.dao.AudioWordDAO;
import model.entity.audioWord.AudioWord;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class AudioWordDAOTest {
    public static List<String> words = new ArrayList<>();
    @BeforeClass
    public static void beforeTest() {
        DBManager.isTest = true;
        for(int i = 0; i<120; i++) {
            words.add("Word1"+i);
        }
    }

    @Test
    public void getAudioWordTest() {

        AudioWordDAO audioWordDAO = new AudioWordDAO();
        List<String> half = new ArrayList<>();
        for (String word : words) {
            AudioWord audioWord = new AudioWord();
            audioWord.setWordString(word);
            audioWord.setLanguage("English");
            audioWord.setExtension(".mp3");
            audioWord.setStandard(false);
            audioWord.setCreatedBy(1);
            audioWord.setAudioWordStream(new ByteArrayInputStream(new byte[]{123,127,87,73}));
            audioWordDAO.insertAudioWord(audioWord);
            if(words.indexOf(word)%2 == 0) {
                half.add(word);
            }
        }

        List<AudioWord> audioWords = audioWordDAO.getAudioWords(1, words, false);
        Assert.assertEquals(audioWords.size(), words.size());
        List<AudioWord> audioWords1 = audioWordDAO.getAudioWords(words);
        Assert.assertEquals(audioWords1.size(), words.size());
        List<AudioWord> audioWords2 = audioWordDAO.getAudioWords(5);
        Assert.assertEquals(audioWords2.size(), 0);
        List<AudioWord> audioWords3 = audioWordDAO.getAudioWords(1, half, false);
        Assert.assertEquals(audioWords3.size(), half.size());
    }
}
