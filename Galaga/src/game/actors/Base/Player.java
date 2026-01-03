package game.actors.base;
import java.util.List;
import engine.StdDraw;


/**
 * La classe {@code Player} représente le joueur dans le jeu.
 * Un joueur possède une position, une vitesse, des points de vie,
 * et peut tirer des missiles.
 */
public class Player extends Entite {

    /** Compteur pour gérer le délai entre les tirs successifs */
    private int countdownSpacePressed; // Sert a savoir si la touche espace etait appuyée avant

    /** Sprite du joueur chargé depuis un fichier externe */
    private static final String SPRITE = loadSprite("../ressources/sprites/ship.spr"); // On charge notre fichier .spr

    /**
     * Construit un joueur avec ses caractéristiques principales.
     *
     * @param x        position initiale sur l'axe des abscisses
     * @param y        position initiale sur l'axe des ordonnées
     * @param length   largeur du joueur
     * @param hp       points de vie initiaux
     * @param vitesse  vitesse de déplacement du joueur
     */
    public Player(double x, double y, double length, int hp, double vitesse) {
        super(x, y, length, vitesse, hp, SPRITE);
        countdownSpacePressed = 0;
    }

    /**
     * Crée un missile tiré par le joueur et l'ajoute à la liste des missiles du joueur.
     *
     * @param missilesDispo liste des missiles disponibles pour le joueur
     */
    public void creeMissile(List<Missiles> missilesDispo) {
        if (missilesDispo.size() < 3) { // Crée un nouveau missile uniquement s'il y a de la place dans l'écran.
            Missiles m = new Missiles(getPosX(), getPosY()+(getLength()/2), 0.04, false, true);
            missilesDispo.add(m);
        }
    }

    /**
     * Gère la perte de vie du joueur.
     *
     * @param modeInfini indique si le mode vies infinies est activé
     */
    public void perdreVie(boolean modeInfini){
        // Si le mode vies infinies est désactivé, le joueur perd une vie
        if (!modeInfini){
            setHp(getHp()-1);
            if (getHp()<=0){
                setHp(0);
            }
        }
    }

    /**
     * Gère le gain de vie du joueur.
     */
    public void gagnerVie(){
        setHp(getHp()+1);
    }

    /**
     * Met à jour l'état du joueur en fonction des entrées clavier.
     *
     * @param missilesDispo liste des missiles disponibles pour le joueur
     */
    public void update(List<Missiles> missilesDispo) {
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) {
            mouvementGauche();
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            mouvementDroit();
        }

        if (countdownSpacePressed > 0.0) { // Création d'un coountdown entre chaque tir par le joueur. On lance un tir par ms.
                                           // Cela empêche que les tirs se collent entre eux.
            setCountdownSpacePressed(getCountdownSpacePressed()-1);
        }

        if (StdDraw.isKeyPressed(32) && (countdownSpacePressed == 0.0)) {
            creeMissile(missilesDispo);
            setCountdownSpacePressed(5); // On remet le compteur à 3 après avoir crée notre missile.
        } 
    }


    /* ------ GETTERS ET SETTERS ------ */

    /**
     * Récupère le compteur de délai entre les tirs successifs.
     * @return compteur de délai entre les tirs
     */
    public int getCountdownSpacePressed() {
        return countdownSpacePressed;
    }

    /**
     * Définit le compteur de délai entre les tirs successifs.
     * @param countdownSpacePressed nouveau compteur de délai entre les tirs
     */
    public void setCountdownSpacePressed(int countdownSpacePressed) {
        this.countdownSpacePressed = countdownSpacePressed;
    }
}
