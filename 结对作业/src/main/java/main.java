public class main {
    public static void main(String[] args) {

        Integer n = null;
        Integer r = null;
        String answerPath = null;
        CreateProblems createProblems = new CreateProblems();
        for(int i = 0; i < args.length; i++){
            String param = args[i];
            if( i%2 == 0 ){
                if ("-n".equals(param)) {
                    n = Integer.valueOf(args[i+1]);
                }else if("-r".equals(param)){
                    r = Integer.valueOf(args[i+1]);
                }else if("-a".equals(param)){
                    answerPath = args[i+1];
                }
            }
        }

        if(null != n && null != r){
            if( n <= 0 || r <= 0){
                try {
                    throw new Exception("输入数据有误，请输入正整数。");
                } catch (Exception e) {
                    System.out.println("输入数据有误，请输入正整数。");
                }
            }
            try{
                createProblems.createProblems(n,r);
            }catch (Exception e){
                System.out.println("生成题目失败，请重试。");
            }

        }else if(answerPath != null){
            try{
                createProblems.checkAnswer(answerPath);
            }catch (Exception e){
                System.out.println("文件不存在，请重试。");
            }

        }

    }
}
