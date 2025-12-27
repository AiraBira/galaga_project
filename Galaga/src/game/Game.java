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
    public ZoneCompteRebours zoneCompteRebours;
    public int compteRebours;
    public int compteRebours2;
    public Partie ecran;

    public int countdown_entre_vies;
    public int countdown_touche_espace;
    public boolean espace_a_ete_appuyee;
    public int countdown_affichage_niveau; // Comme le temps d'une boucle du jeu est 30 ms et qu'on veut 2s de pause.
                                           // 2s = 2000 ms et 2000/30 = à environ 67. Donc on doit faire 67 boucles
                                           // pour afficher le niveau pendant 2s.
    public int niveau_actuel;
    public boolean afficherNiveau;

    public boolean playerPauseTouche; // Nous permet de différencier la pause manuelle et la pause avec minuteur.
    public int etat_jeu = 0; // Si 0 -> Ecran debut de partie
                             // Si 1 -> Affichage niveau
                             // Si 2 -> Jeu
                             // Si 3 -> Winner ! à faire 
                             // Si 4 -> Etat de pause
                             // Si 5 -> Game Over ! à faire 
                            
    /* Créé un jeu avec tous les éléments qui le composent */
    public Game() {

        countdown_entre_vies = 0;
        countdown_touche_espace = 0;
        espace_a_ete_appuyee = false;

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
        playerPauseTouche = false;

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

        // Zones fonctionnalités //
        compteRebours = -1;
        compteRebours2 = 15;
        zoneCompteRebours = new ZoneCompteRebours(compteRebours);
        zoneInfo = new ZoneInfo(0);
    }


    ////////////////////////////// GETTERS ET SETTERS ///////////////////////////////////
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

    public int getCountdown_espace(){
        return countdown_touche_espace;
    }

    public void setCountdown_espace(int c){
        this.countdown_touche_espace = c;
        
    }
    
    public boolean getEspace_a_ete_appuyee(){
        return espace_a_ete_appuyee;
    }

    public void setEspace_a_ete_appuyee(boolean b){
        this.espace_a_ete_appuyee = b;
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

    public static Niveaux getNiveau1() {
        return niveau1;
    }


    public static Niveaux getNiveau2() {
        return niveau2;
    }


    public List<Niveaux> getListNiveaux() {
        return listNiveaux;
    }


    public void setListNiveaux(List<Niveaux> listNiveaux) {
        this.listNiveaux = listNiveaux;
    }


    public List<Formation> getListFormations() {
        return listFormations;
    }


    public ZoneCompteRebours getZoneCompteRebours() {
        return zoneCompteRebours;
    }


    public void setZoneCompteRebours(ZoneCompteRebours zoneCompteRebours) {
        this.zoneCompteRebours = zoneCompteRebours;
    }


    public int getCompteRebours() {
        return compteRebours;
    }


    public void setCompteRebours(int compteRebours) {
        this.compteRebours = compteRebours;
    }


    public Partie getEcran() {
        return ecran;
    }


    public int getCountdown_touche_espace() {
        return countdown_touche_espace;
    }


    public void setCountdown_touche_espace(int countdown_touche_espace) {
        this.countdown_touche_espace = countdown_touche_espace;
    }


    public int getCountdown_affichage_niveau() {
        return countdown_affichage_niveau;
    }


    public int getEtat_jeu() {
        return etat_jeu;
    }

    public int getCountdown_entre_vies(){
        return countdown_entre_vies;
    }

    public void setCountdown_entre_vies(int n){
        this.countdown_entre_vies = n;
    }

    public int getCompteRebours2() {
        return compteRebours2;
    }


    public void setCompteRebours2(int compteRebours2) {
        this.compteRebours2 = compteRebours2;
    }

    //////////////////////////////////////////////////////////////////////////////////:
    
    /*** Initialise l'espace de jeu ***/
    private void init() {
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
    }

    /*** Initialise le jeu et lance la boucle de jeu en temps réel ***/
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

    /** Dessine tous les éléments du jeu **/
    public void draw() {

        if (getEtatJeu() == 0) {
            ecran.debut_partie_draw();
        }

        else if (getEtatJeu() == 1) { // Si nous sommes dans l'état qui affiche le niveau.
            ecran.niveau_affichage_draw();
        }

        // Si on atteint l'état 3, c'est la fin de partie si le joueur à gagné! 
        else if (getEtatJeu() == 3){
            ecran.win_draw();
        }
        // Si on atteint l'état 5 , c'est la fin de partie si le joueur a perdu !
        else if (getEtatJeu() == 5){
            ecran.gameOver_draw();
        }
        else { // L'état 2 est l'état de jeu normal et l'état 4 est l'état en pause dans lequel on affiche tout également. 
            // On fait un fond noir.
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0.0, 0.0, 350, 350);

            // On dessine les éléments du jeu.
            zoneScore.draw();
            zoneInfo.draw(getPlayer().getHp());
            zoneCompteRebours.draw();
            getPlayer().draw();
            getFormation(getNiveau_actuel()-1).draw();

            // On dessine les missiles lancés.
            for (Missiles m : getMissilesDispo()) {
                m.draw();
            }
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
                getFormation(getNiveau_actuel()-1).update(player);
            }
            player.update(getMissilesDispo());
            ecran.update(getScore(), getBestScore());
            zoneScore.update(getScore(), getBestScore());
            zoneCompteRebours.update(compteRebours);
            playerTouche();
            
            for (Missiles missiles : missilesDispo) {
                missiles.update();
            }
            for (Missiles missiles : getFormation(getNiveau_actuel()-1).getListeMissilesEnnemis()) {
                missiles.update();
            }

            suppressionMissilesPlayer(getMissilesDispo()); // Supprime les missiles qu'il faut supprimer

            // Si la formation des monstres est vide et qu'il ne reste plus de niveaux, on
            // termine la partie. Donc on passe à l'état de fin de partie qu'est le 3.
            if (getFormation(getNiveau_actuel()-1).niveauTermine() && getNiveau_actuel() == getTotalNiveaux()) {
                setEtatJeu(3);
            }
            else if (getFormation(getNiveau_actuel()-1).niveauTermine()){
                setNiveau_actuel(getNiveau_actuel()+1);
                player.setPosX(0.5);
                player.setPosY(0.25);
                Partie ecran_new = new Partie(getNiveau_actuel());
                setEcran(ecran_new);
                setEtatJeu(1);
            }
        }
        else if (getEtatJeu() == 4) { // Si on est dans l'état de pause tous les éléments ne s'update pas et donc reste sur place. On a juste un minuteur.
            zoneCompteRebours.update(compteRebours);
            if (getCompteRebours() < 0) {
                playerPauseTouche = false; // Ce n'est pas une pause manuelle.
                setEtatJeu(2);
            }
            else {
                if (getCompteRebours2() <= 0) { // Deuxième minuteur pour le minuteur principal.
                    setCompteRebours(getCompteRebours() - 1);
                    setCompteRebours2(15);
                }
                else {
                    setCompteRebours2(getCompteRebours2() - 1);
                }
            }
        }
        else { // SI ON EST DANS LES ETATS 3 OU 5 ON A LA MEME FONCTIONNALITE MAIS AVEC DIFFERENTS DRAW C TOUT 
            if (getScore() >= getBestScore()) {
                setBestScore(getScore());
            }  
            if (StdDraw.isKeyPressed(32) && !getEspace_a_ete_appuyee()){
                setEspace_a_ete_appuyee(true);
                setCountdown_espace(30);
            }
            
            if (getEspace_a_ete_appuyee()) {
                if (getEspace_a_ete_appuyee() && getCountdown_espace() > 0) {
                    setCountdown_espace(getCountdown_espace()-1);
                }
                else { // Une fois qu'on arrive à 0 on remet l'espace a false et on replay
                    setEspace_a_ete_appuyee(false);
                    replay();
                }
            }
        }
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
        }
    }

    // On verifie les collisions des missiles ennemis contre notre player.
    public void playerTouche(){
        List<Missiles> listMis = getFormation(getNiveau_actuel()-1).getListeMissilesEnnemis(); // On range dans une variable pour pas faire multiples appels.

        for (int i = listMis.size()-1; i >= 0; i--){ // Si on fait à l'envers, quand on aura a supprimer un élément de la liste, on supprime l'actuel
                                                        // et les autres indices ne changeront pas !
            double hautMissile = listMis.get(i).getPosY() + listMis.get(i).getLongueur();
            double basMissile = listMis.get(i).getPosY() - listMis.get(i).getLongueur();
            double gaucheMissile = listMis.get(i).getPosX() - listMis.get(i).getLargeur();
            double droiteMissile = listMis.get(i).getPosX() + listMis.get(i).getLargeur();

            double hautPlayer = player.getPosY() + player.getLength() / 2;
            double basPlayer = player.getPosY() - player.getLength() / 2;
            double gauchePlayer = player.getPosX() - player.getLength() / 2;
            double droitePlayer = player.getPosX() + player.getLength() / 2;

            boolean collisionX = droiteMissile >= gauchePlayer && gaucheMissile <= droitePlayer;
            boolean collisionY = hautMissile >= basPlayer && basMissile <= hautPlayer;

            if (collisionX && collisionY){
                listMis.remove(i);
                getFormation(getNiveau_actuel()-1).setListeMissilesEnnemis(listMis);
                player.perdreVie();
                // Si le player est mort on fini la partie 
                if (player.isDead()){ 
                    setEtatJeu(5);
                }
                // Si le player a encore des vies alors on continue la partie avec une vie en moins 
                // On lance un compte à rebours afin de réaparaitre
                else {
                    // On remet le tout en formation 
                    playerPauseTouche = true; // Pour faire un bouton pause plus tard ? ? ?
                    // Suppression de tous les missiles : 
                    setMissilesDispo(new ArrayList<>());
                    getFormation(getNiveau_actuel()-1).setListeMissilesEnnemis(new ArrayList<>());
                    // Réinitialisation de la position du player et de la formation : 
                    getFormation(getNiveau_actuel()-1).recommencer();
                    player.setPosX(player.getXinit());
                    player.setPosY(player.getYinit());
                    setCompteRebours(3); // On remet le compteur à 3 pour commencer à compter le temps avant le retour dans la partie.
                    setCompteRebours2(15);
                    setEtatJeu(4);
                }
                break;
            }
        }
    }

    public void suppressionMissilesPlayer(List<Missiles> listMissiles) {
        // On supprime les missiles de la liste qui dépassent le haut de l'écran.
        // On fait à l'envers pour que lorsqu'on supprime, les indices des autres éléments ne changent pas vu qu'on supprimera 
        // toujours le dernier élément ! 
        for (int i = listMissiles.size()-1; i >= 0; i--){
            if (listMissiles.get(i).getPosY() + listMissiles.get(i).getLongueur() > 0.91) {
                listMissiles.remove(i);
            }
        }

        // On supprime les missiles en collision avec des monstres, qu'ils soient dans la formation ou hors formation.
        for (int i = listMissiles.size()-1; i >= 0; i--) {
            double hautMissile = listMissiles.get(i).getPosY() + (listMissiles.get(i).getLongueur());
            double gaucheMissile = listMissiles.get(i).getPosX() - (listMissiles.get(i).getLargeur());
            double droiteMissile = listMissiles.get(i).getPosX() + (listMissiles.get(i).getLargeur());

            // DANS FORMATION
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

            // HORS FORMATION
            for (Monster monster : getFormation(getNiveau_actuel()-1).getListeMonstresHorsFormation()) {
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
}
