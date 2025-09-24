package kr.happyjob.study.repository.system;

import kr.happyjob.study.vo.system.CategoryModel;
import kr.happyjob.study.vo.system.RemainDataModel;
import kr.happyjob.study.vo.system.SelectedCategoryModel;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    List<CategoryModel> searchCategories(Map<String, Object> paramMap);

    int searchCategoriesCount(Map<String, Object> paramMap);

    int saveDetailCode(Map<String, Object> paramMap);

    int saveCategory(Map<String, Object> paramMap);

    int duplicateCheck(Map<String, Object> paramMap);

    SelectedCategoryModel searchSelectedCategory(Map<String, Object> paramMap);

    int updateDetailCode(Map<String, Object> paramMap);

    int updateCategory(Map<String, Object> paramMap);

    int updateProductInfo(Map<String, Object> paramMap);

    int updateProductDetail(Map<String, Object> paramMap);

    int updateUseHistory(Map<String, Object> paramMap);

    int updateMyRental(Map<String, Object> paramMap);

    void disableForeignKeyCheck();

    void enableForeignKeyCheck();

    void deleteCategory(Map<String, Object> paramMap);

    void deleteProductDetail(Map<String, Object> paramMap);

    void deleteProductInfo(Map<String, Object> paramMap);

    void deleteUseHistory(Map<String, Object> paramMap);

    void deleteMyRental(Map<String, Object> paramMap);

    void deleteDetailCode(Map<String, Object> paramMap);

    int checkCategoryUsageInProductDetail(Map<String, Object> paramMap);

    int checkCategoryUsageInProductInfo(Map<String, Object> paramMap);

    int checkCategoryUsageInUseHistory(Map<String, Object> paramMap);

    List<RemainDataModel> searchRemainData(Map<String, Object> paramMap);

    int searchRemainDataCount(Map<String, Object> paramMap);
}
