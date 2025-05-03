class Notepad {

    private final Combination[] combinations;

    Notepad() {
        this.combinations = new Combination[]{
                new Combination("1er", "nur Einser zählen"),
                new Combination("2er", "nur Zweier zählen"),
                new Combination("3er", "nur Dreier zählen"),
                new Combination("4er", "nur Vierer zählen"),
                new Combination("5er", "nur Fünfer zählen"),
                new Combination("6er", "nur Sechser zählen"),
                new Combination("Dreierpasch", "alle Augen zählen"),
                new Combination("Viererpasch", "alle Augen zählen"),
                new Combination("Full House", "25 Punkte"),
                new Combination("Kleine Strasse", "30 Punkte"),
                new Combination("Grosse Strasse", "40 Punkte"),
                new Combination("Kniffel", "50 Punkte"),
                new Combination("Chance", "alle Augen zählen")
        };
    }

    Combination[] getCombinations() {
        return this.combinations;
    }

    void printCurrentNotepad() {
        System.out.println("++++++++");
        System.out.println("Notepad:");
        for (Combination combination : this.combinations) {
            System.out.printf("%s: %d\n", combination.getCombinationName(), combination.getPoints());
        }
        System.out.println("++++++++");
    }

}
