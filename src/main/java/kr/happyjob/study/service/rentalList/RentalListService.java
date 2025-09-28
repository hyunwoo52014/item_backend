package kr.happyjob.study.service.rentalList;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.happyjob.study.repository.rentalList.RentalListMapper;
import kr.happyjob.study.vo.rentalList.RentalListModel;

@Service
public class RentalListService {

    // Set logger
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get class name for logger
    private final String className = this.getClass().toString();

    @Autowired
    RentalListMapper rentalListMapper;

    /* 대여 현황 조회 */

    public List<RentalListModel> rentalList(Map<String, Object> paramMap) throws Exception {

        List<RentalListModel> rentalList = rentalListMapper.rentalList(paramMap);

        return rentalList;
    }

    /* 대여 현황 카운트 조회 */

    public int rentalListCnt(Map<String, Object> paramMap) throws Exception {
        int rentalListCnt = rentalListMapper.rentalListCnt(paramMap);
        return rentalListCnt;
    }

}
