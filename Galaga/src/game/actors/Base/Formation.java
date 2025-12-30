package game.actors.Base;

import game.actors.Monsters.*;

import java.util.ArrayList;
import java.util.List;

public class Formation {
    private List<Monster> listeMonstres;
    private boolean directionDroite;
    private double vitesse_formation; // pour uniformiser sinon les monstres se rentrent entre eux

    private List<Monster> listeMonstresHorsFormation;
    private List<Missiles> listeMissilesEnnemis;

    private int cooldownAttaques;
    private int cooldownTirs;
    private int cooldownAttaquesInit;
    private int cooldownTirsInit;

    private static final double CHANCE_ATTAQUE = 0.01; // Chance qu'un monstre en formation commence une attaque
    private static final double CHANCE_TIR_FORMATION = 0.1; // Chance qu'un monstre en formation tire un missile
    private static final double CHANCE_TIR_HORS_FORMATION = 0.5; // Chance qu'un monstre hors formation tire un missile

    public Formation(List<Monster> listeMonstres, double vitesse_formation, int cooldownAttaquesInit,
            int cooldownTirsInit) {
        this.listeMonstres = listeMonstres;
        this.listeMonstresHorsFormation = new ArrayList<>();
        this.directionDroite = true;
        this.listeMissilesEnnemis = new ArrayList<>();
        this.vitesse_formation = vitesse_formation;
        this.cooldownAttaques = 0;
        this.cooldownTirs = 0;
        this.cooldownTirsInit = cooldownTirsInit / 30;
        if (cooldownAttaquesInit == -1) { // On divise par les 30 fps pour avoir le cooldown en secondes uniquement si
                                          // différent de -1
            this.cooldownAttaquesInit = -1;
        } else {
            this.cooldownAttaquesInit = cooldownAttaquesInit / 30;
        }

        for (int i = 0; i < listeMonstres.size(); i++) {
            listeMonstres.get(i).setDroite(directionDroite);
        }

        for (Monster m : listeMonstres) {
            m.setVitesse(vitesse_formation);
        }
    }

    //////////////////////// GETTERS ET SETTERS //////////////////////////
    public List<Monster> getListeMonstres() {
        return listeMonstres;
    }

    public void setListeMonstres(List<Monster> listeMonstres) {
        this.listeMonstres = listeMonstres;
    }

    public boolean isDirectionDroite() {
        return directionDroite;
    }

    public void setDirectionDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    public boolean niveauTermine() {
        return listeMonstres.size() == 0 && listeMonstresHorsFormation.size() == 0;
    }

    public double getVitesseFormation() {
        return vitesse_formation;
    }

    public List<Monster> getListeMonstresHorsFormation() {
        return listeMonstresHorsFormation;
    }

    public List<Missiles> getListeMissilesEnnemis() {
        return listeMissilesEnnemis;
    }

    public void setListeMonstresHorsFormation(List<Monster> listeMonstresHorsFormation) {
        this.listeMonstresHorsFormation = listeMonstresHorsFormation;
    }

    public void setListeMissilesEnnemis(List<Missiles> listeMissilesEnnemis) {
        this.listeMissilesEnnemis = listeMissilesEnnemis;
    }

    public void setVitesse_formation(double vitesse_formation) {
        this.vitesse_formation = vitesse_formation;
    }

    public int getCooldownAttaques() {
        return cooldownAttaques;
    }

    public void setCooldownAttaques(int cooldownAttaques) {
        this.cooldownAttaques = cooldownAttaques;
    }

    public int getCooldownTirs() {
        return cooldownTirs;
    }

    public void setCooldownTirs(int cooldownTirs) {
        this.cooldownTirs = cooldownTirs;
    }

    public int getCooldownAttaquesInit() {
        return cooldownAttaquesInit;
    }

    public int getCooldownTirsInit() {
        return cooldownTirsInit;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    /** Dessine chaque monstre un par un */
    public void draw() {
        for (Monster m : listeMonstres) {
            m.draw();
        }
        for (Monster m : listeMonstresHorsFormation) {
            m.draw();
        }
        for (Missiles mis : listeMissilesEnnemis) {
            mis.draw();
        }
    }

    public void update(Player p, boolean modeInfini) {

        //////////// Supprimer les monstres tués : ////////////

        for (int i = getListeMonstres().size() - 1; i >= 0; i--) {
            if (getListeMonstres().get(i).isDead() || getListeMonstres().get(i).isGone()) {
                getListeMonstres().remove(i);
            }
        }
        for (int i = getListeMonstresHorsFormation().size() - 1; i >= 0; i--) {
            if (getListeMonstresHorsFormation().get(i).isDead() || getListeMonstresHorsFormation().get(i).isGone()) {
                getListeMonstresHorsFormation().remove(i);
            }
        }

        // Vérifie la direction à prendre par l'ensemble de la formation.
        // Si l'un des monstres touche un mur, on change de direction.
        for (Monster m : listeMonstres) {

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

        ///// Update les mouvements de chaque monstre : //////
        for (Monster m : listeMonstres) {
            m.update(isDirectionDroite(), p, modeInfini); // Done la nouvelle direction à tous les monstres
        }

        ///// Gère les attaques des monstres /////
        setCooldownAttaques(getCooldownAttaques() + 1);
        if (getCooldownAttaquesInit() != -1) { //
            if (getCooldownAttaques() >= getCooldownAttaquesInit()) { //
                ///// Vérifie quels monstres vont commencer une attaque aléatoirement : //////

                for (int i = listeMonstres.size() - 1; i >= 0; i--) {
                    if (listeMonstres.get(i).isOneOfFirst(listeMonstres) && !listeMonstres.get(i).isEnAttaque()) {
                        // Chance aléatoire de commencer une attaque
                        if (Math.random() < CHANCE_ATTAQUE) {
                            Monster m = listeMonstres.get(i);
                            m.setEnAttaque(true);
                            listeMonstresHorsFormation.add(m);
                            listeMonstres.remove(i);
                        }
                    }
                }
                setCooldownAttaques(0);
            }
        }

        ///// Update les mouvements des monstres hors formation /////
        for (Monster m : listeMonstresHorsFormation) {
            m.update(isDirectionDroite(), p, modeInfini);
        }

        // Tireurs en formation (première ligne uniquement)
        for (Monster m : listeMonstres) {
            if (m.isOneOfFirst(listeMonstres) && Math.random() < CHANCE_TIR_FORMATION) {
                m.creeMissile(listeMissilesEnnemis);
            }
        }

        // Tireurs hors formation
        for (Monster m : listeMonstresHorsFormation) {
            if (Math.random() < CHANCE_TIR_HORS_FORMATION) {
                m.creeMissile(listeMissilesEnnemis);
            }
        }
    }

    public void recommencer() {
        // On remet tous les monstres hors formation dans la formation
        for (int i = getListeMonstresHorsFormation().size() - 1; i >= 0; i--) {
            getListeMonstresHorsFormation().get(i).setEnAttaque(false);
            getListeMonstres().add(getListeMonstresHorsFormation().get(i));
            getListeMonstresHorsFormation().remove(getListeMonstresHorsFormation().get(i));

        }
        // On réinitialise les positions de chaque monstre.
        for (Monster m : getListeMonstres()) {
            m.setPosX(m.getXinit());
            m.setPosY(m.getYinit());
        }

    }

}