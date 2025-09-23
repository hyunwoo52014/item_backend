package kr.happyjob.study.repository.usermgr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import kr.happyjob.study.vo.usermgr.UserModel;

@Mapper
public interface UsermgrMapper {

	// 리스트 조회
	public List<UserModel> userList(Map<String, Object> paramMap);

	// 카운트 조회
	public int userCnt(Map<String, Object> paramMap);
	
	public UserModel selectuserinfo(Map<String, Object> paramMap);
	
	public void insertuserinfo(Map<String, Object> paramMap);
	
	public void updateuserinfo(Map<String, Object> paramMap);
	
	public void deleteuserinfo(Map<String, Object> paramMap);
	
	public int loginidcheck(Map<String, Object> paramMap);
}