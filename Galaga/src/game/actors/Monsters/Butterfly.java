package game.actors.monsters;
import game.actors.base.*;

/**
 * Classe représentant un monstre de type "Butterfly" dans le jeu.
 * Le Butterfly a un comportement d'attaque spécifique où il monte verticalement.
 * Fille de la classe Monster.
 */
public class Butterfly extends Monster {

    /** Sprite du Butterfly chargé depuis un fichier externe */
    private static final String SPRITE = loadSprite("../ressources/sprites/butterfly.spr"); // On charge notre fichier .spr
    
    /**
     * Construit un monstre de type Butterfly avec ses caractéristiques principales.
     *
     * @param x        position initiale sur l'axe des abscisses
     * @param y        position initiale sur l'axe des ordonnées
     * @param taille   largeur du Butterfly
     * @param valeur   valeur en points du Butterfly
     * @param vitesse  vitesse de déplacement du Butterfly
     * @param cooldownTir délai initial entre chaque tir du Butterfly
     */
    public Butterfly(double x, double y, double taille, int valeur, double vitesse, double cooldownTir) {
        super(x, y, taille, valeur, vitesse, SPRITE, cooldownTir);
    }

    /**
     * Mouvement d'attaque spécifique au Butterfly.
     * Le Butterfly monte verticalement.
     */
    public void mouvement_attaque() {
        setPosY(getPosY() - getVitesse());
    }


    /**
     * 
     */
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
