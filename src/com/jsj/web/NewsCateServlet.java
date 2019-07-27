package com.jsj.web;

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
import java.util.List;

@WebServlet("/newsCate")
public class NewsCateServlet extends HttpServlet {

    private NewsService newsService = ServiceFactory.getNewsService();

    private NewsCateService newsCateService = ServiceFactory.getNewsCateService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer cateId = Integer.valueOf(request.getParameter("cateId"));
        NewsCate newsCate = newsCateService.getNewsCateById(cateId);
        List<News> newsList = newsService.getNewsListByCate(cateId);
        request.setAttribute("newsList",newsList);
        request.setAttribute("cateName",newsCate.getName());
        request.getRequestDispatcher("newsCate.jsp").forward(request,response);
    }
}
