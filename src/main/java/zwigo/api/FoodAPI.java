package zwigo.api;

import zwigo.classes.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/foodItems")
@MultipartConfig(
        location = "D:\\ServletApplication\\src\\main\\resources",
        maxFileSize = 10485760L,
        maxRequestSize = 20971520L,
        fileSizeThreshold = 0
)
public class FoodAPI extends HttpServlet {
    static AdminUtility utility = new AdminUtility();
    Connection c;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in get of foodAPI");
        GetFoodItemsFromDB foodItemsObj = new GetFoodItemsFromDB();
        ArrayList<FoodItem> foodItems = foodItemsObj.getFoodItems();
        req.setAttribute("foodItems", foodItems);
        try {
            dispatcherCall(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e + "-----inGet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in post of foodAPI");
        Enumeration ei=req.getParameterNames();
        while(ei.hasMoreElements()){
            if(ei.nextElement().equals("bulkAddUpload")){
                    utility.bulkAddItems(req);
            }
        }
        try {
            if (req.getParameter("deleteId") != null) {
                doDelete(req, resp);
            } else if (req.getParameter("addNewItem")!=null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/addFoodItems.jsp");
                requestDispatcher.forward(req, resp);
            }else if (req.getParameter("deleteGroup")!=null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/deleteFoodGroup.jsp");
                requestDispatcher.forward(req, resp);
            }else if (req.getParameter("bulkAddItems")!=null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/bulkAddItems.html");
                requestDispatcher.forward(req, resp);
            }else if (req.getParameter("addNewGroup")!=null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/addNewFoodGroup.html");
                requestDispatcher.forward(req, resp);
            }else if (req.getParameter("editId")!=null) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/editFoodItem.jsp");
                requestDispatcher.forward(req, resp);
            } else if (req.getParameter("button").equals("deleteButton")) {
                doDelete(req, resp);
            } else if (req.getParameter("button").equals("1") || req.getParameter("button").equals("-1") || req.getParameter("button").equals("0")) {
                doPut(req, resp);
            } else if (req.getParameter("button").equals("addItem")) {
                    utility.addSingleItem(req.getParameter("item_name"), req.getParameter("item_price"), req.getParameter("item_quantity"), req.getParameter("item_type"),req.getParameter("group"));
            } else if (req.getParameter("button").equals("addGroup")) {
                    utility.addNewFoodGroup(req.getParameter("groupName"));
            }else if (req.getParameter("button").equals("deleteGroup")) {
                    utility.deleteFoodGroup(Integer.parseInt(req.getParameter("group")));
            }
        } catch (Exception e) {
            System.out.println(e + "in doPost FoodAPI");
        }
        try {
            dispatcherCall(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            switch (Integer.parseInt(req.getParameter("button"))) {
                case 1:
                    String[] recordIds = req.getParameterValues("checked");
                    utility.markAvailable(recordIds);
                    break;
                case -1:
                    String[] Ids = req.getParameterValues("checked");
                    utility.markUnavailable(Ids);
                    break;
                case 0:
                    utility.updateFoodData(Integer.parseInt(req.getParameter("id")), req);
                    break;
                default:
                    System.out.println("variable not valid");
                    break;
            }
            dispatcherCall(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e + "------InDoPut");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            try {
                if (req.getParameter("button").equals("deleteButton")) {
                    String[] deleteIds = req.getParameterValues("checked");
                    utility.bulkDeleteItem(deleteIds);
                }
            } catch (NullPointerException e) {
                utility.deleteById(Integer.parseInt(req.getParameter("deleteId")));
            } finally {
                dispatcherCall(req, resp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected void dispatcherCall(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        ServletContext context = req.getServletContext();
        if (req.getParameter("sort") == null) {
            GetFoodItemsFromDB getFoodItemsFromDB = new GetFoodItemsFromDB();
            getFoodItemsFromDB.clearFoodItemsInMemory();
            PreparedStatement query = (PreparedStatement) context.getAttribute("sortQuery");
            getFoodItemsFromDB.setFoodItems(query);
        }
        GetFoodItemsFromDB foodItemsObj=new GetFoodItemsFromDB();
        context.setAttribute("foodItems",foodItemsObj.getFoodItems());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/adminPage.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("destroyed!");
    }
}
