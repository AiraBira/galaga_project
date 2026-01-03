package game;
import game.actors.base.Monster;
import game.actors.monsters.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Représente un niveau du jeu.
 * Classe responsable du chargement des données d'un niveau à partir
 * d'un fichier texte (.lvl). Elle initialise : la liste des monstres, la vitesse de la formation et les délais entre attaques et tirs.
 *
 * ! Nous avons utilisé de l'IA dans cette partie de lecture du fichier .lvl et l'utiliser.
 */
public class Niveaux { 
    /** Liste des monstres présents dans le niveau */
    private List<Monster> monstres = new ArrayList<>();

    /** Nom du niveau tel qu'indiqué dans le fichier (ex : "Level1") */
    private String niveau;

    /** Vitesse de déplacement de la formation de monstres */
    private Double vitesse;

    /** Temps entre chaque attaques de monstres */
    private int cooldownAttaques;

    /** Temps entre chaque tir de monstres */
    private int cooldownTirs;


    /**
     * Construit un niveau à partir d'un fichier de configuration.
     * Le fichier doit contenir :
     * Une première ligne de configuration générale dans lequel on trouve le nom du niveau (on en sort le numéro),
     * la vitesse de la formation, le cooldown des attaques et le cooldown des tirs,
     * Plus, une ligne par monstre avec ses caractéristiques.
     *
     * @param fichierLevel chemin vers le fichier du niveau
     */
    public Niveaux(String fichierLevel) {
        try {
            // Création d'un objet File représentant le fichier de niveau à charger
            File f = new File(fichierLevel);

            // Vérifie que le fichier existe avant d'essayer de le lire
            if (!f.exists()) { // Si le fichier n'existe pas, on affiche un message d'erreur
                               // et on quitte le constructeur pour éviter une exception
                System.out.println("Fichier introuvable : " + f.getAbsolutePath());
                return;
            }

            // Création d'un BufferedReader permettant de lire un fichier texte ligne par ligne
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String ligne; // Variable pour stocker chaque ligne lue

            // On saute la première ligne (config du niveau)
            String premiereLigne = reader.readLine();
            String[] premiereLigneSeparee = premiereLigne.split(" "); // On sépare les données et on les met dans une
                                                                            // liste, la séparation est un espace.
            // Récupération des données de configuration du niveau
            this.niveau = premiereLigneSeparee[0];
            this.vitesse = Double.parseDouble(premiereLigneSeparee[1]); // Vitesse de la formation
            this.cooldownAttaques = Integer.parseInt(premiereLigneSeparee[2]); // Si -1 alors pas d'attaques pendant le
                                                                               // niveau à part les tirs !
            this.cooldownTirs = Integer.parseInt(premiereLigneSeparee[3]); // Temps entre chaque tir des monstres


            // Lecture des lignes suivantes pour créer les monstres. Chaque ligne correspond à un monstre dans la formation du niveau.
            while ((ligne = reader.readLine()) != null) { // Lis chaque ligne jusqu'à la fin du fichier
                String[] data = ligne.split(" "); // Sépare les données de la ligne par des espaces

                String type = data[0]; // Type du monstre
                double x = Double.parseDouble(data[1]); // Position X du monstre
                double y = Double.parseDouble(data[2]); // Position Y du monstre
                double len = Double.parseDouble(data[3]); // Taille du monstre
                int val = Integer.parseInt(data[4]); // Valeur du monstre
                double vit = Double.parseDouble(data[5]); // Vitesse du monstre

                Monster m = null;
                // On vérifie le type de monstre pour créer l'objet correspondant
                if (type.equals("Bee"))
                    m = new Bee(x, y, len, val, vit, getCooldownTirs() / 30);
                else if (type.equals("Butterfly"))
                    m = new Butterfly(x, y, len, val, vit, getCooldownTirs() / 30);
                else if (type.equals("Moth"))
                    m = new Moth(x, y, len, val, vit, getCooldownTirs() / 30);

                if (m != null) // Ajoute le monstre créé à la liste des monstres du niveau si la création a réussi
                    monstres.add(m);
            }

            reader.close(); // Ferme le lecteur de fichier pour libérer les ressources

        } catch (Exception e) { // Gère les exceptions pouvant survenir lors de la lecture du fichier
            e.printStackTrace();
        }
    }

    /* ---- GETTERS ET SETTERS ---- */

    /**
     * Récupère la liste des monstres du niveau.
     * @return la liste des monstres du niveau
     */
    public List<Monster> getMonstres() {
        return monstres;
    }

    /**
     * Définit la liste des monstres du niveau.
     * @param monstres la liste des monstres à définir
     */
    public void setMonstres(List<Monster> monstres) {
        this.monstres = monstres;
    }

    /**
     * Récupère le numéro du niveau à partir du nom.
     * @return le numéro du niveau
     */
    public int getNiveau() {
        if (this.niveau == null) {
            return -1;
        }

        String nbrNiveau = "";
        String l = "Level";
        for (int i = 0; i < niveau.length(); i++) {
            if (i >= l.length()) {
                nbrNiveau += niveau.charAt(i);
            }
        }
        return Integer.parseInt(nbrNiveau);
    }

    /**
     * Définit le numéro du niveau.
     * @param niveau le numéro du niveau à définir
     */
    public void setNiveau(int niveau) {
        this.niveau = "" + niveau;
    }

    /**
     * Récupère la vitesse de la formation de monstres.
     * @return la vitesse de la formation
     */
    public Double getVitesse() {
        return vitesse;
    }

    /**
     * Définit la vitesse de la formation de monstres.
     * @param vitesse la vitesse à définir
     */
    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Récupère le délai entre chaque attaque de monstres.
     * @return le délai entre attaques
     */
    public int getCooldownAttaques() {
        return cooldownAttaques;
    }

    /**
     * Définit le délai entre chaque attaque de monstres.
     * @param cooldownAttaques le délai à définir
     */
    public void setCooldownAttaques(int cooldownAttaques) {
        this.cooldownAttaques = cooldownAttaques;
    }

    /**
     * Récupère le délai entre chaque tir de monstres.
     * @return le délai entre tirs
     */
    public int getCooldownTirs() {
        return cooldownTirs;
    }

    /**
     * Définit le délai entre chaque tir de monstres.
     * @param cooldownTirs le délai à définir
     */
    public void setCooldownTirs(int cooldownTirs) {
        this.cooldownTirs = cooldownTirs;
    }
}
