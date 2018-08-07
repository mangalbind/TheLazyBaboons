package rohit.bman.thelazybaboons.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DomainResponse extends BaseResponse {


    @SerializedName("data")
    private DomainList domainList;

    public DomainResponse(boolean isSuccessFul, String message, DomainList domainList) {
        super(isSuccessFul, message);
        this.domainList = domainList;
    }

    public DomainList getDomainList() {
        return domainList;
    }

    public void setDomainList(DomainList domainList) {
        this.domainList = domainList;
    }

    public class DomainList {

        @SerializedName("domains")
        List<Domain> domains;

        public DomainList(List<Domain> domains) {
            this.domains = domains;
        }

        public List<Domain> getDomains() {
            return domains;
        }

        public void setDomains(List<Domain> domains) {
            this.domains = domains;
        }
    }


}
