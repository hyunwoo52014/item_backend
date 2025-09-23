package kr.happyjob.study.vo.approvals;

import java.sql.Date;


public class ApprovalsVO {

    private String name; // 사용자 이름
    private String product_name; // 제품 이름

    private Date order_date; // 사용 신청일,반납 신청일
    private Date rental_date; // 대여일
    private Date return_date; //반납일

    private String product_state; // 제품 상태


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProduct_state() {
        return product_state;
    }

    public void setProduct_state(String product_state) {
        this.product_state = product_state;
    }

    @Override
    public String toString() {
        return "ApprovalsVO{" +
                "name='" + name + '\'' +
                ", product_name='" + product_name + '\'' +
                ", order_date=" + order_date +
                ", rental_date=" + rental_date +
                ", return_date=" + return_date +
                ", product_state='" + product_state + '\'' +
                '}';
    }
}//end class
