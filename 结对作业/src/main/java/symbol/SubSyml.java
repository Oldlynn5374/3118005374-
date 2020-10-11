package symbol;

public class SubSyml extends Symbol {

    public SubSyml() {
        this.setName("−");
        this.setNo((float) 2);
        this.setPriotiry(1);
    }

    public Float getNo() {
        return no;
    }

    public void setNo(Float no) {
        this.no = no;
    }

    public int getPriotiry() {
        return priotiry;
    }

    public void setPriotiry(int priotiry) {
        this.priotiry = priotiry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
