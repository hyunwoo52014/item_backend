package kr.happyjob.study.service.system;

import kr.happyjob.study.repository.system.CategoryMapper;
import kr.happyjob.study.vo.system.CategoryModel;
import kr.happyjob.study.vo.system.RemainDataModel;
import kr.happyjob.study.vo.system.SelectedCategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public Map<String, Object> updateCategory(Map<String, Object> paramMap) {


        String result = "";
        String resultMsg = "";
        Map<String, Object> resultMap = new HashMap<>();
        try {
            categoryMapper.disableForeignKeyCheck();
            categoryMapper.updateProductInfo(paramMap);
            categoryMapper.updateProductDetail(paramMap);
            categoryMapper.updateUseHistory(paramMap);
            categoryMapper.updateMyRental(paramMap);


            categoryMapper.updateCategory(paramMap);
            categoryMapper.updateDetailCode(paramMap);

            categoryMapper.enableForeignKeyCheck();
            result = "Y";
            resultMsg =  "수정 성공했습니다.";
            resultMap.put("result", result);
            resultMap.put("resultMsg", resultMsg);
            return resultMap;
        } catch (Exception e) {
            categoryMapper.enableForeignKeyCheck();
            throw new RuntimeException("카테고리 수정 중 오류 발생!", e);
        }


    }

    public int duplicateCheck(Map<String, Object> paramMap) {
        return categoryMapper.duplicateCheck(paramMap);
    }

    public SelectedCategoryModel searchSelectedCategory(Map<String, Object> paramMap) {
        return categoryMapper.searchSelectedCategory(paramMap);
    }

    public Map<String, Object> deleteCategory(Map<String, Object> paramMap) {
        try {
            String result = "";
            String resultMsg = "";

            Map<String, Object> resultMap = new HashMap<>();

            int usageCount = categoryMapper.checkCategoryUsageInProductDetail(paramMap);
            usageCount += categoryMapper.checkCategoryUsageInProductInfo(paramMap);
            usageCount += categoryMapper.checkCategoryUsageInUseHistory(paramMap);

            if(usageCount > 0) {
                resultMsg = "현재 남아있는 데이터가 있어 삭제를 진행할 수 없습니다. ";
                result = "N";
                resultMap.put("result", result);
                resultMap.put("resultMsg", resultMsg);
                return resultMap;
            }

            categoryMapper.disableForeignKeyCheck();
            categoryMapper.deleteCategory(paramMap);
            categoryMapper.deleteProductDetail(paramMap);
            categoryMapper.deleteProductInfo(paramMap);
            categoryMapper.deleteUseHistory(paramMap);
            categoryMapper.deleteMyRental(paramMap);

            categoryMapper.deleteDetailCode(paramMap);
            categoryMapper.enableForeignKeyCheck();
            result = "Y";
            resultMsg = "삭제 성공했습니다.";
            resultMap.put("result", result);
            resultMap.put("resultMsg", resultMsg);
            return resultMap;

        } catch (Exception e) {
            categoryMapper.enableForeignKeyCheck();
            throw new RuntimeException("카테고리 삭제 중 서비스 단에서 오류", e);
        }

    }

    public List<RemainDataModel> searchRemainData(Map<String, Object> paramMap) {
        try {
            return categoryMapper.searchRemainData(paramMap);
        } catch (Exception e) {
            throw new RuntimeException("잔여 데이터 조회 서비스단에서 오류 발생", e);
        }

    }

    public int searchRemainDataCount(Map<String, Object> paramMap) {
        try {
            return categoryMapper.searchRemainDataCount(paramMap);
        } catch (Exception e) {
            throw new RuntimeException("잔여 데이터 갯수 조회 서비스단에서 오류 발생", e);
        }
    }
}
