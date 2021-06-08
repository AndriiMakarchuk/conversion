package daoTests;

import model.DBManager;
import model.dao.AudioWordDAO;
import model.dao.WordEndDAO;
import model.entity.audioWord.AudioWord;
import model.entity.audioWord.WordEnd;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class WordEndDAOTest {
    public static List<String> endings = new ArrayList<>();
    @BeforeClass
    public static void beforeTest() {
        DBManager.isTest = true;
        for(int i = 0; i<7; i++) {
            endings.add("Word1"+i);
        }
    }

    @Test
    public void getAudioWordTest() {

        WordEndDAO wordEndDAO = new WordEndDAO();
        List<WordEnd> ends = new ArrayList<>();
        for (String word : endings) {
            WordEnd wordEnd = new WordEnd();
            wordEnd.setName(word);
            wordEnd.setLanguage("English");
            wordEnd.setEndStream(new ByteArrayInputStream(new byte[]{123,127,87,73}));
            ends.add(wordEnd);
        }
        wordEndDAO.insertWordEnds(ends);

        List<WordEnd> testEnds = wordEndDAO.getAudioWords("English");
        Assert.assertEquals(testEnds.size(), ends.size());
    }
}
