import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class Combination {

    private final String combinationName;
    private final String combinationDescription;
    private boolean isChecked;
    private int points;

    Combination(String combinationName, String combinationDescription) {
        this.combinationName = combinationName;
        this.combinationDescription = combinationDescription;
        this.isChecked = false;
        this.points = -1;
    }

    boolean isChecked() {
        return this.isChecked;
    }

    boolean isUnchecked() {
        return !this.isChecked;
    }

    void checkCombination(List<Byte> diceValues) {
        this.isChecked = true;

        switch (this.combinationName) {
            case "1er":
                this.points = diceValues.stream().filter(value -> value == 1).mapToInt(Byte::intValue).sum();
                break;
            case "2er":
                this.points = diceValues.stream().filter(value -> value == 2).mapToInt(Byte::intValue).sum();
                break;
            case "3er":
                this.points = diceValues.stream().filter(value -> value == 3).mapToInt(Byte::intValue).sum();
                break;
            case "4er":
                this.points = diceValues.stream().filter(value -> value == 4).mapToInt(Byte::intValue).sum();
                break;
            case "5er":
                this.points = diceValues.stream().filter(value -> value == 5).mapToInt(Byte::intValue).sum();
                break;
            case "6er":
                this.points = diceValues.stream().filter(value -> value == 6).mapToInt(Byte::intValue).sum();
                break;
            case "Dreierpasch":
                int sumOfDices = diceValues.stream().mapToInt(Byte::intValue).sum();
                this.points = diceValues.stream().distinct().count() <= 3 ? sumOfDices : 0;
                break;
            case "Viererpasch":
                int sumOfDices2 = diceValues.stream().mapToInt(Byte::intValue).sum();
                this.points = diceValues.stream().distinct().count() <= 2 ? sumOfDices2 : 0;
                break;
            case "Full House":
                HashMap<Byte, Long> map = new HashMap<>();
                diceValues.forEach(value -> map.put(value, map.getOrDefault(value, 0L) + 1L));
                int points = 0;
                if (map.size() == 2) {
                    long firstKeyValue = map.values().stream().findFirst().orElse(0L);
                    long secondKeyValue = map.values().stream().skip(1).findFirst().orElse(0L);
                    points = firstKeyValue + secondKeyValue == 5 ? 25 : 0;
                }
                this.points = points;
                break;
            case "Kleine Strasse":
                long distinctCount = diceValues.stream().distinct().count();
                if (distinctCount >= 4) {
                    List<Byte> mutableDiceValues = new ArrayList<>(diceValues);
                    Collections.sort(mutableDiceValues);
                    int intervalDifference = 0;
                    for (int i = 0; i < 4; i++) {
                        int interval = mutableDiceValues.get(i + 1) - mutableDiceValues.get(i);
                        intervalDifference += interval == 1 ? 0 : 1;
                    }
                    this.points = intervalDifference <= 1 ? 30 : 0;
                } else {
                    this.points = 0;
                }
                break;
            case "Grosse Strasse":
                long distinctCount2 = diceValues.stream().distinct().count();
                boolean isSequence = true;
                if (distinctCount2 < 5) {
                    isSequence = false;
                } else {
                    List<Byte> mutableDiceValues = new ArrayList<>(diceValues);
                    Collections.sort(mutableDiceValues);
                    for (int i = 0; i < 4; i++) {
                        int interval = mutableDiceValues.get(i + 1) - mutableDiceValues.get(i);
                        if (interval != 1) {
                            isSequence = false;
                            break;
                        }
                    }
                }

                this.points = isSequence ? 40 : 0;
                break;
            case "Kniffel":
                this.points = diceValues.stream().distinct().count() == 1 ? 50 : 0;
                break;
            case "Chance":
                this.points = diceValues.stream().mapToInt(Byte::intValue).sum();
                break;
        }
    }

    String getCombinationName() {
        return this.combinationName;
    }

    String getCombinationDescription() {
        return this.combinationDescription;
    }

    int getPoints() {
        return this.points;
    }

}
