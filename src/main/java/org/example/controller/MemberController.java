package org.example.controller;

import org.example.container.Container;
import org.example.dto.Member;
import org.example.service.MemberService;

public class MemberController {

  private MemberService memberService;

  public MemberController() {
    this.memberService = Container.memberService;
  }

  public void doJoin() {
    String loginId = null;
    String loginPw = null;
    String loginPwConfirm = null;
    String name = null;
    System.out.println("==회원가입==");
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.sc.nextLine().trim();

      if (loginId.length() == 0 || loginId.contains(" ")) {
        System.out.println("아이디 똑바로 써");
        continue;
      }

      boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

      System.out.println(isLoginIdDup);

      if (isLoginIdDup) {
        System.out.println(loginId + "은(는) 이미 사용중");
        continue;
      }
      break;
    }

    while (true) {
      System.out.print("비밀번호 : ");
      loginPw = Container.sc.nextLine().trim();

      if (loginPw.length() == 0 || loginPw.contains(" ")) {
        System.out.println("비밀번호 똑바로 써");
        continue;
      }

      boolean loginCheckPw = true;

      while (true) {
        System.out.print("비밀번호 확인 : ");
        loginPwConfirm = Container.sc.nextLine().trim();

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
      name = Container.sc.nextLine().trim();

      if (name.length() == 0 || name.contains(" ")) {
        System.out.println("이름 똑바로 써");
        continue;
      }
      break;
    }

    int id = memberService.doJoin(loginId, loginPw, name);

    System.out.println(id + "번 회원 가입완료!");
  }

  public void login() {
    String loginId = null;
    String loginPw = null;

    System.out.println("== 로그인 ==");
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.sc.nextLine().trim();

      if (loginId.length() == 0 || loginId.contains(" ")) {
        System.out.println("아이디를 똑바로 써주세요.");
        continue;
      }
      boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

      if (isLoginIdDup == false) {
        System.out.println(loginId + "은(는)없음");
        continue;
      }
      break;
    }
    Member member = memberService.getMemberByLoginId(loginId);

    int tryMaxCount = 3;
    int tryCount = 0;

    while (true) {
      if (tryCount >= tryMaxCount) {
        System.out.println("비밀번호 확인하고 다시 시도!");
        break;
      }
      System.out.print("비밀번호 : ");
      loginPw = Container.sc.nextLine().trim();

      if (loginPw.length() == 0 || loginPw.contains(" ")) {
        tryCount++;
        System.out.printf("비밀번호를 똑바로 작성 (%d/3)\n", tryCount);
        continue;
      }
      if (member.getLoginPw().equals(loginPw) == false) {
        tryCount++;
        System.out.printf("비밀번호를 똑바로 작성 (%d/3)\n", tryCount);
        continue;
      }

      Container.session.loginedMember = member;
      Container.session.loginedMemberId = member.getId();
      Container.session.loginedMemberPw = member.getLoginPw();


      System.out.println(member.getName() + "님, 로그인 성공!");
      break;
    }
  }

  public void showProfile(){
    if (Container.session.loginedMember == null){
      System.out.println("로그인 상태가 아닙니다.");
      return;
    } else {
      System.out.println(Container.session.loginedMember);
    }
  }

  public void logout(){
    if (Container.session.loginedMember == null) {
      System.out.println("로그인 상태가 아닙니다.");
      return;
    }
    System.out.println("== 로그아웃 됨 ==");
    Container.session.loginedMember = null;
    Container.session.loginedMemberId = -1;
    Container.session.loginedMemberPw = null;
  }

  public void loginmodify() {
    if (Container.session.loginedMember == null) {
      System.out.println("로그인 상태가 아닙니다.");
      return;
    }
    System.out.println("회원 번호 : " + Container.session.loginedMemberId);
    while (true) {
      System.out.print("비밀번호 변경 : ");
      String loginPw = Container.sc.nextLine().trim();
      System.out.print("비밀번호 확인 : ");
      String loginPwConf = Container.sc.nextLine().trim();

      if (!loginPw.equals(loginPwConf)) {
        System.out.println("비밀번호가 다릅니다. 다시 입력해주세요");
        continue;
      }

      int id = memberService.doModifyPw(Container.session.loginedMemberId, loginPw);

      System.out.println("비밀번호 변경 완료.");
      break;
    }
  }

  public void memberout() {
    if (Container.session.loginedMember == null) {
      System.out.println("로그인 상태가 아닙니다.");
      return;
    }
    System.out.println("회원 번호 : " + Container.session.loginedMemberId);
    System.out.println("== 회원 탈퇴 ==");
    while (true) {
      System.out.print("비밀번호 : ");
      String loginPw = Container.sc.nextLine().trim();

      if (!loginPw.equals(Container.session.loginedMemberPw)) {
        System.out.println("비밀번호가 틀립니다. 다시 시도해주세요");
        continue;
      }

      int id = memberService.doDeleteMember(Container.session.loginedMemberId);
      System.out.println(Container.session.loginedMemberId + "번 회원님 회원탈퇴 완료.");
      Container.session.loginedMember = null;
      Container.session.loginedMemberId = -1;
      Container.session.loginedMemberPw = null;
      break;
    }
  }
}