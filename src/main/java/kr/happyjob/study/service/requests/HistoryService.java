package kr.happyjob.study.service.requests;

import kr.happyjob.study.repository.requests.HistoryMapper;
import kr.happyjob.study.vo.requests.HistoryModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service("HistoryService")
public class HistoryService {


    private final Logger log = Logger.getLogger(HistoryService.class);
    private final String classname = this.getClass().toString();
    private final HistoryMapper historymapper;

    public HistoryService(@Autowired HistoryMapper historyMapper) {
        this.historymapper = historyMapper;
    }

    public List<HistoryModel> historyList(@RequestParam Map<String, Object> paramMap) {
        return historymapper.historyList(paramMap);
    }

    public int historyCnt(@RequestParam Map<String, Object> paramMap) {
        return historymapper.historyCnt(paramMap);
    }

    public HistoryModel detailHistory(@RequestParam Map<String, Object> paramMap) {
        return historymapper.detailHistory(paramMap);
    }

    public int deleteHistory(@RequestParam Map<String, Object> paramMap) {
        return historymapper.deleteHistory(paramMap);
    }
}