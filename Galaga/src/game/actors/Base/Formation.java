package game.actors.Base;

import java.util.ArrayList;
import java.util.List;

public class Formation {
    private List<Monster> listeMonstres;
    private int nbrMonstres;
    private boolean directionDroite;
    private static final double VITESSE_FORMATION = 0.001; //pour uniformiser sinon les monstres se rentrent entre eux
    private List<Monster> listeMonstresHorsFormation;
    private List<Monster> tireurs; // Liste de ceux qui peuvent tirer
    private List<Missiles> listeMissilesEnnemis;

    public Formation(List<Monster> listeMonstres) {
        this.listeMonstres = listeMonstres;
        this.listeMonstresHorsFormation = new ArrayList<>();
        this.tireurs = new ArrayList<>();
        this.nbrMonstres = listeMonstres.size();
        this.directionDroite = true;
        this.listeMissilesEnnemis = new ArrayList<>();
        

        for (int i = 0 ; i < nbrMonstres ; i++) {
            listeMonstres.get(i).setDroite(directionDroite);
        }

        for (Monster m : listeMonstres) {

            m.setVitesse(VITESSE_FORMATION);
            if (m.isOneOfFirst(listeMonstres)){
                tireurs.add(m);
            }
        }

        for (Monster m : listeMonstresHorsFormation){
            tireurs.add(m);
        }
    }


    //////////////////////// GETTERS ET SETTERS //////////////////////////
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

    public static double getVitesseFormation() {
        return VITESSE_FORMATION;
    }

    public List<Monster> getListeMonstresHorsFormation() {
        return listeMonstresHorsFormation;
    }

    public List<Monster> getTireurs() {
        return tireurs;
    }

    public List<Missiles> getListeMissilesEnnemis() {
        return listeMissilesEnnemis;
    }

    public void setListeMonstresHorsFormation(List<Monster> listeMonstresHorsFormation) {
        this.listeMonstresHorsFormation = listeMonstresHorsFormation;
    }

    public void setTireurs(List<Monster> tireurs) {
        this.tireurs = tireurs;
    }

    public void setListeMissilesEnnemis(List<Missiles> listeMissilesEnnemis) {
        this.listeMissilesEnnemis = listeMissilesEnnemis;
    }
    //////////////////////////////////////////////////////////////////////////////////////////



    /** Dessine chaque monstre un par un */
    public void draw() {
        for (Monster m : listeMonstres) {
            m.draw();
        }
        for (Monster m : listeMonstresHorsFormation){
            m.draw();
        }
        for (Missiles mis : listeMissilesEnnemis) {
            mis.draw();
        }
    }

    public void update(Player p) {

        ////////////   Supprimer les monstres tués :   ////////////
        for (int i = 0 ; i < getListeMonstres().size() ; i++){
            if (getListeMonstres().get(i).isDead()){
                getListeMonstres().remove(i);
                break;
            }
        }
        for (int i = 0 ; i < getListeMonstresHorsFormation().size() ; i++){
            if (getListeMonstresHorsFormation().get(i).isDead()){
                getListeMonstresHorsFormation().remove(i);
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

        ///// Update les mouvements de chaque monstre :  //////
        for(Monster m : listeMonstres){
            m.update(isDirectionDroite(), p); // Done la nouvelle direction à tous les monstres 
        }

        ///// Vérifie quels monstres vont commencer une attaque aléatoirement : //////
        for (Monster m : listeMonstres){
            if (m.isOneOfFirst(listeMonstres) && !m.isEnAttaque()) {
                // Chance aléatoire de commencer une attaque
                if (Math.random() < 0.001) { // 0.1% de chance par frame
                    m.setEnAttaque(true);
                    listeMonstresHorsFormation.add(m);
                    listeMonstres.remove(m);
                    break;
                }
            }
        }

        ///// Update les mouvements des monstres hors formation /////
        for(Monster m : listeMonstresHorsFormation){
            m.update(isDirectionDroite(), p);
        }

        //// Tire aléatoirement parmis les monstres en première ligne et ceux hors formation : ////// 
        for (Monster m : tireurs){
            if (Math.random() < 0.005) {
                m.creeMissile(getListeMissilesEnnemis());
            }
        }

    }

    public void recommencer(){
        for (Monster m : getListeMonstres()){
            m.setPosX(m.getXinit());
            m.setPosY(m.getYinit());
        }
        for (Monster m : getListeMonstresHorsFormation()){
            m.setPosX(m.getXinit());
            m.setPosY(m.getYinit());
        }
    }

    
}