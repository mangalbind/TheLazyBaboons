package rohit.bman.thelazybaboons.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse extends BaseResponse {

    @SerializedName("data")
    private RegisterData registerData;

    @SerializedName("errors")
    private List<RequestError> requestErrors;

    public RegisterResponse(boolean isSuccessFul, String message, RegisterData registerData, List<RequestError> requestErrors) {
        super(isSuccessFul, message);
        this.registerData = registerData;
        this.requestErrors = requestErrors;
    }

    public RegisterData getRegisterData() {
        return registerData;
    }

    public void setRegisterData(RegisterData registerData) {
        this.registerData = registerData;
    }

    public List<RequestError> getRequestErrors() {
        return requestErrors;
    }

    public void setRequestErrors(List<RequestError> requestErrors) {
        this.requestErrors = requestErrors;
    }

    public class RegisterData {

        private String token;
        private String permission;

        public RegisterData(String token, String permission) {
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
