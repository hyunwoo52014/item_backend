package kr.happyjob.study.service.system;

import kr.happyjob.study.repository.system.CategoryMapper;
import kr.happyjob.study.vo.system.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public List<CategoryModel> searchCategories(Map<String, Object> paramMap) {
        return categoryMapper.searchCategories(paramMap);
    }

    public int searchCategoriesCount(Map<String, Object> paramMap) {
        return categoryMapper.searchCategoriesCount(paramMap);
    }

    public Map<String, Object> saveCategory(Map<String, Object> paramMap) {
        int saveDetailResult = categoryMapper.saveDetailCode(paramMap);

        String result = "";
        String resultMsg = "";
        Map<String, Object> resultMap = new HashMap<>();
        if(saveDetailResult == 1) {
            int saveCategoryResult = categoryMapper.saveCategory(paramMap);
            if (saveCategoryResult == 1) {
                result = "Y";
                resultMsg = "저장 성공했습니다.";
            } else {
                result = "N";
                resultMsg = "저장 실패했습니다.";
            }
        }
        resultMap.put("result", result);
        resultMap.put("resultMsg", resultMsg);

        return resultMap;
    }

    public int duplicateCheck(Map<String, Object> paramMap) {
        return categoryMapper.duplicateCheck(paramMap);
    }
}
