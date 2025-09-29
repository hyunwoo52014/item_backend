package kr.happyjob.study.vo.requests;

public class ApplyModel {

    private int product_detail_code;
    private String category_code;
    private String product_name;
    private String product_state;
    private String name;
    private String loginID;          // 신청자 로그인ID
    private String applicant_name;   // DB에서 가져온 신청자 이름

    private String rentalDate;   // 대여일
    private String returnDate;   // 반납일
    private String order_reason;
    private String category_name;

    public int getProduct_detail_code() { return product_detail_code; }
    public void setProduct_detail_code(int product_detail_code) { this.product_detail_code = product_detail_code; }

    public String getCategory_code() { return category_code; }
    public void setCategory_code(String category_code) { this.category_code = category_code; }

    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }

    public String getProduct_state() { return product_state; }
    public void setProduct_state(String product_state) { this.product_state = product_state; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRentalDate() { return rentalDate; }
    public void setRentalDate(String rentalDate) { this.rentalDate = rentalDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getOrder_reason() { return order_reason; }
    public void setOrder_reason(String order_reason) { this.order_reason = order_reason; }

    public String getCategory_name() { return category_name; }
    public void setCategory_name(String category_name) { this.category_name = category_name; }

    public String getLoginID() { return loginID; }
    public void setLoginID(String loginID) { this.loginID = loginID; }

    public String getApplicant_name() { return applicant_name; }
    public void setApplicant_name(String applicant_name) { this.applicant_name = applicant_name; }
}
