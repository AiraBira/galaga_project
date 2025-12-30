package game.actors.Base;

import java.util.List;

import engine.StdDraw;

public class Monster extends Entite {

    boolean enAttaque;
    boolean directionDroite;
    protected int valeur;
    protected static final int HP = 1;
    protected double cooldownTirInit;
    protected double cooldownTir; // Le temps entre chaque tir est donné par la classe Formation

    /** Créé un joueur. */

    public Monster(double x, double y, double length, int valeur, double vitesse, String sprite,
            double cooldownTirInit) {

        super(x, y, length, vitesse, HP, sprite);
        this.directionDroite = directionDroite;
        this.valeur = valeur;
        this.cooldownTirInit = cooldownTirInit;
        this.cooldownTir = Math.random() * cooldownTirInit; // Décalage aléatoire pour éviter la synchronisation
    }

    public boolean isDirectionDroite() {
        return directionDroite;
    }

    public void setDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    public void creeMissile(List<Missiles> missilesMonstres) {
        if (cooldownTir >= cooldownTirInit) { // On incrémente le cooldown à chaque frame dans l'update de chaque
                                              // monstre indépendamment
            Missiles m = new Missiles(getPosX(), getPosY(), 0.025, false, false);
            missilesMonstres.add(m);
            cooldownTir = 0;
        }
    }

    /* Met à jour la position du joueur en fonction des touches préssé. */
    public void update(boolean direction, Player p, boolean modeInfini) {
    } // Utilisée pour le niveau 1

    public boolean isOneOfFirst(List<Monster> monstres) {

        for (Monster m : monstres) {
            if (m != this && m.getPosY() < getPosY()) {

                double gaucheMonstre = m.getPosX() - m.getLength() / 2;
                double droiteMonstre = m.getPosX() + m.getLength() / 2;

                if (droiteMonstre > (getPosX() - getLength() / 2) && gaucheMonstre < (getPosX() + getLength() / 2)) {
                    return false; // Bloqué par ce monstre
                }
            }
        }
        return true; // Libre
    }

    public boolean isGone() {
        return getPosY() + getLength() < 0.065;
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

    public void setDirectionDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public double getCooldownTirInit() {
        return cooldownTirInit;
    }

    public void setCooldownTirInit(double cooldownTirInit) {
        this.cooldownTirInit = cooldownTirInit;
    }

    public double getCooldownTir() {
        return cooldownTir;
    }

    public void setCooldownTir(double cooldownTir) {
        this.cooldownTir = cooldownTir;
    }

}
