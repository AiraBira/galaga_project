package game.actors.Monsters;

import game.actors.Base.Monster;

public class Moth extends Monster{

    public static final double taille = 0.06;
    public static final int valeur = 300;
    public static final double vitesse = 0.0005;

    public Moth(double x, double y) {
        super(x, y, taille, valeur, vitesse);
    }
    
}
