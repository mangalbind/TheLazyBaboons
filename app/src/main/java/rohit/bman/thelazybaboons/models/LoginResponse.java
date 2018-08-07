package rohit.bman.thelazybaboons.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse extends BaseResponse {

    @SerializedName("data")
    private LoginData loginData;

    @SerializedName("errors")
    private List<RequestError> requestErrors;


    public LoginResponse(boolean isSuccessFul, String message, LoginData loginData, List<RequestError> requestErrors) {
        super(isSuccessFul, message);
        this.loginData = loginData;
        this.requestErrors = requestErrors;
    }

    public List<RequestError> getRequestErrors() {
        return requestErrors;
    }


    public void setRequestErrors(List<RequestError> requestErrors) {
        this.requestErrors = requestErrors;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public class LoginData {

        private String token;
        private String permission;

        public LoginData(String token, String permission) {
            this.token = token;
            this.permission = permission;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }
    }

}
