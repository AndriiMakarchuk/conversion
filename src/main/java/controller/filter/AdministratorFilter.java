package controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdministratorFilter implements Filter{
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Object profileId = req.getServletContext().getAttribute("profileId");
        if ((profileId == null || (Integer) profileId != 1) && !"personal".equals(req.getParameter("name"))) {
            ((HttpServletResponse) resp).sendRedirect("/conversion");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
