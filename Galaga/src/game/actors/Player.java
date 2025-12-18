package game.actors;

import java.util.List;

import engine.StdDraw;

/**
 * Classe représentant le jouuer.
 * A ce stade cen'est qu'un point rouge qui se déplace avec les flèches du
 * clavier.
 */
public class Player extends Entite {

    int countdownSpacePressed; // Sert a savoir si la touche espace etait appuyée avant

    /** Créé un joueur. */

    public Player(double x, double y, double length, int hp, int atk, double vitesse) {
        super(x, y, length, hp, atk, vitesse);
        countdownSpacePressed = 0;
    }

    public int getCountdownSpacePressed() {
        return countdownSpacePressed;
    }

    public void setCountdownSpacePressed(int countdownSpacePressed) {
        this.countdownSpacePressed = countdownSpacePressed;
    }

    /**
     * Dessine le joueur, ici c'est un rond rouge
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(getPosX(), getPosY(), getLength() / 2);
    }

    public void creeMissile(List<Missiles> missilesDispo) {
        if (missilesDispo.size() < 3) { // Crée un nouveau missile uniquement s'il y a de la place dans l'écran.
            Missiles m = new Missiles(getPosX(), getPosY()+(getLength()/2), 0.04, false, true);
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

        if (countdownSpacePressed > 0.0) { // Création d'un coountdown entre chaque tir par le joueur. On lance un tir par ms.
                                           // Cela empêche que les tirs se collent entre eux.
            setCountdownSpacePressed(getCountdownSpacePressed()-1);
        }

        if (StdDraw.isKeyPressed(32) && (countdownSpacePressed == 0.0)) {
            creeMissile(missilesDispo);
            setCountdownSpacePressed(3); // On remet le compteur à 3 après avoir crée notre missile.
        } 
    }

}
