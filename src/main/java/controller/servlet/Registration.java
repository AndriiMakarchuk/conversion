package controller.servlet;
import model.dao.ProfileDAO;
import model.dao.UserDAO;
import model.entity.user.Profile;
import model.entity.user.User;
import model.entity.user.UserDetails;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();
        UserDetails details = new UserDetails();
        user.setLogin(req.getParameter("login"));
        try {
            String password = req.getParameter("password");
            user.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setProfile(new ProfileDAO().getProfile("Standard_User"));
        details.setFirstName(req.getParameter("firstName"));
        details.setLastName(req.getParameter("lastName"));
        details.setEmail(req.getParameter("email"));
        details.setPhone(req.getParameter("phone"));
        System.out.println(user);
        System.out.println(details);
        user.setDetails(details);
        new UserDAO().insertUser(user);
        resp.sendRedirect("/login");
    }

}
