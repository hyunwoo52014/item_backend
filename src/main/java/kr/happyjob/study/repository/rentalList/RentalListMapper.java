package kr.happyjob.study.repository.rentalList;

import java.util.List;
import java.util.Map;

import kr.happyjob.study.vo.rentalList.RentalListModel;

public interface RentalListMapper {

    // 대여 현황 조회
    public List<RentalListModel> rentalList(Map<String, Object> paramMap)throws Exception ;

    // 대여 현황 카운트 조회
    public int rentalListCnt(Map<String, Object> paramMap)throws Exception ;

}
