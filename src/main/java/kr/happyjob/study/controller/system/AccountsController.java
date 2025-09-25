package kr.happyjob.study.controller.system;

import kr.happyjob.study.service.system.AccountsService;
import kr.happyjob.study.vo.system.AccountsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/system/")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @RequestMapping("account")
    @ResponseBody
    public Map<String, Object> Acconts(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));
        int pageSize = Integer.parseInt((String)paramMap.get("pageSize"));
        int pageIndex = (currentPage - 1) * pageSize;

        paramMap.put("pageSize", pageSize);
        paramMap.put("pageIndex", pageIndex);

        List<AccountsModel> accountList = accountsService.accountsList(paramMap);
        int totalCnt = accountsService.accountsCount(paramMap);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("accountList", accountList);
        resultMap.put("totalCnt", totalCnt);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("accountDetail")
    public Map<String, Object> AccountDetail(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        AccountsModel accountDetailList = accountsService.accountDetail(paramMap);

        resultMap.put("accountDetailList", accountDetailList);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("accountEquip")
    public Map<String, Object> AccountEquip(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));
        int pageSize = Integer.parseInt((String)paramMap.get("pageSize"));
        int pageIndex = (currentPage - 1) * pageSize;

        paramMap.put("pageSize", pageSize);
        paramMap.put("pageIndex", pageIndex);

        Map<String, Object> resultMap = new HashMap<>();
        List<AccountsModel> accountEquipList = accountsService.accountEquip(paramMap);
        int totalCnt = accountsService.accountEquipCount(paramMap);

        resultMap.put("accountEquipList", accountEquipList);
        resultMap.put("totalCnt", totalCnt);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("accountDelete")
    public Map<String, Object> AccountDelete(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        String resultMessage = "";
        String resultCode = "";

        int res = accountsService.accountDelete(paramMap);

        if (res > 0){
            resultMessage = "SUCCESS";
            resultCode = "Y";
        } else {
            resultMessage = "FAIL";
            resultCode = "N";
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultCode", resultCode);
        resultMap.put("resultMessage", resultMessage);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("accountRestore")
    public Map<String, Object> AccountRestore(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        String resultMessage = "";
        String resultCode = "";

        int res = accountsService.accountRestore(paramMap);

        if (res > 0){
            resultMessage = "SUCCESS";
            resultCode = "Y";
        } else {
            resultMessage = "FAIL";
            resultCode = "N";
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultCode", resultCode);
        resultMap.put("resultMessage", resultMessage);

        return resultMap;
    }

}
