package game.actors;

import engine.StdDraw;

public class Monster extends Entite {
    

     /**  Créé un joueur.  */

    public Monster(double x, double y, double length, int hp, int atk) {
        super(x, y, length, hp, atk);
    }

    /**
     * Dessine le monstre, pour différencier du joueur on fera un rond noir
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(x, y, length / 2);
    }

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update() {
        double speed = 0.01; // vitesse de déplacement du joueur
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) { 
            x -= speed;
        }
        // Si la flèche haut est préssé
        if (StdDraw.isKeyPressed(38)) {
            y += speed;
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            x += speed;
        }
        // Si la flèche bas est préssé
        if (StdDraw.isKeyPressed(40)) {
            y -= speed;
        }
    }

}
