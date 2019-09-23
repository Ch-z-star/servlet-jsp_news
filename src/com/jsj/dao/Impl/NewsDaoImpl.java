package com.jsj.dao.Impl;

import com.jsj.dao.NewsDao;
import com.jsj.entity.News;
import com.jsj.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao {

    @Override
    public int getCount() throws SQLException {
        int res = -1;
        Connection connection = JdbcUtils.getConnection();
        String sql = "select count(*) from news";
        try (Statement statement = connection.createStatement()){
            try (ResultSet resultSet = statement.executeQuery(sql)){
                if (resultSet.next()){
                    res = resultSet.getInt(1);
                }
            }
        }
        return res;
    }

    @Override
    public int getCountByCate(Integer cateId) throws SQLException {
        int res = -1;
        Connection connection = JdbcUtils.getConnection();
        String sql = "select count(*) from news where cate_id = ? order by time desc";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,cateId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    res = resultSet.getInt(1);
                }
            }
        }
        return res;
    }

    @Override
    public List<News> getNewsList(Integer beginIndex, Integer length) throws SQLException {
        List<News> newsList = new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        String sql = "select * from news order by time desc limit ?, ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, beginIndex);
            preparedStatement.setInt(2, length);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    News news = new News();
                    news.setId(resultSet.getInt("id"));
                    news.setCateId(resultSet.getInt("cate_id"));
                    news.setTitle(resultSet.getString("title"));
                    news.setAuthor(resultSet.getString("author"));
                    news.setTime(resultSet.getDate("time"));
                    news.setContent(resultSet.getString("content"));
                    newsList.add(news);
                }
            }
        }
        JdbcUtils.releaseConnection(connection);
        return newsList;
    }

    @Override
    public List<News> getNewsListByCate(Integer cateId,Integer beginIndex,Integer length) throws SQLException {
        List<News> newsList = new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        String sql = "select * from news where cate_id = ? limit ?, ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,cateId);
            preparedStatement.setInt(2, beginIndex);
            preparedStatement.setInt(3, length);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    News news = new News();
                    news.setId(resultSet.getInt("id"));
                    news.setCateId(resultSet.getInt("cate_id"));
                    news.setTitle(resultSet.getString("title"));
                    news.setAuthor(resultSet.getString("author"));
                    news.setTime(resultSet.getDate("time"));
                    news.setContent(resultSet.getString("content"));
                    newsList.add(news);
                }
            }
        }
        JdbcUtils.releaseConnection(connection);
        return newsList;
    }

    @Override
    public int insert(News news) throws SQLException {
        int res;
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into news(cate_id,title,author,time,content) values (?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,news.getCateId());
            preparedStatement.setString(2,news.getTitle());
            preparedStatement.setString(3,news.getAuthor());
            preparedStatement.setDate(4,new Date(news.getTime().getTime()));
            preparedStatement.setString(5,news.getContent());
            res = preparedStatement.executeUpdate();
        }
        JdbcUtils.releaseConnection(connection);
        return res;
    }

    @Override
    public News getById(Integer id) throws SQLException {
        News news = new News();
        Connection connection = JdbcUtils.getConnection();
        String sql = "select * from news where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    news.setId(resultSet.getInt("id"));
                    news.setCateId(resultSet.getInt("cate_id"));
                    news.setTitle(resultSet.getString("title"));
                    news.setAuthor(resultSet.getString("author"));
                    news.setTime(resultSet.getDate("time"));
                    news.setContent(resultSet.getString("content"));
                }
            }
        }
        JdbcUtils.releaseConnection(connection);
        return news;
    }

    @Override
    public List<News> getAll() throws SQLException {
        List<News> allNews = new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        String sql = "select * from news";
        try(Statement statement = connection.createStatement();ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                News news = new News();
                news.setId(resultSet.getInt("id"));
                news.setCateId(resultSet.getInt("cate_id"));
                news.setTitle(resultSet.getString("title"));
                news.setAuthor(resultSet.getString("author"));
                news.setTime(resultSet.getDate("time"));
                news.setContent(resultSet.getString("content"));
                allNews.add(news);
            }
        }
        JdbcUtils.releaseConnection(connection);
        return allNews;
    }

    @Override
    public int deleteById(Integer id) throws SQLException {
        int res;
        Connection connection = JdbcUtils.getConnection();
        String sql = "delete from news where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            res = preparedStatement.executeUpdate();
        }
        JdbcUtils.releaseConnection(connection);
        return res;
    }
}
