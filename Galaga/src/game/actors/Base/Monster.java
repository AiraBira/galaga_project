package game.actors.Base;

import java.util.List;

import engine.StdDraw;

public class Monster extends Entite {

    boolean enAttaque;
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

    public void creeMissile(List<Missiles> missilesMonstres) {
        Missiles m = new Missiles(getPosX(), getPosY(), 0.025, false, false);
        missilesMonstres.add(m);
    }

    /**
     * Dessine le monstre, pour différencier du joueur on fera un rond noir
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledCircle(getPosX(), getPosY(), getLength() / 2);
    }

    /* Met à jour la position du joueur en fonction des touches préssé. */
    public void update(boolean direction, Player p) {} // Utilisée pour le niveau 1 

    public boolean isOneOfFirst(List<Monster> monstres){

        for (Monster m : monstres){
            if (m != this && m.getPosY() < getPosY()) {

                double gaucheMonstre = m.getPosX() - m.getLength()/2;
                double droiteMonstre = m.getPosX() + m.getLength()/2;
                
                if (droiteMonstre > (getPosX() - getLength()/2) && gaucheMonstre < (getPosX() + getLength()/2)) {
                    return false; // Bloqué par ce monstre
                }
            }
        }
        return true; // Libre
    }

    public boolean isEnAttaque() {
        return enAttaque;
    }

    public void setEnAttaque(boolean enAttaque) {
        this.enAttaque = enAttaque;
    }

    public int getValeur() {
        return valeur;
    }

}
