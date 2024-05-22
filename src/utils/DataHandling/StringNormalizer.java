package utils.DataHandling;

/**
 * The StringNormalizer class provides utility methods for normalizing and formatting strings.
 * It includes methods for capitalizing the first letter of a string and formatting names.
 */
public class StringNormalizer {

    /**
     * Capitalizes the first letter of the given word.
     * 
     * @param word the word to capitalize
     * @return the word with its first letter capitalized, or the original word if it is null or empty
     * Time Complexity: O(n) where n is the length of the word
     */
    public static String capitalizeFirst(String word) {
        if (word == null || word.isEmpty()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    /**
     * Normalizes the given name by capitalizing the first letter of each word.
     * It also replaces hyphens with spaces and ensures each word is properly capitalized.
     * 
     * @param name the name to normalize
     * @return the normalized name with each word capitalized
     * Time Complexity: O(n) where n is the length of the name
     */
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
