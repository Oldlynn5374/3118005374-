import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveFiles {
    public static void save(String content, String filePath) {

        String file = filePath;
/*        if(tag == 1) {
            file = "D:\\JAVA\\Exercises.txt";
        }else if(tag == 2){
            file = "D:\\JAVA\\Answers.txt";
        }else {
            file = "D:\\JAVA\\Grade.txt";
        }*/

        FileWriter fw = null;
        try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File(file);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> read(String filePath){
        BufferedReader br = null;
        String answer = null;
        List<String> answerList = new ArrayList<String>();
        try {
          /*  in = new FileInputStream(new File(filePath));
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            answer = new String(buffer);*/
          br = new BufferedReader(new FileReader(new File(filePath)));
          while((answer = br.readLine())!= null){
              String[] split = answer.split("、");
              answerList.add(split[1].trim());
          }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return answerList;

    }
}
