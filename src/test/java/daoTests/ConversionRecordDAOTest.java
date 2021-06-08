package daoTests;

import model.DBManager;
import model.dao.ConversionDAO;
import model.entity.ConversionRecord;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ConversionRecordDAOTest {
    @BeforeClass
    public static void beforeTest() {
        DBManager.isTest = true;
    }

    @Test
    public void getConversionTest() {
        ConversionRecord conversion = new ConversionRecord();

        InputStream inputStream = new ByteArrayInputStream("Hello dear friend. How are you!".getBytes());
        conversion.setConverted(false);
        conversion.setError(false);
        conversion.setCreatedBy(1);
        conversion.setFileName("randomfileasdu23");
        conversion.setConversionSourceType("txt");
        conversion.setConversionDestinationType("mp3");
        conversion.setSourceFileStream(inputStream);
        ConversionDAO conversionDAO = new ConversionDAO();
        conversionDAO.insertConversion(conversion);
        Assert.assertNotEquals(conversion.getId(), 0);
        conversionDAO.updateConversion(conversion.getId(), true, false);
        Assert.assertTrue(conversionDAO.getConversion(conversion.getId()).getConverted());
    }
}
