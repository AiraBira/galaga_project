package game.actors.Base;

import java.util.ArrayList;
import java.util.List;

public class Formation {
    private List<Monster> listeMonstres;
    private int nbrMonstres;
    private boolean directionDroite;

    public Formation(List<Monster> listeMonstres) {
        this.listeMonstres = listeMonstres;
        this.nbrMonstres = listeMonstres.size();
        this.directionDroite = true;

        for (int i = 0 ; i < nbrMonstres ; i++) {
            listeMonstres.get(i).setDroite(directionDroite);
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

    public boolean isDirectionDroite() {
        return directionDroite;
    }

    public void setDirectionDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    public boolean niveauTermine(){
        return listeMonstres.size() == 0;
    }


    /** Dessine chaque monstre un par un */
    public void draw() {
        for (Monster m : listeMonstres) {
            m.draw();
        }
    }

    public void update() {

        for (int i = 0 ; i < listeMonstres.size() ; i++){
            if (listeMonstres.get(i).isDead()){
                listeMonstres.remove(i);
                break;
            }
        }

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