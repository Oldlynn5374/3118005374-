import number.Number;
import symbol.*;

import java.util.*;

public class CreateProblems {
    //运算数链表，序号为位置
    public List<Number> numList;
    //运算符链表，序号为位置
    public List<Symbol> opList;
    //括号链表，value为出现的位置。其中前半部分是“（”，后半部分是“）”。
    public List<Integer> bracList;

    /**
     * @return 随机数
     * @params 随机数范围
     */
    public Integer randomNum(int range) {
        Random random = new Random();
        Integer num = random.nextInt(range);
        return num;
    }

    /**
     * 生成问题
     *
     * @return 问题的字符串
     * @params 运算数链表，运算符链表，括号链表
     */
    public String createProblem(Integer range) throws Exception {

        //初始化List
        numList = new ArrayList<Number>();
        opList = new ArrayList<Symbol>();
        bracList = new ArrayList<Integer>();


        //运算式的数字个数，最多4个,最少2个。
        Integer num = randomNum(3) + 2;
        //运算符的个数
        Integer opNum = num - 1;
        //括号内包含运算符的个数与位置.
        Integer bracNum = null;

        try {
            //如果括号个数为0，则会抛出异常
            bracNum = randomNum(opNum);
        } catch (Exception e) {
            //无括号时：

        }

        //括号的位置
        Integer bracPos = null;
        //存在括号时：
        if (bracNum == 1) {
            bracPos = randomNum(opNum);
            bracList.add(bracPos);
        } else if (bracNum == 2) {
            bracPos = randomNum(2);
            bracList.add(bracPos);
            bracList.add(bracPos + 1);
        }


        //随机生成参与运算的数
        for (int i = 0; i < num; i++) {

            Integer numerator = randomNum(range - 1) + 1;
            Integer denominator = randomNum(range - 1) + 1;
            Number number = new Number();
            number.setNumerator(numerator);
            number.setDenominator(denominator);
            numList.add(number);

        }
        //随机生成运算符
        for (int i = 0; i < opNum; i++) {
            Integer j = randomNum(4);
            Symbol symbol = null;
            switch (j) {
                case 0:
                    symbol = new AddSyml();
                    break;
                case 1:
                    symbol = new SubSyml();
                    break;
                case 2:
                    symbol = new MulSyml();
                    break;
                case 3:
                    symbol = new DivSyml();
                    break;
            }
            opList.add(symbol);
        }


  /*      Map<Integer,Boolean> bracMap = new HashMap<Integer, Boolean>();
        for(Integer pos : bracList){
            bracMap.put(pos,true);
        }
        //符号
        Symbol symbol = null;
        for(int i =0; i < opNum; i++){
            Boolean hasBrac = bracMap.get(i);
            symbol = opList.get(i);
            if(null == hasBrac){

            }
        }*/
        //生成问题字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            if(i == num - 1){
                sb.append(" " + "r ");
            }else {
                sb.append(" " + "r");
            }
            try {
                sb.append(" " + opList.get(i).getName());
            } catch (Exception e) {

            }
        }

        //插入括号
        try{
            Integer bracFrom = bracList.get(0);
            Integer bracTo = null;
            if (bracNum == 2) {
                bracTo = bracList.get(1);
            }
            if (bracNum == 1) {
                sb.setCharAt((bracFrom * 4) + 3 - 3, '(');
                sb.setCharAt((bracFrom * 4) + 3 + 3, ')');
            } else if (bracNum == 2) {
                sb.setCharAt((bracFrom * 4) + 3 - 3, '(');
                sb.setCharAt((bracTo * 4) + 3 + 3, ')');
            }
        }catch (Exception e){

        }

        String str = sb.toString();
        String[] rs = str.split("r");
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i < rs.length;i++){
            if( i == rs.length - 1){
                builder.append(rs[i]);
            }else {
                builder.append(rs[i]).append(numList.get(i));
            }

        }

        String result = builder.toString();
        result = result.replace("("," ( ");
        result = result.replace(")"," ) ").trim();








 /*       for (int i = 0; i < num; i++) {
            sb.append(" "+numList.get(i).toString());
            try {
                sb.append(" "+opList.get(i).getName());
            } catch (Exception e) {

            }
        */

    /*    try{
            if(bracNum == 1){
                Integer pos = bracList.get(0);
                sb.insert(pos*2,"(");
                sb.insert(pos*2+4,")");
            }else {
                Integer pos = bracList.get(0);
                sb.insert(pos*2,"(");
                pos = bracList.get(1);
                sb.insert(pos*2+4,")");
            }
        }catch (Exception e){

        }*/

        System.out.print("Num: ");
        for (Number number : numList) {
            System.out.print(number + " ");
        }
        System.out.println();
        System.out.print("OP: ");
        for (Symbol symbol : opList) {
            System.out.print(symbol.getName() + " ");
        }
        System.out.println();
        System.out.print("pos: ");
        for (Integer pos : bracList) {
            System.out.print(pos + " ");
        }
        System.out.println();

        System.out.println(solveProblem());

        return result;
    }

    /**
     * 计算问题
     *
     * @return 计算结果
     */
    public String solveProblem() throws Exception {

        //结果
        Number sum = null;
        while (opList.size() != 0) {
            sum = new Number();

            //括号起始位置
            Integer from = null;
            //括号结束位置
            Integer to = null;
            try {
                from = bracList.get(0);
                to = bracList.get(bracList.size() - 1);
            } catch (Exception e) {

            }
            //第一个运算数
            Number num1 = null;
            //第二个运算数
            Number num2 = null;

            //符号起始位置
            Integer opFrom;
            //符号结束位置
            Integer opTo;
            if (from != null) {
                //如果存在括号，那么符号要在括号内寻找优先级最高的
                opFrom = from;
                opTo = to;
            } else {
                //如果不存在括号，从头开始找优先级最高的符号
                opFrom = 0;
                opTo = opList.size() - 1;
            }

            //最大的优先级，默认括号内第一个符号
            Integer maxPriority = opList.get(opFrom).getPriotiry();
            //优先级最大的符号的位置，默认括号内第一个符号
            Integer maxPos = opFrom;
            //目前运算符的优先级
            Integer priority = null;
            //目前运算符的位置
            Integer pos = null;
            //寻找当前运算中，优先级最高的符号以及其位置。
            for (int i = opFrom; i <= opTo; i++) {
                priority = opList.get(i).getPriotiry();
                pos = i;
                if (priority > maxPriority) {
                    maxPriority = priority;
                    maxPos = pos;
                }
            }

            //分子
            Integer numerator = null;
            //分母
            Integer denominator = null;
            //把运算符左右两边的数取出来
            num1 = numList.get(maxPos);
            num2 = numList.get(maxPos + 1);
            String symbol = opList.get(maxPos + 0).getName();
            //计算过程
            if (symbol.equals("+")) {
                denominator = num1.getDenominator() * num2.getDenominator();
                Integer num1Numerator = num1.getNumerator() * num2.getDenominator();
                Integer num2Numerator = num2.getNumerator() * num1.getDenominator();
                numerator = num1Numerator + num2Numerator;
            } else if (symbol.equals("−")) {
                denominator = num1.getDenominator() * num2.getDenominator();
                Integer num1Numerator = num1.getNumerator() * num2.getDenominator();
                Integer num2Numerator = num2.getNumerator() * num1.getDenominator();
                numerator = num1Numerator - num2Numerator;
            } else if (symbol.equals("×")) {
                numerator = num1.getNumerator() * num2.getNumerator();
                denominator = num1.getDenominator() * num2.getDenominator();
            } else if (symbol.equals("÷")) {
                numerator = num1.getNumerator() * num2.getDenominator();
                denominator = num1.getDenominator() * num2.getNumerator();
            }
            sum.setNumerator(numerator);
            sum.setDenominator(denominator);

            //一次运算之后，修改各List数据
            numList.set(maxPos, sum);
            try {
                numList.remove(maxPos + 1);
                opList.remove(maxPos + 0);
                bracList.remove(maxPos);
            } catch (Exception e) {

            }


        }

        return sum.toString();
    }
}
