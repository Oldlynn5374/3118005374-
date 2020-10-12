import com.sun.org.apache.xpath.internal.operations.Div;
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

    //把上面的2个List复制一份，用作保存问题的原始数据
    public List<Number> numList2;
    public List<Symbol> opList2;


    //存储生成问题的运算最后一步字符串
    String resultStorage = null;
    //存储每条问题
    Map<String,Boolean> storage = new HashMap<String, Boolean>();

    //运算结果集
    List<String> resultList = new ArrayList<String>();

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
     * 生成问题集
     * @return
     * @params n为题目数，r为题目数值范围
     */
    public void createProblems(int n , int r) throws Exception {
        //存储生成问题的数字集
        List<Float> numStorage;
        //存储生成问题的符号集序号
        List<Float> opStorage;
        //存储生成问题的符号
        List<Symbol> symlStorage;
        //问题信息
        StringBuilder sb;

        //问题集字符串
        StringBuilder exercisesSb = new StringBuilder();
        //答案字符串
        StringBuilder correctAnswerSb = new StringBuilder();

        for(int i = 0;i < n; ){
            //生成问题并判断合法性
            String problem = createProblem(r);
            String result = solveProblem();
            //判断生成的问题是否合法，不合法就直接跳到下一循环。
            if(null == problem){
                continue;
            }
            if(null == result){
                continue;
            }
            //单个问题生成后：


            sb = new StringBuilder();
            //存储生成问题的数字集
            numStorage = new ArrayList<Float>();
            //存储生成问题的符号集序号
            opStorage = new ArrayList<Float>();
            //存储生成问题的符号
            symlStorage = new ArrayList<Symbol>();

            //获取Number的值
            for(Number number : numList2){
                numStorage.add(number.getValue());
            }
            //获取符号的序号，以便为符号集排序
            for(Symbol symbol : opList2){
                opStorage.add(symbol.getNo());
            }
            quickSort(numStorage,0,numStorage.size() - 1);
            quickSort(opStorage,0,opStorage.size() - 1);

            for(int j = 0; j < opStorage.size(); j++){
                Float no = opStorage.get(j);
                if (no == 1.0) {
                    symlStorage.add(new AddSyml());
                }else if(no == 2.0){
                    symlStorage.add(new SubSyml());
                }else if(no == 3.0){
                    symlStorage.add(new MulSyml());
                }else if(no == 4.0){
                    symlStorage.add(new DivSyml());
                }
            }

            //生成问题的信息字符串，并在storage查找是否存在，以实现排除重复题目。
            for(Float num : numStorage){
                sb.append(num+" ");
            }

            for(Symbol symbol : symlStorage){
                sb.append(symbol.getName()+" ");
            }

            sb.append(resultStorage);
            String problemInfo = sb.toString();

            //在storage中查找是否存在该问题字符串
            if(storage.get(problemInfo) != null){
                System.out.println("出现了重复！！！");
                System.out.println("-----------------------  ");
                continue;
            }

           /* SaveFiles.save((i+1)+"、 "+problem,"D:\\JAVA\\Exercises.txt");
            SaveFiles.save((i+1)+"、 "+result,"D:\\JAVA\\Answers.txt");*/
            exercisesSb.append((i+1)+"、 "+problem).append("\n");
            correctAnswerSb.append((i+1)+"、 "+result).append("\n");

            //保存问题的运算结果，用于对比答案
            resultList.add(result);

            System.out.println("题目： " + problem);
            System.out.println("结果： " + result);
            System.out.println("问题信息 ：" + problemInfo);
            i++;
            storage.put(problemInfo,true);
            System.out.println("-----------------------  " + i);
        }

        SaveFiles.save(exercisesSb.toString(),"Exercises.txt");
        SaveFiles.save(correctAnswerSb.toString(),"Answer.txt");
        //checkAnswer("Answer.txt");


    }

    /**
     *
     * 检查答案
     * @return
     * @params
     */
    public void checkAnswer(String answerPath){
        List<String> answerList ;
        List<String> resultList ;
        answerList = SaveFiles.read(answerPath);
        resultList = SaveFiles.read("Answer.txt");
        List<String> correctList = new ArrayList<String>();
        List<String> wrongList = new ArrayList<String>();
        int correct = 0;
        int wrong = 0;
        for(int i = 0; i < answerList.size() ; i++ ){
            String answer = answerList.get(i);
            String result = resultList.get(i);
            if(answer.equals(result)){
                correctList.add((i+1)+"");
                correct++;
            }else {
                wrongList.add((i+1)+"");
                wrong++;
            }
        }

        StringBuilder correctStr = new StringBuilder();
        correctStr.append("Correct: ").append(correct).append(" (");
        for(int i = 0;i <correctList.size();i++){
            if( i != correctList.size() - 1){
                correctStr.append(correctList.get(i)).append(", ");
            }else {
                correctStr.append(correctList.get(i));
            }
        }
        correctStr.append(")");

        StringBuilder wrongStr = new StringBuilder();
        wrongStr.append("Wrong: ").append(wrong).append(" (");
        for(int i = 0;i <wrongList.size();i++){
            if( i != wrongList.size() - 1){
                wrongStr.append(wrongList.get(i)).append(", ");
            }else {
                wrongStr.append(wrongList.get(i));
            }
        }
        wrongStr.append(")");

        StringBuilder gradeBuilder = new StringBuilder();
        gradeBuilder.append(correctStr.toString());
        gradeBuilder.append("\n");
        gradeBuilder.append(wrongStr.toString());

        SaveFiles.save(gradeBuilder.toString(),"Grade.txt");

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
        numList2 = new ArrayList<Number>();
        opList2 = new ArrayList<Symbol>();



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
            numList2.add(number);

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
            opList2.add(symbol);
        }

       /* 测试查重用代码
         if(i == 1){
            num = 4;
            opNum = 3;
            bracNum = 2;
            //初始化List
            numList = new ArrayList<Number>();
            opList = new ArrayList<Symbol>();
            bracList = new ArrayList<Integer>();
            numList2 = new ArrayList<Number>();
            opList2 = new ArrayList<Symbol>();
            Number number = new Number();
            number.setNumerator(3);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);
            number = new Number();
            number.setNumerator(4);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);

            number = new Number();
            number.setNumerator(5);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);

            number = new Number();
            number.setNumerator(2);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);

            Symbol symbol = new AddSyml();
            opList.add(symbol);
            opList2.add(symbol);
            symbol = new MulSyml();
            opList.add(symbol);
            opList2.add(symbol);
            symbol = new DivSyml();
            opList.add(symbol);
            opList2.add(symbol);

            bracList.add(0);
            bracList.add(1);



        }

        if(i == 0){
            num = 4;
            opNum = 3;
            bracNum = 2;
            //初始化List
            numList = new ArrayList<Number>();
            opList = new ArrayList<Symbol>();
            bracList = new ArrayList<Integer>();
            numList2 = new ArrayList<Number>();
            opList2 = new ArrayList<Symbol>();
            Number number = new Number();
            number.setNumerator(3);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);
            number = new Number();
            number.setNumerator(5);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);
            number = new Number();
            number.setNumerator(4);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);
            number = new Number();
            number.setNumerator(2);
            number.setDenominator(1);
            numList.add(number);
            numList2.add(number);

            Symbol symbol = new AddSyml();
            opList.add(symbol);
            opList2.add(symbol);
            symbol = new MulSyml();
            opList.add(symbol);
            opList2.add(symbol);
            symbol = new DivSyml();
            opList.add(symbol);
            opList2.add(symbol);

            bracList.add(0);
            bracList.add(1);



        }

        i--;*/

        //生成问题字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            //为方便拼接字符串，把所有的数字都替换为r
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

        //从字符串的“r”分裂字符串成多个子串，再与数字一起拼接成完整的字符串
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
        builder.append("= ");

        //格式化字符串
        String result = builder.toString();
        result = result.replace("("," ( ");
        result = result.replace(")"," ) ").trim();


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
            Integer maxPriority = null;
            try{
                maxPriority = opList.get(opFrom).getPriotiry();
            }catch (Exception e){
                return null;
            }
            //优先级最大的符号的位置，默认括号内第一个符号
            Integer maxPos = opFrom;
            //目前运算符的优先级
            Integer priority = null;
            //目前运算符的位置
            Integer pos = null;
            //寻找当前运算中，优先级最高的符号以及其位置。
            for (int i = opFrom; i <= opTo; i++) {
                try{
                    priority = opList.get(i).getPriotiry();
                }catch (Exception e){
                    return null;
                }

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

            if(symbol.equals("÷") && numerator <= 0 || denominator == 0){
                //除数不能为0
                return null;
            }

            if(numerator < 0){
                //计算过程出现负数，不符合要求
                return null;
            }

            if(opList.size() == 1){
                //获取计算的最后一步，以方便验证是否题目重复
                if(num1.getValue() > num2.getValue() && (symbol.equals("+")||symbol.equals("×"))){
                    resultStorage = num2.toString() + symbol + num1.toString();
                }else {
                    resultStorage = num1.toString() + symbol + num2.toString();
                }

            }

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

    /**
     *
     * 快速排序
     * @return
     * @params
     */
    public void quickSort(List<Float> arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }

        int left = leftIndex;
        int right = rightIndex;
        //待排序的第一个元素作为基准值
        Float key = arr.get(left);

        //从左右两边交替扫描，直到left = right
        while (left < right) {
            while (right > left && arr.get(right) >= key) {
                //从右往左扫描，找到第一个比基准值小的元素
                right--;
            }

            //找到这种元素将arr[right]放入arr[left]中
            arr.set(left,arr.get(right));

            while (left < right && arr.get(left) <= key) {
                //从左往右扫描，找到第一个比基准值大的元素
                left++;
            }

            //找到这种元素将arr[left]放入arr[right]中
            arr.set(right,arr.get(left));
        }
        //基准值归位
        arr.set(left,key);
        //对基准值左边的元素进行递归排序
        quickSort(arr, leftIndex, left - 1);
        //对基准值右边的元素进行递归排序。
        quickSort(arr, right + 1, rightIndex);
    }
}
