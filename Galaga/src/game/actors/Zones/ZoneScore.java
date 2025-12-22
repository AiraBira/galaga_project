package game.actors.Zones;
import engine.StdDraw;
import java.awt.Font;


public class ZoneScore {
    
    protected int score;
    protected int bestScore;
    private static final double LONGUEUR = 0.09;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 1.0;
    private static final Font FONT = new Font("SansSerif", Font.BOLD, 20);
    
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
        StdDraw.setFont(FONT);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(0.5,0.97, "HIGH SCORE");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.1,0.94, ""+getScore());
        StdDraw.text(0.5,0.94, ""+getBestScore());

        StdDraw.line(getPosx(), getPosy() - getLongueur(), getPosx() + getLargeur(), getPosy() - getLongueur());
    } 

    public void update(int score, int bestScore){
        setScore(score);
        setBestScore(bestScore);
    }

}
