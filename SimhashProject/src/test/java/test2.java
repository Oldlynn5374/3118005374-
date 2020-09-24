public class test2 {
    public void modify(String str){
        str = "hhhhhh";
    }

    public static void main(String[] args) {
        String str = "ninininini";
        test2 test2 = new test2();
        test2.modify(str);
        System.out.println(str);
    }
}
