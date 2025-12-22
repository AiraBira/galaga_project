package game.actors.Base;

import java.util.List;

import engine.StdDraw;

public class Monster extends Entite {

    boolean directionDroite;

    /** Créé un joueur. */

    public Monster(double x, double y, double length, int hp, int atk, double vitesse, boolean directionDroite) {
        super(x, y, length, hp, atk, vitesse);
        this.directionDroite = directionDroite;
    }

    public boolean isDirectionDroite() {
        return directionDroite;
    }

    public void setDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    public void creeMissile(List<Missiles> missilesDispo) {
        Missiles m = new Missiles(getPosX(), getPosY(), 0.05, false, false);
        missilesDispo.add(m);
    }

    /**
     * Dessine le monstre, pour différencier du joueur on fera un rond noir
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledCircle(getPosX(), getPosY(), getLength() / 2);
    }

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update(boolean directionDroite) {

        if (directionDroite){
            mouvementDroit();
        }
        else {
            mouvementGauche();
        }

    }

}
