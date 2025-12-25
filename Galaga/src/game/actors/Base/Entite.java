package game.actors.Base;

import java.util.List;

public abstract class Entite extends Mouvements {
    protected int hp; // vie totale
    protected double Xinit;
    protected double Yinit;
    //si on veut plus tard :  protected int def; // points de défense de l'entité
    
    
    /**
     * @param x      postion de l'entité sur l'axe des abscisses
     * @param y      position de l'entité sur l'axe des ordonnées
     * @param length largeur de l'entité
     */

    public Entite(double x, double y, double length, double vitesse, int hp) { 
        super(x,y, vitesse, length);
        this.length = length;
        this.hp = hp;
        this.Xinit = x;
        this.Yinit = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getVitesse() {
        return vitesse;
    }

    public double getXinit() {
        return Xinit;
    }

    public double getYinit() {
        return Yinit;
    }
    
    
    /** Dessine de l'entité, elle sera implémenté dans ses classes filles plus tard. */
    public abstract void draw();


    /**  Reçoit des dégats et lui enlève de la vie. */
    public void degats(int degats){
        if (!isDead()){
            setHp(getHp() - degats);
            if (getHp() < 0) {
                setHp(0);
            }
        }
    }

    public boolean isDead(){ // vérifie si l'entité est morte
        return getHp() == 0;
    }

    public void tire(Entite cible){ // attaque une autre entité
        if (!cible.isDead()){
            cible.degats(1);
        }
    }

    
        
}
