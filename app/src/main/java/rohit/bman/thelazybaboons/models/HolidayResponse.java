package rohit.bman.thelazybaboons.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HolidayResponse extends BaseResponse {

    @SerializedName("data")
    private HolidayData holidayData;

    @SerializedName("errors")
    private List<RequestError> requestErrors;


    public HolidayResponse(boolean isSuccessFul, String message, HolidayData holidayData) {
        super(isSuccessFul, message);
        this.holidayData = holidayData;
    }

    public HolidayData getHolidayData() {
        return holidayData;
    }

    public void setHolidayData(HolidayData holidayData) {
        this.holidayData = holidayData;
    }

    public List<RequestError> getRequestErrors() {
        return requestErrors;
    }

    public void setRequestErrors(List<RequestError> requestErrors) {
        this.requestErrors = requestErrors;
    }

    public class HolidayData {

        @SerializedName("holidays")
        List<Holiday> holidays;

        public HolidayData(List<Holiday> holidays) {
            this.holidays = holidays;
        }

        public List<Holiday> getHolidays() {
            return holidays;
        }

        public void setHolidays(List<Holiday> holidays) {
            this.holidays = holidays;
        }
    }

}
