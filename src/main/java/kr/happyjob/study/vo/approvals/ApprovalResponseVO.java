package kr.happyjob.study.vo.approvals;

public class ApprovalResponseVO<T> {
    private boolean success; //성공여부
    private String message; // 메시지(에러 or 안내)
    private T data; // 실제 응답 데이터
    
    
    /* 성공 응답 */
    /**
     *
     * @param data List<ApprovalsVO>
     * @return
     * @param <T>
     */
    public static <T> ApprovalResponseVO<T> success(T data){
        ApprovalResponseVO<T> res=new ApprovalResponseVO<>();
        res.success=true;
        res.data=data;
        return res;
    }
    
    /* 에러 응답 */
    /**
     *
     * @param data ApprovalSearchVO
     * @return
     * @param <T>
     */
    public static <T> ApprovalResponseVO<T> error(String message){
        ApprovalResponseVO<T> res=new ApprovalResponseVO<>();
        res.success=false;
        res.message=message;
        return res;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}//class
