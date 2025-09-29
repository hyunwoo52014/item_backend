package kr.happyjob.study.vo.login;

public class LgnInfoModel {

    // ===== 기존 필드 =====
    private String loginID;      // 로그인 id
    private String user_type;    // 유저 종류 (A/B 등)
    private String name;         // 이름
    private String password;     // 패스워드
    private String hp;           // 연락처
    private String email;        // 이메일
    private String regdate;      // 등록일
    private String addr;         // 주소
    private String addr_detail;  // 주소 상세
    private String birthday;     // 생일
    private String team;         // 소속팀
    private String status_yn;    // 상태(승인여부) Y/N

    // ====== 보조 필드 ======

    private String usr_sst_id;   // 사용자 시스템 ID (옵션)
    // 레거시가 그대로 직렬화할 수도 있어 보조로 둠 (옵션)
    private String approval_cd;  // y/n (옵션)
    private String mem_author;   // mng/gnr (옵션)

    // ===== 기존 getter/setter =====
    public String getLoginID() { return loginID; }
    public void setLoginID(String loginID) { this.loginID = loginID; }
    public String getUser_type() { return user_type; }
    public void setUser_type(String user_type) { this.user_type = user_type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getHp() { return hp; }
    public void setHp(String hp) { this.hp = hp; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRegdate() { return regdate; }
    public void setRegdate(String regdate) { this.regdate = regdate; }
    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }
    public String getAddr_detail() { return addr_detail; }
    public void setAddr_detail(String addr_detail) { this.addr_detail = addr_detail; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
    public String getStatus_yn() { return status_yn; }
    public void setStatus_yn(String status_yn) { this.status_yn = status_yn; }

    // ===== 호환용 alias getter/setter =====
    // lgn_id <-> loginID
    public String getLgn_id() { return this.loginID; }
    public void setLgn_id(String v) { this.loginID = v; }

    // usr_nm <-> name
    public String getUsr_nm() { return this.name; }
    public void setUsr_nm(String v) { this.name = v; }

    // pwd <-> password
    public String getPwd() { return this.password; }
    public void setPwd(String v) { this.password = v; }

    public String getMem_author() {
        if (this.mem_author != null && !this.mem_author.isEmpty()) return this.mem_author;
        if (this.user_type == null) return null;
        // 필요에 맞게 매핑 규칙 수정
        // 예) A=관리자(mng), 그 외=일반(gnr)
        return "A".equalsIgnoreCase(this.user_type) ? "mng" : "gnr";
    }
    public void setMem_author(String v) { this.mem_author = v; }

    public String getUsr_sst_id() { return this.usr_sst_id; }
    public void setUsr_sst_id(String v) { this.usr_sst_id = v; }

    public String getApproval_cd() {
        if (this.approval_cd != null && !this.approval_cd.isEmpty()) return this.approval_cd;
        if (this.status_yn == null) return null;
        return this.status_yn.equalsIgnoreCase("Y") ? "y" : "n";
    }
    public void setApproval_cd(String v) { this.approval_cd = v; }
}
