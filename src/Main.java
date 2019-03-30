import java.io.IOException;
import java.util.*;

public class Main {
    private static List<Character> createAlphabet() {
        List<Character> alphabet = new ArrayList<>();

        for (char c = 'а'; c <= 'е'; c++) {
            alphabet.add(c);
        }

        alphabet.add('ё');

        for (char c = 'ж'; c <= 'я'; c++) {
            alphabet.add(c);
        }

        return alphabet;
    }

    private static Map<Character, Character> getCaesarCipherTable(List<Character> alphabetList, int shift) {
        Map<Character, Character> encryptionTable = new HashMap<>();

        for (int i = 0; i < alphabetList.size(); i++) {
            encryptionTable.put(alphabetList.get(i), alphabetList.get((i + shift) % alphabetList.size()));
        }

        return encryptionTable;
    }

    public static void main(String[] args) throws IOException {
        List<Character> alphabetList = createAlphabet();
        Set<Character> alphabetSet = new HashSet<>(alphabetList);

        Counter encryptedChapterCounter = new Counter(alphabetSet);
        Counter allRomanCounter = new Counter(alphabetSet);

        // Зашифровать первую главу
        TextTransformer encryptor = new TextTransformer(getCaesarCipherTable(alphabetList, 3));
        encryptor.transformFromFile("chapter.txt", "encrypted_chapter.txt");

        // Посчитать частоту букв всего романа
        allRomanCounter.countAllInFile("all.txt");

        // Посчитать частоту в зашифрованной главе
        encryptedChapterCounter.countAllInFile("encrypted_chapter.txt");

        // Сопоставить
        Analyzer analyzer = new Analyzer();
        Map<Character, Character> decryptionTable = analyzer.performAnalysis(
                encryptedChapterCounter.getAmountOfLetters(),
                allRomanCounter.getAmountOfLetters()
        );

        // Расшифровать и вывести в файл
        TextTransformer decryptor = new TextTransformer(decryptionTable);
        decryptor.transformFromFile("encrypted_chapter.txt", "decrypted_chapter.txt");

        // Уточнить таблицу биграммами
        analyzer.addBigrams(decryptionTable, encryptedChapterCounter.getAmountOfBigrams(),
                allRomanCounter.getAmountOfBigrams());

        // Расшифровать после уточнения
        decryptor.transformFromFile("encrypted_chapter.txt", "decrypted_chapter2.txt");
    }
}