package number;

public class Number {
    //分子
    private Integer numerator;
    //分母
    private Integer denominator;

    @Override
    public String toString() {
        String s = null;

        if (denominator == 1) {
            s = numerator.toString();
        } else if (numerator < denominator) {
            s = numerator + "/" + denominator;
        } else if (numerator > denominator) {
            Integer i = numerator / denominator;
            Integer m = numerator % denominator;
            if(m != 0){
                s = i + "'" + m + "/" + denominator;
            }else {
                s = i.toString();
            }

        }else if(numerator == denominator){
            s = "1";
        }

        return s;
    }

    public Integer getNumerator() {
        return numerator;
    }

    public void setNumerator(Integer numerator) {
        this.numerator = numerator;
    }

    public Integer getDenominator() {
        return denominator;
    }

    public void setDenominator(Integer denominator) {
        this.denominator = denominator;
    }
}
