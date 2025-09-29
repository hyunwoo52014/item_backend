package kr.happyjob.study.controller.asset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.happyjob.study.service.asset.InboundService;
import kr.happyjob.study.vo.asset.InboundDetailModel;
import kr.happyjob.study.vo.asset.InboundModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/asset/")
public class InboundController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final String className = this.getClass().toString();

    @Autowired
    InboundService inboundService;

//    @RequestMapping("inbound")
//    public String inbound() {
//
//        logger.info("+ Start " + className + ".inbound");
//
//        logger.info("+ End " + className + ".inbound");
//
//
//        return "/asset/inbound/inbound";
//    }

    @RequestMapping("searchImport")
    @ResponseBody
    public Map<String, Object> searchImport (Model model, @RequestParam Map<String, Object> paramMap) throws Exception {

        logger.info("+ Start " + className + ".searchImport");
        logger.info("   - paramMap : " + paramMap);

        int currentPage = Integer.parseInt((String) paramMap.get("currentPage"));
        int pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
        int pageIndex = (currentPage - 1) * pageSize;

        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);

        List<InboundModel> importList = inboundService.searchImport(paramMap);

        int totalCount = inboundService.countImportList(paramMap);

        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("importList",importList);
        resultMap.put("totalCount",totalCount);
        model.addAttribute("currentPageImportList", currentPage);

        logger.info("+ End " + className + ".searchImport");

        return resultMap;
    }

    @RequestMapping("searchImportDetail")
    @ResponseBody
    public Map<String, Object> searchImportDetail(@RequestParam Map<String, Object> paramMap) throws Exception {

        logger.info("+ Start " + className + ".searchImportDetail");
        logger.info("   - paramMap : " + paramMap);

        String result = "";
        String resultMsg = "";

        InboundDetailModel importDetail = inboundService.searchImportDetail(paramMap);

        Map<String, Object> resultMap = new HashMap<>();

        if(importDetail == null) {
            result = "FAIL";
            resultMsg = "조회 실패했습니다.";

        } else {
            result = "SUCCESS";
            resultMsg = "조회 성공했습니다.";
        }

        resultMap.put("result", result);
        resultMap.put("resultMsg", resultMsg);
        resultMap.put("importDetail", importDetail);


        logger.info("+ End " + className + ".searchImportDetail");

        return resultMap;
    }

    @RequestMapping("registerImportDetail")
    @ResponseBody
    public Map<String, Object> registerImportDetail(@RequestParam Map<String, Object> paramMap) throws Exception {
        logger.info("+ Start " + className + ".searchImportDetail");
        logger.info("   - paramMap : " + paramMap);

        logger.info("   - product_name: " + paramMap.get("product_name"));
        logger.info("   - vendor_name: " + paramMap.get("vendor_name"));
        logger.info("   - category_sel: " + paramMap.get("category_sel"));
        logger.info("   - import_quantity: " + paramMap.get("import_quantity"));
        logger.info("   - import_price: " + paramMap.get("import_price"));
        logger.info("   - import_date: " + paramMap.get("import_date"));
        logger.info("   - content_text: " + paramMap.get("content_text"));
        logger.info("   - manager_name: " + paramMap.get("manager_name"));

        int registImport = inboundService.registerImportDetail(paramMap);

        Map<String, Object> resultMap = new HashMap<>();

        String result = "";
        String resultMsg = "";

        if(registImport > 0) {
            result="SUCCESS";
            resultMsg="저장성공하였습니다!";
        } else {
            result="FAIL";
            resultMsg="저장실패하였습니다!";
        }

        resultMap.put("result", result);
        resultMap.put("resultMsg", resultMsg);

        logger.info("+ End " + className + ".searchImportDetail");

        return resultMap;

    }

}
