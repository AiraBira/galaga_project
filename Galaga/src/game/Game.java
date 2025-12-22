package game;

import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;

import engine.StdDraw;
import game.*;
import game.actors.Base.*;
import game.actors.Zones.*;
import game.actors.Monsters.*;

/**
 * Classe du jeu principal.
 * Gère la création de l'espace de jeu et la boucle de jeu en temps réel.
 */
public class Game {

    public static final int TOTAL_NIVEAUX = 2; // à changer lorqu'on rajoute des niveaux !!
    
    //////////                  NIVEAUX ICI                      //////////////
    public static final Niveaux niveau1 = new Niveaux("../ressources/levels/level1.lvl");
    public static final Niveaux niveau2 = new Niveaux("../ressources/levels/level2.lvl");
    public List<Niveaux> listNiveaux;

    public List<Formation> listFormations;
    
    public Player player;
    public List<Missiles> missilesDispo;

    public int score;
    public int bestScore;

    public ZoneScore zoneScore;
    public ZoneInfo zoneInfo;
    public Partie ecran;

    public int countdown_affichage_niveau; // Comme le temps d'une boucle du jeu est 30 ms et qu'on veut 2s de pause.
                                           // 2s = 2000 ms et 2000/30 = à environ 67. Donc on doit faire 67 boucles
                                           // pour afficher le niveau pendant 2s.
    public int niveau_actuel;
    public boolean afficherNiveau;

    public int etat_jeu = 0; // Si 0 -> Ecran debut de partie
                             // Si 1 -> Affichage niveau
                             // Si 2 -> Jeu
                             // Si 3 -> Fin de partie
                            
    /* Créé un jeu avec tous les éléments qui le composent */
    public Game() {

        // Gérer les niveaux.
        listNiveaux = new ArrayList<>();
        listNiveaux.add(niveau1);
        listNiveaux.add(niveau2);
        niveau_actuel = 1;
        countdown_affichage_niveau = 67;
        afficherNiveau = false;
       
        missilesDispo = new ArrayList<>();
        player = new Player(0.5, 0.25, 0.05, 3, 0.01);

        // On initialise au debut de la partie.
        etat_jeu = 0;

        listFormations = new ArrayList<>();
        for (int i = 0 ; i < listNiveaux.size(); i++) {  // Chaque formation aura l'indice "niveau_actuel - 1".
            listFormations.add(new Formation(listNiveaux.get(i).getMonstres()));
        }

        // Ecran d'accueil //
        ecran = new Partie(getNiveau_actuel());

        // Gérer le score : //
        score = 0;
        bestScore = 0;
        zoneScore = new ZoneScore(score, bestScore);

        zoneInfo = new ZoneInfo(0);
    }

    public int getEtatJeu() {
        return etat_jeu;
    }

    public void setEtatJeu(int nouvelEtat) {
        this.etat_jeu = nouvelEtat;
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

    public static int getTotalNiveaux() {
        return TOTAL_NIVEAUX;
    }

    public List<Formation> getListFormation(){
        return listFormations;
    }

    public Formation getFormation(int n) {
        return listFormations.get(n);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Missiles> getMissilesDispo() {
        return missilesDispo;
    }

    public ZoneScore getZoneScore() {
        return zoneScore;
    }

    public ZoneInfo getZoneInfo() {
        return zoneInfo;
    }

    public Partie getPartie() {
        return ecran;
    }

    public int getNiveau_actuel() {
        return niveau_actuel;
    }

    public boolean isAfficherNiveau() {
        return afficherNiveau;
    }

    public void setAfficherNiveau(boolean a) {
        this.afficherNiveau = a;
    }

    /**
     * Initialise l'espace de jeu
     */
    private void init() {
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
    }

    /* Initialise le jeu et lance la boucle de jeu en temps réel */
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

    /* Condition d'arrêt du jeu 
     * @return true car on n'as pas encore de conditions d'arrêt */
    private boolean isGameRunning() {
        return true;
    }

    /** Dessine tous les éléments du jeu */
    public void draw() {

        if (getEtatJeu() == 0) {
            ecran.debut_partie_draw();
        }

        else if (getEtatJeu() == 1) { // Si nous sommes dans l'état qui affiche le niveau.
            ecran.niveau_affichage_draw();
        }

        else if (getEtatJeu() == 2) { // L'état 2 est l'état de jeu normal. 
            // On fait un fond noir.
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0.0, 0.0, 350, 350);

            // On dessine les éléments du jeu.
            zoneScore.draw();
            zoneInfo.draw(getPlayer().getHp());
            getPlayer().draw();
            getFormation(getNiveau_actuel()-1).draw();

            // On dessine les missiles lancés.
            for (Missiles m : getMissilesDispo()) {
                m.draw();
            }
        }

        // Si on atteint l'état 3, c'est la fin de partie ! 
        else {
            ecran.fin_partie_draw();
        }
    }


    /* Met a jour les attributs de tous les éléments du jeu */
    private void update() {
        
        if (getEtatJeu() == 0) {
            if (StdDraw.isKeyPressed(32)) {
                setEtatJeu(1);
            }
        }
        else if (getEtatJeu() == 1) {
            countdown_affichage_niveau--;
            if (countdown_affichage_niveau == 0) { // Si les 2s sont finies, on passe à l'état de jeu.
                setEtatJeu(2);
                countdown_affichage_niveau = 67; // On réinitialise le compteur d'affichage du niveau à 67.
            }
        }
        else if (getEtatJeu() == 2) {
            if (!getFormation(getNiveau_actuel()-1).niveauTermine()) {
                getFormation(getNiveau_actuel()-1).update();
            }


            player.update(getMissilesDispo());
            zoneScore.update(getScore(), getBestScore());

            for (Missiles missiles : missilesDispo) {
                missiles.update();
            }

            suppressionMissiles(getMissilesDispo()); // Supprime les missiles qu'il faut supprimer

            // Si la formation des monstres est vide et qu'il ne reste plus de niveaux, on
            // termine la partie. Donc on passe à l'état de fin de partie qu'est le 3.
            if (getFormation(getNiveau_actuel()-1).niveauTermine() && getNiveau_actuel() == getTotalNiveaux()) {
                setEtatJeu(3);
            }

            if (getFormation(getNiveau_actuel()-1).niveauTermine()){
                setNiveau_actuel(getNiveau_actuel()+1);
            }

        }

        else {
            if (StdDraw.isKeyPressed(32)){
                replay();
            }
        }

        // if(score == 3){
        // player.setHp(2);
        // }    
    }
    public void replay(){
        missilesDispo.clear();
        setAfficherNiveau(false);
        setNiveau_actuel(1);
        setCountdown_affichage_niveau(67);
        setPlayer(new Player(0.5, 0.25, 0.05, 3, 0.01));
        // Gérer les niveaux.
        listNiveaux.clear();
        listNiveaux.add(new Niveaux("../ressources/levels/level1.lvl"));
        listNiveaux.add(new Niveaux("../ressources/levels/level2.lvl"));
        listFormations.clear();
        for (int i = 0 ; i < listNiveaux.size(); i++) {  // Chaque formation aura l'indice "niveau_actuel - 1".
            listFormations.add(new Formation(listNiveaux.get(i).getMonstres()));
        }

        // Ecran d'accueil //
        setEcran(new Partie(getNiveau_actuel()));

        // Gérer le score : //
        if (getScore() >= getBestScore()){ // si le nouveau score est meilleur que l'ancien best score alors on le met à jour. 
            setBestScore(getScore());
        }
        setScore(0);

        setZoneScore(new ZoneScore(getScore(), getBestScore()));
        setZoneInfo( new ZoneInfo(0));
        setEtatJeu(0);
    }

    public void monstreTouche(Monster m) {
        m.degats(1);
        if (m.isDead()) {
            setScore(getScore() + m.getValeur());
            if (getScore() >= getBestScore()) {
                setBestScore(getScore());
            }  
        }
    }

    // supprime des missiles de la liste si ils dépassent le haut de l'écran.
    public void suppressionMissiles(List<Missiles> listMissiles) {
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

            for (Monster monster : getFormation(getNiveau_actuel()-1).getListeMonstres()) {
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

    

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMissilesDispo(List<Missiles> missilesDispo) {
        this.missilesDispo = missilesDispo;
    }

    public void setCountdown_affichage_niveau(int countdown_affichage_niveau) {
        this.countdown_affichage_niveau = countdown_affichage_niveau;
    }

    public void setNiveau_actuel(int niveau_actuel) {
        this.niveau_actuel = niveau_actuel;
    }

    public void setEtat_jeu(int etat_jeu) {
        this.etat_jeu = etat_jeu;
    }

    public void setListFormations(List<Formation> listFormations) {
        this.listFormations = listFormations;
    }

    public void setZoneScore(ZoneScore zoneScore) {
        this.zoneScore = zoneScore;
    }

    public void setZoneInfo(ZoneInfo zoneInfo) {
        this.zoneInfo = zoneInfo;
    }

    public void setEcran(Partie ecran) {
        this.ecran = ecran;
    }
}
