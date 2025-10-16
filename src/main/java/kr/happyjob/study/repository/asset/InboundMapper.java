package kr.happyjob.study.repository.asset;

import kr.happyjob.study.vo.asset.InboundDetailModel;
import kr.happyjob.study.vo.asset.InboundModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InboundMapper {
    List<InboundModel> searchImport(Map<String, Object> paramMap);

    int countImportList(Map<String, Object> paramMap);

    InboundDetailModel searchImportDetail(Map<String, Object> paramMap);

    List<String> searchVendors(String string);

    int insertNewVendor(Map<String, Object> paramMap);

    int insertProductInfo(Map<String, Object> paramMap);

    int insertProductImport(Map<String, Object> paramMap);

    String selectNewProductNo();

    void insertProductDetail(Map<String, Object> paramMap);

    String getNextProductNo();
}
