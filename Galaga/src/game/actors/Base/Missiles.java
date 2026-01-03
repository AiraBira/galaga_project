package game.actors.base;
import engine.StdDraw;

/**
 * La classe {@code Missiles} représente un projectile tiré par une entité
 * (joueur ou monstre).
 * 
 * Un missile possède une position, une vitesse et une direction de déplacement
 * (vers le haut ou vers le bas). Il peut également être marqué comme touché
 * afin d'être supprimé du jeu.
 */
public class Missiles extends Mouvements {

    /** Indique si le missile a touché une entité ou un obstacle */
    private boolean touche;

    /** Indique si le missile se déplace vers le haut (true) ou vers le bas (false) */
    private boolean mouvHaut;


    /**
     * Construit un missile.
     *
     * @param posX position initiale du missile sur l'axe des abscisses
     * @param posY position initiale du missile sur l'axe des ordonnées
     * @param vitesse vitesse de déplacement du missile
     * @param touche indique si le missile est déjà en collision
     * @param mouvHaut indique la direction du missile (true vers le haut, false vers le bas)
     */
    public Missiles(double posX, double posY, double vitesse, boolean touche, boolean mouvHaut) { // L'argument touche permet de faire
                                                                                // disparaitre le missile s'il touche
                                                                                // qqch
        super(posX, posY, vitesse, 0.0025, 0.010);
        this.touche = touche;
        this.mouvHaut = mouvHaut;
    }

    /**
     * Indique si le missile a touché une cible.
     *
     * @return true si le missile a touché une entité, false sinon
     */
    public boolean isTouche() {
        return touche;
    }

    /**
     * Définit l'état de collision du missile.
     *
     * @param touche true si le missile a touché une entité
     */
    public void setTouche(boolean touche) {
        this.touche = touche;
    }

    /**
     * Indique si le missile se déplace vers le haut.
     *
     * @return true si le missile monte, false s'il descend
     */
    public boolean isMouvHaut() {
        return mouvHaut;
    }

    /**
     * Définit la direction verticale de déplacement du missile.
     *
     * @param mouvHaut true pour un déplacement vers le haut, false vers le bas
     */
    public void setMouvHaut(boolean mouvHaut) {
        this.mouvHaut = mouvHaut;
    }


    /**
     * Dessine le missile à l'écran.
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(getPosX(), getPosY(), getLongueur(), getLargeur());
    }

    /**
     * Déplace le missile vers le haut.
     */
    @Override
    public void mouvementHaut(){
        setPosY(getPosY() + getVitesse());
    }

    /**
     * Déplace le missile vers le bas.
     */
    @Override
    public void mouvementBas(){
        setPosY(getPosY() - getVitesse());
    }

    /**
     * Met à jour la position du missile en fonction de sa direction.
     */
    public void update() {
        if (isMouvHaut()){
            mouvementHaut();
        }
        else {
            mouvementBas();
        } 
    }
}