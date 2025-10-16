package kr.happyjob.study.service.asset;

import kr.happyjob.study.repository.asset.StatisticsMapper;
import kr.happyjob.study.vo.asset.AnnualCategory;
import kr.happyjob.study.vo.asset.StatisticsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class StatisticsService {
    @Autowired
    StatisticsMapper statisticsMapper;


    public List<StatisticsModel> statisticsByPeriod(Map<String, Object> paramMap) {
        return statisticsMapper.statisticsByPeriod(paramMap);
    }

    public List<StatisticsModel> statisticCanUse(Map<String, Object> paramMap) {
        return statisticsMapper.statisticCanUse(paramMap);
    }

    public List searchYear() {
        return statisticsMapper.searchYear();
    }

    public List<AnnualCategory> searchAnnualCategory(Map<String, Object> paramMap) {
        return statisticsMapper.searchAnnualCategory(paramMap);
    }


}
