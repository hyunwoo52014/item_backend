package kr.happyjob.study.vo.menu;

public class MenupermitVO {
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPermit() {
		return permit;
	}
	public void setPermit(String permit) {
		this.permit = permit;
	}
	private String user;    // user_type
	private String permit;  // 권한  Y/N
	
}

