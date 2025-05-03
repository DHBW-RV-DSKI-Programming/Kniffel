import java.util.Random;

class Dice {

    private String diceName;
    private byte diceValue;
    private boolean isBeingKept;

    Dice(String diceName) {
        this.diceName = diceName;
        this.diceValue = (byte) (new Random().nextInt(6) + 1);
        this.isBeingKept = false;
    }

    byte getDiceValue() {
        return this.diceValue;
    }

    boolean isBeingKept() {
        return this.isBeingKept;
    }

    void rollDice() {
        this.diceValue = (byte) (new Random().nextInt(6) + 1);
    }

    void keepDice() {
        this.isBeingKept = true;
    }

    void unkeepDice() {
        this.isBeingKept = false;
    }

    void printDiceValue() {
        String keptText = this.isBeingKept ? " (kept)" : "";
        System.out.printf("%s: %d%s\n", this.diceName, this.diceValue, keptText);
    }

}
