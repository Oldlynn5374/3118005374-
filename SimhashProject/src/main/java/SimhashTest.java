import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimhashTest {

    //词出现最大次数
    private int maxCount = 5;

    //哈希值位数
    private int hashBits = 64;

    //词的出现次数集合
    private Map<String, Integer> wordCount;

    //不参与加权运算的词性
    private List<String> blackNature;

    //参与加权运算词性的权值
    private Map<String, Integer> natureWeight;

    //自定义不同词性的权值，以及是否参与加权运算
    private void setWeight() {
        //w为标点，表示标点不参与运算
        blackNature.add("w");
        //n为名词，表示名词的权值为3
        natureWeight.put("n", 3);
    }

    //构造方法，初始化
    public SimhashTest() {
        wordCount = new HashMap<String, Integer>();
        blackNature = new ArrayList<String>();
        natureWeight = new HashMap<String, Integer>();
    }

    //内容预处理，过滤无关内容
    private String cleanWords(String content) {
        //过滤HTML标签
        content = Jsoup.clean(content, Whitelist.none());

        content = StringUtils.lowerCase(content);
        //过滤符
        String[] tokens = {" ", "\n", "\\r", "\\n", "\\t", "&nbsp;", "\r", "\t"};
        for (String token : tokens) {
            content = content.replace(token, "");
        }
        return content;
    }

    //计算哈希值
    private BigInteger hash(String source) {

        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {

            // 当sourece 的长度过短，会导致hash算法失效，因此需要对过短的词补偿
            while (source.length()<3) {
                source = source+source.charAt(0);
            }
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashBits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    //计算simhash值
    private BigInteger simHash(String content) {
        content = cleanWords(content);
        setWeight();

        //content内各关键字权值数组，用于合并运算
        int[] array = new int[hashBits];
        //把内容进行分词操作
        List<Term> terms = StandardTokenizer.segment(content);
        for (Term term : terms) {
            String word = term.word;
            String nature = term.nature.toString();
            //过滤不参与运算的词性
            if (this.blackNature.contains(nature)) {
                continue;
            }

            //过滤频率过高的词
            if (!wordCount.containsKey(word)) {
                //第一次出现，次数为1
                wordCount.put(word, 1);
            } else {
                if (wordCount.get(word) > maxCount) {
                    //出现次数过多，跳过
                    continue;
                } else if ((wordCount.get(word) <= maxCount)) {
                    //每出现一次，次数+1
                    wordCount.put(word, wordCount.get(word) + 1);
                }
            }


            //计算关键词的哈希值
            BigInteger hash = hash(word);
            //关键词的权值
            Integer weight = natureWeight.get(term.nature.toString());
            if (null == weight) {
                weight = 1;
            }


            //加权、合并操作
            for (int i = 0; i < hashBits; i++) {
                BigInteger bit = new BigInteger("1").shiftLeft(i);
                if (hash.and(bit).signum() == 1) {
                    array[i] += weight;
                } else {
                    array[i] -= weight;
                }
            }
        }

        //降维操作
        BigInteger simHashCode = new BigInteger("0");
        for(int i =0;i<hashBits;i++){
            if(array[i]>=0){
                simHashCode = simHashCode.add(new BigInteger("1").shiftLeft(i));
            }
        }

        return simHashCode;
    }

    //文本对比
    public String compare(String content1, String content2) {

        BigDecimal simHash1 = new BigDecimal(simHash(content1));
        BigDecimal simHash2 = new BigDecimal(simHash(content2));

        BigDecimal result;
        //较小的simhash作为被除数，较大的作为除数，商即为相似度，越接近1越相似。
        if (simHash1.compareTo(simHash2) > -1) {
            result = simHash2.divide(simHash1, 4, RoundingMode.HALF_UP);
        } else {
            result = simHash1.divide(simHash2, 4, RoundingMode.HALF_UP);
        }
        return result.toString();
    }


}
