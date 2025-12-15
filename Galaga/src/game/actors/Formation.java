package game.actors;

import java.util.ArrayList;
import java.util.List;

public class Formation {
    private List<Monster> listeMonstres;
    private int nbrMonstres;
    private final double length = 0.05;
    private boolean directionDroite = true;

    public Formation(int nbrMonstres, List<Pair<Double, Double>> tabPositions) {
        this.listeMonstres = new ArrayList<>();
        this.nbrMonstres = nbrMonstres;
        for (int i = 0; i < nbrMonstres; i++) {
            listeMonstres.add(new Monster(tabPositions.get(i).getKey(), tabPositions.get(i).getValue(), length, 1, 1, 0.01, directionDroite));
        }
    }


    public List<Monster> getListeMonstres() {
        return listeMonstres;
    }

    public void setListeMonstres(List<Monster> listeMonstres) {
        this.listeMonstres = listeMonstres;
    }

    public int getNbrMonstres() {
        return nbrMonstres;
    }

    public void setNbrMonstres(int nbrMonstres) {
        this.nbrMonstres = nbrMonstres;
    }

    public double getLength() {
        return length;
    }

    public boolean isDirectionDroite() {
        return directionDroite;
    }

    public void setDirectionDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }


    /** Dessine chaque monstre un par un */
    public void draw() {
        for (Monster m : listeMonstres) {
            m.draw();
        }
    }

    public void update() {
        
        // Vérifie la direction à prendre par l'ensemble de la formation.
        // Si l'un des monstres touche un mur, on change de direction.
        for(Monster m : listeMonstres){

            // mur gauche atteint par n'importe lequel des monstres : 
            if (m.getPosX() - m.getLength() / 2 <= 0.0) {
                setDirectionDroite(true);
                break;
            }

            // mur droit atteint par n'importe lequel des monstres : 
            else if (m.getPosX() + m.getLength() / 2 >= 1.0) {
                setDirectionDroite(false);
                break;
            }
        }

        // Puis on update les mouvements de chaque monstre : 
        for(Monster m : listeMonstres){
            m.update(isDirectionDroite()); // Done la nouvelle direction à tous les monstres 
        }

    }

    
}