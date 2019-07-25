package com.jsj.web.manage;

import com.jsj.entity.Admin;
import com.jsj.entity.News;
import com.jsj.entity.NewsCate;
import com.jsj.factory.ServiceFactory;
import com.jsj.service.NewsCateService;
import com.jsj.service.NewsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/manage/publish")
public class PublishNewsServlet extends HttpServlet {

    private NewsCateService newsCateService = ServiceFactory.getNewsCateService();

    private NewsService newsService = ServiceFactory.getNewsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<NewsCate> allCate = newsCateService.getAllCate();
            request.getSession().setAttribute("allCate",allCate);
            request.getRequestDispatcher("/admin/manage/publish.jsp").forward(request,response);
        } catch (SQLException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("网络异常，请稍后重试");
            response.setHeader("refresh", "2;url=/admin/manage/index.jsp");
        }
    }


    /**
     * 发表文章
     * @param request
     * @param response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        News news = new News();
        news.setTitle(request.getParameter("title"));
        news.setAuthor(admin.getUsername());
        news.setCateId(Integer.valueOf(request.getParameter("cate")));
        news.setTime(new Date());
        news.setContent(request.getParameter("content"));
        try {
            int res = newsService.publish(news);
            if (res>0){
                out.println("新闻发表成功");
                response.setHeader("refresh", "2;url=/admin/manage/index.jsp");
            }else {
                out.println("新闻发表失败");
                response.setHeader("refresh", "2;url=/admin/manage/index.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("网络异常，请稍后重试");
            response.setHeader("refresh", "2;url=/admin/manage/index.jsp");
        }
    }

}
