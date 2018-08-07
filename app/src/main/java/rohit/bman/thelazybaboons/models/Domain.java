package rohit.bman.thelazybaboons.models;

public class Domain {

    private int id;
    private String domain;

    public Domain(int id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
