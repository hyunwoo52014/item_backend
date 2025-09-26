package kr.happyjob.study.service.login;

import java.time.LocalDate;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.repository.login.LoginMapper;
import kr.happyjob.study.repository.login.LoginProcMapper;
import kr.happyjob.study.vo.login.LgnInfoModel;
import kr.happyjob.study.vo.login.LoginVO;
import kr.happyjob.study.vo.login.UserVO;

@Service
public class LoginService {
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());
	private final String className = this.getClass().toString();
	   
	
	@Autowired
	private LoginMapper mapper;

    @Autowired
    private MailSendService mailSendService;

    //단순 로그인
	public UserVO login(LoginVO vo) {
		return mapper.login(vo);
	}

/*    // 소셜 로그인
    public LgnInfoModel loginProc(Map<String, Object> paramMap) {
        return procMapper.loginProc(paramMap);
    }*/

    /*회원가입*/
    public int registerUser(Map<String, Object> paramMap) {
        paramMap.putIfAbsent("user_type", "B");
        paramMap.putIfAbsent("regdate", LocalDate.now().toString());
        paramMap.putIfAbsent("status_yn","Y");
        return mapper.registerUser(paramMap);
    }


    /** loginID 중복체크 */
    public int checkDuplicatedLoginID(String loginID) {
        return mapper.checkDuplicatedLoginID(loginID);
    }

    /** email 중복체크 */
    public int checkDuplicatedEmail(String email) {
        return mapper.checkDuplicatedEmail(email);
    }

    /** 사용자 정보 조회(이름/이메일 등으로) */
    public LgnInfoModel selectFindId(Map<String, Object> p) {
        return mapper.selectFindId(p);
    }

    /** 비밀번호 변경 */
    public void passwordChangeUpdate(Map<String, Object> p) {
        mapper.passwordChangeUpdate(p);
    }

    /** 이름+이메일로 존재여부(아이디/비번 찾기 전 단계) */
    public int searchUserExist(Map<String, Object> p) {
        return mapper.searchUserExist(p);
    }

    /** 소셜로그인 최소 정보 가입(필요 시) */
    public int registerSocialLogin(Map<String, Object> p) {
        p.putIfAbsent("user_type", "B");
        p.putIfAbsent("regdate", LocalDate.now().toString());
        p.putIfAbsent("status_yn", "Y");
        return mapper.registerSocialLogin(p);
    }

    /** 추가 정보 업데이트 */
    public int updateUser(Map<String, Object> p) {
        return mapper.updateUser(p);
    }

    /** 메일 발송(인증번호/임시비번) */
    public void sendEmail(String to, String content, boolean isAuth) throws Exception {
        mailSendService.sendEmail(to, content, isAuth);
    }

    /** 인증번호/임시비번 난수 */
    public String randomCode() throws Exception {
        return mailSendService.RandomNum();
    }




}
