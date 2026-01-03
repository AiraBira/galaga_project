package game.actors.base;
import java.util.ArrayList;
import java.util.List;
import engine.StdDraw;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Classe abstraite représentant une entité du jeu.
 * Une entité est un objet vivant du jeu (joueur, monstre, etc.) possédant : des points de vie, 
 * une position, une vitesse de déplacement, un sprite sous forme de String ensuite convertie en pixels.
 * Cette classe gère la logique commune à toutes les entités.
 */
public abstract class Entite extends Mouvements {
    
    /** vie totale */
    protected int hp;
    /** position initiale X */
    protected double Xinit; 
    /** position initiale Y */
    protected double Yinit;
    /** représentation textuelle du sprite */
    protected String sprite;
    /** tableau de pixels du sprite */
    protected List<List<Character>> tabPixels; 

    //si on veut plus tard :  protected int def; // points de défense de l'entité
    
    /**
     * Construit une entité avec ses caractéristiques.
     *
     * @param x        position y
     * @param y        position x
     * @param length   largeur de l'entité
     * @param vitesse  vitesse deplacement
     * @param hp       vie
     * @param sprite   sprite 
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
    

    /**
     * Enleve de la vie
     *
     * @param degats points de vie à enlever
     */
    public void degats(int degats){
        if (!isDead()){
            setHp(getHp() - degats);
            if (getHp() < 0) {
                setHp(0);
            }
        }
    }

    /**
     * Vérifie si l'entité est morte
     * @return true si l'entité est morte
     */
    public boolean isDead(){
        return getHp() == 0;
    }

    /**
     * Tire sur une autre entité, lui enlève de la vie
     *
     * @param cible entité ciblée
     */
    public void tire(Entite cible){
        if (!cible.isDead()){
            cible.degats(1);
        }
    }
    
    /**
     * Convertit un sprite texte en tableau de pixels.
     * Chaque ligne est une ligne de chars.
     *
     * @param text sprite txt
     * @return tableau de pixels
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
     * Convertit char en couleur.
     *
     * @param c caractère qui est une couleur
     * @return couleur
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
     * Charge un fichier sprite (.spr) et le convertit en texte.
     * Inspirée de la classe Niveaux pour le chargement de fichiers, dans laquelle nous avons utilisé chatgpt pour nous aider.
     * Ici il y a eu simplement de l'adaptation, pas d'IA directement.
     * 
     * @param textLoad chemin sprite
     * @return sprite txt
     */
    public static String loadSprite(String textLoad){ // Utilisée plus tard pour charger nos fichiers .spr
        String sprite = "";
        try {
            // Création d'un objet File qui est le fichier de niveau à charger
            File fichier = new File(textLoad);
            
            // Vérifie que le fichier existe avant d'essayer de le lire
            if (!fichier.exists()) { // Si le fichier n'existe pas, message d'erreur
                                // et on quitte le constructeur pour éviter une exception
                System.out.println("Fichier introuvable : " + fichier.getAbsolutePath());
                return "";
            }
            // Création d'un BufferedReader qui permet de lire un texte ligne par ligne
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            String ligne = "";
            while ((ligne = reader.readLine()) != null) {
                sprite += ligne + "\n"; //scan.nextLine() + "\n";
            }

            reader.close(); // Ferme lecteur

        } catch (Exception e) { // Gère les exceptions
            e.printStackTrace();
        }
        return sprite;
    }

    /**
     * Dessine l'entité à l'écran à partir de son sprite.
     * Chaque caractère s'affiche comme pixel coloré.
     */
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

    /* ------ GETTERS ET SETTERS ------ */
    
    /**
     * Renvoi la vie.
     * @return points de vie de l'entité
     */
    public int getHp() {
        return hp;
    }

    /**
     * Modifie la vie.
     *
     * @param hp nouveaux points de vie
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Renvoi la vitesse.
     * @return vitesse de l'entité
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * Renvoi la position x.
     * @return position x
     */
    public double getXinit() {
        return Xinit;
    }

    /**
     * Renvoi la position y
     * @return position y
     */
    public double getYinit() {
        return Yinit;
    }
    
    /**
     * Renvoi le sprite txt
     * @return sprite 
     */
    public String getSprite() {
        return sprite;
    }

    /**
     * Renvoi le tableau de pixesl
     * @return tab de pixels
     */
    public List<List<Character>> getTabPixels() {
        return tabPixels;
    }

}
