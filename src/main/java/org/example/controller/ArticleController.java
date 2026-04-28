package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.service.ArticleService;
import org.example.util.SecSql;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController {
  private ArticleService articleService;
  private Scanner sc;

  public ArticleController() {
    this.articleService = Container.articleService;
    this.sc = Container.sc;
  }
  // 함수 구현

  // 글 쓰기
  public void doWrite() {
    System.out.println("== 글쓰기 ==");
    if (Container.session.loginedMember == null) {
      System.out.println("로그인 상태가 아닙니다.");
      return;
    }
    System.out.print("제목 : ");
    String title = sc.nextLine();
    System.out.print("내용 : ");
    String body = sc.nextLine();
    int memberId = Container.session.loginedMemberId;

    int id = articleService.doWrite(title, body, memberId);

    System.out.println(id + "번 글이 생성됨");
  }

  // 글 리스트
  public void showList() {
    System.out.println("== 목록 ==");
    List<Article> articles = articleService.getArticles();

    if (articles.size() == 0) {
      System.out.println("게시글이 없습니다");
      return;
    }

    System.out.println("  번호  /   제목   /   회원번호");
    for (Article article : articles) {
      System.out.printf("  %d     /   %s    /     %d\n", article.getId(), article.getTitle(), article.getmemberId());
    }
  }

  public void showListMy() {
    System.out.println("== 내 게시물 목록 ==");
    if (Container.session.loginedMember == null) {  //콘테이너랑 세션을 이용하여 저장한 loginMember 값이 null 이면 비로그인상태
      System.out.println("로그인 상태가 아닙니다.");
      return;
    }

    List<Article> articles = articleService.getMemberArticles(Container.session.loginedMemberId);
    if (articles.size() == 0) {
      System.out.println("게시글이 없습니다");
      return;
    }
    System.out.println("  번호  /   제목   /   회원번호");
    for (Article article : articles) {
      System.out.printf("  %d     /   %s    /     %d\n", article.getId(), article.getTitle(), article.getmemberId());
      //번호 표시 오류 회원번호가 -> 번호에 가있고 회원번호에는 다른 번호가 입려됨
    }
  }

  // 글 수정
  public void doModify(String cmd) {
    int id = 0;

    try {
      id = Integer.parseInt(cmd.split(" ")[2]);
    } catch (Exception e) {
      System.out.println("번호는 정수로 입력해");
      return;
    }
    Map<String, Object> articleMap = articleService.getArticleById(id);
    if (articleMap.isEmpty()) {
      System.out.println(id + "번 글은 없음");
      return;
    }
    int memberid = (int) articleMap.get("memberId");  //SQL DB 에서 가져온 article 테이블의 memberId 값을 memberid 변수에 넣음

    if (Container.session.loginedMember == null) {  //콘테이너랑 세션을 이용하여 저장한 loginMember 값이 null 이면 비로그인상태
      System.out.println("로그인 상태가 아닙니다.");
      return;
    }
    if (Container.session.loginedMemberId != memberid) {   //회원번호가 다르면 권한이 없음.
      System.out.println("해당 게시글에 대한 권한이 없습니다.");
      return;
    }

    System.out.println("== 수정 ==");
    System.out.print("새 제목 : ");
    String title = sc.nextLine().trim();
    System.out.print("새 내용 : ");
    String body = sc.nextLine().trim();

    int num = articleService.doUpdate(id, title, body);

    System.out.println(id + "번 글이 수정되었습니다.");
  }

  // 상세보기
  public void showDetail(String cmd) {
    int id = 0;

    try {
      id = Integer.parseInt(cmd.split(" ")[2]);
    } catch (Exception e) {
      System.out.println("번호는 정수로 입력해");
      return;
    }
    Map<String, Object> articleMap = articleService.getArticleById(id);
    if (articleMap.isEmpty()) {
      System.out.println(id + "번 글은 없음");
      return;
    }

    System.out.println("== 상세보기 ==");

    Article article = new Article(articleMap);

    System.out.println("번호 : " + article.getId());
    System.out.println("작성날짜 : " + article.getRegDate());
    System.out.println("수정날짜 : " + article.getUpdateDate());
    System.out.println("제목 : " + article.getTitle());
    System.out.println("내용 : " + article.getBody());
    System.out.println("회원번호 : " + article.getmemberId());
  }

  // 글 삭제
  public void doDelete(String cmd) {
    int id = 0;

    try {
      id = Integer.parseInt(cmd.split(" ")[2]);
    } catch (Exception e) {
      System.out.println("번호는 정수로 입력해");
      return;
    }
    SecSql sql = new SecSql();

    Map<String, Object> articleMap = articleService.getArticleById(id);
    if (articleMap.isEmpty()) {
      System.out.println(id + "번 글은 없음");
      return;
    }
    int memberid = (int) articleMap.get("memberId");
    if (Container.session.loginedMember == null) {
      System.out.println("로그인 상태가 아닙니다.");
      return;
    }
    if (Container.session.loginedMemberId != memberid) {
      System.out.println("해당 게시글에 대한 권한이 없습니다.");
      return;
    }

    System.out.println("== 삭제 ==");
    articleService.doDelete(id);

    System.out.println(id + "번 글이 삭제되었습니다.");
  }
}
