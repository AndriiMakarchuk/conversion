package controller.servlet;

import model.dao.ConversionDAO;
import model.entity.ConversionRecord;
import model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ConversionResult extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession userSession = req.getSession();

        if(userSession.getAttribute("user") == null) {
            resp.sendRedirect("/login");
        } else {
            User user = (User) userSession.getAttribute("user");
            ConversionDAO conversionDAO = new ConversionDAO();
            List<ConversionRecord> conversionRecordList = conversionDAO.getConversions(user.getId());
            req.setAttribute("conversions", conversionRecordList);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/conversionResult.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
