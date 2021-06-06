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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AudioWordList extends HttpServlet {
    private Map<Integer, AudioWord> allAudioWords;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<AudioWord> audioWords;
        AudioWordDAO audioWordDAO = new AudioWordDAO();
        if("standard".equals(name)) {
            audioWords = audioWordDAO.getStandardAudioWords();
        } else if("personal".equals(name) && req.getSession().getAttribute("user") != null) {
            User user = (User) req.getSession().getAttribute("user");
            audioWords = audioWordDAO.getAudioWords(user.getId());
        } else {
            audioWords = audioWordDAO.getStandardAudioWords();
        }
        audioWordDAO.close();
        allAudioWords = audioWords.stream().collect(Collectors.toMap(AudioWord::getId, AudioWord::getAudioWord));
        req.setAttribute("wordList", audioWords);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/audioWordList.jsp");
        requestDispatcher.forward(req, resp);
        //checkAdminAccess(req, resp, "WEB-INF/conversion/audioWordList.jsp", "/conversion");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("Upload New Audio File".equals(req.getParameter("action"))) {
            req.getSession().setAttribute("wordId", req.getParameter("wordId"));
            resp.sendRedirect("/addAudioWord?name=" + req.getParameter("name") + "&type=update&wordName=" + req.getParameter("wordName"));
        } else if("Delete".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("wordId"));
            AudioWordDAO audioWordDAO = new AudioWordDAO();
            audioWordDAO.deleteAudioWord(id);
            audioWordDAO.close();
            resp.sendRedirect("/audioWordList?name="+req.getParameter("name"));
        }
    }
}
