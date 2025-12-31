package game.actors.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import engine.StdDraw;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;


public abstract class Entite extends Mouvements {
    protected int hp; // vie totale
    protected double Xinit;
    protected double Yinit;
    protected String sprite;
    protected List<List<Character>> tabPixels;
    //si on veut plus tard :  protected int def; // points de défense de l'entité
    
    
    /**
     * @param x      postion de l'entité sur l'axe des abscisses
     * @param y      position de l'entité sur l'axe des ordonnées
     * @param length largeur de l'entité
     */

    public Entite(double x, double y, double length, double vitesse, int hp, String sprite) { 
        super(x,y, vitesse, length);
        this.length = length;
        this.hp = hp;
        this.Xinit = x;
        this.Yinit = y;
        // On crée notre tableau de pixels une seule fois ici pour ne pas le refaire plein de fois dans le draw.
        tabPixels = chargeTableauPixels(sprite);
    }

    //////////////////  GETTERS ET SETTERS   ////////////////////
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getVitesse() {
        return vitesse;
    }

    public double getXinit() {
        return Xinit;
    }

    public double getYinit() {
        return Yinit;
    }
    
    public String getSprite() {
        return sprite;
    }

    public List<List<Character>> getTabPixels() {
        return tabPixels;
    }
    /////////////////////////////////////////////////////////////////////////////

    /**  Reçoit des dégats et lui enlève de la vie. */
    public void degats(int degats){
        if (!isDead()){
            setHp(getHp() - degats);
            if (getHp() < 0) {
                setHp(0);
            }
        }
    }

    public boolean isDead(){ // vérifie si l'entité est morte
        return getHp() == 0;
    }

    public void tire(Entite cible){ // attaque une autre entité
        if (!cible.isDead()){
            cible.degats(1);
        }
    }

    /////////////////////    GERE L'AFFICHAGE    /////////////////// 
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

    // Faite grâce à l'aide de ChatGPT
    public static String loadSprite(String textLoad){ // Utilisée plus tard pour charger nos fichiers .spr
        String sprite = "";
        Scanner scan;
        try {
            scan = new Scanner(new File(textLoad));
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
    public void draw(){
        double taillePixel = getLength() / tabPixels.get(0).size(); // On calcule la taille de pixels qu'on peut mettre 
        for (int i = 0 ; i < tabPixels.size(); i++){
            for (int j = 0; j < tabPixels.get(i).size(); j++){
                Color couleurPixel = conversionCharToColor(tabPixels.get(i).get(j));
                
                double xPixel = getPosX() - getLength() / 2 + j * taillePixel + taillePixel / 2;
                double yPixel = getPosY() + getLength() / 2 - i * taillePixel - taillePixel / 2;

                StdDraw.setPenColor(couleurPixel);
                StdDraw.filledSquare(xPixel, yPixel, taillePixel / 2);
            }
        }
    }
}
