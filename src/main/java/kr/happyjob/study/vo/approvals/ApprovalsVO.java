package kr.happyjob.study.vo.approvals;

import java.sql.Date;


public class ApprovalsVO {

    private String name; // 사용자 이름
    private String product_name; // 제품 이름

    private Date order_date; // 사용 신청일,반납 신청일
    private Date rental_date; // 대여일
    private Date return_date; //반납일

    private String product_state; // 제품 상태

}//end class
