package org.example.controller;

import org.example.Member;
import org.example.service.MemberService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class MemberController {
  private Connection conn;
  private Scanner sc;

  private MemberService memberService;

  public MemberController(Scanner sc, Connection conn) {
    this.sc = sc;
    this.conn = conn;
    this.memberService = new MemberService();
  }

  public void doJoin() {
    String loginId = null;
    String loginPw = null;
    String loginPwConfirm = null;
    String name = null;
    System.out.println("==회원가입==");
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = sc.nextLine().trim();

      if (loginId.length() == 0 || loginId.contains(" ")) {
        System.out.println("아이디 똑바로 써");
        continue;
      }

      boolean isLoginIdDup = memberService.isLoginIdDup(conn, loginId);

      System.out.println(isLoginIdDup);

      if (isLoginIdDup) {
        System.out.println(loginId + "은(는) 이미 사용중");
        continue;
      }
      break;
    }

    while (true) {
      System.out.print("비밀번호 : ");
      loginPw = sc.nextLine().trim();

      if (loginPw.length() == 0 || loginPw.contains(" ")) {
        System.out.println("비밀번호 똑바로 써");
        continue;
      }

      boolean loginCheckPw = true;

      while (true) {
        System.out.print("비밀번호 확인 : ");
        loginPwConfirm = sc.nextLine().trim();

        if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
          System.out.println("비밀번호 확인 (똑바로 작성)");
          continue;
        }

        if (loginPw.equals(loginPwConfirm) == false) {
          System.out.println("비밀번호가 일치하지 않음");
          loginCheckPw = false;
        }
        break;
      }
      if (loginCheckPw) {
        break;
      }
    }
    while (true) {
      System.out.print("이름 : ");
      name = sc.nextLine().trim();

      if (name.length() == 0 || name.contains(" ")) {
        System.out.println("이름 똑바로 써");
        continue;
      }
      break;
    }

    int id = memberService.doJoin(conn, loginId, loginPw, name);

    System.out.println(id + "번 회원 가입완료!");
  }

  public void dologin() {
    int loginIdcount = 0;
    int loginPwcount = 0;
    while (true) {

      System.out.print("아이디 : ");
      String loginId = sc.nextLine().trim();
      List<Member> members = memberService.getlogininfo(conn, loginId);
      String CheckId = null;
      String CheckPw = null;
      String name = null;

      System.out.println(members);
      for (Member member : members) {
        CheckId = member.getLoginId();
        CheckPw = member.getLoginPw();
        name = member.getName();
      }

      System.out.println(CheckId);
      System.out.println(CheckPw);
      System.out.println(name);
      if (CheckId == null) {
        System.out.println("아이디가 존재하지않습니다");
        loginIdcount++;
        if(loginIdcount == 3) {
          System.out.println("3회이상 틀리셧습니다.");
          System.out.println("로그인 실패.");
          break;
        }
        continue;
      }
      System.out.println("아이디가 일치합니다.");

      while (true) {
        System.out.print("비밀번호 : ");
        String loginPw = sc.nextLine().trim();

        if (!CheckPw.equals(loginPw)) {
          System.out.println("비밀번호가 일치하지 않습니다.");
          loginPwcount++;
          if (loginPwcount == 3) {
            System.out.println("3회이상 틀리셧습니다. 로그인 실패.");
            CheckPw = null;
            break;
          }

        }
        System.out.println("로그인 성공");
        System.out.println(name + "님 환영합니다.");
        break;
      }
      if (CheckPw == null) {
        System.out.println("로그인 실패.");
        break;
      }


    }
  }
}