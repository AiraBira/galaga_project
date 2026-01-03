package game.actors.zones;
import java.awt.Font;
import engine.StdDraw;

/**
 * La classe {@code ZoneCompteRebours} gère l'affichage et la mise à jour
 * d'un compte à rebours dans le jeu.
 */
public class ZoneCompteRebours {
    /** Valeur actuelle du compte à rebours */
    private int compte;

    /** Position X de la zone de compte à rebours */
    private static final double POSX = 0.0;

    /** Position Y de la zone de compte à rebours */
    private static final double POSY = 1.0;

    /** Police utilisée pour l'affichage du compte à rebours */
    private static final Font FONT = new Font("SansSerif", Font.BOLD, 60);
    
    /** Construit une zone de compte à rebours avec la valeur initiale spécifiée.
     *
     * @param compte la valeur initiale du compte à rebours
     */
    public ZoneCompteRebours(int compte) {
        this.compte = compte;
    }

    /////// GETTERS ET SETTERS /////////
    
    /**
     * Récupère la position X de la zone de compte à rebours.
     * @return position X
     */
    public static double getPosx() {
        return POSX;
    }

    /**
     * Récupère la position Y de la zone de compte à rebours.
     * @return position Y
     */
    public static double getPosy() {
        return POSY;
    }

    /**
     * Définit la valeur du compte à rebours.
     * @param compte nouvelle valeur du compte à rebours
     */
    public void setCompte(int compte) {
        this.compte = compte;
    }

    /**
     * Récupère la valeur actuelle du compte à rebours.
     * @return valeur du compte à rebours
     */
    public int getCompte() {
        return compte;
    }

    /**
     * Affiche le compte à rebours à l'écran.
     */
    public void draw() {
        if (getCompte() >= 0){
            StdDraw.setFont(FONT);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.text(0.5,0.5, ""+compte);
        }
    } 

    /**
     * Met à jour la valeur du compte à rebours.
     * @param compte  nouvelle valeur du compte à rebours
     */
    public void update(int compte){
        setCompte(compte);
    }
}
