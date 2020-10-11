package symbol;

public class AddSyml extends Symbol {

    public AddSyml() {
        this.setPriotiry(1);
        this.setNo((float) 1);
        this.setName("+");
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
