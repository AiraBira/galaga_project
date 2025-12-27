package game.actors.Monsters;

import game.actors.Base.*;

public class Moth extends Monster {

    private boolean capture;
    public static final double TAILLE = 0.06;
    public static final int VALEUR = 300;
    public static final double VITESSE = 0.0005;
    public static final String SPRITE = loadSprite("../ressources/sprites/catcher.spr"); // On charge notre fichier .spr


    public Moth(double x, double y) {
        super(x, y, TAILLE, VALEUR, VITESSE, SPRITE);
        this.capture = false;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    // Vérifie si le Moth est au-dessus du joueur et déclenche la capture
    private void verifierCapture(Player p) {
        double gaucheMoth = getPosX() - getLargeur();
        double droiteMoth = getPosX() + getLargeur();
        double gauchePlayer = p.getPosX() - p.getLargeur();
        double droitePlayer = p.getPosX() + p.getLargeur();

        if (droiteMoth >= gauchePlayer && gaucheMoth <= droitePlayer) {
            setCapture(true);
            p.perdreVie(); // vie perdue lors de la capture
        }
    }

    public void mouvement_attaque(Player p) {
        if (!isCapture()) {
            // Descente normale
            setPosY(getPosY() - getVitesse());

            // Si on est au-dessus du joueur, déclenche capture
            if (getPosY() <= 0.6) {
                verifierCapture(p);
            }
        }
        else {
            // Moth reste au-dessus du joueur, descend légèrement
            if (p.getPosX() < getPosX()) {
                setPosX(getPosX() - getVitesse());
            }
            else if (p.getPosX() > getPosX()) {
                setPosX(getPosX() + getVitesse());
            }
            setPosY(getPosY() - getVitesse() * 0.1); // descend doucement
        }
    }


    public void update(boolean directionDroite, Player p) {

        if (isEnAttaque()) { // Les mouvements changent que si le monstre est l'un des premiers dans la formation 
            mouvement_attaque(p);
        }
        else if (directionDroite) {
            mouvementDroit();
        }
        else {
            mouvementGauche();
        }
    }
}
