package edu.uneti.predictemailspam.untils;

import java.text.Normalizer;

public class ConverterString {
    private static String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input, Normalizer.Form.NFKD);
    }

    // Loại bỏ các dấu tiếng Việt
    public static String deAccent(String content) {
        return normalize(content).replaceAll("\\p{M}", "");
    }
}
