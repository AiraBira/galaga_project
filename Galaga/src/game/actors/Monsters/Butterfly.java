package game.actors.Monsters;

import game.actors.Base.Monster;

public class Butterfly extends Monster{

    public static final double taille = 0.06;
    public static final int valeur = 200;
    public static final double vitesse = 0.001;

    public Butterfly(double x, double y) {
        super(x, y, taille, valeur, vitesse);
    }
    
}
