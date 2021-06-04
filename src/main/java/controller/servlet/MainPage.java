package controller.servlet;

import conversion.ConversionHelper;
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

            ConversionHelper conversionHelper = new ConversionHelper(new String(inputStream.readAllBytes()));
            inputStream.reset();

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("inputStream", inputStream);
            httpSession.setAttribute("unknownWords", conversionHelper.getUnknownWords());
            resp.sendRedirect("/addAudioWords");
        }
    }
}
