package kr.happyjob.study.controller.usermgr;

import kr.happyjob.study.service.Usermgr.UsermgrreactService;
import kr.happyjob.study.vo.usermgr.UserModel;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/usermgrreact/")
public class UsermgrreactController {

    // Set logger
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get class name for logger
    private final String className = this.getClass().toString();

    @Autowired
    private UsermgrreactService usermgrreactService;

    // 사용자관리 리스트 출력
    @RequestMapping("userListreact")
    @ResponseBody
    public Map<String, Object> userListreact(Model model, @RequestParam Map<String, Object> paramMap,
                                             HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("   - Strat : " + className + ".userListreact");
        logger.info("   - paramMap : " + paramMap);

        int currentpage = Integer.parseInt((String)paramMap.get("currentpage"));
        int pagesize = Integer.parseInt((String)paramMap.get("pagesize"));
        int startpoint = (currentpage - 1) * pagesize;

        paramMap.put("pagesize", pagesize);
        paramMap.put("startpoint", startpoint);

        List<UserModel> datalist = usermgrreactService.userListreact(paramMap);
        int totalcnt = usermgrreactService.userListreactcnt(paramMap);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("datalist",datalist);
        resultMap.put("totalcnt",totalcnt);

        logger.info("   - stop : " + className + ".userListreact");

        return resultMap;
    }

    @RequestMapping("selectuser")
    @ResponseBody
    public Map<String, Object> selectuser(Model model, @RequestParam Map<String, Object> paramMap,
                                          HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("   - Strat : " + className + ".selectuser");
        logger.info("   - paramMap : " + paramMap);

        String result = "";
        String resultmsg = "";

        UserModel userdata = new UserModel();

        paramMap.put("imgread","N");

        try {
            userdata = usermgrreactService.selectuser(paramMap);
            result = "Y";
            resultmsg = "조회 되었습니다.";
        }
        catch(Exception e) {
            result = "N";
            resultmsg = e.getMessage();
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("userdata",userdata);
        resultMap.put("result",result);
        resultMap.put("resultmsg",resultmsg);

        logger.info("   - stop : " + className + ".selectuser");

        return resultMap;
    }


    @PostMapping("filedownload")
    @ResponseBody
    public void filedownload(Model model, @RequestParam Map<String, Object> paramMap,
                             HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("   - Strat : " + className + ".filedownload");
        logger.info("   - paramMap : " + paramMap);

        paramMap.put("imgread","Y");

        UserModel userdata = usermgrreactService.selectuser(paramMap);

        byte fileByte[] = FileUtils.readFileToByteArray(new File(userdata.getPygicalpath()));

        logger.info("   - paramMap : " + userdata.getPygicalpath());

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(userdata.getFilename(),"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();

        logger.info("   - stop : " + className + ".filedownload");

    }

    @RequestMapping("saveuser")
    @ResponseBody
    public Map<String, Object> saveuser(Model model, @RequestParam Map<String, Object> paramMap,
                                        HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("   - Strat : " + className + ".saveuser");
        logger.info("   - paramMap : " + paramMap);

        String result = "";
        String resultmsg = "";

        String action = (String) paramMap.get("action");
        logger.info("   - action : " + action);

        try {

            if("I".equals(action)) {
                logger.info("   - insert  : " + action);
                usermgrreactService.insertuser(paramMap, request);
            } else if("U".equals(action)) {
                logger.info("   - update  : " + action);
                usermgrreactService.updateuser(paramMap, request);
            } else {
                logger.info("   - delete  : " + action);
                usermgrreactService.deleteuser(paramMap, request);
            }

            result = "Y";
            resultmsg = "저장 되었습니다.";
        }
        catch(Exception e) {
            result = "N";
            resultmsg = e.getMessage();
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result",result);
        resultMap.put("resultmsg",resultmsg);

        logger.info("   - stop : " + className + ".saveuser");

        return resultMap;
    }

    @RequestMapping("dupcheck")
    @ResponseBody
    public Map<String, Object> dupcheck(Model model, @RequestParam Map<String, Object> paramMap,
                                        HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("   - Strat : " + className + ".dupcheck");
        logger.info("   - paramMap : " + paramMap);

        String result = "";
        String resultmsg = "";

        try {
            int chkcnt = usermgrreactService.dupcheck(paramMap);

            if(chkcnt > 0) {
                result = "N";
                resultmsg = "중복되어 사용이 불가 합니다.";
            } else {
                result = "Y";
                resultmsg = "사용 가능 합니다.";
            }
        }
        catch(Exception e) {
            result = "N";
            resultmsg = e.getMessage();
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result",result);
        resultMap.put("resultmsg",resultmsg);

        logger.info("   - stop : " + className + ".dupcheck");

        return resultMap;
    }






}