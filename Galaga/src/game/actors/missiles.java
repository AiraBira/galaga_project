package game.actors;

import engine.StdDraw;

public class Missiles extends Mouvements {

    boolean touche;

    public Missiles(double posX, double posY, double vitesse, boolean touche) { // L'argument touche permet de faire
                                                                                // disparaitre le missile s'il touche
                                                                                // qqch
        super(posX, posY, vitesse, 0.005);
        this.touche = touche;
    }

    public boolean isTouche() {
        return touche;
    }

    public void setTouche(boolean touche) {
        this.touche = touche;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(getPosX(), getPosY(), getLength(), 0.05);
    }

    public void update() {
        mouvementHaut();
    }

}