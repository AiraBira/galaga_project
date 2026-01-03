package game;
import java.util.ArrayList;
import java.util.List;
import engine.StdDraw;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import game.actors.base.*;
import game.actors.monsters.*;
import game.actors.zones.*;

/**
 * Classe du jeu principal.
 * Gère la création de l'espace de jeu et la boucle de jeu en temps réel.
 */
public class Game {

    /** Nombre total de niveaux disponibles dans le jeu */
    private static final int TOTAL_NIVEAUX = 2; // à changer lorqu'on rajoute des niveaux !!

    /* ------------- NIVEAUX ------------- */
    private static final Niveaux niveau1 = new Niveaux("../ressources/levels/level1.lvl");
    private static final Niveaux niveau2 = new Niveaux("../ressources/levels/level2.lvl");

    private List<Niveaux> listNiveaux;
    private List<Formation> listFormations;
    private int niveau_actuel;

    /* ------------- Entités ------------- */
    private Player player;
    private List<Missiles> missilesDispo;

    /* ------------- Scores ------------- */
    private int score;
    private int bestScore;

    /* ------------- Zones affichage ------------- */
    private ZoneScore zoneScore;
    private ZoneInfo zoneInfo;
    private ZoneCompteRebours zoneCompteRebours;
    private Partie ecran;

    /* ------------- Compte à rebours ------------- */
    private int compteRebours;
    private int compteRebours2;
    private int countdown_entre_vies;
    private int countdown_touche_espace;

    private boolean espace_a_ete_appuyee;
    private boolean afficherNiveau;

    /** Compteur servant à afficher le niveau pendant environ 2 secondes */
    private int countdown_affichage_niveau; // Comme le temps d'une boucle du jeu est 30 ms et qu'on veut 2s de pause.
                                            // 2s = 2000 ms et 2000/30 = à environ 67. Donc on doit faire 67 boucles
                                            // pour afficher le niveau pendant 2s.
    

    /** Active ou désactive le mode vies infinies */
    private static boolean viesInfinies = false; // Mode vies infinies

    /** Empêche l’activation multiple avec la touche 'i' */
    private boolean toucheIappuyee = false; // Avec la touche 'i'

    /**
     * État actuel du jeu :
     * 
     *   -1 : écran de sélection du niveau 
     *    0 : écran de début de partie
     *    1 : affichage du niveau pdt 2s
     *    2 : jeu normal 
     *    3 : victoire
     *    4 : pause après mort
     *    5 : game over
     * 
     */
    private int etat_jeu = -1; 

    /**
     * Construit le jeu.
     * Initialise tous les éléments du jeu, y compris le joueur, les niveaux,
     * les formations de monstres, les zones d'affichage, et les compteurs.
     */
    public Game() {

        /*  Gérer les affichages de chaque niveau.*/
        countdown_affichage_niveau = 67;
        afficherNiveau = false;

        /* Compteurs */
        countdown_entre_vies = 0;
        countdown_touche_espace = 0;
        espace_a_ete_appuyee = false;

        /*  Gérer les niveaux. */
        listNiveaux = new ArrayList<>(); // Si nouveau niveau, l'ajouter ici aussi !!
        listNiveaux.add(niveau1);
        listNiveaux.add(niveau2);
        niveau_actuel = 1;

        /*  Gérer le player et les missiles. */
        missilesDispo = new ArrayList<>();
        player = new Player(0.5, 0.25, 0.05, 3, 0.01);

        /*  On initialise au debut de la partie. */
        etat_jeu = -1; // Commence par sélection de niveau

        /*  Charger les formations de chaque niveau. */
        listFormations = new ArrayList<>();
        for (int i = 0; i < listNiveaux.size(); i++) { // Chaque formation aura l'indice "niveau_actuel - 1".
            Niveaux n = listNiveaux.get(i);
            listFormations.add(new Formation(n.getMonstres(), n.getVitesse(), n.getCooldownAttaques(), n.getCooldownTirs()));
        }

        /*  Ecran d'accueil */
        ecran = new Partie(getNiveau_actuel());

        /*  Gére le score  */
        score = 0;
        bestScore = chargementBestScore();
        
        /* Charge le meilleur score depuis le fichier */
        zoneScore = new ZoneScore(score, bestScore);

        /* Zones fonctionnalités */
        compteRebours = -1;
        compteRebours2 = 15;
        zoneCompteRebours = new ZoneCompteRebours(compteRebours);
        zoneInfo = new ZoneInfo(0);
    }

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

    /*
     * Condition d'arrêt du jeu
     * 
     * @return true car on n'as pas encore de conditions d'arrêt
     */
    private boolean isGameRunning() {
        return true;
    }

    /** Dessine tous les éléments du jeu **/
    public void draw() {

        if (getEtatJeu() == -1) { // Si nous sommes dans l'état de sélection de niveau.
            ecran.selection_niveau_draw();
        }

        else if (getEtatJeu() == 0) { // Si nous sommes dans l'état de début de partie.
            ecran.debut_partie_draw();
        }

        else if (getEtatJeu() == 1) { // Si nous sommes dans l'état qui affiche le niveau pendant 2s.
            ecran.niveau_affichage_draw();
        }

        else if (getEtatJeu() == 3) { // Si nous sommes dans l'état qui affiche la victoire.
            ecran.win_draw();
        }

        else if (getEtatJeu() == 5) { // Si nous sommes dans l'état de game over.
            ecran.gameOver_draw();
        } 

        else { // Si nous sommes dans l'état de jeu normal (2) ou de pause après mort (4). Dans les deux  cas on affiche tout.
                 
            StdDraw.setPenColor(StdDraw.BLACK); // On fait un fond noir.
            StdDraw.filledRectangle(0.0, 0.0, 350, 350);

            // On dessine les éléments du jeu.
            getPlayer().draw();
            getFormation(getNiveau_actuel() - 1).draw();

            // On dessine les missiles lancés.
            for (Missiles m : getMissilesDispo()) {
                m.draw();
            }

            // On dessine chaque zone d'affichage.
            zoneScore.draw();
            zoneCompteRebours.draw();
            zoneInfo.draw(getPlayer().getHp(), isViesInfinies());
        }
    }


    /* Met a jour les attributs de tous les éléments du jeu. */
    private void update() {

        // Si la touche 'i' est appuyée, on active ou désactive le mode vies infinies
        if ((StdDraw.isKeyPressed(73) || StdDraw.isKeyPressed(105)) && !isToucheIappuyee()) {
            setViesInfinies(!isViesInfinies());
            setToucheIappuyee(true);
        }
        else if (!StdDraw.isKeyPressed(73) && !StdDraw.isKeyPressed(105)) {
            setToucheIappuyee(false);
        }

        if (getEtatJeu() == -1) { // Si on est dans l'état de sélection de niveau.
            if (StdDraw.isKeyPressed(49)) { // Touche "1" lance le jeu depuis le niveau 1 
                replay(1);
                setEtatJeu(0);
            }
            else if (StdDraw.isKeyPressed(50)) { // Touche "2" lance le jeu depuis le niveau 2
                replay(2);
                setEtatJeu(0);
            }
        }
        else if (getEtatJeu() == 0) { // Si on est dans l'état de début de partie.
            if (StdDraw.isKeyPressed(32)) {
                setEtatJeu(1);
            }
        }
        else if (getEtatJeu() == 1) { // Si on est dans l'état d'affichage du niveau pendant 2s.
            //countdown_affichage_niveau--;
            setCountdown_affichage_niveau(getCountdown_affichage_niveau()-1);
            if (getCountdown_affichage_niveau() == 0) { // Si les 2s sont finies, on passe à l'état de jeu.
                setEtatJeu(2);
                setCountdown_affichage_niveau(67); // On réinitialise le compteur d'affichage du niveau à 67.
            }
        }
        //// ÉTAT DE JEU NORMAL ////
        else if (getEtatJeu() == 2) {
            // Met à jour tous les éléments du jeu
            if (!getFormation(getNiveau_actuel() - 1).niveauTermine()) {
                getFormation(getNiveau_actuel() - 1).update(getPlayer(), isViesInfinies());
            }
            player.update(getMissilesDispo());
            ecran.update(getScore(), getBestScore());
            zoneScore.update(getScore(), getBestScore());
            zoneCompteRebours.update(getCompteRebours());
            playerTouche(); // Fonction qui vérifie si le player a été touché, se trouve plus bas dans ce fichier.

            // Met à jour les missiles du joueur
            for (Missiles missiles : getMissilesDispo()) { 
                missiles.update();
            }
            // Met a jour les missiles des monstres
            for (Missiles missiles : getFormation(getNiveau_actuel() - 1).getListeMissilesEnnemis()) {
                missiles.update();
            }

            suppressionMissilesPlayer(getMissilesDispo()); // Supprime les missiles qu'il faut supprimer

            // Si la formation des monstres est vide et qu'il ne reste plus de niveaux, on
            // termine la partie. Donc on passe à l'état de fin de partie qu'est le 3.
            if (getFormation(getNiveau_actuel() - 1).niveauTermine() && getNiveau_actuel() == getTotalNiveaux()) {
                setEtatJeu(3);
            }
            // Sinon si la formation est vide mais qu'il reste des niveaux, on passe au niveau suivant.
            else if (getFormation(getNiveau_actuel() - 1).niveauTermine()) { 
                setNiveau_actuel(getNiveau_actuel() + 1);
                player.setPosX(0.5);
                player.setPosY(0.25);
                Partie ecran_new = new Partie(getNiveau_actuel());
                setEcran(ecran_new);
                setEtatJeu(1);
            }
        }
        else if (getEtatJeu() == 4) { // Si on est dans l'état de pause tous les éléments ne s'update pas et donc
                                      // reste sur place. On a juste un minuteur.
            zoneCompteRebours.update(getCompteRebours());
            if (getCompteRebours() < 0) {
                // playerPauseTouche = false; // Ce n'est pas une pause manuelle.
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
        else { // SI ON EST DANS LES ETATS 3 OU 5 ON A LA MEME FONCTIONNALITE MAIS AVEC
                 // DIFFERENTS DRAW C TOUT
            if (getScore() >= getBestScore()) {
                setBestScore(getScore());
            }
            if (StdDraw.isKeyPressed(32) && !getEspace_a_ete_appuyee()) {
                setEspace_a_ete_appuyee(true);
                setCountdown_espace(10);
            }
            if (getEspace_a_ete_appuyee()) {
                if (getEspace_a_ete_appuyee() && getCountdown_espace() > 0) {
                    setCountdown_espace(getCountdown_espace() - 1);
                } else { // Une fois qu'on arrive à 0 on remet l'espace a false et on replay
                    setEspace_a_ete_appuyee(false);
                    setEtatJeu(-1);
                }
            }
        }
    }


    /**
     * Réinitialise le jeu pour recommencer une partie.
     *
     * @param level est le niveau à charger
     */
    public void replay(int level) {
        missilesDispo.clear();
        setAfficherNiveau(false);
        setNiveau_actuel(level); // Réinitialise au niveau que l'on veut
        setCountdown_affichage_niveau(67);
        setPlayer(new Player(0.5, 0.25, 0.05, 3, 0.01));
        // Gérer les niveaux.
        listNiveaux.clear();
        listNiveaux.add(new Niveaux("../ressources/levels/level1.lvl"));
        listNiveaux.add(new Niveaux("../ressources/levels/level2.lvl"));
        listFormations.clear();
        for (int i = 0; i < listNiveaux.size(); i++) { // Chaque formation aura l'indice "niveau_actuel - 1".
            Niveaux n = listNiveaux.get(i);
            listFormations
                    .add(new Formation(n.getMonstres(), n.getVitesse(), n.getCooldownAttaques(), n.getCooldownTirs()));
        }

        // Ecran d'accueil //
        setEcran(new Partie(getNiveau_actuel()));

        // Gérer le score : //
        if (getScore() >= getBestScore()) { // si le nouveau score est meilleur que l'ancien best score alors on le met
                                            // à jour.
            setBestScore(getScore());
        }
        setScore(0);

        setZoneScore(new ZoneScore(getScore(), getBestScore()));
        setZoneInfo(new ZoneInfo(0));
        setEtatJeu(-1); // Retour à la sélection de niveau
    }

    /**
     * Gère l'impact d'un missile du joueur sur un monstre. 
     * Si le monstre entré en paramètres est mort, change le score en y ajoutant la valeur du monstre et y provoque des dégats.
     *
     * @param m monstre que l'on verifie
     */
    public void monstreTouche(Monster m) {
        m.degats(1);
        if (m.isDead()) {
            setScore(getScore() + m.getValeur());
            // Si le monstre est un Moth qui avait capturé le joueur, rendre la vie volée
            if (m instanceof Moth) {
                Moth moth = (Moth) m;
                if (moth.isCapture()) {
                    getPlayer().gagnerVie();
                }
            }
            // Si nouveau meilleur score, mettre à jour et sauvegarder
            if (getScore() > getBestScore()) {
                setBestScore(getScore());
                saveNewBestScore();
                setZoneScore(new ZoneScore(getScore(), getBestScore()));
            }
        }
    }
    

    /**
     * Vérifie si le joueur est touché par un missile ou un monstre.
     */
    public void playerTouche() {

        double hautPlayer = player.getPosY() + player.getLength() / 2;
        double basPlayer = player.getPosY() - player.getLength() / 2;
        double gauchePlayer = player.getPosX() - player.getLength() / 2;
        double droitePlayer = player.getPosX() + player.getLength() / 2;

        boolean collisionXMissile = false;
        boolean collisionYMissile = false;
        boolean missileTouche = false;

        boolean collisionXMonstre = false;
        boolean collisionYMonstre = false;
        boolean monstreTouche = false;

        List<Missiles> listMis = getFormation(getNiveau_actuel() - 1).getListeMissilesEnnemis(); // On range dans une
                                                                                                 // variable pour pas
                                                                                                 // faire multiples
                                                                                                 // appels.

        for (int i = listMis.size() - 1; i >= 0; i--) { // Si on fait à l'envers, quand on aura a supprimer un élément
                                                        // de la liste, on supprime l'actuel
                                                        // et les autres indices ne changeront pas !
            double hautMissile = listMis.get(i).getPosY() + listMis.get(i).getLongueur();
            double basMissile = listMis.get(i).getPosY() - listMis.get(i).getLongueur();
            double gaucheMissile = listMis.get(i).getPosX() - listMis.get(i).getLargeur();
            double droiteMissile = listMis.get(i).getPosX() + listMis.get(i).getLargeur();

            collisionXMissile = droiteMissile >= gauchePlayer && gaucheMissile <= droitePlayer;
            collisionYMissile = hautMissile >= basPlayer && basMissile <= hautPlayer;

            if (collisionXMissile && collisionYMissile) {
                listMis.remove(i);
                missileTouche = true;
            }
        }

        // Une collision est possible que avec les monstres hors formations qui nous
        // attaquent !
        List<Monster> listMon = getFormation(getNiveau_actuel() - 1).getListeMonstresHorsFormation();

        for (int i = listMon.size() - 1; i >= 0; i--) {
            double hautMonstre = listMon.get(i).getPosY() + listMon.get(i).getLength();
            double basMonstre = listMon.get(i).getPosY() - listMon.get(i).getLength();
            double droiteMonstre = listMon.get(i).getPosX() + listMon.get(i).getLargeur();
            double gaucheMonstre = listMon.get(i).getPosX() - listMon.get(i).getLargeur();

            collisionXMonstre = droiteMonstre >= gauchePlayer && gaucheMonstre <= droitePlayer;
            collisionYMonstre = hautMonstre >= basPlayer && basMonstre <= hautPlayer;

            if (collisionXMonstre && collisionYMonstre) {
                monstreTouche(listMon.get(i));
                listMon.remove(i);
                monstreTouche = true;
            }
        }

        if (missileTouche || monstreTouche) {
            getFormation(getNiveau_actuel() - 1).setListeMissilesEnnemis(listMis);
            player.perdreVie(isViesInfinies());
            // Si le player est mort on fini la partie
            if (player.isDead()) {
                setEtatJeu(5);
            }
            // Si le player a encore des vies alors on continue la partie avec une vie en
            // moins
            // On lance un compte à rebours afin de réaparaitre
            else {
                // On remet le tout en formation
                // Suppression de tous les missiles :
                setMissilesDispo(new ArrayList<>());
                getFormation(getNiveau_actuel() - 1).setListeMissilesEnnemis(new ArrayList<>());
                // Réinitialisation de la position du player et de la formation :
                getFormation(getNiveau_actuel() - 1).recommencer();
                player.setPosX(player.getXinit());
                player.setPosY(player.getYinit());
                setCompteRebours(3); // On remet le compteur à 3 pour commencer à compter le temps avant le retour
                                     // dans la partie.
                setCompteRebours2(15);
                setEtatJeu(4);
            }
        }
    }

    /**
     * Supprime les missiles du joueur lorsqu'ils sortent de l'écran
     * ou entrent en collision avec un monstre.
     *
     * @param listMissiles liste des missiles du joueur
     */
    public void suppressionMissilesPlayer(List<Missiles> listMissiles) {
        // On supprime les missiles de la liste qui dépassent le haut de l'écran.
        // On fait à l'envers pour que lorsqu'on supprime, les indices des autres
        // éléments ne changent pas vu qu'on supprimera
        // toujours le dernier élément !
        for (int i = listMissiles.size() - 1; i >= 0; i--) {
            if (listMissiles.get(i).getPosY() + listMissiles.get(i).getLongueur() > 0.91) {
                listMissiles.remove(i);
                break;
            }
        }

        // On supprime les missiles en collision avec des monstres, qu'ils soient dans
        // la formation ou hors formation.
        for (int i = listMissiles.size() - 1; i >= 0; i--) {
            double hautMissile = listMissiles.get(i).getPosY() + (listMissiles.get(i).getLongueur());
            double gaucheMissile = listMissiles.get(i).getPosX() - (listMissiles.get(i).getLargeur());
            double droiteMissile = listMissiles.get(i).getPosX() + (listMissiles.get(i).getLargeur());

            // MONSTRES DANS LA FORMATION
            List<Monster> listMon = getFormation(getNiveau_actuel() - 1).getListeMonstres();
            for (int j = listMon.size() - 1; j >= 0; j--) {
                double basMonstre = listMon.get(j).getPosY() - (listMon.get(j).getLength() / 2);
                if (((gaucheMissile <= listMon.get(j).getPosX() + (listMon.get(j).getLength() / 2)) &&
                        (droiteMissile >= listMon.get(j).getPosX() - (listMon.get(j).getLength() / 2))) &&
                        (hautMissile >= basMonstre)) {

                    // Si un monstre est touché :
                    monstreTouche(listMon.get(j));
                    listMissiles.remove(i);
                    break;
                }
            }

            // MONSTRES HORS FORMATION (CEUX QUI ATTAQUENT LE PLAYER)

            List<Monster> listMonHorsFormation = getFormation(getNiveau_actuel() - 1).getListeMonstresHorsFormation();
            for (int j = listMonHorsFormation.size() - 1; j >= 0; j--) {
                double basMonstre = listMonHorsFormation.get(j).getPosY()
                        - (listMonHorsFormation.get(j).getLength() / 2);
                if (((gaucheMissile <= listMonHorsFormation.get(j).getPosX()
                        + (listMonHorsFormation.get(j).getLength() / 2)) &&
                        (droiteMissile >= listMonHorsFormation.get(j).getPosX()
                                - (listMonHorsFormation.get(j).getLength() / 2)))
                        &&
                        (hautMissile >= basMonstre)) {

                    // Si un monstre est touché :
                    monstreTouche(listMonHorsFormation.get(j));
                    listMissiles.remove(i);
                    break;
                }

            }
        }
    }

    /////////////////////// Gérer le Highscore du fichier ////////////////////////
    /// Ici j'ai utilisé l'IA pour m'aider à écrire ces fonctions de gestion du highscore, 
    // tout est écrit dans le fichier IAGenerative !
    
    /*** Charge la valeur du best score depuis le fichier. 
     * @return l'entier representant le meilleur score jamais eu
     * 
     * ***/ 
    public int chargementBestScore() {
        File f = new File("../ressources/highscore/highscore.sc");
        if (!f.exists()) { // Si le fichier n'existe pas, on le crée et on initialise le best score à 0.
            try {
                if (f.getParentFile() != null) // On crée les dossiers parents si ils n'existent pas.
                    f.getParentFile().mkdirs();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) { // Une fois tout crée, on crée le
                                                                                  // fichier
                                                                                  // et on écrit 0 dedans.
                    bw.write("0");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0; // On initialise le best score à 0.
        }
        // Dans le cas où le fichier existe, on lit son contenu pour récupérer le best
        // score.
        try (BufferedReader lecture = new BufferedReader(new FileReader(f))) {
            String ligne = lecture.readLine();
            if (ligne != null) { // On vérifie qu'il y a bien écrit quelque chose dans le fichier.
                try {
                    return Integer.parseInt(ligne.trim()); // On essaie de parser le contenu en entier et on le
                                                           // retourne.
                } catch (NumberFormatException e) { // Si jamais il y a une erreur de parsing, on initialise le best
                                                    // score à 0.
                    return 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Sauvegarde le meilleur score actuel dans le fichier.
     */
    public void saveNewBestScore() {
        File f = new File("../ressources/highscore/highscore.sc"); // Emplacement du fichier.
        try {
            if (f.getParentFile() != null) // On crée les dossiers parents si ils n'existent pas.
                f.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) { // Si tout va bien, on écrit le nouveau
                                                                              // best score dans le fichier.
                bw.write(Integer.toString(getBestScore()));
            }
        } catch (IOException e) { // En cas d'erreur, on a une exception.
            e.printStackTrace();
        }
    }


    /* -----     GETTERS ET SETTERS    ----- */
    
    /**
     * Retourne l'état actuel du jeu.
     * 
     * @return l'état du jeu
     */
    public int getEtatJeu() {
        return etat_jeu;
    }

    /**
     * Définit un nouvel état pour le jeu.
     * 
     * @param nouvelEtat le nouvel état à définir
     */
    private void setEtatJeu(int nouvelEtat) {
        this.etat_jeu = nouvelEtat;
    }

    /**
     * Retourne le score actuel du joueur.
     * 
     * @return le score actuel
     */
    public int getScore() {
        return score;
    }

    /**
     * Retourne le meilleur score enregistré.
     * 
     * @return le meilleur score
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * Définit un nouveau score.
     * 
     * @param score le nouveau score à définir
     */
    private void setScore(int score) {
        this.score = score;
    }

    /**
     * Définit un nouveau meilleur score.
     * 
     * @param bestScore le nouveau meilleur score à définir
     */
    private void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    /**
     * Retourne le nombre total de niveaux disponibles dans le jeu.
     * 
     * @return le nombre total de niveaux
     */
    public static int getTotalNiveaux() {
        return TOTAL_NIVEAUX;
    }

    /**
     * Retourne la liste des formations de monstres.
     * 
     * @return la liste des formations
     */
    public List<Formation> getListFormation() {
        return listFormations;
    }

    /**
     * Retourne la formation de monstres pour un niveau donné.
     * 
     * @param n le numéro du niveau (n+1 en réalité car on entre toujours niveau_actuel - 1)
     * @return la formation de monstres correspondante
     */
    public Formation getFormation(int n) {
        return listFormations.get(n);
    }

    /**
     * Retourne le joueur actuel.
     * 
     * @return le joueur
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Retourne la liste des missiles disponibles.
     * 
     * @return la liste des missiles
     */
    public List<Missiles> getMissilesDispo() {
        return missilesDispo;
    }

    /**
     * Retourne la zone de score.
     * 
     * @return la zone de score
     */
    public ZoneScore getZoneScore() {
        return zoneScore;
    }

    /**
     * Retourne la zone d'information.
     * 
     * @return la zone d'information
     */
    public ZoneInfo getZoneInfo() {
        return zoneInfo;
    }

    /**
     * Retourne l'écran de la partie.
     * 
     * @return l'écran de la partie
     */
    public Partie getPartie() {
        return ecran;
    }

    /**
     * Retourne le niveau actuel.
     * 
     * @return le niveau actuel
     */
    public int getNiveau_actuel() {
        return niveau_actuel;
    }

    /**
     * Indique si le niveau doit être affiché.
     * 
     * @return true si le niveau doit être affiché, false sinon
     */
    public boolean isAfficherNiveau() {
        return afficherNiveau;
    }

    /**
     * Définit si le niveau doit être affiché.
     * 
     * @param a true pour afficher le niveau, false sinon
     */
    private void setAfficherNiveau(boolean a) {
        this.afficherNiveau = a;
    }

    /**
     * Retourne le compte à rebours pour la touche espace.
     * 
     * @return le compte à rebours pour la touche espace
     */
    public int getCountdown_espace() {
        return countdown_touche_espace;
    }

    /**
     * Définit le compte à rebours pour la touche espace.
     * 
     * @param c le nouveau compte à rebours
     */
    private void setCountdown_espace(int c) {
        this.countdown_touche_espace = c;

    }

    /**
     * Indique si la touche espace a été appuyée.
     * 
     * @return true si la touche espace a été appuyée, false sinon
     */
    public boolean getEspace_a_ete_appuyee() {
        return espace_a_ete_appuyee;
    }

    /**
     * Définit si la touche espace a été appuyée.
     * 
     * @param b true si la touche espace a été appuyée, false sinon
     */
    private void setEspace_a_ete_appuyee(boolean b) {
        this.espace_a_ete_appuyee = b;
    }

    /**
     * Définit le joueur actuel.
     * @param player le joueur à définir
     */
    private void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Définit la liste des missiles disponibles.
     * @param missilesDispo la liste des missiles à définir
     */
    private void setMissilesDispo(List<Missiles> missilesDispo) {
        this.missilesDispo = missilesDispo;
    }

    /**
     * Définit le compte à rebours pour l'affichage du niveau.
     * @param countdown_affichage_niveau le nouveau compte à rebours
     */
    private void setCountdown_affichage_niveau(int countdown_affichage_niveau) {
        this.countdown_affichage_niveau = countdown_affichage_niveau;
    }

    /**
     * Définit le niveau actuel.
     * @param niveau_actuel le niveau à définir
     */
    private void setNiveau_actuel(int niveau_actuel) {
        this.niveau_actuel = niveau_actuel;
    }

    /**
     * Définit la zone de score.
     * @param zoneScore la zone de score à définir
     */
    private void setZoneScore(ZoneScore zoneScore) {
        this.zoneScore = zoneScore;
    }

    /**
     * Définit la zone d'information.
     * @param zoneInfo la zone d'information à définir
     */
    private void setZoneInfo(ZoneInfo zoneInfo) {
        this.zoneInfo = zoneInfo;
    }

    /**
     * Définit l'écran de la partie.
     * @param ecran l'écran à définir
     */
    private void setEcran(Partie ecran) {
        this.ecran = ecran;
    }

    /**
     * Retourne le niveau 1.
     * @return le niveau 1
     */
    public static Niveaux getNiveau1() {
        return niveau1;
    }

    /**
     * Retourne le niveau 2.
     * @return le niveau 2
     */
    public static Niveaux getNiveau2() {
        return niveau2;
    }

    /**
     * Retourne la liste des niveaux.
     * @return la liste des niveaux
     */
    public List<Niveaux> getListNiveaux() {
        return listNiveaux;
    }

    /**
     * Retourne la liste des formations.
     * @return la liste des formations
     */
    public List<Formation> getListFormations() {
        return listFormations;
    }

    /**
     * Retourne la zone de compte à rebours.
     * @return la zone de compte à rebours
     */
    public ZoneCompteRebours getZoneCompteRebours() {
        return zoneCompteRebours;
    }

    /**
     * Retourne la zone de compte à rebours.
     * @return la zone de compte à rebours
     */
    public int getCompteRebours() {
        return compteRebours;
    }

    /**
     * Définit la zone de compte à rebours.
     * @param zoneCompteRebours la zone de compte à rebours à définir
     */
    private void setCompteRebours(int compteRebours) {
        this.compteRebours = compteRebours;
    }

    /**
     * Retourne l'écran de la partie.
     * @return l'écran de la partie
     */
    public Partie getEcran() {
        return ecran;
    }

    /**
     * Retourne le compte à rebours pour la touche espace.
     * @return le compte à rebours pour la touche espace
     */
    public int getCountdown_touche_espace() {
        return countdown_touche_espace;
    }

    /**
     * Retourne le compteur d'affichage du niveau.
     * @return le compteur d'affichage du niveau
     */
    public int getCountdown_affichage_niveau() {
        return countdown_affichage_niveau;
    }

    /**
     * Retourne l'état actuel du jeu.
     * @return l'état du jeu
     */
    public int getEtat_jeu() {
        return etat_jeu;
    }

    /**
     * Retourne le countdown entre vies.
     * @return le countdown entre vies
     */
    public int getCountdown_entre_vies() {
        return countdown_entre_vies;
    }

    /**
     * Définit le countdown entre vies.
     * @param n le countdown entre vies à définir
     */
    public void setCountdown_entre_vies(int n) {
        this.countdown_entre_vies = n;
    }

    /**
     * Retourne le compte à rebours 2.
     * @return le compte à rebours 2
     */
    public int getCompteRebours2() {
        return compteRebours2;
    }

    /**
     * Définit le compte à rebours 2.
     * @param compteRebours2 le compte à rebours 2 à définir
     */
    public void setCompteRebours2(int compteRebours2) {
        this.compteRebours2 = compteRebours2;
    }

    /**
     * Indique si le mode vies infinies est activé.
     * @return true si le mode vies infinies est activé, false sinon
     */
    public static boolean isViesInfinies() {
        return viesInfinies;
    }

    /**
     * Définit si le mode vies infinies est activé.
     * @param viesInfinies true pour activer le mode vies infinies, false pour le désactiver
     */
    public static void setViesInfinies(boolean viesInfinies) {
        Game.viesInfinies = viesInfinies;
    }

    /**
     * Indique si la touche 'i' a été appuyée.
     * @return true si la touche 'i' a été appuyée, false sinon
     */
    public boolean isToucheIappuyee() {
        return toucheIappuyee;
    }

    /**
     * Définit si la touche 'i' a été appuyée.
     * @param toucheIappuyee true si la touche 'i' a été appuyée, false sinon
     */
    public void setToucheIappuyee(boolean toucheIappuyee) {
        this.toucheIappuyee = toucheIappuyee;
    }

    ////////////////////////////////////////////////////////////////////////////////// 
}
