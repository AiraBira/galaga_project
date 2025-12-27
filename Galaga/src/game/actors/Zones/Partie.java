package game.actors.Zones;

import engine.StdDraw;
import java.awt.Font;


public class Partie {
    public int score;
    public int bestScore;
    public int niveau;
    private static final double LONGUEUR = 1;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 0.0;
    private static final Font FONT1 = new Font("Arial", Font.BOLD, 40);
    private static final Font FONT2 = new Font("Arial", Font.BOLD, 32);


    public Partie(int niveau){
        this.niveau = niveau;
    }
    
    public double getLongueur() {
        return LONGUEUR;
    }

    public double getLargeur() {
        return LARGEUR;
    }

    public double getPosx() {
        return POSX;
    }

    public double getPosy() {
        return POSY;
    }

    public int getNiveau(){
        return niveau;
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

    public void update(int score, int bestScore){
        setScore(score);
        setBestScore(bestScore);
    }

    public void debut_partie_draw() {
        StdDraw.setFont(FONT1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.text(0.5, 0.60, "PUSH SPACE BUTTON");
    }

    public void niveau_affichage_draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setFont(FONT2);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0.5, 0.60, "LEVEL " + getNiveau());
    }

    public void win_draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setFont(FONT2);
        StdDraw.text(0.5, 0.65, "YOU WON !");

        StdDraw.setFont(FONT1);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.text(0.5, 0.45, "PRESS SPACE TO REPLAY");
    } 

    public void gameOver_draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(FONT2);
        StdDraw.text(0.5, 0.65, "GAME OVER");

        StdDraw.setFont(FONT1);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.text(0.5, 0.45, "PRESS SPACE TO REPLAY");

        if (getScore() > getBestScore()){
            StdDraw.setFont(FONT2);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text(0.5, 0.25, "NEW RECORD :  " + getScore());
        }
    }
}
