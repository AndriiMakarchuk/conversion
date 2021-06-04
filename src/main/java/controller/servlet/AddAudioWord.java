package controller.servlet;

import model.dao.AudioWordDAO;
import model.entity.audioWord.AudioWord;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAudioWord extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/addAudioWord.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("Save".equals(req.getParameter("action"))) {
            if(!createAudioWord(req)) {
                req.setAttribute("errorMessage", "This word already exist!");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/addAudioWord.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("/audioWordList");
            }
        } else if ("Save & New".equals(req.getParameter("action"))) {
            if(!createAudioWord(req)) {
                req.setAttribute("errorMessage", "This word already exist!");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/addAudioWord.jsp");
                requestDispatcher.forward(req, resp);
            }
            resp.sendRedirect("/addAudioWord");
        }  else if ("Cancel".equals(req.getParameter("action"))) {
            resp.sendRedirect("/audioWordList");
        }
    }

    private Boolean createAudioWord(HttpServletRequest request) throws ServletException, IOException {
        String wordName = request.getParameter("wordName");

        AudioWordDAO audioWordDAO = new AudioWordDAO();
        AudioWord audioWord = audioWordDAO.getAudioWord(wordName);
        if(audioWord == null) {
            audioWord = new AudioWord();
            audioWord.setWordString(wordName);
            audioWord.setLanguage("English");
            audioWord.setExtension(".mp3");
            audioWord.setAudioWordStream(request.getPart("wordAudioData").getInputStream());
            return true;
        } else {
            return false;
        }
    }
}
