package symbol;

public class MulSyml extends Symbol {

    public MulSyml() {
        this.setName("×");
        this.setNo((float)3);
        this.setPriotiry(2);
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
