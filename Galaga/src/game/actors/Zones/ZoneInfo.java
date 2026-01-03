package game.actors.zones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import engine.StdDraw;

/**
 * La classe {@code ZoneInfo} gère l'affichage des informations de jeu telles que
 * le niveau actuel et les vies restantes du joueur.
 */
public class ZoneInfo {
    
    /** Niveau actuel de la partie */
    private int niveau;

    /**
     * Dimensions et position de la zone d'information
     */
    private static final double LONGUEUR = 0.09;

    /** Largeur de la zone d'information */
    private static final double LARGEUR = 1;

    /** Position X de la zone d'information */
    private static final double POSX = 0.0;

    /** Position Y de la zone d'information */
    private static final double POSY = 0.0;

    /** Tableaux de pixels pour les sprites de vie */
    private List<List<Character>> tabPixelsHP = chargeTableauPixels(loadSpriteVie()); // Les vaisseaux de vies 

    /** Tableaux de pixels pour les sprites du logo niveau*/
    private List<List<Character>> tabPixelsLevel = chargeTableauPixels(loadSpriteNiveau()); // Le logo niveau

    /** Construit une zone d'information avec le niveau spécifié.
     *
     * @param niveau le niveau actuel de la partie
     */
    public ZoneInfo(int niveau) {
        this.niveau = niveau;
    }

    /**
     * Charge un texte représentant un sprite dans un tableau de pixels.
     * @param text le texte du sprite
     * @return tableau de pixels du sprite
     */
    public List<List<Character>> chargeTableauPixels(String text){
        /// Charger le texte dans un tableau de tableaux représentant les pixels.
        List<List<Character>> tab = new ArrayList<>();
        tab.add(new ArrayList<>()); // On crée la première ligne pour pouvoir commencer à y mettre les caractères.

        int indiceLigne = 0;
        for (int i = 0 ; i < text.length(); i++){
            if (text.charAt(i) == '\n'){
                tab.add(new ArrayList<>()); // On ajoute une nouvelle ligne suivante
                indiceLigne++;
            }
            else {
                tab.get(indiceLigne).add(text.charAt(i)); // ça convertit automatiquement le type primitif char en l'objet character donc pas de soucis
            }
        }

        return tab;
    }    

    /**
     * Convertit un caractère du sprite en couleur.
     *
     * @param c caractère représentant une couleur
     * @return couleur associée
     */
    public Color conversionCharToColor(Character c){
        if (c.equals('R')){
            return StdDraw.RED;
        }
        else if (c.equals('B')){
            return StdDraw.BLUE;
        }
        else if (c.equals('G')){
            return StdDraw.GREEN;
        }
        else if (c.equals('Y')){
            return StdDraw.YELLOW;
        }
        else if (c.equals('W')){
            return StdDraw.WHITE;
        }
        else {
            return StdDraw.BLACK;
        }
    }

    /**
     * Charge le sprite représentant une vie depuis un fichier .spr
     * @return  texte du sprite
     */
    public static String loadSpriteVie(){ // Utilisée plus tard pour charger nos fichiers .spr
        String sprite = "";
        try {
            // Création d'un objet File représentant le fichier de niveau à charger
            File fichier = new File("../ressources/sprites/ship.spr");
            
            // Vérifie que le fichier existe avant d'essayer de le lire
            if (!fichier.exists()) { // Si le fichier n'existe pas, on affiche un message d'erreur
                                // et on quitte le constructeur pour éviter une exception
                System.out.println("Fichier introuvable : " + fichier.getAbsolutePath());
                return "";
            }

            // Création d'un BufferedReader permettant de lire un fichier texte ligne par ligne
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            String ligne = "";

            //Scanner scan;
        
            //scan = new Scanner(new File(textLoad));
            while ((ligne = reader.readLine()) != null) {
                sprite += ligne + "\n"; //scan.nextLine() + "\n";
            }

            reader.close(); // Ferme le lecteur de fichier pour libérer les ressources

        } catch (Exception e) { // Gère les exceptions pouvant survenir lors de la lecture du fichier
            e.printStackTrace();
        }
        return sprite;
    }

    /**
     * Charge le sprite représentant le logo niveau depuis un fichier .spr
     * @return texte du sprite
     */
    public static String loadSpriteNiveau(){ // Utilisée plus tard pour charger nos fichiers .spr
        String sprite = "";
        try {
            // Création d'un objet File représentant le fichier de niveau à charger
            File fichier = new File("../ressources/sprites/level.spr");
            
            // Vérifie que le fichier existe avant d'essayer de le lire
            if (!fichier.exists()) { // Si le fichier n'existe pas, on affiche un message d'erreur
                                // et on quitte le constructeur pour éviter une exception
                System.out.println("Fichier introuvable : " + fichier.getAbsolutePath());
                return "";
            }

            // Création d'un BufferedReader permettant de lire un fichier texte ligne par ligne
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            String ligne = "";

            //Scanner scan;
        
            //scan = new Scanner(new File(textLoad));
            while ((ligne = reader.readLine()) != null) {
                sprite += ligne + "\n"; //scan.nextLine() + "\n";
            }

            reader.close(); // Ferme le lecteur de fichier pour libérer les ressources

        } catch (Exception e) { // Gère les exceptions pouvant survenir lors de la lecture du fichier
            e.printStackTrace();
        }
        return sprite;
    }

    /**
     * Affiche les informations de jeu à l'écran.
     *
     * @param hp          nombre de vies restantes du joueur
     * @param modeInfini  indique si le mode vies infinies est activé
     */
    public void draw(int hp, boolean modeInfini){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(getPosx(), getPosy(), getLargeur(), getLongueur());
        double gap=0.05;
        double taillePixel = 0.05 / tabPixelsHP.get(0).size(); // On calcule la taille de pixels qu'on peut mettre 
        for (int vies =1; vies<=hp; vies++){

            for (int i = 0 ; i < tabPixelsHP.size(); i++){
                for (int j = 0; j < tabPixelsHP.get(i).size(); j++){
                    Color couleurPixel = conversionCharToColor(tabPixelsHP.get(i).get(j));
                    
                    double xPixel = (getPosx()+gap) - 0.02 + j * taillePixel + taillePixel / 2;
                    double yPixel = (getPosy()+0.05) + 0.02 - i * taillePixel - taillePixel / 2;
                    StdDraw.setPenColor(couleurPixel);
                    StdDraw.filledSquare(xPixel, yPixel, taillePixel / 2);
                }
            }
            gap+=0.05;
        }

        double taillePixelNiveau = 0.1 / tabPixelsHP.get(0).size();
        for (int i = 0 ; i < tabPixelsLevel.size(); i++){
            for (int j = 0; j < tabPixelsLevel.get(i).size(); j++){
                Color couleurPixel = conversionCharToColor(tabPixelsLevel.get(i).get(j));
                    
                double xPixel = getPosx() + 0.90 + j * taillePixelNiveau + taillePixelNiveau / 2;
                double yPixel = (getPosy()+0.085) + 0.02 - i * taillePixelNiveau - taillePixelNiveau / 2;
                StdDraw.setPenColor(couleurPixel);
                StdDraw.filledSquare(xPixel, yPixel, taillePixelNiveau / 2);
            }
        }

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.line(getPosx(), getPosy() + getLongueur(), getPosx()+getLargeur(), getPosy()+getLongueur());

        // Indicateur du mode vies infinies
        if (modeInfini) {
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.text(0.90, 0.95, "INFINITE LIVES");
        }
    }

    /* ------ GETTERS ET SETTERS ------ */
    
    /**
     * Récupère le niveau actuel de la partie.
     * @return niveau actuel
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * Définit le niveau actuel de la partie.
     * @param niveau nouveau niveau
     */
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    /**
     * Récupère la longueur de la zone d'information.
     * @return longueur
     */
    public static double getLongueur() {
        return LONGUEUR;
    }

    /**
     * Récupère la largeur de la zone d'information.
     * @return largeur
     */
    public static double getLargeur() {
        return LARGEUR;
    }

    /**
     * Récupère la position X de la zone d'information.
     * @return position X
     */
    public static double getPosx() {
        return POSX;
    }

    /**
     * Récupère la position Y de la zone d'information.
     * @return position Y
     */
    public static double getPosy() {
        return POSY;
    }


}
