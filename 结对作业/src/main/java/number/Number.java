package number;

public class Number {
    //分子
    private Integer numerator;
    //分母
    private Integer denominator;


    public Float getValue(){
        Float value = null;
        value = Float.valueOf(numerator) / Float.valueOf(denominator);
        return value;
    }

    @Override
    public String toString() {
        String s = null;

        Integer gcdNum = gcd(numerator,denominator);
        numerator = numerator / gcdNum;
        denominator = denominator / gcdNum;

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

    /**
     *
     * 求最大公因数
     * @return 最大公因数
     * @params 两个数
     */
    public Integer gcd(Integer m , Integer n){
        if( m%n == 0 ){
            return n;
        }else {
            return gcd(n,m%n);
        }
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
