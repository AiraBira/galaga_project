package game.actors;

import java.util.ArrayList;
import java.util.List;

import engine.StdDraw;

public class Formation {
    private List<Monster> listeMonstres;
    private int nbrMonstres;
    private final double length = 0.05;

    public Formation(int nbrMonstres, List<Pair<Double, Double>> tabPositions) {
        this.listeMonstres = new ArrayList<>();
        this.nbrMonstres = nbrMonstres;
        for (int i = 0; i < nbrMonstres; i++) {
            listeMonstres.add(new Monster(tabPositions.get(i).getKey(), tabPositions.get(i).getValue(), length, 1, 1));
        }
    }

    /** Dessine chaque monstre un par un */
    public void draw() {
        for (Monster m : listeMonstres) {
            m.draw();
        }
    }

    public void update() {
        double speed = 0.01; // vitesse de déplacement du joueur
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) {
            for (Monster m : listeMonstres) {
                m.setX(m.getX() - speed);
            }
        }
        // Si la flèche haut est préssé
        if (StdDraw.isKeyPressed(38)) {
            for (Monster m : listeMonstres) {
                m.setY(m.getY() + speed);
            }
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            for (Monster m : listeMonstres) {
                m.setX(m.getX() + speed);
            }
        }
        // Si la flèche bas est préssé
        if (StdDraw.isKeyPressed(40)) {
            for (Monster m : listeMonstres) {
                m.setY(m.getY() - speed);
            }
        }

    }

}