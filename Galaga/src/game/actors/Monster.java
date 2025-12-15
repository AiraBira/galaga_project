package game.actors;

import engine.StdDraw;

public class Monster extends Entite {
    

     /**  Créé un joueur.  */

    public Monster(double x, double y, double length, int hp, int atk, double vitesse) {
        super(x, y, length, hp, atk, vitesse);
    }

    /**
     * Dessine le monstre, pour différencier du joueur on fera un rond noir
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(getPosX(), getPosY(), getLength() / 2);
    }

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update() {
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) { 
            mouvementGauche();
        }
        // Si la flèche haut est préssé
        if (StdDraw.isKeyPressed(38)) {
            mouvementHaut();
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            mouvementDroit();
        }
        // Si la flèche bas est préssé
        if (StdDraw.isKeyPressed(40)) {
            mouvementBas();
        }

    }

}
