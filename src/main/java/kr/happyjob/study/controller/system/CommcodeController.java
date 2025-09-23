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

import kr.happyjob.study.service.system.CommcodeService;
import kr.happyjob.study.vo.system.CommcodeModel;

@Controller
@RequestMapping("/system/")
public class CommcodeController {
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();
	   
	
	@Autowired
	private CommcodeService commcodeservice;

		
	@RequestMapping("/listgroupcode")
	@ResponseBody
	public Map<String, Object> listgroupcode(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start Commcodecontroller.listgroupcode");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  // 페이지 번호
		  
		  int cpage = Integer.parseInt((String) paramMap.get("cpage"));
		  int pagesize = Integer.parseInt((String) paramMap.get("pagesize"));
		  int startpoint = (cpage - 1) * pagesize;
		  
		  paramMap.put("startpoint",startpoint);
		  paramMap.put("pagesize",pagesize);		  
		  
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  
		  
		  try {
			  List<CommcodeModel> commcodeModel = commcodeservice.listgroupcode(paramMap);
	          int totalcnt = commcodeservice.totalcntgroupcode(paramMap);

		      resultMap.put("commcodeModel", commcodeModel);
		      resultMap.put("totalcnt", totalcnt);
		      resultMap.put("result", "Y");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "N");
		  }	  
	  
	  
	    logger.info("+ End Commcodecontroller.listgroupcode");

	    return resultMap;
    }
	   	
	@RequestMapping("/savegroupcode")
	@ResponseBody
	public Map<String, Object> savegroupcode(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start Commcodecontroller.savegroupcode");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  
		  String action = (String) paramMap.get("action");
		  paramMap.put("liginid",(String)session.getAttribute("loginId"));
		  
		  try {
			  
			  if("I".equals(action)) {
				  commcodeservice.insertgroupcode(paramMap);
			  } else if("U".equals(action)) {
				  commcodeservice.updategroupcode(paramMap);				  
			  } else if("D".equals(action)) {
				  commcodeservice.deletegroupcode(paramMap);
			  }

		      resultMap.put("result", "SUCCESS");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "FAIL");
			  resultMap.put("resultmsg", e.getMessage());
		  }	  
	  
	  
	    logger.info("+ End Commcodecontroller.savegroupcode");

	    return resultMap;
    }
	
	@RequestMapping("/selectgroupcode")
	@ResponseBody
	public Map<String, Object> selectgroupcode(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start Commcodecontroller.selectgroupcode");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		   
		  try {
			  CommcodeModel commcodeModel = commcodeservice.selectgroupcode(paramMap);

			  resultMap.put("commcodeModel", commcodeModel);			  
		      resultMap.put("result", "SUCCESS");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "FAIL");
			  resultMap.put("resultmsg", e.getMessage());
		  }	  
	  
	  
	    logger.info("+ End Commcodecontroller.selectgroupcode");

	    return resultMap;
    }
	
	@RequestMapping("/listdetailcode")
	@ResponseBody
	public Map<String, Object> listdetailcode(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start Commcodecontroller.listdetailcode");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  int cpage = Integer.parseInt((String) paramMap.get("cpage"));
		  int pagesize = Integer.parseInt((String) paramMap.get("pagesize"));
		  int startpoint = (cpage - 1) * pagesize;
		  
		  paramMap.put("startpoint",startpoint);
		  paramMap.put("pagesize",pagesize);
		  
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  
		  
		  try {
			  List<CommcodeModel> commcodeModel = commcodeservice.listdetailcode(paramMap);
	          int totalcnt = commcodeservice.totalcntdetailcode(paramMap);

		      resultMap.put("commcodeModel", commcodeModel);
		      resultMap.put("totalcnt", totalcnt);
		      resultMap.put("result", "Y");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "N");
		  }	  
	  
	  
	    logger.info("+ End Commcodecontroller.listdetailcode");

	    return resultMap;
    }	
	

	@RequestMapping("/savedetailcode")
	@ResponseBody
	public Map<String, Object> savedetailcode(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start Commcodecontroller.savedetailcode");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  
		  String action = (String) paramMap.get("action");
		  paramMap.put("loginId",(String)session.getAttribute("loginId"));
		  
		  logger.info("   - loginId : " + paramMap.get("loginId"));
		  
		  
		  try {
			  
			  if("I".equals(action)) {
				  commcodeservice.insertdetailcode(paramMap);
			  } else if("U".equals(action)) {
				  commcodeservice.updatedetailcode(paramMap);				  
			  } else if("D".equals(action)) {
				  commcodeservice.deletedetailcode(paramMap);
			  }

		      resultMap.put("result", "SUCCESS");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "FAIL");
			  resultMap.put("resultmsg", e.getMessage());
		  }	  
	  
	  
	    logger.info("+ End Commcodecontroller.savedetailcode");

	    return resultMap;
    }
	

	@RequestMapping("/selectdetailcode")
	@ResponseBody
	public Map<String, Object> selectdetailcode(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start Commcodecontroller.selectdetailcode");
		  logger.info("   - ParamMap : " + paramMap);
		    
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		   
		  try {
			  CommcodeModel commcodeModel = commcodeservice.selectdetailcode(paramMap);

			  resultMap.put("commcodeModel", commcodeModel);			  
		      resultMap.put("result", "SUCCESS");
	  
		  } catch (Exception e) {
			  resultMap.put("result", "FAIL");
			  resultMap.put("resultmsg", e.getMessage());
		  }	  
	  
	  
	    logger.info("+ End Commcodecontroller.selectdetailcode");

	    return resultMap;
    }
}