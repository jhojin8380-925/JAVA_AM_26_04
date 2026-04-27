package org.example.dao;

import org.example.Member;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberDao {

  public boolean isLoginIdDup(Connection conn, String loginId) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?;", loginId);

    return DBUtil.selectRowBooleanValue(conn, sql);
  }

  public int doJoin(Connection conn, String loginId, String loginPw, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW(),");
    sql.append("updateDate = NOW(),");
    sql.append("loginId = ?,", loginId);
    sql.append("loginPw = ?,", loginPw);
    sql.append("name = ?;", name);

    return DBUtil.insert(conn, sql);
  }

  public List<Member> getlogininfo(Connection conn, String loginId) {
    List<Member> members = new ArrayList<>();
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    List<Map<String, Object>> memberListMap = DBUtil.selectRows(conn, sql);
    for (Map<String, Object> memberMap : memberListMap) {
      members.add(new Member(memberMap));
    }
    return members;
  }
}