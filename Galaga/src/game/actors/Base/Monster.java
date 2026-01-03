package game.actors.base;
import java.util.List;


/**
 * La classe {@code Monster} représente un monstre ennemi dans le jeu.
 * Un monstre possède une position, une vitesse, des points de vie, une valeur en points,
 * une direction de déplacement, et peut tirer des missiles.
 */
public class Monster extends Entite {

    /** Indique si le monstre est actuellement en attaque hors de la formation */
    protected boolean enAttaque; 

    /** Indique la direction actuelle de déplacement du monstre */
    protected boolean directionDroite;

    /** Valeur en points du monstre lorsqu'il est détruit */
    protected int valeur;

    /** Points de vie du monstre */
    protected static final int HP = 1;

    /** Délai initialisé entre chaque tir du monstre */
    protected double cooldownTirInit; // Délai initial entre chaque tir
    /** Délai actuel entre chaque tir du monstre */
    protected double cooldownTir; // Le temps entre chaque tir est donné par la classe Formation

    /**
     * Construit un monstre avec ses caractéristiques principales.
     *
     * @param x        position initiale sur l'axe des abscisses
     * @param y        position initiale sur l'axe des ordonnées
     * @param length   largeur du monstre
     * @param valeur   valeur en points du monstre
     * @param vitesse  vitesse de déplacement du monstre
     * @param sprite   sprite du monstre sous forme de texte
     * @param cooldownTirInit délai initial entre chaque tir du monstre
     */
    public Monster(double x, double y, double length, int valeur, double vitesse, String sprite,
            double cooldownTirInit) {

        super(x, y, length, vitesse, HP, sprite);
        this.valeur = valeur;
        this.cooldownTirInit = cooldownTirInit;
        this.cooldownTir = Math.random() * cooldownTirInit; // Décalage aléatoire pour éviter la synchronisation
    }

    /**
     * Crée un missile tiré par le monstre et l'ajoute à la liste des missiles ennemis.
     *
     * @param missilesMonstres liste des missiles tirés par les monstres
     */
    public void creeMissile(List<Missiles> missilesMonstres) {
        if (cooldownTir >= cooldownTirInit) { // On incrémente le cooldown à chaque frame dans l'update de chaque
                                              // monstre indépendamment
            Missiles m = new Missiles(getPosX(), getPosY(), 0.025, false, false);
            missilesMonstres.add(m);
            cooldownTir = 0;
        }
    }

    /**
     * Met à jour l'état du monstre.
     * Cette méthode est vide dans la classe de base et doit être implémentée
     * dans les sous-classes spécifiques de monstres.
     *
     * @param direction      direction actuelle de la formation (true pour droite, false pour gauche)
     * @param p              joueur actuel
     * @param modeInfini     indique si le mode infini est activé
     */
    public void update(boolean direction, Player p, boolean modeInfini) {
    }

    /**
     * Vérifie si le monstre est l'un des premiers dans la formations.
     *
     * @param monstres liste des monstres dans la formation
     * @return true si le monstre est libre de tirer, false sinon
     */
    public boolean isOneOfFirst(List<Monster> monstres) {

        for (Monster m : monstres) {
            if (m != this && m.getPosY() < getPosY()) {

                double gaucheMonstre = m.getPosX() - m.getLength() / 2;
                double droiteMonstre = m.getPosX() + m.getLength() / 2;

                if (droiteMonstre > (getPosX() - getLength() / 2) && gaucheMonstre < (getPosX() + getLength() / 2)) {
                    return false; // Bloqué par ce monstre
                }
            }
        }
        return true;
    }

    /**
     * Vérifie si le monstre est sorti de l'écran (au-dessus).
     *
     * @return true si le monstre est hors de l'écran, false sinon
     */
    public boolean isGone() {
        return getPosY() + getLength() < 0.065;
    }

    /* ------ GETTERS ET SETTERS ------ */

    /**
     * Récupère la direction actuelle de déplacement du monstre.
     * @return true si le monstre se déplace vers la droite, false sinon
     */
    public boolean isDirectionDroite() {
        return directionDroite;
    }

    /**
     * Définit la direction de déplacement du monstre.
     * @param directionDroite true pour déplacer vers la droite, false pour gauche
     */
    public void setDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    /**
     * Indique si le monstre est actuellement en attaque hors de la formation.
     * @return true si le monstre est en attaque, false sinon
     */
    public boolean isEnAttaque() {
        return enAttaque;
    }

    /**
     * Définit si le monstre est en attaque hors de la formation.
     * @param enAttaque true pour indiquer que le monstre est en attaque, false sinon
     */
    public void setEnAttaque(boolean enAttaque) {
        this.enAttaque = enAttaque;
    }

    /**
     * Récupère la valeur en points du monstre.
     * @return la valeur en points du monstre
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Définit la direction de déplacement du monstre.
     * @param directionDroite true pour déplacer vers la droite, false pour gauche
     */
    public void setDirectionDroite(boolean directionDroite) {
        this.directionDroite = directionDroite;
    }

    /**
     * Définit la valeur en points du monstre.
     * @param valeur la valeur en points à définir
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /**
     * Récupère le délai initial entre chaque tir du monstre.
     * @return le délai initial entre chaque tir
     */
    public double getCooldownTirInit() {
        return cooldownTirInit;
    }

    /**
     * Définit le délai initial entre chaque tir du monstre.
     * @param cooldownTirInit le délai initial à définir
     */
    public void setCooldownTirInit(double cooldownTirInit) {
        this.cooldownTirInit = cooldownTirInit;
    }

    /**
     * Récupère le délai actuel entre chaque tir du monstre.
     * @return le délai actuel entre chaque tir
     */
    public double getCooldownTir() {
        return cooldownTir;
    }

    /**
     * Définit le délai actuel entre chaque tir du monstre.
     * @param cooldownTir le délai actuel à définir
     */
    public void setCooldownTir(double cooldownTir) {
        this.cooldownTir = cooldownTir;
    }

}
