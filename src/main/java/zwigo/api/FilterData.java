package zwigo.api;
import zwigo.classes.GetFoodItemsFromDB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebFilter("/foodItems")
public class FilterData implements Filter {
    GetFoodItemsFromDB getFoodItemsFromDB = new GetFoodItemsFromDB();
    Connection c;
    PreparedStatement preparedStatement;
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost/Zwigo","postgres", "postgres");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse res=(HttpServletResponse) servletResponse;
        ServletContext context= req.getServletContext();
        c = (Connection) context.getAttribute("connection");
        if(req.getParameter("sort")!=null) {
            try {
                preparedStatement = c.prepareStatement("SELECT * FROM food_items fi left join food_item_groups fig on fi.group_id =fig.group_id where is_deleted='No'  AND (? = '%' OR fig.group_name ILIKE ?) and item_id::TEXT ilike ? and item_name ilike ? and price::text ilike ? and quantity::text ilike ? and item_type ilike ? and is_available ilike ?  ORDER BY "+req.getParameter("sort"));
                preparedStatement.setString(1,req.getSession().getAttribute("searchByGroup")!=null&&req.getSession().getAttribute("searchByGroup")!=""? (String) req.getSession().getAttribute("searchByGroup"):"%");
                preparedStatement.setString(2,req.getSession().getAttribute("searchByGroup")!=null&&req.getSession().getAttribute("searchByGroup")!=""? (String) req.getSession().getAttribute("searchByGroup"):"%");
                preparedStatement.setString(3, req.getSession().getAttribute("searchById")!=null&&req.getSession().getAttribute("searchById")!=""? (String) req.getSession().getAttribute("searchById") :"%");
                preparedStatement.setString(4,req.getSession().getAttribute("searchByName")!=null&&req.getSession().getAttribute("searchByName")!=""?"%"+ (String) req.getSession().getAttribute("searchByName")+"%":"%");
                preparedStatement.setString(5,req.getSession().getAttribute("searchByPrice")!=null&&req.getSession().getAttribute("searchByPrice")!=""?"%"+ (String) req.getSession().getAttribute("searchByPrice")+"%":"%");
                preparedStatement.setString(6,req.getSession().getAttribute("searchByQuantity")!=null&&req.getSession().getAttribute("searchByQuantity")!=""?"%"+ (String) req.getSession().getAttribute("searchByQuantity")+"%":"%");
                preparedStatement.setString(7,req.getSession().getAttribute("searchByType")!=null&&req.getSession().getAttribute("searchByType")!=""? (String) req.getSession().getAttribute("searchByType"):"%");
                preparedStatement.setString(8,req.getSession().getAttribute("searchByAvailability")!=null&&req.getSession().getAttribute("searchByAvailability")!=""? (String) req.getSession().getAttribute("searchByAvailability"):"%");
                System.out.println(preparedStatement);
            } catch (Exception e) {
                System.out.println(e);
            }
            getFoodItemsFromDB.clearFoodItemsInMemory();
            getFoodItemsFromDB.setFoodItems(preparedStatement);
            context.setAttribute("sortQuery", preparedStatement);
        }
        if(req.getParameter("search")!=null){
            try {
                PreparedStatement searchQuery=c.prepareStatement("select * from food_items fi left join food_item_groups fig on fi.group_id =fig.group_id where is_deleted='No'  AND (? = '%' OR fig.group_name ILIKE ?) and item_id::TEXT ilike ? and item_name ilike ? and price::text ilike ? and quantity::text ilike ? and item_type ilike ? and is_available ilike ?  order by item_id ASC");
                searchQuery.setString(1,req.getParameter("searchByGroup")!=null&&req.getParameter("searchByGroup")!=""?"%"+req.getParameter("searchByGroup")+"%":"%");
                searchQuery.setString(2,req.getParameter("searchByGroup")!=null&&req.getParameter("searchByGroup")!=""?"%"+req.getParameter("searchByGroup")+"%":"%");
                searchQuery.setString(3,req.getParameter("searchById")!=null&&req.getParameter("searchById")!=""?req.getParameter("searchById"):"%");
                searchQuery.setString(4,req.getParameter("searchByName")!=null&&req.getParameter("searchByName")!=""?"%"+req.getParameter("searchByName")+"%":"%");
                searchQuery.setString(5,req.getParameter("searchByPrice")!=null&&req.getParameter("searchByPrice")!=""?"%"+req.getParameter("searchByPrice")+"%":"%");
                searchQuery.setString(6,req.getParameter("searchByQuantity")!=null&&req.getParameter("searchByQuantity")!=""?"%"+req.getParameter("searchByQuantity")+"%":"%");
                searchQuery.setString(7,req.getParameter("searchByType")!=null&&req.getParameter("searchByType")!=""?req.getParameter("searchByType"):"%");
                searchQuery.setString(8,req.getParameter("searchByAvailability")!=null&&req.getParameter("searchByAvailability")!=""?req.getParameter("searchByAvailability"):"%");
                context.setAttribute("sortQuery",searchQuery);
                getFoodItemsFromDB.clearFoodItemsInMemory();
                getFoodItemsFromDB.setFoodItems(searchQuery);
                HttpSession session=req.getSession();
                session.setAttribute("searchById",req.getParameter("searchById"));
                session.setAttribute("searchByName",req.getParameter("searchByName"));
                session.setAttribute("searchByPrice",req.getParameter("searchByPrice"));
                session.setAttribute("searchByQuantity",req.getParameter("searchByQuantity"));
                session.setAttribute("searchByType",req.getParameter("searchByType"));
                session.setAttribute("searchByAvailability",req.getParameter("searchByAvailability"));
                session.setAttribute("searchByGroup",req.getParameter("searchByGroup"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(req.getParameter("recordLimitPerPage")!=null){
            context.setAttribute("recordLimit",Integer.parseInt(req.getParameter("recordLimit")));
            context.setAttribute("pageNo",1);
            context.setAttribute("endIndex",(Integer)context.getAttribute("recordLimit")*(Integer)context.getAttribute("pageNo"));
            context.setAttribute("startIndex",(Integer)context.getAttribute("endIndex")-(Integer)context.getAttribute("recordLimit"));
        }
        if(req.getParameter("pageNo")!=null){
            context.setAttribute("pageNo",Integer.parseInt(req.getParameter("pageNo")));
            context.setAttribute("endIndex",(Integer)context.getAttribute("recordLimit")*(Integer)context.getAttribute("pageNo"));
            context.setAttribute("startIndex",(Integer)context.getAttribute("endIndex")-(Integer)context.getAttribute("recordLimit"));
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
