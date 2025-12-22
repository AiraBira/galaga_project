package game.actors.Monsters;

import game.actors.Base.Monster;

public class Bee extends Monster{

    public static final double taille = 0.06;
    public static final int valeur = 100;
    public static final double vitesse = 0.002;

    public Bee(double x, double y) {
        super(x, y, taille, valeur, vitesse);
    }
    
}
