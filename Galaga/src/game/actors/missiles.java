package game.actors;

import engine.StdDraw;

public class Missiles extends Mouvements {

    boolean touche;
    boolean mouvHaut;

    public Missiles(double posX, double posY, double vitesse, boolean touche, boolean mouvHaut) { // L'argument touche permet de faire
                                                                                // disparaitre le missile s'il touche
                                                                                // qqch
        super(posX, posY, vitesse, 0.005);
        this.touche = touche;
        this.mouvHaut = mouvHaut;
    }

    public boolean isTouche() {
        return touche;
    }

    public void setTouche(boolean touche) {
        this.touche = touche;
    }

    public boolean isMouvHaut() {
        return mouvHaut;
    }

    public void setMouvHaut(boolean mouvHaut) {
        this.mouvHaut = mouvHaut;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(getPosX(), getPosY(), getLength(), 0.05);
    }

    @Override
    public void mouvementHaut(){
        setPosY(getPosY() + getVitesse());
    }

    @Override
    public void mouvementBas(){
        setPosY(getPosY() - getVitesse());
    }

    public void update() {
        if (isMouvHaut()){
            mouvementHaut();
        }
        else {
            mouvementBas();
        }
        
    }

    

}