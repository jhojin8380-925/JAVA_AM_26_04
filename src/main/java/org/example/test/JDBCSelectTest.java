package org.example.test;

import org.example.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {
    Connection conn = null;  // 커넥션 -> 자바랑 MySQL이랑 연결해주는
    PreparedStatement pstmt = null;
    ResultSet rs = null;   //MySQL 에서 값을 가져오기 위해 ResultSet 사용
    List<Article> articles = new ArrayList<>();
    try {
      Class.forName("org.mariadb.jdbc.Driver");
      String url = "jdbc:mariadb://127.0.0.1:3306/JDBC_AM_26_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
      conn = DriverManager.getConnection(url, "root", "");
      System.out.println("연결 성공!");           // ---------------------- 구현ㅁㄳ

      String sql = "SELECT *";
      sql += " FROM article";
      sql += " ORDER BY id DESC;";

      System.out.println(sql);
      Statement stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);



      pstmt = conn.prepareStatement(sql);

      while(rs.next()) {
        int id = rs.getInt("id");
        String regDate = rs.getString("regDate");
        String updateDate = rs.getString("updateDate");
        String title = rs.getString("title");
        String body = rs.getString("body");

        Article article = new Article(id, regDate, updateDate, title, body);

        articles.add(article);
      }

      for(Article article : articles) {
        System.out.println("게시글번호 : " + article.getId());
        System.out.println("등록일 : " + article.getRegDate());
        System.out.println("수정일 : " + article.getUpdateDate());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
      }
                                        // ----------------------------------구현
    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패" + e);
    } catch (SQLException e) {
      System.out.println("에러 : " + e);
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
