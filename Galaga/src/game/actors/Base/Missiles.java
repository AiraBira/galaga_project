package game.actors.base;
import engine.StdDraw;

/**
 * La classe {@code Missiles} représente un projectile tiré par une entité
 * (joueur ou monstre).
 * 
 * Un missile possède une position, une vitesse et une direction de déplacement. 
 * Il peut également être marqué comme touché afin d'être supprimé du jeu.
 */
public class Missiles extends Mouvements {

    /** Indique si missile touché ou pas */
    private boolean touche;

    /** Indique déplacement du missile */
    private boolean mouvHaut;


    /**
     * Construit un missile.
     *
     * @param posX position init x
     * @param posY position init y
     * @param vitesse vitesse de déplacement 
     * @param touche indique si missile en collision
     * @param mouvHaut indique la direction
     */
    public Missiles(double posX, double posY, double vitesse, boolean touche, boolean mouvHaut) { // L'argument touche permet de faire
                                                                                // disparaitre le missile s'il touche
                                                                                // qqch
        super(posX, posY, vitesse, 0.0025, 0.010);
        this.touche = touche;
        this.mouvHaut = mouvHaut;
    }

    /**
     * Dessine le missile à l'écran
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(getPosX(), getPosY(), getLongueur(), getLargeur());
    }

    /**
     * Déplace le missile vers le haut
     */
    @Override
    public void mouvementHaut(){
        setPosY(getPosY() + getVitesse());
    }

    /**
     * Déplace le missile vers le bas
     */
    @Override
    public void mouvementBas(){
        setPosY(getPosY() - getVitesse());
    }

    /**
     * Met à jour la position du missile en fonction de sa direction
     */
    public void update() {
        if (isMouvHaut()){
            mouvementHaut();
        }
        else {
            mouvementBas();
        } 
    }

    /* ------ GETTERS ET SETTERS ------ */
    /**
     * Indique si le missile a touché une cible.
     *
     * @return true = missile a touché une entité, false sinon
     */
    public boolean isTouche() {
        return touche;
    }

    /**
     * Modifie l'état de collision du missile.
     *
     * @param touche true si le missile a touché une entité
     */
    public void setTouche(boolean touche) {
        this.touche = touche;
    }

    /**
     * Indique si le missile se déplace vers le haut.
     *
     * @return true s'il monte, false s'il descend
     */
    public boolean isMouvHaut() {
        return mouvHaut;
    }

    /**
     * Modifie  la direction verticale du missile
     *
     * @param mouvHaut true = vers le haut, false vers le bas
     */
    public void setMouvHaut(boolean mouvHaut) {
        this.mouvHaut = mouvHaut;
    }

}