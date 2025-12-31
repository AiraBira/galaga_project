package game.actors.Monsters;

import game.actors.Base.*;

public class Butterfly extends Monster {

    private static final String SPRITE = loadSprite("../ressources/sprites/butterfly.spr"); // On charge notre fichier
                                                                                           // .spr

    public Butterfly(double x, double y, double taille, int valeur, double vitesse, double cooldownTir) {
        super(x, y, taille, valeur, vitesse, SPRITE, cooldownTir);
    }

    public void mouvement_attaque() {
        setPosY(getPosY() - getVitesse());
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
