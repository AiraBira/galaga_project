package game.actors.Zones;
import java.awt.Font;
import engine.StdDraw;

public class ZoneCompteRebours {
    int compte;
    //private static final double LONGUEUR = ;
    //private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 1.0;
    private static final Font FONT = new Font("SansSerif", Font.BOLD, 60);
    
    public ZoneCompteRebours(int compte) {
        this.compte = compte;
    }

    public static double getPosx() {
        return POSX;
    }

    public static double getPosy() {
        return POSY;
    }

    public void setCompte(int compte) {
        this.compte = compte;
    }

    public int getCompte() {
        return compte;
    }

    public void draw() {
        if (getCompte() >= 0){
            StdDraw.setFont(FONT);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.text(0.5,0.5, ""+compte);
        }
    } 

    public void update(int compte){
        setCompte(compte);
    }
}
