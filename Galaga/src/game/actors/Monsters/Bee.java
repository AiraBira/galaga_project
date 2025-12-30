package game.actors.Monsters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import game.actors.Base.*;

public class Bee extends Monster {

    public static final String SPRITE = loadSprite("../ressources/sprites/bee.spr"); // On charge notre fichier .spr
                                                                                     // pour dessiner le bee

    public Bee(double x, double y, double taille, int valeur, double vitesse, double cooldownTir) {
        super(x, y, taille, valeur, vitesse, SPRITE, cooldownTir); // On le met dans le constructeur d'entité pour que
                                                                   // ça soit dessiné
    }

    public void mouvement_attaque() {
        setPosY(getPosY() - getVitesse() * 2);
        setPosX(getPosX() + Math.cos(getPosY() * 20) * 0.01);
    }

    @Override
    public void update(boolean directionDroite, Player p, boolean modeInfini) { // Reconnait la difference entre le
                                                                                // update de mosntre et des autres
                                                                                // classes
        if (isEnAttaque()) {
            mouvement_attaque();
        } else if (directionDroite) {
            mouvementDroit();
        } else {
            mouvementGauche();
        }
        setCooldownTir(getCooldownTir() + 1); // On incrémente le cooldown de tir
    }

}
