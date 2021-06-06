package controller.servlet;

import conversion.ConversionHelper;
import model.entity.user.User;

import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class MainPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/conversion/conversion.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("action").equals("conversion")) {
            Part part = req.getPart("conversionFile");
            InputStream inputStream = part.getInputStream();

            HttpSession httpSession = req.getSession();
            User user = (User) httpSession.getAttribute("user");
            System.out.println("----------------------------------------");
            System.out.println(req.getParameter("useStandard"));
            System.out.println("----------------------------------------");
            boolean useStandard = "on".equals(req.getParameter("useStandard"));

            ConversionHelper conversionHelper = new ConversionHelper(new String(inputStream.readAllBytes()), useStandard,  user.getId());
            inputStream.reset();

            httpSession.setAttribute("useStandard", useStandard);
            httpSession.setAttribute("inputStream", inputStream);
            httpSession.setAttribute("unknownWords", conversionHelper.getUnknownWords());
            resp.sendRedirect("/addAudioWords");
        }
    }
}
