package game.actors.base;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Formation} gère le comportement collectif des monstres ennemis.
 * Elle s'occupe du déplacement horizontal de la formation, de la gestion des attaques et des tirs
 * des monstres quittant la formation, des missiles ennemis.
 */
public class Formation {
    /** Liste des monstres actuellement dans la formation */
    private List<Monster> listeMonstres;

    /** Indique la direction actuelle de déplacement de la formation */
    private boolean directionDroite;

    /** Vitesse commune à tous les monstres de la formation */
    private double vitesse_formation; // pour uniformiser sinon les monstres se rentrent entre eux

    /** Liste des monstres qui ont quitté la formation pour attaquer */
    private List<Monster> listeMonstresHorsFormation;

    /** Liste des missiles tirés par les ennemis */
    private List<Missiles> listeMissilesEnnemis;

    /** Compteur de cooldown pour les attaques */
    private int cooldownAttaques;

    /** Compteur de cooldown pour les tirs */
    private int cooldownTirs;

    /** Valeur initiale du cooldown des attaques */
    private int cooldownAttaquesInit;

    /** Valeur initiale du cooldown des tirs */
    private int cooldownTirsInit;

    /** Probabilité qu’un monstre commence une attaque depuis la formation */
    private static final double CHANCE_ATTAQUE = 0.01; // Chance qu'un monstre en formation commence une attaque

    /** Probabilité qu’un monstre en formation tire un missile */
    private static final double CHANCE_TIR_FORMATION = 0.1; // Chance qu'un monstre en formation tire un missile

    /** Probabilité qu’un monstre hors formation tire un missile */
    private static final double CHANCE_TIR_HORS_FORMATION = 0.5; // Chance qu'un monstre hors formation tire un missile

    /**
     * Classe qui gère le comportement collectif des monstres ennemis.
     * Elle s'occupe du déplacement horizontal de la formation, de la gestion des attaques et des tirs
     * des monstres quittant la formation, des missiles ennemis.
     * 
     * @param listeMonstres liste des monstres composant la formation
     * @param vitesse_formation vitesse de déplacement horizontale de la formation
     * @param cooldownAttaquesInit délai entre chaque attaque de monstres (en frames
     * @param cooldownTirsInit délai entre chaque tir de monstres (en frames    
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

    //////////////////////// GETTERS ET SETTERS //////////////////////////
    
    /**
     * Récupère la liste des monstres dans la formation.
     * @return la liste des monstres dans la formation
     */
    public List<Monster> getListeMonstres() {
        return listeMonstres;
    }

    /**
     * Définit la liste des monstres dans la formation.
     * @param listeMonstres la liste des monstres à définir
     */
    public void setListeMonstres(List<Monster> listeMonstres) {
        this.listeMonstres = listeMonstres;
    }

    /**
     * Récupère la direction actuelle de déplacement de la formation.
     * @return true si la formation se déplace vers la droite, false sinon
     */
    public boolean isDirectionDroite() {
        return directionDroite;
    }

    /**
     * Définit la direction de déplacement de la formation.
     * @param directionDroite true pour déplacer vers la droite, false pour gauche
     */
    public void setDirectionDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    /**
     * Vérifie si le niveau est terminé (tous les monstres éliminés).
     * @return true si le niveau est terminé, false sinon
     */
    public boolean niveauTermine() {
        return listeMonstres.size() == 0 && listeMonstresHorsFormation.size() == 0;
    }

    /**
     * Récupère la vitesse de déplacement de la formation.
     * @return la vitesse de la formation
     */
    public double getVitesseFormation() {
        return vitesse_formation;
    }

    /**
     * Récupère la liste des monstres hors formation.
     * @return la liste des monstres hors formation
     */
    public List<Monster> getListeMonstresHorsFormation() {
        return listeMonstresHorsFormation;
    }

    /**
     * Récupère la liste des missiles ennemis.
     * @return la liste des missiles ennemis
     */
    public List<Missiles> getListeMissilesEnnemis() {
        return listeMissilesEnnemis;
    }

    /**
     * Définit la liste des monstres hors formation.
     * @param listeMonstresHorsFormation la liste des monstres hors formation à définir
     */
    public void setListeMonstresHorsFormation(List<Monster> listeMonstresHorsFormation) {
        this.listeMonstresHorsFormation = listeMonstresHorsFormation;
    }

    /**
     * Définit la liste des missiles ennemis.
     * @param listeMissilesEnnemis la liste des missiles ennemis à définir
     */
    public void setListeMissilesEnnemis(List<Missiles> listeMissilesEnnemis) {
        this.listeMissilesEnnemis = listeMissilesEnnemis;
    }

    /**
     * Définit la vitesse de déplacement de la formation.
     * @param vitesse_formation la vitesse de la formation à définir
     */
    public void setVitesse_formation(double vitesse_formation) {
        this.vitesse_formation = vitesse_formation;
    }

    /**
     * Récupère le compteur de cooldown des attaques.
     * @return le compteur de cooldown des attaques
     */
    public int getCooldownAttaques() {
        return cooldownAttaques;
    }

    /**
     * Définit le compteur de cooldown des attaques.
     * @param cooldownAttaques le compteur de cooldown des attaques à définir
     */
    public void setCooldownAttaques(int cooldownAttaques) {
        this.cooldownAttaques = cooldownAttaques;
    }

    /**
     * Récupère le compteur de cooldown des tirs.
     * @return le compteur de cooldown des tirs
     */
    public int getCooldownTirs() {
        return cooldownTirs;
    }

    /**
     * Définit le compteur de cooldown des tirs.
     * @param cooldownTirs le compteur de cooldown des tirs à définir
     */
    public void setCooldownTirs(int cooldownTirs) {
        this.cooldownTirs = cooldownTirs;
    }

    /**
     * Récupère la valeur initiale du cooldown des attaques.
     * @return la valeur initiale du cooldown des attaques
     */
    public int getCooldownAttaquesInit() {
        return cooldownAttaquesInit;
    }

    /**
     * Récupère la valeur initiale du cooldown des tirs.
     * @return la valeur initiale du cooldown des tirs
     */
    public int getCooldownTirsInit() {
        return cooldownTirsInit;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    
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
     * @param p joueur courant
     * @param modeInfini indique si le mode infini est activé
     */
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
        // On réinitialise les positions de chaque monstre.
        for (Monster m : getListeMonstres()) {
            m.setPosX(m.getXinit());
            m.setPosY(m.getYinit());
        }

    }

}