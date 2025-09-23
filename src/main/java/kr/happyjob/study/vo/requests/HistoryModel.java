package kr.happyjob.study.vo.requests;

public class HistoryModel {
    // tb_userinfo로 부터
    String loginID;
    String team;
    String name;

    // tb_product_info로 부터
    String productNo;
    String productName;

    // tb_product_detail로 부터
    String order_reason;
    String returnDate;
    String rentalDate;
    String product_detail_code;
    String order_date;
    String status;

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrder_reason() {
        return order_reason;
    }

    public void setOrder_reason(String order_reason) {
        this.order_reason = order_reason;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getProduct_detail_code() {
        return product_detail_code;
    }

    public void setProduct_detail_code(String product_detail_code) {
        this.product_detail_code = product_detail_code;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
