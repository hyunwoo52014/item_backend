package kr.happyjob.study.repository.login;

import java.util.Map;

import kr.happyjob.study.vo.login.LgnInfoModel;
import org.apache.ibatis.annotations.Mapper;

import kr.happyjob.study.vo.login.LoginVO;
import kr.happyjob.study.vo.login.UserVO;

@Mapper
public interface LoginMapper {
    //단순로그인
	UserVO login(LoginVO vo);

    //아이디 찾기 등 사용자 정보 조회
    LgnInfoModel selectFindId(Map<String, Object> param);

    // 회원가입
    int registerUser(Map<String, Object> param);
    //소셜로그인
    int registerSocialLogin(Map<String, Object> param);
    //아디이 중복 확인
    int checkDuplicatedLoginID(String loginID);
    // 이메일 중복 확인
    int checkDuplicatedEmail(String email);


    //이이디 비밀번호 찾기 공통
    int searchUserExist(Map<String, Object> param);

    //비밀번호 변경
    int passwordChangeUpdate(Map<String, Object> param);
    //추가정보 업데이트
    int updateUser(Map<String, Object> param);


}

