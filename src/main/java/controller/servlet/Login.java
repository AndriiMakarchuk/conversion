package controller.servlet;

import model.dao.UserDAO;
import model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/login.jsp");
            requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User user;
        String errorMessage = null;
        try {
            UserDAO userDAO = new UserDAO();
            user = userDAO.getUser(login);
            try {
                if (req.getParameter("password").equals(user.getPassword())) {
                    req.getServletContext().setAttribute("active", "active");
                    HttpSession userSession = req.getSession();
                    userDAO.getUserDetails(user);
                    userSession.setAttribute("user", user);
                    req.getServletContext().setAttribute("profileId", user.getProfile().getId());
                    resp.sendRedirect("/conversion");
                    return;
                } else {
                    errorMessage = "Incorrect password";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            errorMessage = "Incorrect login";
        }
        req.setAttribute("errorMessage", errorMessage);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/conversion/login.jsp");
        requestDispatcher.forward(req, resp);
    }

}