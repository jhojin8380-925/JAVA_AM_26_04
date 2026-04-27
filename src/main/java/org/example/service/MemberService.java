package org.example.service;

import org.example.Member;
import org.example.dao.MemberDao;

import java.sql.Connection;
import java.util.List;

public class MemberService {

  private MemberDao memberDao;

  public MemberService() {
    this.memberDao = new MemberDao();
  }

  public boolean isLoginIdDup(Connection conn, String loginId) {
    return memberDao.isLoginIdDup(conn, loginId);
  }

  public int doJoin(Connection conn, String loginId, String loginPw, String name) {
    return memberDao.doJoin(conn, loginId, loginPw, name);
  }

  public List<Member> getlogininfo(Connection conn, String loginId) {
    return memberDao.getlogininfo(conn, loginId);
  }
}