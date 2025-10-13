package kr.happyjob.study.repository.asset;

import kr.happyjob.study.vo.asset.AnnualCategory;
import kr.happyjob.study.vo.asset.StatisticsModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {

    List<StatisticsModel> statisticsByPeriod(Map<String, Object> paramMap);

    List<StatisticsModel> statisticCanUse(Map<String, Object> paramMap);

    List searchYear();

    List<AnnualCategory> searchAnnualCategory(Map<String, Object> paramMap);
}
