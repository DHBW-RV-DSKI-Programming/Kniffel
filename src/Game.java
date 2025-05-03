import java.util.*;

class Game {

    private final Dice[] dices;
    private final Notepad notepad;
    private final static Scanner sc = new Scanner(System.in);

    Game() {
        this.dices = new Dice[]{
                new Dice("Dice 1"),
                new Dice("Dice 2"),
                new Dice("Dice 3"),
                new Dice("Dice 4"),
                new Dice("Dice 5")
        };
        this.notepad = new Notepad();
    }

    void playGame() {
        boolean isGameRunning = true;
        int roundNumber = 1;

        while (isGameRunning) {
            System.out.println("***********");
            System.out.printf("* Round %d *\n", roundNumber);
            System.out.println("***********");

            for (int i = 1; i <= 3; i++) {
                System.out.println("-----------");
                System.out.printf("Throw Nr. %d\n", i);
                System.out.println("-----------");

                rollDices();
                printDices();
                boolean isCombinationChosen = askForCombination(i == 3);
                if (isCombinationChosen) {
                    break;
                }
                if (i != 3) decideWhichDicesToKeep();
            }

            notepad.printCurrentNotepad();
            Arrays.stream(dices).forEach(Dice::unkeepDice);

            roundNumber++;

            isGameRunning = roundNumber <= notepad.getCombinations().length;
        }
        sc.close();

        System.out.println("Game over!");

        int totalPoints = Arrays.stream(notepad.getCombinations()).mapToInt(Combination::getPoints).sum();
        int firstPartSum = Arrays.stream(notepad.getCombinations())
                .filter(combination -> combination.getCombinationName().equals("1er")
                        || combination.getCombinationName().equals("2er")
                        || combination.getCombinationName().equals("3er")
                        || combination.getCombinationName().equals("4er")
                        || combination.getCombinationName().equals("5er")
                        || combination.getCombinationName().equals("6er"))
                .mapToInt(Combination::getPoints)
                .sum();
        totalPoints = firstPartSum < 63 ? totalPoints : totalPoints + 35;
        System.out.printf(">>> Total points: %d\n", totalPoints);
    }

    private void rollDices() {
        Arrays.stream(dices)
                .filter(dice -> !dice.isBeingKept())
                .forEach(Dice::rollDice);
    }

    private void printDices() {
        Arrays.stream(dices)
                .forEach(Dice::printDiceValue);
    }

    private void decideWhichDicesToKeep() {
        System.out.println("> Choose which dices to keep as comma separated list (1-5) with no spaces. Enter nothing to keep no dice:");
        String input = sc.nextLine();
        if (input.isEmpty()) {
            return;
        }
        String[] inputArray = input.split(",");
        Arrays.stream(inputArray).forEach(s -> dices[Integer.parseInt(s) - 1].keepDice());
    }

    private boolean askForCombination(boolean isLastThrow) {
        if (!isLastThrow) {
            boolean isValid = false;
            while (!isValid) {
                System.out.println("> Do you want to check a combination? (y/n):");
                String answer = sc.nextLine().toLowerCase();
                if (!answer.equals("y") && !answer.equals("n")) {
                    continue;
                }
                if (answer.equals("n")) {
                    return false;
                } else {
                    isValid = true;
                }
            }
        }

        boolean isValid = false;
        while (!isValid) {
            System.out.println("> Enter the combination name you want to check:");
            String input = sc.nextLine();
            if (!input.isEmpty()) {
                Combination combi = Arrays.stream(notepad.getCombinations())
                        .filter(combination -> combination.getCombinationName().equals(input))
                        .filter(Combination::isUnchecked)
                        .findFirst()
                        .orElse(null);
                if (combi == null) {
                    System.out.println("Invalid combination name!");
                    continue;
                }
                List<Byte> diceValues = Arrays.stream(dices).map(Dice::getDiceValue).toList();
                combi.checkCombination(diceValues);
                isValid = true;
            }
        }
        return true;
    }

}
