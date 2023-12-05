package zwigo.api;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("initialized");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getUserPrincipal().getName()+"---");
        HttpSession session=req.getSession();
        session.setAttribute("username",req.getUserPrincipal().getName());
        if(req.getUserPrincipal()==null){
            resp.sendRedirect("index.html?error=true");
        }
        else if(req.isUserInRole("Admin")){
            resp.sendRedirect("foodItems");
        }
        else if(req.isUserInRole("Customer")){
            resp.sendRedirect("customer");
        }
    }
}
