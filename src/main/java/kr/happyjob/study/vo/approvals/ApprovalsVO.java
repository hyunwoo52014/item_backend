package kr.happyjob.study.vo.approvals;

import java.sql.Date;


public class ApprovalsVO {

    private int usage_code; // 사용이력 코드

    private String category_code; // 카테고리 코드
    private int product_detail_code; // 제품 상세 코드

    private String loginID; // 사용자 ID
    private String name; // 사용자 이름

    private String product_no; //제품 번호
    private String product_name; // 제품 이름

    private Date order_date; // 사용 신청일,반납 신청일
    private Date rental_date; // 대여일
    private Date return_date; //반납일

    private String order_reason; //신청 사유

    private String product_state; // 제품 상태


    /********************************************************************/

    public int getUsage_code() {
        return usage_code;
    }

    public void setUsage_code(int usage_code) {
        this.usage_code = usage_code;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public int getProduct_detail_code() {
        return product_detail_code;
    }

    public void setProduct_detail_code(int product_detail_code) {
        this.product_detail_code = product_detail_code;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public void setRental_date(Date rental_date) {
        this.rental_date = rental_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public String getOrder_reason() {
        return order_reason;
    }

    public void setOrder_reason(String order_reason) {
        this.order_reason = order_reason;
    }

    public String getProduct_state() {
        return product_state;
    }

    public void setProduct_state(String product_state) {
        this.product_state = product_state;
    }

    @Override
    public String toString() {
        return "ApprovalsVO{" +
                "usage_code=" + usage_code +
                ", category_code='" + category_code + '\'' +
                ", product_detail_code=" + product_detail_code +
                ", loginID='" + loginID + '\'' +
                ", name='" + name + '\'' +
                ", product_no='" + product_no + '\'' +
                ", product_name='" + product_name + '\'' +
                ", order_date=" + order_date +
                ", rental_date=" + rental_date +
                ", return_date=" + return_date +
                ", order_reason='" + order_reason + '\'' +
                ", product_state='" + product_state + '\'' +
                '}';
    }
}//end class

