package symbol;

public class DivSyml extends Symbol {

    public DivSyml() {
        this.setName("÷");
        this.setPriotiry(2);
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
