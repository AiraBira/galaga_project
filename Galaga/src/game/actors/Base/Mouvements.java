package game.actors.base;

/**
 * La classe abstraite {@code Mouvements} définit les comportements de base pour les entités mobiles dans le jeu.
 * Elle fournit des méthodes pour déplacer les entités dans différentes directions.
 */
public abstract class Mouvements {

    /** Position X de l'entité */
    protected double posX;

    /** Position Y de l'entité */
    protected double posY;

    /** Longueur ou taille de l'entité */
    protected double length;

    /** Vitesse de déplacement de l'entité */
    protected double vitesse;

    /** Dimensions supplémentaires de l'entité (utilisé pour la forme rectangulaire des missiles)*/
    protected double longueur;

    /** Dimensions supplémentaires de l'entité (utilisé pour la forme rectangulaire des missiles)*/
    protected double largeur;

    /**
     * Constructeur de base.
     *
     * @param posX position initiale sur l'axe des abscisses
     * @param posY position initiale sur l'axe des ordonnées
     * @param vitesse vitesse de déplacement
     * @param length longueur ou taille de l'entité
     */
    public Mouvements(double posX, double posY, double vitesse, double length) {
        this.posX = posX;
        this.posY = posY;
        this.vitesse = vitesse;
        this.length = length;
    }

    /**
     * Constructeur avec dimensions supplémentaires.
     *
     * @param posX position initiale sur l'axe des abscisses
     * @param posY position initiale sur l'axe des ordonnées
     * @param vitesse vitesse de déplacement
     * @param longueur longueur de l'entité
     * @param largeur largeur de l'entité
     */
    public Mouvements(double posX, double posY, double vitesse, double longueur, double largeur) {
        this.posX = posX;
        this.posY = posY;
        this.vitesse = vitesse;
        this.longueur = longueur;
        this.largeur = largeur;
    }

    /**
     * Déplace l'entité vers la droite.
     */
    public void mouvementDroit() {
        setPosX(getPosX() + getVitesse());

        if (getPosX()-(getLength()/2) <= 0.0) { // Ne peuvent pas dépasser les limites.
            setPosX(0+(getLength()/2));
        } else if (getPosX()+(getLength()/2) >= 1.0) {
            setPosX(1.0-(getLength()/2));
        }

    }

    /**
     * Déplace l'entité vers la gauche.
     */
    public void mouvementGauche() {
        setPosX(getPosX() - getVitesse());

        if (getPosX()-(getLength()/2) <= 0.0) {
            setPosX(0+(getLength()/2));
        } else if (getPosX()+(getLength()/2) >= 1.0) {
            setPosX(1.0-(getLength()/2));
        }

    }

    /**
     * Déplace l'entité vers le haut.
     */
    public void mouvementHaut() {
        setPosY(getPosY() + getVitesse());

        if (getPosY()-(getLength()/2) <= 0.0) {
            setPosY(0+(getLength()/2));
        }
        else if (getPosY()+(getLength()/2)>= 1.0) {
            setPosY(1.0-(getLength()/2));
        }
    }

    /**
     * Déplace l'entité vers le bas.
     */
    public void mouvementBas() {
        setPosY(getPosY() - getVitesse());

        if (getPosY()-(getLength()/2) <= 0.0) {
            setPosY(0+(getLength()/2));
        }
        else if (getPosY()+(getLength()/2) >= 1.0) {
            setPosY(1.0-(getLength()/2));
        }
    }

    /* ------ GETTERS ET SETTERS ------ */
    /**
     * Récupère la position X de l'entité.
     * @return position X de l'entité
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Définit la position X de l'entité.
     * @param posX nouvelle position X
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Récupère la position Y de l'entité.
     * @return position Y de l'entité
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Définit la position Y de l'entité.
     * @param posY nouvelle position Y
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Récupère la vitesse de déplacement de l'entité.
     * @return vitesse de l'entité
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * Définit la vitesse de déplacement de l'entité.
     * @param vitesse nouvelle vitesse
     */
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Récupère la longueur ou taille de l'entité.
     * @return longueur ou taille de l'entité
     */
    public double getLength() {
        return length;
    }

    /**
     * Définit la longueur ou taille de l'entité.
     * @param length nouvelle longueur ou taille
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Récupère la longueur de l'entité (pour les formes rectangulaires).
     * @return longueur de l'entité
     */
    public double getLongueur() {
        return longueur;
    }

    /**
     * Récupère la largeur de l'entité (pour les formes rectangulaires).
     * @return largeur de l'entité
     */
    public double getLargeur() {
        return largeur;
    }
    

}
