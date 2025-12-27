package game.actors.Monsters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import game.actors.Base.*;

public class Bee extends Monster{

    public static final double taille = 0.06;
    public static final int valeur = 100;
    public static final double vitesse = 0.002;
    public static final String SPRITE = loadSprite("../ressources/sprites/bee.spr"); // On charge notre fichier .spr
    
    public Bee(double x, double y) {
        super(x, y, taille, valeur, vitesse, SPRITE);  // On le met dans le constructeur d'entité pour que ça soit dessiné
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
