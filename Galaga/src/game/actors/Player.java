package game.actors;

import java.util.List;

import engine.StdDraw;

/**
 * Classe représentant le jouuer.
 * A ce stade cen'est qu'un point rouge qui se déplace avec les flèches du
 * clavier.
 */
public class Player extends Entite {

    boolean espaceWasPressed; // Sert a savoir si la touche espace etait appuyée avant

    /** Créé un joueur. */

    public Player(double x, double y, double length, int hp, int atk, double vitesse) {
        super(x, y, length, hp, atk, vitesse);
        espaceWasPressed = false;
    }

    public boolean wasEspacePressed() {
        return espaceWasPressed;
    }

    public void setEspaceWasPressed(boolean espaceWasPressed) {
        this.espaceWasPressed = espaceWasPressed;
    }

    /**
     * Dessine le joueur, ici c'est un rond rouge
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(getPosX(), getPosY(), getLength() / 2);
    }

    public void creeMissile(List<Missiles> missilesDispo) {
        if (missilesDispo.size() < 3) {
            Missiles m = new Missiles(getPosX(), getPosY(), 0.15, false);
            missilesDispo.add(m);
        }
    }

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update(List<Missiles> missilesDispo) {
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) {
            mouvementGauche();
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            mouvementDroit();
        }

        if (StdDraw.isKeyPressed(32) && !wasEspacePressed()) {
            creeMissile(missilesDispo);
            setEspaceWasPressed(true);
        } else {
            setEspaceWasPressed(false);
        }
    }

}
