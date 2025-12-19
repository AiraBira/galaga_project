package game.actors;

import engine.StdDraw;

public class ZonePartieFinie {
    protected int niveau;
    private static final double LONGUEUR = 1;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 0.0;

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

    public void draw() {
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());
    } 
}
