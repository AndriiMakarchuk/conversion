package controller.servlet;

import controller.RandomFileNameController;
import conversion.ConversionTXTToMP3;
import model.dao.AudioWordDAO;
import model.dao.ConversionDAO;
import model.entity.ConversionRecord;
import model.entity.audioWord.AudioWord;
import model.entity.user.User;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class AddAudioWords extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> unknownWords = null;
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("unknownWords") != null) {
            unknownWords = (List<String>) httpSession.getAttribute("unknownWords");
        }

        if(unknownWords == null || unknownWords.size() == 0) {
            doConversion(req);
            //req.getSession().setAttribute("inputStream", null);
            resp.sendRedirect("/conversionResult");
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/addAudioWords.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("uploadWordFiles".equals(req.getParameter("action"))) {
            List<String> unknownWords = (List<String>) req.getSession().getAttribute("unknownWords");
            for(String str : unknownWords) {
                createAudioWord(req, str);
            }
            doConversion(req);
            req.getSession().setAttribute("unknownWords", null);
            //req.getSession().setAttribute("inputStream", null);

            resp.sendRedirect("/conversionResult");
        }
    }

    private void createAudioWord(HttpServletRequest request, String wordName) throws ServletException, IOException {
        AudioWordDAO audioWordDAO = new AudioWordDAO();
        AudioWord audioWord = new AudioWord();
        audioWord.setWordString(wordName);
        audioWord.setLanguage("English");
        audioWord.setExtension(".mp3");
        audioWord.setAudioWordStream(request.getPart(wordName).getInputStream());
        audioWordDAO.insertAudioWord(audioWord);
    }

    private ConversionRecord doConversion(HttpServletRequest req) throws IOException {
        String fileName = new RandomFileNameController().nextString();

        ConversionRecord conversion = new ConversionRecord();
        HttpSession userSession = req.getSession();
        User user = (User) userSession.getAttribute("user");
        InputStream inputStream = (InputStream) userSession.getAttribute("inputStream");
        conversion.setCreatedDate(Date.valueOf(LocalDate.now()));
        conversion.setConverted(false);
        conversion.setError(false);
        conversion.setCreatedBy(user.getId());
        conversion.setFileName(fileName);
        conversion.setConversionSourceType("txt");
        conversion.setConversionDestinationType("mp3");
        conversion.setSourceFileStream(inputStream);

        ConversionDAO conversionDAO = new ConversionDAO();
        conversionDAO.insertConversion(conversion);
        ConversionTXTToMP3 conversionTXTToMP3 = new ConversionTXTToMP3(conversion);
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.start(new Runnable() {
            public void run() {
                conversionTXTToMP3.convert();
                asyncContext.complete();
            }
        });
        return conversion;
    }
}