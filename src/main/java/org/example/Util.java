package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {  //Util 이라는 클래스를 만들어줌
  public static String getDateTime() { //함수를 바로 호출하기 위해 static을 붙임
    LocalDateTime Now = LocalDateTime.now();  //LocalDateTime이라는 내장함수를 이용하여 Now변수를 만들어줌  //LocalDateTime.now() 는 현재 시간을 의미
    String formatedNow = Now.format(DateTimeFormatter.ofPattern("YYYY-mm-dd HH:mm:ss")); //문자열 포멧팅을 이용하여 저장된 시간을 출력
    return formatedNow; //그 시간값을 저장
  }
}
