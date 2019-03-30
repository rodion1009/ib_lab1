import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Analyzer {
    private final int TOP_BIGRAMS_AMOUNT = 5;

    private List<Character> getSortedValuesList(Map<Character, Integer> map) {
        return map.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<String> getTopBigrams(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                .subList(0, TOP_BIGRAMS_AMOUNT);
    }

    Map<Character, Character> performAnalysis(Map<Character, Integer> table1, Map<Character, Integer> table2) {
        List<Character> list1 = getSortedValuesList(table1);
        List<Character> list2 = getSortedValuesList(table2);

        Map<Character, Character> result = new HashMap<>();

        for (int i = 0; i < list1.size(); i++) {
            result.put(list1.get(i), list2.get(i));
        }

        return result;
    }

    void addBigrams(Map<Character, Character> table, Map<String, Integer> bigrams1, Map<String, Integer> bigrams2) {
        List<String> bigrams1Top = getTopBigrams(bigrams1);
        List<String> bigrams2Top = getTopBigrams(bigrams2);

        for (int i = 0; i < TOP_BIGRAMS_AMOUNT; i++) {
            for (int j = 0; j < 2; j++) {
                if (table.get(bigrams1Top.get(i).charAt(j)) != table.get(bigrams2Top.get(i).charAt(j))) {
                    table.replace(bigrams1Top.get(i).charAt(j), table.get(bigrams1Top.get(i).charAt(j)),
                            bigrams2Top.get(i).charAt(j));
                }
            }
        }
    }
}
