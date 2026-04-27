package org.example.controller;

import org.example.Article;
import org.example.service.ArticleService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ArticleController {
  private Connection conn;
  private Scanner sc;

  private ArticleService articleService;

  public ArticleController(Scanner sc, Connection conn) {
    this.sc = sc;
    this.conn = conn;
    this.articleService = new ArticleService();
  }

  public void dowrite() {
    System.out.println("== 글쓰기 ==");
    System.out.print("제목 : ");
    String title = sc.nextLine();
    System.out.print("내용 : ");
    String body = sc.nextLine();

    int id = articleService.dowrite(conn, title, body);
    System.out.println(id + "번 글이 생성됨");
  }

  public void dolist() {
    System.out.println("== 목록 ==");

    List<Article> articles = articleService.dolist(conn);

    if (articles.size() == 0) {
      System.out.println("게시글이 없습니다");
      return;
    }
    System.out.println("  번호  /   제목  ");
    for (Article article : articles) {
      System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
    }
  }

}