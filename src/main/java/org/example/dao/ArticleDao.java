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
}
