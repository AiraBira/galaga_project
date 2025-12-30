package game.actors.Zones;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;

import engine.StdDraw;

public class ZoneInfo {
    
    protected int niveau;
    private static final double LONGUEUR = 0.09;
    private static final double LARGEUR = 1;
    private static final double POSX = 0.0;
    private static final double POSY = 0.0;

    protected List<List<Character>> tabPixelsHP = chargeTableauPixels(loadSpriteVie());
    protected List<List<Character>> tabPixelsLevel = chargeTableauPixels(loadSpriteNiveau());


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

    public static String loadSpriteVie(){ // Utilisée plus tard pour charger nos fichiers .spr
        String sprite = "";
        Scanner scan;
        try {
            scan = new Scanner(new File("../ressources/sprites/ship.spr"));
            while (scan.hasNextLine()) {
                sprite += scan.nextLine() + "\n";
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sprite;
    }

    public static String loadSpriteNiveau(){ // Utilisée plus tard pour charger nos fichiers .spr
        String sprite = "";
        Scanner scan;
        try {
            scan = new Scanner(new File("../ressources/sprites/level.spr"));
            while (scan.hasNextLine()) {
                sprite += scan.nextLine() + "\n";
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sprite;
    }

    /** Dessine de l'entité, elle sera implémenté dans ses classes filles plus tard. */
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
}
