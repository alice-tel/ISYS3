package game.games.Stratego.Pion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import game.framework.GameFramework;

public class spelerPionnen {
    private JPanel pionpanel;
    private ArrayList<Pion> pionnen;

    public spelerPionnen() {
        pionnen = new ArrayList<>();
        initializePionnen();
    }

    private void initializePionnen() {
        for (int i = 0; i < 6; i++) { pionnen.add(new Pion("Bomb", 11, new noMove(), new noAttack())); }
        pionnen.add(new Pion("Marshal", 10, new Move(), new Attack()));
        pionnen.add(new Pion("General", 9, new Move(), new Attack()));
        for (int i = 0; i < 2; i++) { pionnen.add(new Pion("Colonel", 8, new Move(), new Attack())); }
        for (int i = 0; i < 3; i++) { pionnen.add(new Pion("Major", 7, new Move(), new Attack())); }
        for (int i = 0; i < 4; i++) { pionnen.add(new Pion("Captain", 6, new Move(), new Attack())); }
        for (int i = 0; i < 4; i++) { pionnen.add(new Pion("Lieutenant", 5, new Move(), new Attack())); }
        for (int i = 0; i < 4; i++) { pionnen.add(new Pion("Sergeant", 4, new Move(), new Attack())); }
        for (int i = 0; i < 5; i++) { pionnen.add(new Pion("Miner", 3, new Move(), new MinerAttack())); }
        for (int i = 0; i < 8; i++) { pionnen.add(new Pion("Scout", 2, new ScoutMove(), new Attack())); }
        pionnen.add(new Pion("Spy", 1, new Move(), new SpyAttack()));
        pionnen.add(new Pion("Flag", 0, new noMove(), new noAttack()));
    }
}

