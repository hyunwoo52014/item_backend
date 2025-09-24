package kr.happyjob.study.vo.returns;

public class ReturnsModel {

    private int product_detail_code;
    private String product_name;
    private String category_code;
    private String product_no;
    private String category_name;
    private String product_state;


    public String getProduct_state() {
        return product_state;
    }
    public void setProduct_state(String product_state) {
        this.product_state = product_state;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String getProduct_no() {
        return product_no;
    }
    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }
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

}
