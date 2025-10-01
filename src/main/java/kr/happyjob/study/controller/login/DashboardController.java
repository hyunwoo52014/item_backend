package kr.happyjob.study.controller.login;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();

	@RequestMapping("/dashboard")
	public String initDashboard(Model model, @RequestParam Map<String, Object> paramMap, jakarta.servlet.http.HttpServletRequest request,
                                jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.http.HttpSession session) throws Exception {
		
		
		logger.info("+ Start " + className + ".initDashboard");
		/* ############## set input data################# */
		paramMap.put("loginId", session.getAttribute("loginId")); // 제목
		paramMap.put("userType", session.getAttribute("userType")); // 오피스 구분 //
																	// 코드
		paramMap.put("reg_date", session.getAttribute("reg_date")); // 등록 일자
		logger.info("   - paramMap : " + paramMap);

		String returnType = "/dashboard";

		logger.info("+ end " + className + ".initDashboard");

		return returnType;
	}
}
