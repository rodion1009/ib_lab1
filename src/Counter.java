import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class Counter {
    private Set<Character> alphabet;
    private Map<Character, Integer> letters = new HashMap<>();
    private Map<String, Integer> bigrams = new HashMap<>();

    Counter(Set<Character> alphabet) {
        this.alphabet = alphabet;

        for (char letter: alphabet) {
            letters.put(letter, 0);
        }
    }

    private void countLetters(String text) {
        for (char letter: text.toCharArray()) {
            if (alphabet.contains(letter)) {
                letters.put(letter, letters.get(letter) + 1);
            }
        }
    }

    private void countBigrams(String text) {
        char[] chars = text.toCharArray();
        String bigram;

        for (int i = 0; i < chars.length - 1; i++) {
            if (alphabet.contains(chars[i])) {
                bigram = String.valueOf(chars[i]);
                i++;
                while (!alphabet.contains(chars[i]) && i < chars.length - 1) {
                    i++;
                }
                if (alphabet.contains(chars[i])) {
                    bigram += chars[i];
                } else {
                    break;
                }

                if (bigrams.containsKey(bigram)) {
                    bigrams.put(bigram, bigrams.get(bigram) + 1);
                } else {
                    bigrams.put(bigram, 1);
                }
            }
        }
    }

    void countAllInFile(String filePath) throws FileNotFoundException {
        String line;
        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                countLetters(line);
                countBigrams(line);
            }
        }
    }

    Map<Character, Integer> getAmountOfLetters() {
        return letters;
    }

    Map<String, Integer> getAmountOfBigrams() {
        return bigrams;
    }
}
