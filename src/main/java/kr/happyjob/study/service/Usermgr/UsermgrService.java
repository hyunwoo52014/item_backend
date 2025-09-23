package kr.happyjob.study.service.Usermgr;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.happyjob.study.repository.usermgr.UsermgrMapper;
import kr.happyjob.study.vo.usermgr.UserModel;

@Service
public class UsermgrService {

	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();

	
	@Autowired
	private UsermgrMapper userMapper;

	public List<UserModel> userList(Map<String, Object> paramMap) throws Exception {
		return userMapper.userList(paramMap);
	}

	public int userCnt(Map<String, Object> paramMap) throws Exception {
		return userMapper.userCnt(paramMap);
	}
	
	public UserModel selectuserinfo(Map<String, Object> paramMap) throws Exception {
		return userMapper.selectuserinfo(paramMap);
	}
	
	public void saveuserinfo(Map<String, Object> paramMap) throws Exception {
		
		String action = (String) paramMap.get("action");
		
		if("I".equals(action) || action == "I") {  // 등록
			userMapper.insertuserinfo(paramMap);
		} else if("U".equals(action) || action == "U") {  // 수정
			userMapper.updateuserinfo(paramMap);
		} else {  // 삳제
			userMapper.deleteuserinfo(paramMap);
		}
		
		return;
	}	
	
    public int loginidcheck(Map<String, Object> paramMap) throws Exception {				
		return userMapper.loginidcheck(paramMap);
	}	
	
	

}