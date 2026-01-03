package game.actors.zones;
import engine.StdDraw;
import java.awt.Font;

/**
 * La classe {@code ZoneScore} gère l'affichage du score actuel
 * et du meilleur score enregistré dans le jeu.
 */
public class ZoneScore {
    
    /** Score actuel du joueur */
    private int score;

    /** Meilleur score enregistré */
    private int bestScore;

    /** Dimensions et position de la zone de score */
    private static final double LONGUEUR = 0.09;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 1.0;

    /** Police utilisée pour l'affichage du score */
    private static final Font FONT = new Font("SansSerif", Font.BOLD, 20);
    
    /** Construit une zone de score avec les scores spécifiés.
     *
     * @param score le score actuel du joueur
     * @param bestScore le meilleur score enregistré
     */
    public ZoneScore(int score, int bestScore) {
        this.score = score;
        this.bestScore = bestScore;
    }
    
    /**
     * Affiche le score actuel et le meilleur score à l'écran.
     */
    public void draw() {
        StdDraw.setFont(FONT);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(0.5,0.97, "HIGH SCORE");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.1,0.94, ""+getScore());
        StdDraw.text(0.5,0.94, ""+getBestScore());

        StdDraw.line(getPosx(), getPosy() - getLongueur(), getPosx() + getLargeur(), getPosy() - getLongueur());
    } 

    /**
     * Met à jour les scores affichés.
     * @param score le score actuel
     * @param bestScore le meilleur score enregistré
     */
    public void update(int score, int bestScore){
        setScore(score);
        setBestScore(bestScore);
    }

    /* ------ GETTERS ET SETTERS ------ */
    
    /**
     * Récupère le score actuel du joueur.
     * @return score actuel
     */
    public int getScore() {
        return score;
    }

    /**
     * Définit le score actuel du joueur.
     * @param score nouveau score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Récupère le meilleur score enregistré.
     * @return meilleur score
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * Définit le meilleur score enregistré.
     * @param bestScore nouveau meilleur score
     */
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    /**
     * Récupère la longueur de la zone de score.
     * @return longueur
     */
    public static double getLongueur() {
        return LONGUEUR;
    }

    /**
     * Récupère la largeur de la zone de score.
     * @return largeur
     */
    public static double getLargeur() {
        return LARGEUR;
    }
    
    /**
     * Récupère la position X de la zone de score.
     * @return position X
     */
    public static double getPosx() {
        return POSX;
    }

    /**
     * Récupère la position Y de la zone de score.
     * @return position Y
     */
    public static double getPosy() {
        return POSY;
    }

}
