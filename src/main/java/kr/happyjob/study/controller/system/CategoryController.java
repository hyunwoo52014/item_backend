package kr.happyjob.study.controller.system;

import kr.happyjob.study.service.system.CategoryService;
import kr.happyjob.study.vo.system.CategoryModel;
import kr.happyjob.study.vo.system.RemainDataModel;
import kr.happyjob.study.vo.system.SelectedCategoryModel;
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

    @PostMapping("/searchSelectedCategory")
    @ResponseBody
    public Map<String, Object> searchSelectedCategory(@RequestParam Map<String, Object> paramMap) throws Exception{
        logger.info(" + Start CategoryController.searchSelectedCategory");
        logger.info("   - ParamMap : " + paramMap);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            SelectedCategoryModel selectedCategory = categoryService.searchSelectedCategory(paramMap);
            resultMap.put("selectedCategory", selectedCategory);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            logger.info(" + End CategoryController.searchSelectedCategory");
        }
        return resultMap;
    }

    @PostMapping("/saveCategory")
    @ResponseBody
    public Map<String, Object> saveCategory(@RequestParam Map<String, Object> paramMap) {
        logger.info(" + Start CategoryController.saveCategory");
        logger.info("   - ParamMap : " + paramMap);
        Map<String, Object> saveResult = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();

        if(((String)paramMap.get("state")).equals("I")) {
            saveResult = categoryService.saveCategory(paramMap);
        }
        if(((String)paramMap.get("state")).equals("U")) {
            saveResult = categoryService.updateCategory(paramMap);
        }

        resultMap.put("resultMsg", saveResult.get("resultMsg"));

        logger.info(" + End CategoryController.saveCategory");

        return resultMap;
    }

    @PostMapping("/deleteCategory")
    @ResponseBody
    public Map<String, Object> deleteCategory(@RequestParam Map<String, Object> paramMap) {
        logger.info(" + Start CategoryController.deleteCategory");
        logger.info("   - ParamMap : " + paramMap);

        try {
            Map<String, Object> resultMap = new HashMap<>();
            Map<String, Object> deleteResult = categoryService.deleteCategory(paramMap);
            resultMap.put("result", deleteResult.get("result"));
            resultMap.put("resultMsg", deleteResult.get("resultMsg"));
            return resultMap;
        } catch (Exception e) {
            throw new RuntimeException("삭제중 오류 발생!!", e);
        } finally {
            logger.info(" + End CategoryController.deleteCategory");
        }
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

    @PostMapping("/searchRemainData")
    @ResponseBody
    public Map<String, Object> searchRemainData(@RequestParam Map<String, Object> paramMap) {
        logger.info(" + Start CategoryController.searchRemainData");
        logger.info("   - ParamMap : " + paramMap);

        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<RemainDataModel> remainDataList = categoryService.searchRemainData(paramMap);
            int remainDataListCount = categoryService.searchRemainDataCount(paramMap);
            resultMap.put("totalCount", remainDataListCount);
            resultMap.put("remainDataList", remainDataList);
            return resultMap;
        } catch (Exception e) {
            throw new RuntimeException("잔여 데이터 조회 컨트롤러에서 오류", e);
        } finally {
            logger.info(" + End CategoryController.searchRemainData");
        }
    }
}
