package zwigo.api;

import zwigo.classes.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns ={ "/customer"},asyncSupported = true)
public class CustomerAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerUtility customerUtility = new CustomerUtility();
        HttpSession session = req.getSession();
        try {
            customerUtility.setGroupedFoodItems(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("in get of CustomerAPI");
        try {
            if (req.getParameter("page").equals("getOrders")) {
                customerUtility.setOrderDetails(req);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ordersPage.jsp");
                requestDispatcher.forward(req, resp);
            }if (req.getParameter("page").equals("getSingleOrderData")) {
                customerUtility.setSingleOrderData(req);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/SingleOrderDetails.jsp");
                requestDispatcher.forward(req, resp);
            }
            if (req.getParameter("page").equals("accountDetails")) {
                HttpSession session1 = req.getSession();
                session1.setAttribute("accountInfo", customerUtility.getAccountData(req));
                RequestDispatcher  requestDispatcher = req.getRequestDispatcher("/customerAccount.jsp");
                requestDispatcher.forward(req, resp);
            }
            if (req.getParameter("page").equals("getCart")) {
                customerUtility.setCustomerCartItems(req);
                RequestDispatcher  requestDispatcher = req.getRequestDispatcher("/cartPage.jsp");
                requestDispatcher.forward(req, resp);
            }
        } catch (Exception e) {
            System.out.println(e + req.getParameter("page")+"--in GetCustomerAPI");
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customerPage.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CustomerUtility customerUtility = new CustomerUtility();
        RequestDispatcher requestDispatcher;
        System.out.println("in post of CustomerAPI");
        try {
            customerUtility.setGroupedFoodItems(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            switch (Integer.parseInt(req.getParameter("button"))) {
                case 1:
                    try {
                        customerUtility.applyAccountChanges(req);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    requestDispatcher = req.getRequestDispatcher("/customerAccount.jsp");
                    req.setAttribute("successMsg", "Successfully Updated Account Details!");
                    requestDispatcher.include(req, resp);
                    break;
                case -1:
                    try {
                        customerUtility.setGroupedFoodItems(req);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    requestDispatcher = req.getRequestDispatcher("/customerPage.jsp");
                    requestDispatcher.forward(req, resp);
                    break;
                case -2:
                    requestDispatcher = req.getRequestDispatcher("/cartPage.jsp");
                    requestDispatcher.forward(req, resp);
                    break;
                case -3:
                    customerUtility.deleteCartItemById(req);
                    customerUtility.setCustomerCartItems(req);
                    requestDispatcher = req.getRequestDispatcher("/cartPage.jsp");
                    requestDispatcher.forward(req, resp);
                    break;
                case 2:
                    customerUtility.addItemToCart(req);
                    customerUtility.setCustomerCartItems(req);
                    requestDispatcher = req.getRequestDispatcher("/customerPage.jsp");
                    requestDispatcher.forward(req, resp);
                    break;
                case 0:
                    customerUtility.updateCartDetails(req);
                    customerUtility.setCustomerCartItems(req);
                    CustomerUtility custUtility=new CustomerUtility();
                    HashMap<String,String> custDetail=custUtility.getCartItem(Integer.parseInt(req.getParameter("id")),req);
                    req.getSession().setAttribute("custDetails",custDetail);
                    requestDispatcher = req.getRequestDispatcher("/editCartItemDetails.jsp");
                    req.setAttribute("successMsg", "Successfully Updated Cart Item Details!");
                    requestDispatcher.forward(req, resp);
                    break;
                case 3:
                    try {
                        customerUtility.setGroupedFoodItems(req);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    customerUtility.checkOut(req);
                    requestDispatcher = req.getRequestDispatcher("/customerPage.jsp");
                    requestDispatcher.forward(req, resp);
                    break;
                case -4:
                    CustomerUtility custUtilityObj=new CustomerUtility();
                    HashMap<String,String> custDetails=custUtilityObj.getCartItem(Integer.parseInt(req.getParameter("id")),req);
                    req.getSession().setAttribute("custDetails",custDetails);
                    requestDispatcher = req.getRequestDispatcher("/editCartItemDetails.jsp");
                    requestDispatcher.forward(req, resp);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e+"---inpost");
            requestDispatcher = req.getRequestDispatcher("/customerPage.jsp");
            requestDispatcher.forward(req, resp);
        }


    }

}
