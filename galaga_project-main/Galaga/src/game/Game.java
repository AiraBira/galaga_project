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
    // public Player player; // Jouer, seul éléments actuellement dans notre jeu
    public Formation formation1;
    public Player player;
    public List<Missiles> missilesDispo;

    public int score;
    public int bestScore;
    public ZoneScore zoneScore;
    public ZoneInfo zoneInfo;
    public ZonePartieFinie zonePartieFinie;

    /**
     * Créé un jeu avec tous les éléments qui le composent
     */
    public Game() {

        missilesDispo = new ArrayList<>();
        player = new Player(0.5, 0.25, 0.05, 3, 1, 0.01);

        // Positions de la formation 1 //
        List<Pair<Double, Double>> tabPos = new ArrayList<>();
        tabPos.add(new Pair<Double, Double>(0.40, 0.80));
        tabPos.add(new Pair<Double, Double>(0.5, 0.80));
        tabPos.add(new Pair<Double, Double>(0.60, 0.80));
        tabPos.add(new Pair<Double, Double>(0.45, 0.75));
        tabPos.add(new Pair<Double, Double>(0.55, 0.75));
        tabPos.add(new Pair<Double, Double>(0.5, 0.7));

        // Gérer les formations //
        formation1 = new Formation(6, tabPos);

        // Gérer le score : //
        score = 0;
        bestScore = 0;
        zoneScore = new ZoneScore(score, bestScore);
        zoneInfo = new ZoneInfo(0);
        zonePartieFinie = new ZonePartieFinie();
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
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

    /** Dessine tous les éléments du jeu */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.0, 0.0, 350, 350);

        zoneScore.draw();
        zoneInfo.draw(player.getHp());
        player.draw();

        formation1.draw();

        for (Missiles m : missilesDispo) {
            m.draw();
        }

        if (formation1.niveauTermine()) {
            zonePartieFinie.draw();
        }
    }

    public void monstreTouche(Monster m) {
        m.degats(1);
        if (m.isDead()) {
            setScore(getScore() + 1);
        }
    }

    // supprime des missiles de la liste si ils dépassent le haut de l'écran.
    public void verificationMissiles(List<Missiles> listMissiles) {
        for (int i = 0; i < listMissiles.size(); i++) {
            if (listMissiles.get(i).getPosY() + listMissiles.get(i).getLongueur() > 0.91) { // le bas de l'écran est Y=0
                                                                                            // et le haut est Y=1 !
                listMissiles.remove(i);
            }
        }

        for (int i = 0; i < listMissiles.size(); i++) {
            double hautMissile = listMissiles.get(i).getPosY() + (listMissiles.get(i).getLongueur());
            double gaucheMissile = listMissiles.get(i).getPosX() - (listMissiles.get(i).getLargeur());
            double droiteMissile = listMissiles.get(i).getPosX() + (listMissiles.get(i).getLargeur());

            for (Monster monster : formation1.getListeMonstres()) {
                double basMonstre = monster.getPosY() - (monster.getLength() / 2);
                if (((gaucheMissile <= monster.getPosX() + (monster.getLength() / 2)) &&
                        (droiteMissile >= monster.getPosX() - (monster.getLength() / 2))) &&
                        (hautMissile >= basMonstre)) {

                    // Si un monstre est touché :
                    monstreTouche(monster);
                    listMissiles.remove(i);
                    break;
                }
            }
        }
    }

    /* Met a jour les attributs de tous les éléments du jeu */
    private void update() {
        player.update(missilesDispo);
        if (!formation1.niveauTermine()) {
            formation1.update();
        }

        zoneScore.update(score);
        // if(score == 3){
        // player.setHp(2);
        // }

        for (Missiles missiles : missilesDispo) {
            missiles.update();
        }

        verificationMissiles(missilesDispo);
    }
}
