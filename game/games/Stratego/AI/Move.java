package game.games.Stratego.AI;

public class Move {
    
        int[] from;
        int[] to;
        int value;
    
        public Move(int[] from, int[] to) {
            this.from = from;
            this.to = to;
        }
    
        public Move(int[] from, int value) {
            this.from = from;
            this.value = value;
        }
    public int[] getFrom() {
        return from;
    }
    public int[] getTo() {
        return to;
    }
    public int getValue() {
        return value;
    }
}
