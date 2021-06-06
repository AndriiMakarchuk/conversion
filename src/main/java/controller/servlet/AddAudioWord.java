package controller.servlet;

import model.dao.AudioWordDAO;
import model.entity.audioWord.AudioWord;
import model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class AddAudioWord extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/addAudioWord.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage;
        String nameParameter = req.getParameter("name");
        String type = req.getParameter("type");

        if("update".equals(type)) {
            if("Save".equals(req.getParameter("action"))) {
                updateAudioWord(req);
            }
            resp.sendRedirect("/audioWordList?name=" + nameParameter);
        } else {

            if ("standard".equals(nameParameter)) {
                errorMessage = "This word already exist in Standard Dictionary!";
            } else {
                errorMessage = "This word already exist in your Dictionary!";
            }

            if ("Save".equals(req.getParameter("action"))) {
                if (!createAudioWord(req, nameParameter)) {
                    req.setAttribute("errorMessage", errorMessage);
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/addAudioWord.jsp");
                    requestDispatcher.forward(req, resp);
                } else {
                    resp.sendRedirect("/audioWordList?name=" + nameParameter);
                }
            } else if ("Save & New".equals(req.getParameter("action"))) {
                if (!createAudioWord(req, nameParameter)) {
                    req.setAttribute("errorMessage", errorMessage);
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/addAudioWord.jsp");
                    requestDispatcher.forward(req, resp);
                } else {
                    resp.sendRedirect("/addAudioWord?name=" + nameParameter);
                }
            } else if ("Cancel".equals(req.getParameter("action"))) {
                resp.sendRedirect("/audioWordList?name=" + nameParameter);
            }
        }
    }

    private Boolean createAudioWord(HttpServletRequest request, String nameParameter) throws ServletException, IOException {
        String wordName = request.getParameter("wordName");
        User user = (User) request.getSession().getAttribute("user");

        AudioWordDAO audioWordDAO = new AudioWordDAO();
        AudioWord audioWord = audioWordDAO.getAudioWord(wordName, user.getId());
        if("standard".equals(nameParameter)) {
            audioWordDAO.getStandardAudioWord(wordName);
        } else {
            audioWordDAO.getAudioWord(wordName, user.getId());
        }
        System.out.println(audioWord);
        if(audioWord == null) {

            audioWord = new AudioWord();
            audioWord.setWordString(wordName);
            audioWord.setLanguage("English");
            audioWord.setExtension(".mp3");
            audioWord.setStandard("standard".equals(nameParameter));
            audioWord.setAudioWordStream(request.getPart("wordAudioData").getInputStream());
            audioWord.setCreatedBy(user.getId());
            audioWordDAO.insertAudioWord(audioWord);
            audioWordDAO.close();
            return true;
        } else {
            audioWordDAO.close();
            return false;
        }
    }

    private void updateAudioWord(HttpServletRequest request) throws IOException, ServletException {
        AudioWordDAO audioWordDAO = new AudioWordDAO();
        int wordId = Integer.parseInt(String.valueOf(request.getSession().getAttribute("wordId")));
        InputStream inputStream = request.getPart("wordAudioData").getInputStream();
        audioWordDAO.updateAudioWord(wordId, inputStream);
        request.getSession().setAttribute("wordId", null);
        audioWordDAO.close();
    }
}
