package game.actors.monsters;
import game.actors.base.*;

/**
 * Classe représentant un monstre de type "Moth" dans le jeu.
 * Le Moth a un comportement d'attaque spécifique où il tente de capturer le joueur.
 * Fille de la classe Monster.
 */
public class Moth extends Monster {

    /** Indique si le Moth a capturé le joueur */
    private boolean capture;
    
    /** Sprite du Moth chargé depuis un fichier externe */
    private static final String SPRITE = loadSprite("../ressources/sprites/catcher.spr"); // On charge notre fichier .spr

    /**
     * Construit un monstre de type Moth avec ses caractéristiques principales.
     *
     * @param x        position initiale sur l'axe des abscisses
     * @param y        position initiale sur l'axe des ordonnées
     * @param taille   largeur du Moth
     * @param valeur   valeur en points du Moth
     * @param vitesse  vitesse de déplacement du Moth
     * @param cooldownTir délai initial entre chaque tir du Moth
     */
    public Moth(double x, double y, double taille, int valeur, double vitesse, double cooldownTir) {
        super(x, y, taille, valeur, vitesse, SPRITE, cooldownTir);
        this.capture = false;
    }

    /**
     * Vérifie si le Moth capture le joueur.
     * @param p le joueur
     * @param modeInfini indique si le mode vies infinies est activé
     */
    private void verifierCapture(Player p, boolean modeInfini) {
        double gaucheMoth = getPosX() - getLength();
        double droiteMoth = getPosX() + getLength();
        double gauchePlayer = p.getPosX() - p.getLength();
        double droitePlayer = p.getPosX() + p.getLength();

        if (droiteMoth >= gauchePlayer && gaucheMoth <= droitePlayer) {
            p.perdreVie(modeInfini); // vie perdue lors de la capture
            setCapture(true);
        }
    }

    /**
     * Mouvement d'attaque spécifique au Moth.
     * @param p le joueur
     * @param directionDroite indique la direction actuelle de la formation
     * @param modeInfini indique si le mode vies infinies est activé
     */
    public void mouvement_attaque(Player p, boolean directionDroite, boolean modeInfini) {
        if (!isCapture()) {
            double limiteY = 0.40;
            // Si on est au-dessus du joueur, déclenche capture
            if (!isCapture() && getPosY() <= limiteY) {
                verifierCapture(p, modeInfini);
                setPosY(limiteY);
            }
            else{
                // Descente normale
                setPosY(getPosY() - getVitesse());
                if (p.getPosX() < getPosX()) {
                    setPosX(getPosX() - getVitesse() * 2);
                } else if (p.getPosX() > getPosX()) {
                    setPosX(getPosX() + getVitesse() * 2);
                }
            }  
        } else {
            double limiteY = 0.55;
            // Moth reste au-dessus du joueur
            if (getPosY() >= limiteY) {
                if (directionDroite) {
                    mouvementDroit();
                } else {
                    mouvementGauche();
                }
            }
            else {
                setPosY(getPosY() + getVitesse() *2); // remonte après capture
            }
            
            
        }
    }

    /**
     * Met à jour l'état du Moth en fonction de sa direction et de son état d'attaque.
     * @param directionDroite direction actuelle de la formation (true pour droite, false pour gauche)
     * @param p le joueur
     * @param modeInfini indique si le mode infini est activé
     */
    @Override
    public void update(boolean directionDroite, Player p, boolean modeInfini) {
        if (isEnAttaque()) { // Les mouvements changent que si le monstre est l'un des premiers dans la
            // formation
            mouvement_attaque(p, directionDroite, modeInfini);
        } else if (directionDroite) {
            mouvementDroit();
        } else {
            mouvementGauche();
        }
        setCooldownTir(getCooldownTir() + 1); // On incrémente le cooldown de tir
    }

    /* ------ GETTERS ET SETTERS ------ */
    /**
     * Vérifie si le Moth a capturé le joueur.
     * @return true si le Moth a capturé le joueur, false sinon
     */
    public boolean isCapture() {
        return capture;
    }

    /**
     * Définit l'état de capture du Moth.
     * @param capture true si le Moth a capturé le joueur, false sinon
     */
    public void setCapture(boolean capture) {
        this.capture = capture;
    }


    
}
