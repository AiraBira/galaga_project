package game.actors.monsters;
import game.actors.base.*;

/**
 * Classe représentant un monstre de type "Bee" dans le jeu.
 * Le Bee a un comportement d'attaque spécifique où il descend en oscillant horizontalement.
 * Fille de la classe Monster.
 */
public class Bee extends Monster {

    /** Sprite du Bee chargé depuis un fichier externe */
    private static final String SPRITE = loadSprite("../ressources/sprites/bee.spr"); // On charge notre fichier .spr
                                                                                     // pour dessiner le bee

    /**
     * Construit un monstre de type Bee avec ses caractéristiques principales.
     *
     * @param x        position initiale sur l'axe des abscisses
     * @param y        position initiale sur l'axe des ordonnées
     * @param taille   largeur du Bee
     * @param valeur   valeur en points du Bee
     * @param vitesse  vitesse de déplacement du Bee
     * @param cooldownTir délai initial entre chaque tir du Bee
     */
    public Bee(double x, double y, double taille, int valeur, double vitesse, double cooldownTir) {
        super(x, y, taille, valeur, vitesse, SPRITE, cooldownTir); // On le met dans le constructeur d'entité pour que
                                                                   // ça soit dessiné
    }

    /**
     * Mouvement d'attaque spécifique au Bee.
     * Le Bee descend en oscillant horizontalement.
     */
    public void mouvement_attaque() {
        setPosY(getPosY() - getVitesse() * 2);
        setPosX(getPosX() + Math.cos(getPosY() * 20) * 0.01);
    }

    /**
     * Met à jour l'état du Bee en fonction de sa direction et de son état d'attaque.
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
