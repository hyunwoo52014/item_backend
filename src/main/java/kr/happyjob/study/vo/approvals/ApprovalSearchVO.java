package kr.happyjob.study.vo.approvals;

public class ApprovalSearchVO {
    private String searchStr;
    private String searchWordStr;

    private String category_code;
    private int product_detail_code;

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public String getSearchWordStr() {
        return searchWordStr;
    }

    public void setSearchWordStr(String searchWordStr) {
        this.searchWordStr = searchWordStr;
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

    //
}//class
