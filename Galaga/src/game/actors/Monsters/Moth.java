package game.actors.Monsters;

import game.actors.Base.*;

public class Moth extends Monster {

    private boolean capture;
    private static final String SPRITE = loadSprite("../ressources/sprites/catcher.spr"); // On charge notre fichier .spr

    public Moth(double x, double y, double taille, int valeur, double vitesse, double cooldownTir) {
        super(x, y, taille, valeur, vitesse, SPRITE, cooldownTir);
        this.capture = false;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    // Vérifie si le Moth est au-dessus du joueur et déclenche la capture
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
}
