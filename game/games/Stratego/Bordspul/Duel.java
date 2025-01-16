package game.games.Stratego.Bordspul;

import game.games.Stratego.Strategys.IAttackStrategy;

public class Duel{

    private  String attacker,deffender;
    private  int intattacker, intdeffender ;

    
    public Duel(String attacker, String deffender){
        this.attacker = attacker;
        this.deffender = deffender;
        handleString();
    }
    public void handleString(){
        String[] partsattacker = attacker.split(" ");
        String[] partsdeffender = deffender.split(" ");
        intattacker = Integer.parseInt(partsattacker[2]);
        intdeffender = Integer.parseInt(partsdeffender[2]);
        
    }

    public  Boolean attackerwin(){
        if( intattacker == 1 && intdeffender == 10){
            return true;
        }
        else if( intattacker == 3 && intdeffender == 11){
            return true;
        }
        else if( intattacker > intdeffender){
            return true;
        }
        else{
            return false;
        }
    }
    public  Boolean defenderwin(){
        if( intdeffender > intattacker){
            return true;
        }
        else{
            return false;
        }
        
    }




}
