package game.actors;

import java.util.List;

public abstract class Entite extends Mouvements {
    protected int hp; // vie totale
    protected int atk; // points d'attaque de l'entité 7
    protected double vitesse;
    //si on veut plus tard :  protected int def; // points de défense de l'entité
    
    
    /**
     * @param x      postion de l'entité sur l'axe des abscisses
     * @param y      position de l'entité sur l'axe des ordonnées
     * @param length largeur de l'entité
     */

    public Entite(double x, double y, double length, int hp, int atk, double vitesse) { 
        super(x,y, vitesse, length);
        this.length = length;
        this.hp = hp;
        this.atk = atk;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
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

    public void autoSuppression(){} // à utiliser lorsque l'entité est morte et que l'on veut qu'elle ne s'affiche plus (et donc la supprimer)

    public void tire(Entite cible){ // attaque une autre entité
        if (!cible.isDead()){
            cible.degats(this.getAtk());
        }
    }

    public abstract void creeMissile(List<Missiles> missilesDispo);

    
        
}
