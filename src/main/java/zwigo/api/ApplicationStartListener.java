package zwigo.api;
import zwigo.classes.GetFoodItemsFromDB;
import javax.servlet.*;
import java.sql.*;

public class ApplicationStartListener implements ServletContextListener {
    Connection c;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Application Initialized");
        ServletContext context = servletContextEvent.getServletContext();
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost/Zwigo", "postgres", "postgres");
            PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM food_items WHERE is_deleted='No' ORDER BY item_id ASC;");
            context.setAttribute("sortQuery", preparedStatement);
            context.setAttribute("connection", c);
            GetFoodItemsFromDB getFoodItemsFromDB = new GetFoodItemsFromDB();
            getFoodItemsFromDB.setFoodItems(preparedStatement);
            context.setAttribute("recordLimit",10);
            context.setAttribute("pageNo",1);
            context.setAttribute("endIndex",(Integer)context.getAttribute("recordLimit")*(Integer)context.getAttribute("pageNo"));
            context.setAttribute("startIndex",(Integer)context.getAttribute("endIndex")-(Integer)context.getAttribute("recordLimit"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Application Destroyed");
    }
}
