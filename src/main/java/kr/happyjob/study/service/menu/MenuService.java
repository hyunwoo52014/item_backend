package kr.happyjob.study.service.menu;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.repository.menu.MenuMapper;
import kr.happyjob.study.vo.menu.MainmenuVO;
import kr.happyjob.study.vo.menu.MenupermitVO;

@Service
public class MenuService {
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();
	   
	
	@Autowired
	private MenuMapper menumapper;
	
	public List<MainmenuVO> mainmenusearch(Map<String, Object> paramMap) {
		return menumapper.mainmenusearch(paramMap);
	}
	
	public int mainmenusearchtotalcnt(Map<String, Object> paramMap) {
		return menumapper.mainmenusearchtotalcnt(paramMap);
	}
	
	public List<MainmenuVO> submenusearch(Map<String, Object> paramMap) {
		return menumapper.submenusearch(paramMap);
	}
	
	public int submenusearchtotalcnt(Map<String, Object> paramMap) {
		return menumapper.submenusearchtotalcnt(paramMap);
	}
	
	public int mainmenudupcheck(Map<String, Object> paramMap) {
		return menumapper.mainmenudupcheck(paramMap);
	}
	
	public int mainmenuinsert(Map<String, Object> paramMap) {
		return menumapper.mainmenuinsert(paramMap);
	}
	
	public int mainmenuipdate(Map<String, Object> paramMap) {
		return menumapper.mainmenuipdate(paramMap);
	}
	
	public int mainmenudelete(Map<String, Object> paramMap) {
		return menumapper.mainmenudelete(paramMap);
	}
	
	
	
	public MainmenuVO mainmenudetail(Map<String, Object> paramMap) {
		return menumapper.mainmenudetail(paramMap);
	}
	
	public MainmenuVO submenuselect(Map<String, Object> paramMap) {
		return menumapper.submenuselect(paramMap);
	}
	
	public int submenuinsert(Map<String, Object> paramMap) {
		return menumapper.submenuinsert(paramMap);
	}
	
	public int submenuipdate(Map<String, Object> paramMap) {
		return menumapper.submenuipdate(paramMap);
	}
	
	public int submenudelete(Map<String, Object> paramMap) {
		return menumapper.submenudelete(paramMap);
	}
	
	public List<MenupermitVO> selectpermmit(Map<String, Object> paramMap) {
		return menumapper.selectpermmit(paramMap);
	}
	
	public int deletepermmit(Map<String, Object> paramMap) {
		return menumapper.deletepermmit(paramMap);
	}
	
	public int insertpermmit(String Usertype, Map<String, Object> paramMap) {
				
		paramMap.put("Usertype", Usertype);		
		
		return menumapper.insertpermmit(paramMap);
	}
	
	public int mainmenupermmitselect(String Usertype, Map<String, Object> paramMap) {
		
		paramMap.put("Usertype", Usertype);		
		
		return menumapper.mainmenupermmitselect(paramMap);
	}
	
	public int mainmenupermmitdelete(String Usertype, Map<String, Object> paramMap) {
		
		paramMap.put("Usertype", Usertype);		
		
		return menumapper.mainmenupermmitdelete(paramMap);
	}
	
	
	
	public int mainmenupermmitcount(String Usertype, Map<String, Object> paramMap) {
		
		paramMap.put("Usertype", Usertype);		
		
		return menumapper.mainmenupermmitcount(paramMap);
	}
	
	public int mainmenuinsertpermit(String Usertype, Map<String, Object> paramMap) {
		
		paramMap.put("Usertype", Usertype);		
		
		return menumapper.mainmenuinsertpermit(paramMap);
	}
	
	
	
	
	
	
	
	
}
