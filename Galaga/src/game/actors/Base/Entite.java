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
     * Construit une entité avec ses caractéristiques principales.
     *
     * @param x        position initiale sur l'axe des abscisses
     * @param y        position initiale sur l'axe des ordonnées
     * @param length   largeur de l'entité
     * @param vitesse  vitesse de déplacement
     * @param hp       points de vie initiaux
     * @param sprite   sprite de l'entité sous forme de texte
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

    /* ------ GETTERS ET SETTERS ------ */

    /**
     * Récupère les points de vie de l'entité.
     * @return points de vie de l'entité
     */
    public int getHp() {
        return hp;
    }

    /**
     * Modifie les points de vie de l'entité.
     *
     * @param hp nouveaux points de vie
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Récupère la vitesse de déplacement de l'entité.
     * @return vitesse de l'entité
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * Récupère la position initiale X de l'entité.
     * @return position initiale X
     */
    public double getXinit() {
        return Xinit;
    }

    /**
     * Récupère la position initiale Y de l'entité.
     * @return position initiale Y
     */
    public double getYinit() {
        return Yinit;
    }
    
    /**
     * Récupère le sprite textuel de l'entité.
     * @return sprite de l'entité
     */
    public String getSprite() {
        return sprite;
    }

    /**
     * Récupère le tableau de pixels du sprite de l'entité.
     * @return tableau de pixels du sprite
     */
    public List<List<Character>> getTabPixels() {
        return tabPixels;
    }

    /////////////////////////////////////////////////////////////////////////////

    /**
     * Inflige des dégâts à l'entité et réduit ses points de vie.
     *
     * @param degats nombre de points de vie à retirer
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
     * Vérifie si l'entité est morte (points de vie à 0).
     * @return true si l'entité est morte, false sinon
     */
    public boolean isDead(){
        return getHp() == 0;
    }

    /**
     * Tire sur une autre entité en lui infligeant des dégâts.
     *
     * @param cible entité attaquée
     */
    public void tire(Entite cible){
        if (!cible.isDead()){
            cible.degats(1);
        }
    }

    /////////////////////    GERE L'AFFICHAGE    /////////////////// 
    
    
    /**
     * Convertit un sprite texte en tableau de pixels.
     * Chaque ligne correspond à une ligne de caractères.
     *
     * @param text sprite sous forme de texte
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
     * Charge un fichier sprite (.spr) et le convertit en texte.
     * Inspirée de la classe Niveaux pour le chargement de fichiers, dans laquelle nous avons utilisé chatgpt pour nous aider.
     * Ici il y a eu simplement de l'adaptation, pas d'IA directement.
     * 
     * @param textLoad chemin vers le fichier sprite
     * @return sprite sous forme de chaîne de caractères
     */
    public static String loadSprite(String textLoad){ // Utilisée plus tard pour charger nos fichiers .spr
        String sprite = "";
        try {
            // Création d'un objet File représentant le fichier de niveau à charger
            File fichier = new File(textLoad);
            
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
     * Dessine l'entité à l'écran à partir de son sprite.
     * Chaque caractère du sprite est affiché comme un pixel de la couleur que représente son caractère.
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
}
