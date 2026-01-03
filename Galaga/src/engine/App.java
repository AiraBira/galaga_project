package engine;

import game.Game;

/**
 * Classe de lancement du projet
 * 
 * @author JRIRIA ATIGUI Abir 
 * @author LOGMO TONDI Victoria
 */

public class App {

    /**
     * Constructeur par défaut pour régler le javadoc.
     */
    public App() {
    }

    /** Point d'entrée principal de l'application.
     *
     * @param args les arguments de la ligne de commande
     * @throws Exception en cas d'erreur lors de l'exécution
     */
    public static void main(String[] args) throws Exception {
        // Création d'un nouveau jeu et lancement de celui-ci
        Game g = new Game();
        g.launch();
    }
}
