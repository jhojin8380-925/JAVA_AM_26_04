package org.example;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
  public void run() {
    System.out.println("==프로그램 시작==");
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.print("명령어 ) ");
      String cmd = sc.nextLine().trim();

      Connection conn = null;

      try {
        Class.forName("org.mariadb.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

      String url = "jdbc:mariadb://127.0.0.1:3306/JDBC_AM_26_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

      try {
        conn = DriverManager.getConnection(url, "root", "");

        int actionResult = action(conn, sc, cmd);

        if (actionResult == -1) {
          System.out.println("== 프로그램 종료 ==");
          sc.close();
          break;
        }

      } catch (SQLException e) {
        System.out.println("에러 1 : " + e);
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

  private int action(Connection conn, Scanner sc, String cmd) {
    if (cmd.equals("exit")) {
      return -1;
    }
    MemberController memberController = new MemberController(sc, conn);
    ArticleController articleController = new ArticleController(sc, conn, cmd);

    if (cmd.equals("member join")) {
      memberController.doJoin();
    } else if (cmd.equals("article write")) {
      articleController.dowrite();
    } else if (cmd.equals("article list")) {
      articleController.dolist();
    } else if (cmd.startsWith("article modify")) {
      articleController.domodify();
    } else if (cmd.startsWith("article detail")) {
      articleController.dodetail();
    } else if (cmd.startsWith("article delete")) {
      articleController.dodelete();
    }
    return 0;
  }
}