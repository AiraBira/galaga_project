package game.actors.Base;

import java.util.List;

import engine.StdDraw;

public class Monster extends Entite {

    boolean directionDroite;
    protected int valeur;
    protected static final int HP = 1;

    /** Créé un joueur. */

    public Monster(double x, double y, double length, int valeur, double vitesse) {
        
        super(x, y, length, vitesse, HP);
        this.directionDroite = directionDroite;
        this.valeur = valeur;
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

    public int getValeur() {
        return valeur;
    }

}
