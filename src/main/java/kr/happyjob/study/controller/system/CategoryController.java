package kr.happyjob.study.controller.system;

import kr.happyjob.study.service.system.CategoryService;
import kr.happyjob.study.vo.system.CategoryModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/categories")
public class CategoryController {
    // Set logger
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get class name for logger
    private final String className = this.getClass().toString();

    @Autowired
    CategoryService categoryService;

    @PostMapping("/searchCategories")
    @ResponseBody
    public Map<String, Object> searchCategories(@RequestParam Map<String, Object> paramMap) throws Exception{
        logger.info(" + Start CategoryController.searchCategories");
        logger.info("   - ParamMap : " + paramMap);

        int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));
        int pageSize = Integer.parseInt((String)paramMap.get("pageSize"));
        int pageIndex = (currentPage - 1) * pageSize;

        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);

        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<CategoryModel> categoryList = categoryService.searchCategories(paramMap);
            int categoryListCount = categoryService.searchCategoriesCount(paramMap);

            resultMap.put("categoryList", categoryList);
            resultMap.put("categoryListCount", categoryListCount);
            resultMap.put("pageSize", pageSize);
            resultMap.put("currentPage", currentPage);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logger.info(" + End CategoryController.searchCategories");
        }

        return resultMap;
    }

    @PostMapping("/saveCategory")
    @ResponseBody
    public Map<String, Object> saveCategory(@RequestParam Map<String, Object> paramMap) {
        logger.info(" + Start CategoryController.saveCategory");
        logger.info("   - ParamMap : " + paramMap);

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> saveResult = categoryService.saveCategory(paramMap);

        if(saveResult.get("result").equals("Y")) {
            resultMap.put("resultMsg", saveResult.get("resultMsg"));
        } else {
            resultMap.put("resultMsg", saveResult.get("resultMsg"));
        }

        logger.info(" + End CategoryController.saveCategory");

        return resultMap;
    }

    @PostMapping("/duplicateCheck")
    @ResponseBody
    public Map<String, Object> duplicateCheck(@RequestParam Map<String, Object> paramMap) throws Exception{
        logger.info(" + Start CategoryController.duplicateCheck");
        logger.info("   - ParamMap : " + paramMap);

        String result = "";
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int checkCount = categoryService.duplicateCheck(paramMap);

            if(checkCount > 0) {
                result = "Y";
            } else {
                result = "N";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resultMap.put("result", result);
        logger.info(" + End CategoryController.duplicateCheck");
        return resultMap;
    }
}
