package kr.happyjob.study.service.system;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.repository.system.UsermgrsystemMapper;
import kr.happyjob.study.vo.system.UsermngModel;

@Service
public class UsermgrsystemService {

	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();

	@Autowired
	private UsermgrsystemMapper usermgrsystemMapper;

	public List<UsermngModel> userListvue(Map<String, Object> paramMap) throws Exception {
		return usermgrsystemMapper.userListvue(paramMap);
	}
	
	public int userListvuetotalcnt(Map<String, Object> paramMap) throws Exception {
		return usermgrsystemMapper.userListvuetotalcnt(paramMap);
	}
	
	public int usercheckLoginID(Map<String, Object> paramMap) throws Exception {
		return usermgrsystemMapper.usercheckLoginID(paramMap);
	}
	
	public int userinsert(Map<String, Object> paramMap) throws Exception {
		return usermgrsystemMapper.userinsert(paramMap);
	}
	
	public int userupdate(Map<String, Object> paramMap) throws Exception {
		return usermgrsystemMapper.userupdate(paramMap);
	}
	
	public int userdelete(Map<String, Object> paramMap) throws Exception {
		return usermgrsystemMapper.userdelete(paramMap);
	}
	
	
	
	

}