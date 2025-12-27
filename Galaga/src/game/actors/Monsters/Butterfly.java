package game.actors.Monsters;

import game.actors.Base.*;

public class Butterfly extends Monster{
    public static final double taille = 0.06;
    public static final int valeur = 200;
    public static final double vitesse = 0.01;
    public static final String SPRITE = loadSprite("../ressources/sprites/butterfly.spr"); // On charge notre fichier .spr


    public Butterfly(double x, double y) {
        super(x, y, taille, valeur, vitesse, SPRITE);
    }

    public void mouvement_attaque(){
        setPosY(getPosY() - getVitesse());
    }


    public void update(boolean directionDroite , Player p){

        if (isEnAttaque()){
            mouvement_attaque();
        }
        else if (directionDroite) {
            mouvementDroit();
        }
        else {
            mouvementGauche();
        }
    }
    
}
