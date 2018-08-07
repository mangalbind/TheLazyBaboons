package rohit.bman.thelazybaboons.models;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("success")
    private boolean isSuccessFul;

    @SerializedName("msg")
    private String message;

    public BaseResponse(boolean isSuccessFul, String message) {
        this.isSuccessFul = isSuccessFul;
        this.message = message;
    }

    public boolean isSuccessFul() {
        return isSuccessFul;
    }

    public void setSuccessFul(boolean successFul) {
        isSuccessFul = successFul;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
