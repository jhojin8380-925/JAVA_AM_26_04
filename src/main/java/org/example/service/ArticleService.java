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

  public List<Article> dolist(Connection conn) {
    return articleDao.dolist(conn);
  }

  public boolean doSelect(Connection conn, int id) {
    return articleDao.doSelect(conn, id);
  }

  public int domodify(Connection conn, int id, String title, String body) {
    return articleDao.domodify(conn, id, title, body);
  }

  public List<Article> dodetail(Connection conn, int id) {
    return articleDao.dodetail(conn, id);
  }

  public int dodelete(Connection conn, int id) {
    return articleDao.dodelete(conn, id);
  }
}
