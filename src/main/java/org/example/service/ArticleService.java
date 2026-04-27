package org.example.service;

import org.example.Article;
import org.example.dao.ArticleDao;

import java.sql.Connection;
import java.util.List;

public class ArticleService {

  private ArticleDao articleDao;

  public ArticleService() {
    this.articleDao = new ArticleDao();
  }

  public int dowrite(Connection conn, String title, String body) {
    return articleDao.dowrite(conn, title, body);
  }

public List<Article> dolist(Connection conn) { return articleDao.dolist(conn); }


}
