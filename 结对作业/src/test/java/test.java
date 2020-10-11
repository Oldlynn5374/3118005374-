import number.Number;
import org.junit.Test;
import symbol.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    CreateProblems createProblems = new CreateProblems();

    public static void setArrayList(List list) {
        list.set(1, 1111);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(11);
        list.add(22);
        list.add(33);
        test.setArrayList(list);
        for (Integer i : list) {
            System.out.println(i);
        }
    }

    @Test
    public void testSyml() {
        Symbol syml = new DivSyml();
        System.out.println(syml.getName() + " " + syml.getPriotiry());
    }

    @Test
    public void testRandomNum() {
        while (true) {
            Integer num = createProblems.randomNum(3) + 2;
            System.out.println(num);
            if (num == 4) {
                return;
            }
        }
    }

    @Test
    public void testRandomList() {
        //运算式的数字个数，最多4个,最少2个。
        Integer num = createProblems.randomNum(3) + 2;
        createProblems.bracList = new ArrayList<Integer>();
        createProblems.numList = new ArrayList<Number>();
        createProblems.opList = new ArrayList<Symbol>();
        List<Integer> bracList = createProblems.bracList;
        List<Number> numList = createProblems.numList;
        List<Symbol> opList = createProblems.opList;
        //运算符的个数
        Integer opNum = num - 1;
        Integer bracNum = null;
        try {
            //括号内包含运算符的个数与位置
            bracNum = createProblems.randomNum(opNum);
        } catch (Exception e) {

        }

        System.out.println("num: " + num);
        System.out.println("opNum: " + opNum);
        System.out.println("bracNum: " + bracNum);

        //括号的位置
        Integer bracPos = null;
        //存在括号时：
        if (bracNum == 1) {
            bracPos = createProblems.randomNum(opNum);
            createProblems.bracList.add(bracPos);
        } else if (bracNum == 2) {
            bracPos = createProblems.randomNum(2);
            createProblems.bracList.add(bracPos);
            createProblems.bracList.add(bracPos + 1);
        }

        //随机生成参与运算的数
        for(int i = 0; i < num; i++){

            Integer numerator = createProblems.randomNum(10 - 1) + 1;
            Integer denominator = createProblems.randomNum(10 - 1) + 1;
            Number number = new Number();
            number.setNumerator(numerator);
            number.setDenominator(denominator);
            numList.add(number);

        }
        //随机生成运算符
        for (int i = 0; i < opNum; i++) {
            Integer j = createProblems.randomNum(4);
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
            createProblems.opList.add(symbol);
        }

        System.out.print("Num: ");
        for (Number number : createProblems.numList) {
            System.out.print(number + " ");
        }
        System.out.println();
        System.out.print("OP: ");
        for (Symbol symbol : createProblems.opList) {
            System.out.print(symbol.getName() + " ");
        }
        System.out.println();
        System.out.print("pos: ");
        for (Integer pos : createProblems.bracList) {
            System.out.print(pos + " ");
        }


/*        Map<Integer, String> bracMap = new HashMap<Integer, String>();
        for(Integer pos : bracList){
            if(pos < bracList.size()/2){
                bracMap.put(pos,"(");
            }else {
                bracMap.put(pos,")");
            }
        }*/

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(createProblems.numList.get(i).toString());
            try {
                sb.append(createProblems.opList.get(i).getName());
            } catch (Exception e) {

            }
        }

        int bracListSize = bracNum;
        try{
            if(bracListSize == 1){
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

        }


        System.out.println();
        System.out.println(sb);
    }

    @Test
    public void testCreateProblem() throws Exception {
        for(int i = 0;i < 100; ){
            String problem = createProblems.createProblem(10);
            String result = createProblems.solveProblem();
            if(null == problem){
                continue;
            }
            if(null == result){
                continue;
            }
            i++;
            System.out.println(problem);
            System.out.println(result);

            System.out.println(i+"  -----------------");
        }

    }

    @Test
    public void testCreateProblems() throws Exception {
        createProblems.createProblems(10,10);
    }

    @Test
    public void testRead(){
        List<String> read = SaveFiles.read("D:\\JAVA\\Answers.txt");
        System.out.println(read);
    }

    @Test
    public void testGcd(){
        System.out.println(new Number().gcd(6,18));
    }

    @Test
    public void testMap(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("a","a");
        map.put("a","b");
        System.out.println(map.get("a"));
    }

    @Test
    public void testArrayEqual(){
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(222);
        list2.add(1);
        list2.add(2);
        list2.add(222);

        System.out.println(list1.equals(list2));
    }

    @Test
    public void testSort(){
        List<Float> list = new ArrayList<Float>();
        list.add((float) 2);
        list.add((float) 5);
        list.add((float) 1);
        list.add((float) 10);
        System.out.println(list);
        createProblems.quickSort(list,0,list.size() - 1);
        System.out.println(list);

    }


    @Test
    public void testString(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        list.add("+");
        list.add("2");
        for(String s : list){
            System.out.print(s);
        }

    }

    @Test
    public void testIntString(){
        Integer i = 11;
        String s = i.toString();
        System.out.println(s);
    }

    @Test
    public void testNumber(){
        Number number = new Number();
        number.setNumerator(3);
        number.setDenominator(1);
        System.out.println(number.getValue() == 3.0);
    }

    @Test
    public void testList(){
        List<Integer> list = new ArrayList<Integer>();
        list.set(0,1);
        System.out.println(list);


    }

    @Test
    public void testSpilt(){
        String str = "(r + r)+ r × r ";
        String[] rs = str.split("r");
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < rs.length;i++){
            if( i == rs.length - 1){
                sb.append(rs[i]);
            }else {
                sb.append(rs[i]).append(i);
            }

        }
        System.out.println(sb);
    }

    @Test
    public void testTrim(){
        String str = "abcd";
        str = str.replace(""," ").trim();
        System.out.println(str);
        str = "a b c d";
        str = str.replace(" ","");
        System.out.println(str);
    }
}
