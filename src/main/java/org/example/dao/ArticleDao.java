package org.example.dao;

import org.example.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {

  public int dowrite(Connection conn, String title, String body) {

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW(),");
    sql.append("updateDate = NOW(),");
    sql.append("title = ?,", title);
    sql.append("`body` = ?;", body);
    return DBUtil.insert(conn, sql);
  }

  public List<Article> dolist(Connection conn) {
    List<Article> articles = new ArrayList<>();
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }
    return articles;
  }

  public boolean doSelect(Connection conn, int id) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?;", id);

    return DBUtil.selectRowBooleanValue(conn, sql);
  }

  public int domodify(Connection conn, int id, String title, String body) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    if (title.length() > 0) {
      sql.append(", title = ?", title);
    }
    if (body.length() > 0) {
      sql.append(", `body` = ?", body);
    }
    sql.append("WHERE id = ?;", id);

    return DBUtil.update(conn, sql);
  }

  public List<Article> dodetail(Connection conn, int id) {
    List<Article> articles = new ArrayList<>();
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }
    return articles;
  }

  public int dodelete(Connection conn, int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?;", id);

    return DBUtil.delete(conn, sql);
  }
}
