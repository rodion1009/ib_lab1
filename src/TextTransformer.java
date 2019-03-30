import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class TextTransformer {
    private Map<Character, Character> transformationTable;

    TextTransformer(Map<Character, Character> transformationTable) {
        this.transformationTable = transformationTable;
    }

    private String transform(String text) {
        String encryptedText = "";

        for (char letter: text.toCharArray()) {
            if (transformationTable.containsKey(letter)) {
                encryptedText += transformationTable.get(letter);
            } else {
                encryptedText += letter;
            }
        }

        return encryptedText;
    }

    void transformFromFile(String inputFilePath, String outputFilePath) throws IOException {
        try (Scanner scanner = new Scanner(new FileReader(inputFilePath));
             FileWriter writer = new FileWriter(outputFilePath))
        {
            while (scanner.hasNextLine()) {
                writer.write(transform(scanner.nextLine().toLowerCase()) + "\n");
            }
        }
    }
}
