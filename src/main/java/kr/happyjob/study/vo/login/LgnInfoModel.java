package kr.happyjob.study.vo.login;


public class LgnInfoModel {

    // 로그인 id
    private String loginID;
    // 유저 종류, 유저/관리자
    private String user_type;
    // 이름
    private String name;
    // 패스워드
    private String password;
    // 연락처(휴대폰 번호)
    private String hp;
    // 이메일
    private String email;
    // 등록일
    private String regdate;
    // 주소
    private String addr;
    // 주소 상세
    private String addr_detail;
    // 생일
    private String birthday;
    // 소속팀
    private String team;
    // 상태(승인여부)
    private String status_yn;


    public String getLoginID() {
        return loginID;
    }
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }
    public String getUser_type() {
        return user_type;
    }
    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getHp() {
        return hp;
    }
    public void setHp(String hp) {
        this.hp = hp;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRegdate() {
        return regdate;
    }
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getAddr_detail() {
        return addr_detail;
    }
    public void setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    public String getStatus_yn() {
        return status_yn;
    }
    public void setStatus_yn(String status_yn) {
        this.status_yn = status_yn;
    }

}
