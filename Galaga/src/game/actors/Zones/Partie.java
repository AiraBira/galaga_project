package game.actors.zones;
import engine.StdDraw;
import java.awt.Font;

/**
 * La classe {@code Partie} gère les différents écrans affichés lors d'une partie du jeu,
 * tels que la sélection du niveau, le début de partie, l'affichage du niveau,
 * l'écran de victoire et l'écran de game over.
 */
public class Partie {
    
    /** Score actuel du joueur */
    private int score;

    /** Meilleur score enregistré */
    private int bestScore;

    /** Niveau actuel de la partie */
    private int niveau;

    /** Dimensions et position de l'écran de la partie */
    private static final double LONGUEUR = 1;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 0.0;

    /** Polices utilisées pour l'affichage du texte */
    private static final Font FONT1 = new Font("Arial", Font.BOLD, 40);
    private static final Font FONT2 = new Font("Arial", Font.BOLD, 30);

    /** Construit une partie avec le niveau spécifié.
     *
     * @param niveau le niveau de la partie
     */
    public Partie(int niveau) {
        this.niveau = niveau;
    }
    
    /** Met à jour le score et le meilleur score de la partie.
     *
     * @param score le score actuel du joueur
     * @param bestScore le meilleur score enregistré
     */
    public void update(int score, int bestScore) {
        setScore(score);
        setBestScore(bestScore);
    }

    /**
     * Affiche l'écran de sélection du niveau.
     */
    public void selection_niveau_draw() {
        StdDraw.setFont(FONT1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0.5, 0.65, "SELECT LEVEL");

        StdDraw.setFont(FONT2);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.text(0.5, 0.45, "PRESS 1 FOR LEVEL 1");
        StdDraw.text(0.5, 0.30, "PRESS 2 FOR LEVEL 2");
        StdDraw.text(0.5, 0.15, "PRESS 3 FOR THE BOSS");
    }

    /**
     * Affiche l'écran de début de partie.
     */
    public void debut_partie_draw() {
        StdDraw.setFont(FONT1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.text(0.5, 0.60, "PUSH SPACE BUTTON");
    }

    /**
     * Affiche l'écran d'affichage du niveau.
     */
    public void niveau_affichage_draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setFont(FONT2);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0.5, 0.60, "LEVEL " + getNiveau());
    }

    /**
     * Affiche l'écran de victoire.
     */
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

    /**
     * Affiche l'écran de game over.
     */
    public void gameOver_draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLongueur(), getLargeur());

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(FONT2);
        StdDraw.text(0.5, 0.65, "GAME OVER");

        StdDraw.setFont(FONT1);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.text(0.5, 0.45, "PRESS SPACE TO REPLAY");

        if (getScore() > getBestScore()) {
            StdDraw.setFont(FONT2);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text(0.5, 0.25, "NEW RECORD :  " + getScore());
        }
    }

    /* ------ GETTERS ET SETTERS ------ */
    
    /**
     * Récupère la longueur de l'écran de la partie.
     * @return longueur de l'écran
     */
    public double getLongueur() {
        return LONGUEUR;
    }

    /**
     * Récupère la largeur de l'écran de la partie.
     * @return largeur de l'écran
     */
    public double getLargeur() {
        return LARGEUR;
    }

    /**
     * Récupère la position X de l'écran de la partie.
     * @return position X
     */
    public double getPosx() {
        return POSX;
    }

    /**
     * Récupère la position Y de l'écran de la partie.
     * @return position Y
     */
    public double getPosy() {
        return POSY;
    }

    /**
     * Récupère le niveau actuel de la partie.
     * @return niveau actuel
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Récupère le score actuel de la partie.
     * @return score actuel
     */
    public int getScore() {
        return score;
    }

    /**
     * Définit le score actuel de la partie.
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
}
