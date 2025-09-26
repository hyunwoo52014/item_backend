package kr.happyjob.study.controller.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.happyjob.study.repository.login.ListUsrChildMnuAtrtMapper;
import kr.happyjob.study.service.login.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import kr.happyjob.study.vo.login.LoginVO;
import kr.happyjob.study.vo.login.UserVO;
import kr.happyjob.study.vo.login.LgnInfoModel;
import kr.happyjob.study.vo.login.UsrMnuAtrtModel;
import kr.happyjob.study.vo.login.UsrMnuChildAtrtModel;

@Controller
public class LoginController {
	
	// Set logger
	private final Logger logger = LogManager.getLogger(this.getClass());

	// Get class name for logger
	private final String className = this.getClass().toString();
	   
	
	@Autowired
	private LoginService loginService; // 일반용

	@Autowired
	private LoginProcService loginProcService; //소셜용

    @Autowired
    private ListUsrChildMnuAtrtService listUsrChildMnuAtrtService;

    @Autowired
    private ListUsrChildMnuAtrtMapper listUsrChildMnuAtrtMapper;

    @Autowired
    private ListUsrMnuAtrtService listUsrMnuAtrtService;

    @Autowired
    private MailSendService mailSendService;


	
	@GetMapping("/main")
	public ModelAndView main() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView loginPage() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}
	
	@GetMapping("/react")
	public ModelAndView reactPage() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("react");
		return mv;
	}

	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(LoginVO vo) throws Exception {
		logger.info("login start");
		logger.info("inputId / inputPw");
		logger.info(vo.getId() + " / " + vo.getPw());
		UserVO userVo = new UserVO();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			userVo = loginService.login(vo);
			if (userVo == null) {
				resultMap.put("resCode", "F");
				resultMap.put("resMsg", "로그인 실패");
			} else {
				resultMap.put("resCode", "S");
				resultMap.put("resMsg", "로그인 성공");
				//userVo = service.login(vo);
				logger.info("userVo");
				logger.info(userVo.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

   /* // 소셜 로그인  - lgnInfoModel
	@RequestMapping("/loginProc.do")
	@ResponseBody
	public Map<String, Object> loginProc(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
	         HttpServletResponse response, HttpSession session) throws Exception {

	      logger.info("+ Start LoginController.loginProc.do");
		  logger.info("   - ParamMap : " + paramMap);

          // 사용자 로그인
		  String result;
		  String resultMsg;
		  Map<String, Object> resultMap = new HashMap<String, Object>();
		  
		  
		  try {
			  LgnInfoModel lgnInfoModel = loginProcService.loginProc(paramMap);
	      
		  
		       logger.info("   - lgnInfoModel : " + lgnInfoModel);

		  
		       if (lgnInfoModel != null) {
		    	   result = "SUCCESS";
		  	       resultMsg = "사용자 로그인 정보가 일치 합니다.";
		  	       System.out.println("asdf" + lgnInfoModel.getApproval_cd());
		  	       System.out.println("y".equals(lgnInfoModel.getApproval_cd()));
		  	       System.out.println("asdf" + lgnInfoModel.getApproval_cd());
		  	       System.out.println("n".equals(lgnInfoModel.getApproval_cd()));
		  	       // 사용자 메뉴 권한 조회
		  	       paramMap.put("usr_sst_id", lgnInfoModel.getUsr_sst_id());
		  	       paramMap.put("userType",lgnInfoModel.getMem_author());
		  	       // 메뉴 목록 조회 0depth
		  	       List<UsrMnuAtrtModel> listUsrMnuAtrtModel = listUsrMnuAtrtService.listUsrMnuAtrt(paramMap);
		  	       // 메뉴 목록 조회 1depth
		  	       for(UsrMnuAtrtModel list : listUsrMnuAtrtModel){
		  	          Map<String, Object> resultMapSub = new HashMap<String, Object>();
		  	          resultMapSub.put("lgn_Id", paramMap.get("lgn_Id")); 
		  	          resultMapSub.put("hir_mnu_id", list.getMnu_id());
		  	          resultMapSub.put("userType",lgnInfoModel.getMem_author());
		  	          list.setNodeList(listUsrChildMnuAtrtService.listUsrChildMnuAtrt(resultMapSub));
		  	       }
		  	     
		  	       session.setAttribute("loginId",lgnInfoModel.getLgn_id());                     //   로그인 ID
		  	       session.setAttribute("userNm",lgnInfoModel.getUsr_nm());                  // 사용자 성명
		  	       session.setAttribute("usrMnuAtrt", listUsrMnuAtrtModel);
		  	       session.setAttribute("userType", lgnInfoModel.getMem_author());            // 로그린 사용자 권란       A: 관리자       B: 기업회원    C:일반회원
		  	       session.setAttribute("serverName", request.getServerName());
		  	
		  	       resultMap.put("loginId",lgnInfoModel.getLgn_id()); 
		  	       resultMap.put("userNm",lgnInfoModel.getUsr_nm()); 
		  	       resultMap.put("usrMnuAtrt", listUsrMnuAtrtModel);
		  	       resultMap.put("userType", lgnInfoModel.getMem_author());
		  	       resultMap.put("serverName", request.getServerName());
			} else {
	
		         result = "FALSE";
		         resultMsg = "사용자 로그인 정보가 일치하지 않습니다.";
		    }

	            
	    
		    resultMap.put("result", result);
		    resultMap.put("resultMsg", resultMsg);
		    resultMap.put("serverName", request.getServerName());
	  
		  } catch (Exception e) {
			    e.printStackTrace();
		  }	  
	  
	  
	    logger.info("+ End LoginController.loginProc.do");

	    return resultMap;
}*/
	   
	   
	   /**
	* 로그아웃
	* @param request
	* @param response
	* @param session
	* @return
	*/
	   @RequestMapping(value = "/loginOut.do")
	   public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	                  
	      ModelAndView mav = new ModelAndView();
	      session.invalidate();
	      mav.setViewName("redirect:/login");
	      
	      return mav;
	   }

       /* 회원가입 - 일반회원 */
       // 4. 일반 회원 가입
       @RequestMapping("/register.do")
       @ResponseBody
       public Map<String, Object> registerUser(Model model, @RequestParam Map<String, Object> paramMap,
                                               HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
           logger.info("+ Start " + className + ".registerUser");
           logger.info("   - paramMap : " + paramMap);

           String action = (String) paramMap.get("action");
           String result = "SUCCESS";
           String resultMsg;

           if ("I".equals(action)) {
               paramMap.put("status_yn", "Y");
               loginService.registerUser(paramMap);
               resultMsg = "가입 요청 완료";
           } else {

               result = "FAIL";
               resultMsg = "가입 요청 실패";
           }

           Map<String, Object> resultMap = new HashMap<String, Object>();
           resultMap.put("result", result);
           resultMap.put("resultMsg", resultMsg);

           logger.info("+ End " + className + ".registerUser");

           return resultMap;
       }

    // 5. 아이디 중복 확인
    @RequestMapping("/checkDuplicatedloginID")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkDuplicatedloginID(Model model, @RequestParam Map<String, Object> paramMap,
                                                                      HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".loginID_check");

        String loginID = (String) paramMap.get("loginID");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        String result = null;
        String resultMsg = null;
        int duplicationVal = loginService.checkDuplicatedLoginID(loginID);

        try {
            if("N".equals(loginService.selectFindId(paramMap).getStatus_yn())) {
                result = "FAIL";
                resultMsg = "비활성화된 계정이 있습니다.";
                status = HttpStatus.UNAUTHORIZED;
            } else if(duplicationVal > 0) {
                result = "FAIL";
                resultMsg = "이미 가입된 아이디가 있습니다.";
                status = HttpStatus.FORBIDDEN;
            }
        } catch(Exception e){
            result = "FAIL";
            resultMsg = "서버 오류";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info(e);
        }

        if(!status.is4xxClientError()){
            result = "SUCCESS";
            resultMsg = "성공";
            status = HttpStatus.OK;
        }


        resultMap.put(result, resultMsg);
        logger.info("+ End " + className + ".loginID_check");
        return new ResponseEntity<>(resultMap, status);
    }

    // 6. 이메일 중복 확인
    @RequestMapping("/checkDuplicatedEmail")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> check_email(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                           HttpServletResponse response, HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".loginID_check");

        String email = (String) paramMap.get("email");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        String result = null;
        String resultMsg = null;
        int duplicationVal = loginService.checkDuplicatedEmail(email);

        try {
            if("N".equals(loginService.selectFindId(paramMap).getStatus_yn())) {
                result = "FAIL";
                resultMsg = "비활성화된 계정이 있습니다.";
                status = HttpStatus.UNAUTHORIZED;
            } else if(duplicationVal > 0) {
                result = "FAIL";
                resultMsg = "이미 가입된 이메일이 있습니다.";
                status = HttpStatus.FORBIDDEN;
            }
        } catch(Exception e){
            result = "FAIL";
            resultMsg = "서버 오류";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.info(e);
        }

        if(!status.is4xxClientError()){
            result = "SUCCESS";
            resultMsg = "성공";
            status = HttpStatus.OK;
        }


        resultMap.put(result, resultMsg);
        logger.info("+ End " + className + ".loginID_check");

        return new ResponseEntity<>(resultMap, status);
    }

    // 7. 아이디 찾기 (이름/이메일) 이메일과 이름 일치 여부 확인 뒤, 인증번호를 세션에다 저장후 200응답
    @RequestMapping("/sendMailForFindID")
    public ResponseEntity<?> emailSendForIdAuth(Model model, HttpServletRequest request, HttpServletResponse response,
                                                HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".sendMail");

        String name = request.getParameter("name");
        String emailNum = request.getParameter("email");
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("name", name);
        paramMap.put("email", emailNum);

        int result = mailSendService.searchUserExist(paramMap);
        if (result == 0) {
            return new ResponseEntity<>("Fail, Not Found ID", HttpStatus.NOT_FOUND);
        }

        String authNumId = "";

        try {
            authNumId = mailSendService.RandomNum();
            mailSendService.sendEmail(emailNum, authNumId, true);
        } catch (Exception e) {
            return new ResponseEntity<>("Fail, Error Generate", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        session.setAttribute("authNumber", authNumId);
        session.setAttribute("find_temp_name", name);
        session.setAttribute("find_temp_email", emailNum);

        logger.info("+ End " + className + ".emailSendAuth");
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    // 7-2. 이메일 인증 번호 대조 및 결과 반환, 받은 인증 번호를 세션에 저장한 값과 비교하고 맞을 시 유저정보 가져와서 응답.
    @RequestMapping("/validation/id/mail")
    public ResponseEntity<?> mailAuthComparisonForID(HttpServletRequest request, HttpServletResponse response,
                                                     HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".mailAuthComparisonForID");
        String authNum = request.getParameter("validationNumber");

        if (!session.getAttribute("authNumber").equals(authNum)) {
            return new ResponseEntity<>("fail", HttpStatus.FORBIDDEN);
        } else {
            session.removeAttribute("authNumber");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", session.getAttribute("find_temp_name"));
        paramMap.put("email", session.getAttribute("find_temp_email"));

        LgnInfoModel model = loginService.selectFindId(paramMap);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("loginID", model.getLoginID());
        resultMap.put("regdate", model.getRegdate());

        session.removeAttribute("find_temp_name");
        session.removeAttribute("find_temp_email");
        logger.info(" + End " + className + ".mailAuthComparisonForID");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    // 7-3. 이메일 인증 취소 (필요한지 좀 더 고민, 세션 관리 부분에서 취소해야 하는지 여부)

    // 8. 비밀번호 찾기
    @RequestMapping("/sendMailForFindPW")
    public ResponseEntity<?> emailSendForPwAuth(Model model, @RequestParam Map<String, Object> paramMap,
                                                HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("+ Start " + className + ".selectFindInfoPw");
        logger.info("   - paramMap : " + paramMap);

        Map<String, Object> resultMap = new HashMap<>();
        // 이메일이 존재하는지
        int result = mailSendService.searchUserExist(paramMap);

        if (result == 0) {
            return new ResponseEntity<>("FAIL", HttpStatus.NOT_FOUND);
        }

        // 소셜 로그인은 주소를 안 가져오므로 이를 더미 컬럼으로 사용
        LgnInfoModel lgnModel = loginService.selectFindId(paramMap);
        if("SOCIALLOGIN".equalsIgnoreCase(lgnModel.getAddr())){
            resultMap.put("result", "소셜로그인 계정");
            switch (lgnModel.getAddr_detail().toUpperCase()) {
                case "NAVER":
                    resultMap.put("result_detail", "네이버");
                    return new ResponseEntity<>(resultMap, HttpStatus.NOT_ACCEPTABLE);
                case "KAKAO":
                    resultMap.put("result_detail", "카카오");
                    return new ResponseEntity<>(resultMap, HttpStatus.NOT_ACCEPTABLE);
                case "GOOGLE":
                    resultMap.put("result_detail", "구글");
                    return new ResponseEntity<>(resultMap, HttpStatus.NOT_ACCEPTABLE);
                default:
                    resultMap.put("result_detail", "처리 불능, 문의 바람");
                    return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        String authNumId = mailSendService.RandomNum();
        mailSendService.sendEmail((String) paramMap.get("email"), authNumId, true);

        session.setAttribute("temp_pw_email", paramMap.get("email"));
        session.setAttribute("authNumber", authNumId);

        resultMap.put("status", "mail send Success");
        resultMap.put("email", session.getAttribute("temp_pw_email"));

        logger.info("+ End " + className + ".selectFindInfoPw");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    // 8-2. 이메일 인증 번호 대조 및 결과 반환, 아이디 가져와서 비밀번호를 랜덤 생성후 update하고, 그 결과를 반환
    @RequestMapping("/validation/update/pw/mail")
    public ResponseEntity<?> mailAuthComparisonForPW(HttpServletRequest request, HttpServletResponse response,
                                                     HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".mailAuthComparisonForPW");
        String authNum = request.getParameter("validationNumber");

        if (!session.getAttribute("authNumber").equals(authNum)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("status", "auth_fail");
            resultMap.put("authResultMsg", "인증번호가 발송되지 않았습니다.");
            resultMap.put("requestEmail", (String) session.getAttribute("temp_pw_email") + "로 전송된 인증 번호를 다시 확인해 주세요");
            resultMap.put("authNumberResult", "인증 실패, 재전송 요망");
            return new ResponseEntity<>("fail", HttpStatus.FORBIDDEN);
        } else {
            session.removeAttribute("authNumber");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", session.getAttribute("temp_pw_email"));
        LgnInfoModel model = loginService.selectFindId(paramMap);

        String change_password = mailSendService.RandomNum();

        paramMap.put("LoginID", model.getLoginID());
        paramMap.put("change_password", change_password);

        loginService.passwordChangeUpdate(paramMap);

        mailSendService.sendEmail((String) session.getAttribute("temp_pw_email"), change_password, false);
        paramMap.remove("change_password");
        paramMap.remove("LoginID");

        paramMap.put("status", "Update SUCCESS");
        session.removeAttribute("find_temp_email");
        logger.info(" + End " + className + ".mailAuthComparisonForPW");
        return new ResponseEntity<>(paramMap, HttpStatus.OK);
    }


}