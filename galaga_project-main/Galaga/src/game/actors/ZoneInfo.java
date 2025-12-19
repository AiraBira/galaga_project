package game.actors;

import engine.StdDraw;

public class ZoneInfo {
    
    protected int niveau;
    private static final double LONGUEUR = 0.09;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 0.0;

    public ZoneInfo(int niveau) {
        this.niveau = niveau;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
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

    public void draw(int hp) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.line(getPosx(), getPosy() + getLongueur(), getPosx()+getLargeur(), getPosy()+getLongueur());
        double gap=0.05;
        for (int i=1;i<=hp;i++){
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(getPosx()+gap, getPosy()+0.05, 0.02);
            gap+=0.05;
        }
    } 
}
