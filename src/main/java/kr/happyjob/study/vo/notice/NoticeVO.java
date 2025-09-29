package kr.happyjob.study.vo.notice;

public class NoticeVO {

    private int notice_code;   // PK
    private String loginID;    // 작성자
    private String title;      // 제목
    private String content;    // 내용
    private String writeDate;  // 작성일

    public int getNotice_code() { return notice_code; }
    public void setNotice_code(int notice_code) { this.notice_code = notice_code; }

    public String getLoginID() { return loginID; }
    public void setLoginID(String loginID) { this.loginID = loginID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getWriteDate() { return writeDate; }
    public void setWriteDate(String writeDate) { this.writeDate = writeDate; }

}
