package kr.happyjob.study.controller.asset;

import kr.happyjob.study.service.asset.StatisticsService;
import kr.happyjob.study.vo.asset.AnnualCategory;
import kr.happyjob.study.vo.asset.StatisticsModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/asset/")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final String className = this.getClass().toString();

    @RequestMapping("statisticsByPeriod")
    @ResponseBody
    public Map<String, Object> statisticsByPeriod(@RequestParam Map<String, Object> paramMap) {
        logger.info("+ Start " + className + ".statisticsByPeriod");
        logger.info("   - paramMap : " + paramMap);

        int totalAmount = 0;
        int canUseAmount = 0;

        Map<String,	Object> resultMap = new HashMap<>();

        List<StatisticsModel> statisticsList = statisticsService.statisticsByPeriod(paramMap);
        for(StatisticsModel sModel : statisticsList) {
            totalAmount += sModel.getAmount();
        }

        List<StatisticsModel> statisticsCanUseList = statisticsService.statisticCanUse(paramMap);
        for(StatisticsModel sModel : statisticsCanUseList) {
            canUseAmount += sModel.getAmount();
        }

        resultMap.put("totalAmount", totalAmount);
        resultMap.put("canUseAmount", canUseAmount);
        resultMap.put("totalList", statisticsList);

        logger.info("+ End " + className + ".statisticsByPeriod");

        return resultMap;
    }
    @RequestMapping("searchYear")
    @ResponseBody
    public Map<String, Object> searchYear() {
        logger.info("+ Start " + className + ".searchYear");

        Map<String, Object> resultMap = new HashMap<>();

        List yearList = statisticsService.searchYear();

        resultMap.put("yearList", yearList);

        logger.info("+ End " + className + ".searchYear");

        return resultMap;
    }

    @RequestMapping("searchAnnualCategory")
    @ResponseBody
    public Map<String, Object> searchAnnualCategory(@RequestParam Map<String, Object> paramMap) {
        logger.info("+ Start " + className + ".searchAnnualCategory");
        logger.info("   - paramMap : " + paramMap);

        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<AnnualCategory> annualCategoryList = statisticsService.searchAnnualCategory(paramMap);

        resultMap.put("annualCategoryList", annualCategoryList);

        logger.info("+ End " + className + ".searchAnnualCategory");

        return resultMap;
    }

}
