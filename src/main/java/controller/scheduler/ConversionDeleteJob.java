package controller.scheduler;

import model.dao.ConversionDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ConversionDeleteJob implements Runnable {

    @Override
    public void run() {
        // Do your hourly job here.
        System.out.println("Job trigged by scheduler");
        Timestamp halfDayAgo = new Timestamp(System.currentTimeMillis() - 12 * 60 * 60 * 1000);
        ConversionDAO conversionDAO = new ConversionDAO();
        conversionDAO.deleteConversion(halfDayAgo);
        conversionDAO.close();
    }
}