package kr.happyjob.study.vo.returns;

public class ReturnsModel {

    private int product_detail_code;
    private String product_name;
    private String category_code;
    private String product_no;
    private String category_name;
    private String product_state;

    // 상세 모달창에 조회하기 위해 새로 추가함
    private String loginID;          // pd.loginID
    private String order_date;       // pd.order_date
    private String rental_date;      // pd.rental_date
    private String return_date;      // pd.return_date
    private String return_reason;     // pd.order_reason
    private String approve;          // pd.approve
    private String user_name;        // ui.name AS user_name
    private String apply_date;       // ui.regdate AS apply_date

    public int getProduct_detail_code() {
        return product_detail_code;
    }

    public void setProduct_detail_code(int product_detail_code) {
        this.product_detail_code = product_detail_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getProduct_state() {
        return product_state;
    }

    public void setProduct_state(String product_state) {
        this.product_state = product_state;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getRental_date() {
        return rental_date;
    }

    public void setRental_date(String rental_date) {
        this.rental_date = rental_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getreturn_reason() {
        return return_reason;
    }

    public void setreturn_reason(String order_reason) {
        this.return_reason = order_reason;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }
}
