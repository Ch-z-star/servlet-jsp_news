package com.jsj.web.manage;

import com.jsj.entity.NewsVo;
import com.jsj.entity.Page;
import com.jsj.factory.ServiceFactory;
import com.jsj.service.NewsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/admin/manage/news")
public class NewsServlet extends HttpServlet {

    private NewsService newsService = (NewsService) ServiceFactory.getService("NewsService");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageIndex = request.getParameter("pageIndex")!=null?Integer.valueOf(request.getParameter("pageIndex")):1;
        int pageSize = request.getParameter("pageSize")!=null?Integer.valueOf(request.getParameter("pageSize")):10;
        Page<NewsVo> newsVoPage = newsService.getNewsVoPage(pageIndex,pageSize);
        request.setAttribute("newsVoPage", newsVoPage);
        request.getRequestDispatcher("/WEB-INF/view/admin/manage/news.jsp").forward(request,response);
    }

    /**
     * 删除新闻文章
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            Integer id = Integer.valueOf(request.getParameter("id"));
            int res = newsService.deleteNewsById(id);
            if (res>0){
                out.println("新闻删除成功");
            }else {
                out.println("新闻删除失败");
            }
            response.setHeader("refresh", "2;url=/admin/manage/news");
        }
    }


}
