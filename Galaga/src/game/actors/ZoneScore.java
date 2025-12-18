package game.actors;
import engine.StdDraw;


public class ZoneScore {
    
    protected int score;
    protected int bestScore;
    private static final double LONGUEUR = 0.09;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 1.0;
    
    public ZoneScore(int score, int bestScore) {
        this.score = score;
        this.bestScore = bestScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public static double getLongueur() {
        return LONGUEUR;
    }

    public static double getLargeur() {
        return LARGEUR;
    }

    public static double getPosx() {
        return POSX;
    }

    public static double getPosy() {
        return POSY;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLargeur(), getLongueur()); // Prend la moitié de la longueur et largeur.
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(0.5,0.97, "HIGH SCORE");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.1,0.94, ""+getScore());
        StdDraw.text(0.5,0.94, ""+getBestScore());
    } 
    



}
