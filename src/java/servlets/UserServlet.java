
package servlets;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.*;


public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        RoleService rs = new RoleService();
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        try {
            
            if (action != null && action.equals("edit")) {
                User user = us.get(email);
                request.setAttribute("editUser", user);
            } else if (action != null && action.equals("delete")) {
                us.delete(email);
            }
            
            List<User> users = us.getAll();
            request.setAttribute("users", users);

            List<Role> roles = rs.getAll();
            request.setAttribute("roles", roles);

        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("message", "error");
        }
        
        request.setAttribute("action", null);
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        String action = request.getParameter("action");
        String email = request.getParameter("email_add");
        String first = request.getParameter("first_name_add");
        String last = request.getParameter("last_name_add");
        String password = request.getParameter("password_add");
        Boolean active = false;
        int role = Integer.parseInt(request.getParameter("roles_add"));
        
        System.out.println(action + email + active + first + last + password + role);
        System.out.println(email);
        
        try {
            if (action.equals("add")) {
                us.insert(email, active, first, last, password, role);
            } else if (action.equals("update")) {
                User user = new User(email, active, first, last, password, role);
                us.update(user);
            }
            List<User> users = us.getAll();
            request.setAttribute("users", users);
            
            List<Role> rolesList = rs.getAll();
            request.setAttribute("roles", rolesList);
            
            
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("message", "error");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }
}
