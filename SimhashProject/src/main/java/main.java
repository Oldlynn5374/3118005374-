import java.io.*;
import java.util.Date;

public class main {
    public static void main(String[] args) throws Exception {
        Date now = new Date();
        SimhashTest simhashTest = new SimhashTest();


        InputStream in = new FileInputStream(new File(args[0]));
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        String content1 = new String(buffer, "UTF-8");

        in = new FileInputStream(new File(args[1]));
        buffer = new byte[in.available()];
        in.read(buffer);
        String content2 = new String(buffer, "UTF-8");

        String result = simhashTest.compare(content1,content2);
        System.out.println("相似度："+result);

       BufferedWriter bw = new BufferedWriter(new FileWriter(args[2]));
        bw.write("相似度："+result);
        bw.flush();
        bw.close();
        in.close();
        Date later = new Date();
        System.out.println("用时："+ (later.getTime() - now.getTime())+"ms");
    }
}
