package game;

import java.util.ArrayList;
import java.util.List;

import engine.StdDraw;
import game.actors.*;


/**
 * Classe du jeu principal.
 * Gère la création de l'espace de jeu et la boucle de jeu en temps réel.
 */
public class Game {
    //public Player player; // Jouer, seul éléments actuellement dans notre jeu
    public Formation formation1;
    public Player player;
    public List<Missiles> missilesDispo;

    /**
     * Créé un jeu avec tous les éléments qui le composent
     */
    public Game() {

        missilesDispo = new ArrayList<>();
        player = new Player(0.5, 0.25, 0.05, 3, 1, 0.01);
        List<Pair<Double,Double>> tabPos= new ArrayList<>();
        tabPos.add(new Pair<Double,Double>(0.40, 0.80));
        tabPos.add(new Pair<Double,Double>(0.5, 0.80));
        tabPos.add(new Pair<Double,Double>(0.60, 0.80));
        tabPos.add(new Pair<Double,Double>(0.45, 0.75));
        tabPos.add(new Pair<Double,Double>(0.55, 0.75));
        tabPos.add(new Pair<Double,Double>(0.5, 0.7));
        
        formation1 = new Formation(6, tabPos);
    }

    /**
     * Initialise l'espace de jeu
     */
    private void init() {
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Initialise le jeu et lance la boucle de jeu en temps réel
     */
    public void launch() {
        init();

        while (isGameRunning()) {
            StdDraw.clear(); // On efface tous ce qu'il y a sur l'interface

            update(); // on met a jour les attributs de chaque éléments
            draw(); // on dessine chaques éléments

            StdDraw.show(); // on montre l'interface
            StdDraw.pause(30); // on attend 30 milisecondes avant de recommencer
        }
    }

    /**
     * Condition d'arrêt du jeu
     * 
     * @return true car on n'as pas encore de conidtions d'arrêt
     */
    private boolean isGameRunning() {
        return true;
    }

    /**
     * Dessin tous les éléments du jeu
     */
    public void draw() {
        player.draw();
        formation1.draw();

       
        for (Missiles m : missilesDispo){
            m.draw();
        }
        
        
    }

    /**
     * Met a jour les attributs de tous les éléments du jeu
     */
    private void update() {
        player.update(missilesDispo);
        formation1.update();

        
        for (Missiles m : missilesDispo){
            m.update();
        }
        
    }
}
