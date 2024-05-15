package utils;

public class StringNormalizer {
     public static String capitalizeFirst(String word) {
        if (word == null || word.isEmpty()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public static String name(String name) {
        String temp = capitalizeFirst(name.toLowerCase().replace("-", " "));
        if (temp.contains(" ")) {
            String[] words = temp.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String word : words) {
                sb.append(capitalizeFirst(word)).append(" ");
            }
            return sb.toString().trim();
        } else {
            return temp;
        }
    }
}
