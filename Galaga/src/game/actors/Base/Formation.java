package game.actors.base;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Formation} gère le comportement collectif des monstres ennemis.
 * Elle s'occupe du déplacement horizontal de la formation, de la gestion des attaques et des tirs
 * des monstres quittant la formation, des missiles ennemis.
 */
public class Formation {
    /** Liste des monstres ds la formation */
    private List<Monster> listeMonstres;

    /** Direction de déplacement de la formation */
    private boolean directionDroite;

    /** Vitesse commune */
    private double vitesse_formation; // pour uniformiser sinon les monstres se rentrent entre eux

    /** Liste des monstres qui sont sortis de la formation pour attaquer */
    private List<Monster> listeMonstresHorsFormation;

    /** Liste des missiles tirés par les ennemis */
    private List<Missiles> listeMissilesEnnemis;

    /** Compteur de cooldown attaques */
    private int cooldownAttaques;

    /** Compteur de cooldown tirs */
    private int cooldownTirs;

    /** Valeur init du cooldown attaques */
    private int cooldownAttaquesInit;

    /** Valeur init du cooldown tirs */
    private int cooldownTirsInit;

    /** Probabilité qu’un monstre attaque*/
    private static final double CHANCE_ATTAQUE = 0.01;

    /** Probabilité qu’un monstre tire */
    private static final double CHANCE_TIR_FORMATION = 0.1; 

    /** Probabilité qu’un monstre libre tire */
    private static final double CHANCE_TIR_HORS_FORMATION = 0.5; 

    /**
     * Classe qui gère le comportement collectif des monstres ennemis.
     * 
     * @param listeMonstres liste des monstres 
     * @param vitesse_formation vitesse
     * @param cooldownAttaquesInit délai entre chaque attaque 
     * @param cooldownTirsInit délai entre tirs   
     * */
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
    
    
    /**
     * Dessine la formation et les monstres.
     */
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

    /**
     * Met à jour l'état de la formation et des monstres.
     *
     * @param p joueur 
     * @param modeInfini indique si le mode infini est activé
     */
    public void update(Player p, boolean modeInfini) {

        // Supprimer les monstres tués : 

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

        //Update les mouvements de chaque monstre : 
        for (Monster m : listeMonstres) {
            m.update(isDirectionDroite(), p, modeInfini); // Done la nouvelle direction à tous les monstres
        }

        //Gère les attaques des monstres 
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

        //Update les mouvements des monstres hors formation
        for (Monster m : listeMonstresHorsFormation) {
            m.update(isDirectionDroite(), p, modeInfini);
        }

        // Tireurs en formation (en première ligne uniquement)
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

    /**
     * Réinitialise la formation après une défaite ou un redémarrage du niveau.
     */
    public void recommencer() {
        // On remet tous les monstres hors formation dans la formation
        for (int i = getListeMonstresHorsFormation().size() - 1; i >= 0; i--) {
            getListeMonstresHorsFormation().get(i).setEnAttaque(false);
            getListeMonstres().add(getListeMonstresHorsFormation().get(i));
            getListeMonstresHorsFormation().remove(getListeMonstresHorsFormation().get(i));

        }
        // réinitialise les positions de chaque monstre.
        for (Monster m : getListeMonstres()) {
            m.setPosX(m.getXinit());
            m.setPosY(m.getYinit());
        }

    }


    /* ------ GETTERS ET SETTERS ------ */
    /**
     * Renvoi la liste de monstres ds la formation
     * @return la liste des monstres
     */
    public List<Monster> getListeMonstres() {
        return listeMonstres;
    }

    /**
     * Modifie la liste des monstres dans la formation.
     * @param listeMonstres la liste de monstres
     */
    public void setListeMonstres(List<Monster> listeMonstres) {
        this.listeMonstres = listeMonstres;
    }

    /**
     * Renvoi la direction de la formation
     * @return true on se déplace vers la droite, false sinon
     */
    public boolean isDirectionDroite() {
        return directionDroite;
    }

    /**
     * Modifié la direction.
     * @param directionDroite true vaut droite , false = gauche
     */
    public void setDirectionDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    /**
     * Vérifie si le niveau est terminé.
     * @return true = terminé, false sinon
     */
    public boolean niveauTermine() {
        return listeMonstres.size() == 0 && listeMonstresHorsFormation.size() == 0;
    }

    /**
     * Renvoi la vitesse 
     * @return la vitesse 
     */
    public double getVitesseFormation() {
        return vitesse_formation;
    }

    /**
     * Renvoi la liste des monstres hors formation
     * @return la liste des monstres hors formation
     */
    public List<Monster> getListeMonstresHorsFormation() {
        return listeMonstresHorsFormation;
    }

    /**
     * Renvoi liste des missiles ennemis
     * @return la liste des missiles ennemis
     */
    public List<Missiles> getListeMissilesEnnemis() {
        return listeMissilesEnnemis;
    }

    /**
     * Modifie la liste des monstres hors formation
     * @param listeMonstresHorsFormation liste des monstres
     */
    public void setListeMonstresHorsFormation(List<Monster> listeMonstresHorsFormation) {
        this.listeMonstresHorsFormation = listeMonstresHorsFormation;
    }

    /**
     * Modifie la liste des missiles ennemi
     * @param listeMissilesEnnemis la liste des missiles
     */
    public void setListeMissilesEnnemis(List<Missiles> listeMissilesEnnemis) {
        this.listeMissilesEnnemis = listeMissilesEnnemis;
    }

    /**
     * Modifie la vitesse de formation
     * @param vitesse_formation la vitesse
     */
    public void setVitesse_formation(double vitesse_formation) {
        this.vitesse_formation = vitesse_formation;
    }

    /**
     * Renvoi le cooldown attaques.
     * @return le cooldown attaques
     */
    public int getCooldownAttaques() {
        return cooldownAttaques;
    }

    /**
     * Modifie le cooldown attaques.
     * @param cooldownAttaques le cooldown attaques
     */
    public void setCooldownAttaques(int cooldownAttaques) {
        this.cooldownAttaques = cooldownAttaques;
    }

    /**
     * Renvoi  cooldown tirs.
     * @return le cooldown tirs
     */
    public int getCooldownTirs() {
        return cooldownTirs;
    }

    /**
     * Modifie le cooldown  tirs.
     * @param cooldownTirs cooldown des tirs 
     */
    public void setCooldownTirs(int cooldownTirs) {
        this.cooldownTirs = cooldownTirs;
    }

    /**
     * Renvoi la valeur init du cooldown attaques.
     * @return la valeur init du cooldown attaques
     */
    public int getCooldownAttaquesInit() {
        return cooldownAttaquesInit;
    }

    /**
     * Renvoi la valeur init du cooldown tirs.
     * @return la valeur init du cooldown tirs
     */
    public int getCooldownTirsInit() {
        return cooldownTirsInit;
    }

}