package kr.happyjob.study.controller.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.happyjob.study.repository.login.ListUsrChildMnuAtrtMapper;
import kr.happyjob.study.service.login.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.happyjob.study.vo.login.LoginVO;
import kr.happyjob.study.vo.login.UserVO;
import kr.happyjob.study.vo.login.LgnInfoModel;
import kr.happyjob.study.vo.login.UsrMnuAtrtModel;
import kr.happyjob.study.vo.login.UsrMnuChildAtrtModel;

/* 추가*/
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;


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

    @Value("${google.query.parameter.client.id}")
    private String GOOGLE_CLIENT_ID;


    @Value("${google.query.parameter.client.secret}")
    private String GOOGLE_CLIENT_SECRET;


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

    /* 일반/ 소셜용 세션 */
    private Map<String, Object> setSessionAndBuildResponse(HttpServletRequest request,
                                                           HttpSession session,
                                                           LgnInfoModel lgnInfoModel) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        // 메뉴 권한 조회(0 depth)
        Map<String, Object> menuParam = new HashMap<>();
        menuParam.put("usr_sst_id", lgnInfoModel.getUsr_sst_id());
        menuParam.put("userType", lgnInfoModel.getMem_author());
        List<UsrMnuAtrtModel> listUsrMnuAtrtModel = listUsrMnuAtrtService.listUsrMnuAtrt(menuParam);

        // 메뉴 권한 조회(1 depth)
        for (UsrMnuAtrtModel list : listUsrMnuAtrtModel) {
            Map<String, Object> subParam = new HashMap<>();
            subParam.put("lgn_Id", lgnInfoModel.getLgn_id());
            subParam.put("hir_mnu_id", list.getMnu_id());
            subParam.put("userType", lgnInfoModel.getMem_author());
            list.setNodeList(listUsrChildMnuAtrtService.listUsrChildMnuAtrt(subParam));
        }

        // 세션 셋팅
        session.setAttribute("loginId", lgnInfoModel.getLgn_id());
        session.setAttribute("userNm", lgnInfoModel.getUsr_nm());
        session.setAttribute("usrMnuAtrt", listUsrMnuAtrtModel);
        session.setAttribute("userType", lgnInfoModel.getMem_author());
        session.setAttribute("serverName", request.getServerName());
        session.setAttribute("team", lgnInfoModel.getTeam());

        // 응답 공통 포맷
        resultMap.put("result", "SUCCESS");
        resultMap.put("resultMsg", "로그인 성공");
        resultMap.put("loginId", lgnInfoModel.getLgn_id());
        resultMap.put("userNm", lgnInfoModel.getUsr_nm());
        resultMap.put("usrMnuAtrt", listUsrMnuAtrtModel);
        resultMap.put("userType", lgnInfoModel.getMem_author());
        resultMap.put("serverName", request.getServerName());
        resultMap.put("team", lgnInfoModel.getTeam());
        return resultMap;
    }


    /*  로그인 */
    @PostMapping(
            value = {"/loginProc.do", "/api/loginProc.do", "/login.do", "/api/login.do"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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
/*                result = "SUCCESS";
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
                resultMap.put("serverName", request.getServerName());*/

                /* 추가 */
                return setSessionAndBuildResponse(request, session, lgnInfoModel);

            } else {

                result = "FALSE";
                resultMsg = "사용자 로그인 정보가 일치하지 않습니다.";
                resultMap.put("resultMsg", resultMsg);
                resultMap.put("result", result);
                resultMap.put("serverName", request.getServerName());
            }



            resultMap.put("result", result);
            resultMap.put("resultMsg", resultMsg);
            resultMap.put("serverName", request.getServerName());

        } catch (Exception e) {
            e.printStackTrace();
        }


        logger.info("+ End LoginController.loginProc.do");

        return resultMap;
    }


    /* 구글 로그인 */

    /* 구글 동의 화면 - 엔드포인트*/
    @GetMapping({"/api/googleLogin","/googleLogin"})
    public String googleSocialLogin(HttpSession session) {
        String redirectUri = "http://localhost/login/oauth2/process"; // 콜백 고정(아래와 동일해야 함)
        String scope = "openid email profile";
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + GOOGLE_CLIENT_ID
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=" + scope
                + "&access_type=offline"
                + "&prompt=consent";

        session.setAttribute("socialLogin", "google");
        return "redirect:" + googleAuthUrl;
    }


    /* 콜백처리 *//* String -> void */
    @RequestMapping("/login/oauth2/process")
    public void socialLoginRedirect(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        logger.info("+ Start /login/oauth2/process");

        String registerId = "";
        String registerName = "";
        String registerEmail = "";
        String accessToken = "";

        if ("google".equals(session.getAttribute("socialLogin"))) {
            logger.info(">>>>>>>> 구글 로그인 처리");
            String code = params.get("code");

            if (code == null || code.isEmpty()) {
                logger.error("Authorization code 없음");
                //return "redirect:/login";
                response.sendRedirect("http://localhost:3000/login?error=no_code");

            }

            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 토큰 요청
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", GOOGLE_CLIENT_ID);
            body.add("client_secret", GOOGLE_CLIENT_SECRET);
            body.add("code", code);
            body.add("redirect_uri", "http://localhost/login/oauth2/process");
            body.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> tokenResponse = template.postForEntity(
                    "https://oauth2.googleapis.com/token",
                    entity,
                    Map.class
            );

            if (tokenResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> result = tokenResponse.getBody();
                accessToken = (String) result.get("access_token");

                logger.info("액세스 토큰 발급 성공");

                // 사용자 정보 요청
                HttpHeaders userinfoHeaders = new HttpHeaders();
                userinfoHeaders.set("Authorization", "Bearer " + accessToken);
                HttpEntity<String> userinfoEntity = new HttpEntity<>(userinfoHeaders);

                ResponseEntity<Map> userinfoResponse = template.exchange(
                        "https://www.googleapis.com/oauth2/v2/userinfo",
                        HttpMethod.GET,
                        userinfoEntity,
                        Map.class
                );

                if (userinfoResponse.getStatusCode() == HttpStatus.OK) {
                    Map<String, Object> userInfo = userinfoResponse.getBody();

                    registerId = (String) userInfo.get("id");
                    registerName = (String) userInfo.get("name");
                    registerEmail = (String) userInfo.get("email");

                    logger.info("사용자 정보: ID=" + registerId + ", Name=" + registerName + ", Email=" + registerEmail);
                } else {
                    logger.error("사용자 정보 요청 실패");
                    //return "redirect:/login";
                    response.sendRedirect("http://localhost:3000/login?error=userinfo_failed");
                }
            } else {
                logger.error("토큰 요청 실패");
                //return "redirect:/login";
                response.sendRedirect("http://localhost:3000/login?error=token_failed");
            }
        } else {
            logger.error("잘못된 소셜 로그인");
            //return "redirect:/login";
            response.sendRedirect("http://localhost:3000/login?error=invalid_social");
            return;
        }

        session.setAttribute("accessToken", accessToken);

        // upsertSocialUser 사용하여 사용자 처리
        Map<String, Object> upsertParam = new HashMap<>();
        upsertParam.put("email", registerEmail);
        upsertParam.put("name", registerName);
        upsertParam.put("googleSub", registerId);

        LgnInfoModel lgnInfoModel = loginService.upsertSocialUser(upsertParam);

        if (lgnInfoModel == null) {
            logger.error("사용자 처리 실패");
            //return "redirect:/login";
            response.sendRedirect("http://localhost:3000/login?error=user_process_failed");
            return;
        }

        if ("N".equals(lgnInfoModel.getStatus_yn())) {
            logger.warn("비활성화 계정");
            //return "redirect:/login?error=disabled";
            response.sendRedirect("http://localhost:3000/login?error=disabled");
            return;
        }

        // 메뉴 권한 조회 - 올바른 Service 사용
        Map<String, Object> menuParam = new HashMap<>();
        menuParam.put("userType", lgnInfoModel.getUser_type());

        // listUsrMnuAtrtService 사용
        List<UsrMnuAtrtModel> listUsrMnuAtrtModel = listUsrMnuAtrtService.listUsrMnuAtrt(menuParam);

        // 1depth 메뉴 조회
        for (UsrMnuAtrtModel list : listUsrMnuAtrtModel) {
            Map<String, Object> subParam = new HashMap<>();
            subParam.put("hir_mnu_id", list.getMnu_id());
            subParam.put("userType", lgnInfoModel.getUser_type());

            // listUsrChildMnuAtrtService 사용
            list.setNodeList(listUsrChildMnuAtrtService.listUsrChildMnuAtrt(subParam));
        }

        // 세션 설정
        session.setAttribute("loginId", lgnInfoModel.getLoginID());
        session.setAttribute("userNm", lgnInfoModel.getName());
        session.setAttribute("usrMnuAtrt", listUsrMnuAtrtModel);
        session.setAttribute("userType", lgnInfoModel.getUser_type());
        session.setAttribute("serverName", request.getServerName());
        session.setAttribute("email", registerEmail);
        session.setAttribute("reg_date", lgnInfoModel.getRegdate());
        //additional-info
        session.setAttribute("team",lgnInfoModel.getTeam());

        logger.info("+ End /login/oauth2/process - SUCCESS");
        //return "redirect:/dashboard";
        //return "forward:/index.html";
        /*return "redirect:http://localhost:3000/dashboard?socialLogin=success";*/
        response.sendRedirect("http://localhost:3000/auth-callback");
    }

    /* AuthCallBack 에서 세숀 확인 */
    @GetMapping({"/api/auth/check", "/auth/check"})
    @ResponseBody
    public Map<String, Object> checkSession(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String loginId = (String) session.getAttribute("loginId");

        if(loginId != null){
            result.put("loginId", loginId);
            result.put("userNm", session.getAttribute("userNm"));
            result.put("userType", session.getAttribute("userType"));
            result.put("usrMnuAtrt", session.getAttribute("usrMnuAtrt"));
            result.put("serverName", session.getAttribute("serverName"));
            result.put("email", session.getAttribute("email"));

            Object teamObj = session.getAttribute("team");

            if(teamObj == null){
                Map<String, Object> pTeam = new HashMap<>();
                pTeam.put("loginID", loginId);
                LgnInfoModel user = loginService.selectFindId(pTeam);
                if (user != null && user.getTeam() != null && !user.getTeam().isBlank()) {
                    teamObj = user.getTeam();
                    session.setAttribute("team", teamObj);
                }
            }
            result.put("team", session.getAttribute("team"));



        }

        return result;
    }


    /*로그아웃*/
    @RequestMapping(value = "/loginOut.do")
    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        ModelAndView mav = new ModelAndView();
        session.invalidate();
        mav.setViewName("redirect:/login");

        return mav;
    }

    /* 회원가입 - 일반회원 */
    // 4. 일반 회원 가입
    @RequestMapping({"/register.do", "/api/register.do"})
    @ResponseBody
    public Map<String, Object> registerUser(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("+ Start " + className + ".registerUser");
        logger.info("   - paramMap : " + paramMap);

        String action = (String) paramMap.get("action");

        // 필수값
        String loginID = (String) paramMap.get("loginID");
        String password = (String) paramMap.get("password");
        String name = (String) paramMap.get("name");
        String birthday = (String)paramMap.get("birthday");
        String email = (String) paramMap.get("email");
        String addr = (String)paramMap.get("addr");
        String addrDetail = (String) paramMap.get("addr_detail");
        String team = (String) paramMap.get("team");
        String hp = (String) paramMap.get("hp");

        if (loginID == null || loginID.isBlank()
                || password == null || password.isBlank()
                || name == null || name.isBlank()
                || birthday == null || birthday.isBlank()
                || email == null || email.isBlank()
                || addr == null || addr.isBlank()
                || addrDetail == null || addrDetail.isBlank()
                || team == null || team.isBlank()
                || hp == null || hp.isBlank()) {

            Map<String, Object> rm = new HashMap<>();
            rm.put("result", "FAIL");
            rm.put("resultMsg", "필수 입력값이 누락되었습니다.");
            return rm;
        }

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

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        resultMap.put("resultMsg", resultMsg);
        logger.info("+ End " + className + ".registerUser");
        return resultMap;
    }

    // 5. 아이디 중복 확인
    @RequestMapping({"/checkDuplicatedloginID", "/api/checkDuplicatedloginID"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkDuplicatedloginID(Model model, @RequestParam Map<String, Object> paramMap,
                                                                      HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".checkDuplicatedloginID");

        String loginID = (String) paramMap.get("loginID");
        Map<String, Object> resultMap = new HashMap<>();


        // 기본값 - 성공
        HttpStatus status = HttpStatus.OK;
        String result = "SUCCESS";
        String resultMsg = "사용 가능한 아이디입니다.";

        int duplicationVal =  0;

        try {
            duplicationVal = loginService.checkDuplicatedLoginID(loginID);

            if (duplicationVal > 0) {
                LgnInfoModel existingUser = loginService.selectFindId(paramMap);

                if (existingUser != null && "N".equals(existingUser.getStatus_yn())) {
                    // Case: ID는 존재하고 상태가 'N'(비활성화)인 경우
                    result = "FAIL";
                    resultMsg = "비활성화된 계정으로 등록되어 있습니다.";
                    status = HttpStatus.UNAUTHORIZED; // 401 Unauthorized
                } else {
                    // Case: ID가 존재하고 활성화 상태인 경우 (단순 중복)
                    result = "FAIL";
                    resultMsg = "이미 가입된 아이디가 있습니다.";
                    status = HttpStatus.CONFLICT; // 409 Conflict
                }
            }

        } catch(Exception e){
            result = "FAIL";
            resultMsg = "서버 오류 (예외 발생)";
            status = HttpStatus.INTERNAL_SERVER_ERROR; // 500 Internal Server Error
            logger.error("Error in checkDuplicatedloginID", e);
        }
        resultMap.put("result", result);
        resultMap.put("resultMsg", resultMsg);

        logger.info("+ End " + className + ".checkDuplicatedloginID");
        return new ResponseEntity<>(resultMap, status);
    }


    // 6. 이메일 중복 확인
    @RequestMapping({"/checkDuplicatedEmail", "/api/checkDuplicatedEmail"})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> check_email(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                           HttpServletResponse response, HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".loginID_check");

        String email = (String) paramMap.get("email");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        String result = null;
        String resultMsg = null;

/*        try {
            int duplicationVal = loginService.checkDuplicatedEmail(email);

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
        }*/

        try {
            int duplicationVal = loginService.checkDuplicatedEmail(email);

            if (duplicationVal > 0) {
                result = "FAIL";
                resultMsg = "이미 가입된 이메일이 있습니다.";
                status = HttpStatus.CONFLICT; // 409
            } else {
                result = "SUCCESS";
                resultMsg = "사용 가능한 이메일입니다.";
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            result = "FAIL";
            resultMsg = "서버 오류";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        resultMap.put("result", result);
        resultMap.put("resultMsg", resultMsg);
        logger.info("+ End " + className + ".loginID_check");

        return new ResponseEntity<>(resultMap, status);
    }


    // 7. 아이디 찾기 (이름/이메일) 이메일과 이름 일치 여부 확인 뒤, 인증번호를 세션에다 저장후 200응답
    @RequestMapping({"/sendMailForFindID", "/api/sendMailForFindID"})
    public ResponseEntity<?> emailSendForIdAuth(Model model, HttpServletRequest request, HttpServletResponse response,
                                                HttpSession session) throws Exception {
        logger.info("+ Start " + className + ".sendMail");

        String name = request.getParameter("name");
        String emailNum = request.getParameter("email");
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("name", name);
        paramMap.put("email", emailNum);

        // 존재여부
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
    @RequestMapping({"/validation/id/mail","/api/validation/id/mail"})
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

    // 8. 비밀번호 찾기
    @RequestMapping({"/sendMailForFindPW", "/api/sendMailForFindPW"})
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

        paramMap.put("loginID", model.getLoginID());
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

    // 소셜 아이디 update
    @PostMapping(
            value = {"/api/login/additional/update/data"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateAdditionalInfo(
            @RequestParam Map<String, Object> paramMap,
            HttpSession session,
            HttpServletRequest request
    ) {
        Map<String, Object> result = new HashMap<>();
        try {

            String loginID = (String) paramMap.get("loginID");
            String team = (String) paramMap.get("team");
            if (loginID == null || loginID.isBlank()) {
                Object sid = session.getAttribute("loginId");
                if (sid != null) loginID = sid.toString();
            }

            // 2) 검증
            if (loginID == null || loginID.isBlank()) {
                result.put("result", "FAIL");
                result.put("resultMsg", "loginID가 없습니다.");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            if (team == null || team.isBlank()) {
                result.put("result", "FAIL");
                result.put("resultMsg", "team이 없습니다.");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }

            // 3) 업데이트
            Map<String, Object> upd = new HashMap<>();
            upd.put("loginID", loginID);
            upd.put("team", team);
            int updated = loginService.updateUser(upd);

            // 4) 세션도 갱신(원하면)
             session.setAttribute("team", team);

            if (updated > 0) {
                result.put("result", "SUCCESS");
                result.put("resultMsg", "추가정보 저장 완료");
                result.put("loginId", loginID);
                result.put("team", team);
                result.put("serverName", request.getServerName());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.put("result", "FAIL");
                result.put("resultMsg", "변경된 내용이 없습니다.");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }

        } catch (Exception e) {
            logger.error("updateAdditionalInfo error", e);
            result.put("result", "FAIL");
            result.put("resultMsg", "서버 오류");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}