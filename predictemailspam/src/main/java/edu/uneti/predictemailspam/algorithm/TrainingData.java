package edu.uneti.predictemailspam.algorithm;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class TrainingData {
    /**
     *
     * @param content - một chuỗi chứa nội dung
     * @return Set<String> một bộ dữ liệu chứa các từ cắt từ chuỗi content, các từ trùng lặp trong câu sẽ bị loại bỏ
     */
    public static Set<String> toBagOfWord(String content) {
        // Khởi tạo một HashSet collection bag để chứa các từ
        HashSet<String> bag = new HashSet<>();
        // tách các từ phân cách bởi các dấu ,.!*"'()
        StringTokenizer s1 = new StringTokenizer(content, " ,.!*\"\'()");
        // Duyệt qua các từ trong chuỗi và lưu vào trong bag
        while (s1.hasMoreTokens()) {
            bag.add(s1.nextToken());
        }
        // Trả về một danh sách từ được cat ra từ trong param content và loại bỏ các từ trùng lặp
        return bag;
    }
}
