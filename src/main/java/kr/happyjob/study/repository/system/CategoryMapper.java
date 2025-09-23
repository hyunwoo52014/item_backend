package kr.happyjob.study.repository.system;

import kr.happyjob.study.vo.system.CategoryModel;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    List<CategoryModel> searchCategories(Map<String, Object> paramMap);

    int searchCategoriesCount(Map<String, Object> paramMap);

    int saveDetailCode(Map<String, Object> paramMap);

    int saveCategory(Map<String, Object> paramMap);

    int duplicateCheck(Map<String, Object> paramMap);
}
