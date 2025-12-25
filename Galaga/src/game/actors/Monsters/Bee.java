package game.actors.Monsters;

import game.actors.Base.*;

public class Bee extends Monster{

    public static final double taille = 0.06;
    public static final int valeur = 100;
    public static final double vitesse = 0.002;

    public Bee(double x, double y) {
        super(x, y, taille, valeur, vitesse);
    }

    public void mouvement_attaque(){
        setPosY(getPosY() - getVitesse()*2);
        setPosX(getPosX() + Math.cos(getPosY() * 20) *0.01);
    }

    public void update(boolean directionDroite, Player p){ // Reconnait la difference entre le update de mosntre et des autres classes 

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
