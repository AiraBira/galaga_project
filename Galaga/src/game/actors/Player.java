package game.actors;

import engine.StdDraw;

/**
 * Classe représentant le jouuer.
 * A ce stade cen'est qu'un point rouge qui se déplace avec les flèches du
 * clavier.
 */
public class Player extends Entite{
    

    /**  Créé un joueur.  */

    public Player(double x, double y, double length, int hp, int atk) {
        super(x, y, length, hp, atk);
    }

    /**
     * Dessine le joueur, ici c'est un rond rouge
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(x, y, length / 2);
    }

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update() {
        double speed = 0.01; // vitesse de déplacement du joueur
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) { 
            // mouvementGauche();
            x -= speed; // ici on pourra faire des méthodes venant d'une interface MOUVEMENTS dans laquelle on aura egalement les collisions pris en compte 
        }
        // Si la flèche haut est préssé
        if (StdDraw.isKeyPressed(38)) {
            //y += speed;
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            x += speed;
        }
        // Si la flèche bas est préssé
        if (StdDraw.isKeyPressed(40)) {
            //y -= speed;
        }
    }
}
