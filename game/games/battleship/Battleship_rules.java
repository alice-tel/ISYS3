package game.games.battleship;

public class Battleship_rules extends BattleshipGame {

    private static final String rules = "Spelregels Battleship\n\n" +
        "Setup-fase:\n" +
        "• Spelbord: 8x8 grid voor elke speler.\n" +
        "• Schepen: Elke speler plaatst vier schepen op het bord:\n" +
        "   - 1x Lengte 6\n" +
        "   - 1x Lengte 4\n" +
        "   - 1x Lengte 3\n" +
        "   - 1x Lengte 2\n" +
        "• Plaatsingsregels:\n" +
        "   - Schepen mogen elkaar niet raken, maar mogen wel tegen de rand.\n" +
        "   - Schepen kunnen horizontaal of verticaal worden geplaatst.\n" +
        "   - Klik met de rechter muisknop op 1 van de schepen om de schepen te roteren.\n" +
        "• Klaar-knop: Zodra alle schepen geplaatst zijn, druk je op de 'Klaar'-knop om verder te gaan.\n\n" +
        "Spelfase:\n" +
        "• Doel: Om beurten aanvallen uitvoeren en proberen de schepen van de tegenstander tot zinken te brengen.\n" +
        "• Aanvallen:\n" +
        "   - Klik op een vakje van het tegenstanderbord om aan te vallen.\n" +
        "   - Rood betekent dat je een schip hebt geraakt ('Hit').\n" +
        "   - Zwart betekent een misser ('Miss').\n" +
        "• Beurtwisseling:\n" +
        "   - Als je raakt, mag je nog een keer aanvallen.\n" +
        "   - Als je mist, is de andere speler aan de beurt.\n\n" +
        "Winconditie:\n" +
        "• Het spel eindigt zodra alle delen van de schepen van een speler zijn geraakt.\n" +
        "• De speler die als laatste nog overgebleven schepen heeft, wint het spel.\n\n" +
        "Spelvoorwaarden:\n" +
        "• Beide spelers moeten eerst al hun schepen correct hebben geplaatst.\n" +
        "• Het spel kan op elk moment worden gereset als beide spelers daarmee instemmen.\n";

    public static String getRules() {
        return rules;
    }
}
