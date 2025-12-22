package game.actors.Zones;

import engine.StdDraw;
import java.awt.Font;


public class Partie {
    public int niveau;
    private static final double LONGUEUR = 1;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 0.0;
    private static final Font FONT1 = new Font("Arial", Font.BOLD, 32);
    private static final Font FONT2 = new Font("Arial", Font.BOLD, 28);


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

    public void fin_partie_draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.setFont(FONT2);
        StdDraw.text(0.5, 0.65, "FIN DE PARTIE");

        StdDraw.setFont(FONT1);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(0.5, 0.45, "PRESS SPACE TO REPLAY");
    } 
}
