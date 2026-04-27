package org.example.service;

import org.example.dao.ArticleDao;

import java.sql.Connection;

public class ArticleService {

  private ArticleDao articleDao;

  public ArticleService() {
    this.articleDao = new ArticleDao();
  }

  public int dowrite(Connection conn, String title, String body) {
    return articleDao.dowrite(conn, title, body);
  }




}
