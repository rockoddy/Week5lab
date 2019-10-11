package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 794471
 */
public class ShoppingListServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        handle(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        handle(request, response);
    }

    
    private void handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        
        
        if(action != null)
        {
            switch (action) {
                case "register":
                    session.setAttribute("username", username);
                    ArrayList<String> itemList = new ArrayList<>();
                    session.setAttribute("list", itemList);
                    break;
                case "logout":
                    session.invalidate();
                    getServletContext().getRequestDispatcher("/WEB-INF/register.jsp")
                            .forward(request, response);
                    break;
                case "add":
                    {
                        String str = request.getParameter("item");
                        ArrayList tempList = (ArrayList) session.getAttribute("list");
                        tempList.add(str);
                        session.setAttribute("list", tempList);
                        break;
                    }
                case "delete":
                    {
                        String str = request.getParameter("thing");
                        ArrayList tempList = (ArrayList) session.getAttribute("list");
                        tempList.remove(str);
                        session.setAttribute("list", tempList);
                        break;
                    }
                default:
                    break;
            }
        }

        
        String name = (String) session.getAttribute("username");
        ArrayList list = (ArrayList) session.getAttribute("list");
        
        if(name == null)
        {
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp")
                .forward(request, response);
        }
        else 
        {
            request.setAttribute("name", name);           
            request.setAttribute("displayList", list);
            
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp")
                .forward(request, response);
        }
        
    }
    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }
}