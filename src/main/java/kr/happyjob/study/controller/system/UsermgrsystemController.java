package kr.happyjob.study.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import kr.happyjob.study.service.system.UsermgrsystemService;
import kr.happyjob.study.vo.system.UsermngModel;

@Controller
@RequestMapping("/system/")
public class UsermgrsystemController {
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();
	   
	
	@Autowired
	private UsermgrsystemService usermgrsystemService;

		
	@RequestMapping("/userListvue.do")
	@ResponseBody
	public Map<String, Object> userListvue(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start UsermgrController.userListvue");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  
		  // 페이지 번호 ( Page 번호 - 1 ) * pagesize		  
		  int currentpage = Integer.parseInt((String) paramMap.get("currentpage"));
		  int pagesize = Integer.parseInt((String) paramMap.get("pagesize"));
		  int startpoint = (currentpage - 1) * pagesize;
		  
		  paramMap.put("startpoint",startpoint);
		  paramMap.put("pagesize",pagesize);		  
		  
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  		  
		  try {
			  List<UsermngModel> userlistModel = usermgrsystemService.userListvue(paramMap);
	          int totalcnt = usermgrsystemService.userListvuetotalcnt(paramMap);

		      resultMap.put("userlistModel", userlistModel);
		      resultMap.put("totalcnt", totalcnt);
		      resultMap.put("result", "Y");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "N");
		  }	  
	  
	  
	    logger.info("+ End UsermgrController.userListvue");

	    return resultMap;
    }
	
	@RequestMapping("/usercheckLoginID.do")
	@ResponseBody
	public Map<String, Object> usercheckLoginID(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start UsermgrController.usercheckLoginID");
		  logger.info("   - ParamMap : " + paramMap);
		      
		  
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  		  
		  try {			  
	          int totalcnt = usermgrsystemService.usercheckLoginID(paramMap);

	          if(totalcnt > 0) {
	        	  resultMap.put("result", "N");
	          } else {
	        	  resultMap.put("result", "Y");
	          }
	  
		  } catch (Exception e) {
			  resultMap.put("result", "N");
		  }	  
	  
	  
	    logger.info("+ End UsermgrController.usercheckLoginID");

	    return resultMap;
    }
	
	@RequestMapping("/userselectone.do")
	@ResponseBody
	public Map<String, Object> userselectone(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start UsermgrController.userselectone");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  		  
		  try {			  
			  List<UsermngModel> userlistModel = usermgrsystemService.userListvue(paramMap);
	          
		      resultMap.put("userlistModel", userlistModel.get(0));
		      resultMap.put("result", "Y");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "N");
		  }	  
	  
	  
	    logger.info("+ End UsermgrController.userselectone");

	    return resultMap;
    }
	
	@RequestMapping("/usersave.do")
	@ResponseBody
	public Map<String, Object> usersave(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start UsermgrController.usersave");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  		  
		  String action = (String) paramMap.get("action");
		   
		  try {	
			  
			  if(action == "I" || "I".equals(action) ) {
				  usermgrsystemService.userinsert(paramMap);
			  } else if(action == "U" || "U".equals(action) ) {
				  usermgrsystemService.userupdate(paramMap);
			  } else {
				  usermgrsystemService.userdelete(paramMap);
			  }
		      
		      resultMap.put("result", "Y");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "N");
		  }	  
	  
	  
	    logger.info("+ End UsermgrController.usersave");

	    return resultMap;
    }
	
	
	   	
}