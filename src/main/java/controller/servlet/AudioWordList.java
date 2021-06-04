package controller.servlet;

import model.dao.AudioWordDAO;
import model.entity.audioWord.AudioWord;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AudioWordList extends HttpServlet {
    private Map<Integer, AudioWord> allAudioWords;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AudioWordDAO audioWordDAO = new AudioWordDAO();
        List<AudioWord> audioWords = audioWordDAO.getAudioWords();
        System.out.println("------AudioWOrds_____" + audioWords);
        allAudioWords = audioWords.stream().collect(Collectors.toMap(AudioWord::getId, AudioWord::getAudioWord));
        req.setAttribute("wordList", audioWords);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/audioWordList.jsp");
        requestDispatcher.forward(req, resp);
        //checkAdminAccess(req, resp, "WEB-INF/conversion/audioWordList.jsp", "/conversion");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("Upload New Audio File".equals(req.getParameter("action"))) {
            String wordString = req.getParameter("wordName");
            //System.out.println(wordString);
        } else if("Delete".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("wordId"));
            AudioWordDAO audioWordDAO = new AudioWordDAO();
            audioWordDAO.deleteAudioWord(id);
            resp.sendRedirect("/audioWordList");
        } else if("Add New Word".equals(req.getParameter("action"))) {
            resp.sendRedirect("/addAudioWord");
        }
    }
}
