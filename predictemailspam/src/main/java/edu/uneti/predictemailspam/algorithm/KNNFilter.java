package edu.uneti.predictemailspam.algorithm;

import edu.uneti.predictemailspam.untils.ConverterString;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNNFilter {
    private List<String> emails;// tạo 1 list chứa các dữ liệu của file tranningData
    private List<String> labels;// tạo 1 list chứa các nhãn (N/S) cuối mỗi dòng của file data
    private Map<String, Double> idfMap;// tạo map chứa (mỗi 1 từ trong file tranning dâta sẽ gồm 1 key-value)

    public KNNFilter(String filePath) {
        emails = new ArrayList<>();
        labels = new ArrayList<>();
        idfMap = new HashMap<>();
        addWordFromFileData(filePath);
    }

    /*
    * Đọc dữ liệu trong file tranning data
    * Tách các từ trong các file chứa nội dung training
    * Thêm vào Map gồm có key(nội dung của từng từ được tách ra), value(điểm trọng số của từ đó)
    * */
    private void addWordFromFileData(String filePath) {
        Path path = Paths.get(filePath);
        try (BufferedReader reader = Files.newBufferedReader(path)) {// dọc file
            String line;
            while ((line = reader.readLine()) != null) {// đọc từng dòng 1
                String[] data = getDataEmail(line);// lấy ra giá trị của từng dòng trong tranning data
                emails.add(ConverterString.deAccent(data[0]));
                labels.add(data[1]);
                String[] words = data[0].split(" ");// cắt từng từ trong dòng
                String[] newWords = new String[words.length];
                for(int i = 0; i < words.length ; i++){
                    newWords[i] = ConverterString.deAccent(words[i]);
                }
                for (String word : newWords) {//
                    if (!idfMap.containsKey(word)) {
                        idfMap.put(word, 1.0);
                    }
                }
            }
            for (String word : idfMap.keySet()) {
                double idf = 0;
                for (String email : emails) {
                    if (email.contains(word)) {
                        idf++;
                    }
                }
                idfMap.put(word, Math.log(emails.size() / (idf + 1)));//tính số lần xuất hện của từ trong data tranning, xh càng nhiều trọng số càng nhỏ
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean predict(String content) {// xử lý nội dung nhập vào
        String[] words = content.split(" ");

        Map<String, Double> tfMap = new HashMap<>();
        for (String word : words) {
            countOccurrenceWord(tfMap, word);
        }
        double maxScore = 0;
        String maxLabel = "";
        maxLabel = getLabelOfWord(words, tfMap, maxScore, maxLabel);
        return "S".equals(maxLabel);
    }

    private String getLabelOfWord(String[] words, Map<String, Double> tfMap, double maxScore, String maxLabel) {
        for (int i = 0; i < emails.size(); i++) {// lấy ra dữ liệu và nhãn của từng dòng
            String trainEmail = emails.get(i);
            String trainLabel = labels.get(i);
            String[] trainWords = trainEmail.split(" ");
            Map<String, Double> trainTfMap = new HashMap<>();
            for (String word : trainWords) {// đánh trọng số cho từng dòng trong file data tranning
                countOccurrenceWord(trainTfMap, word);
            }
            double score = 0;
            score = updateScoreOfWord(words, tfMap, trainTfMap, score);

            if (score > maxScore) {
                maxScore = score;
                maxLabel = trainLabel;
            }
        }
        return maxLabel;
    }

    private double updateScoreOfWord(String[] words, Map<String, Double> tfMap, Map<String, Double> trainTfMap, double score) {
        for (String word : words) {//input, nội dung trong tranning
            if (trainTfMap.containsKey(word)) {
                score += tfMap.get(word) * trainTfMap.get(word) * idfMap.get(word);// kiểm tra giá trị trong file data tranning có giống với input k
            }
        }
        return score;
    }

    private static void countOccurrenceWord(Map<String, Double> tfMap, String word) {
        if (!tfMap.containsKey(word)) {
            tfMap.put(word, 1.0);// nếu kí tự xuất hiện 1 lần, đánh trọng số là 1
        } else {
            tfMap.put(word, tfMap.get(word) + 1);// xuất hiện n lần->cập nhật trọng số thêm n lần
        }
    }

    private static String[] getDataEmail(String content) {//lưu nội dung của từ dòng trong file trânning data, từ đi kí tự cuối
        int length = content.length();
        String[] data = new String[2];
        data[0] = content.substring(0, length - 1);// dữ liệu tranning
        data[1] = content.substring(length - 1);// label
        return data;
    }

}
