package game;
import game.actors.Base.Monster;
import game.actors.Monsters.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class Niveaux { // Utilisation de l'IA ici  
    List<Monster> monstres = new ArrayList<>();
    String niveau;
    Double vitesse;
    int cooldownAttaques;
    int cooldownTirs;


    public Niveaux(String fichierLevel) {
        try {
            File f = new File(fichierLevel);
            if (!f.exists()) {
                System.out.println("Fichier introuvable : " + f.getAbsolutePath());
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(f));
            String ligne;

            // On saute la première ligne (config du niveau)
            String premiereLigne = reader.readLine();
            String[] premiereLigneSeparee = premiereLigne.split(" "); // On sépare les données et on les met dans une liste, la séparation est un espace.
            this.niveau = premiereLigneSeparee[0];
            this.vitesse = Double.parseDouble(premiereLigneSeparee[1]); // Vitesse de la formation 
            this.cooldownAttaques = Integer.parseInt(premiereLigneSeparee[2]); // Si -1 alors pas d'attaques pendant le niveau à part les tirs ! 
            this.cooldownTirs = Integer.parseInt(premiereLigneSeparee[3]);

            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split(" ");

                String type = data[0]; // Type du monstre
                double x = Double.parseDouble(data[1]); // Position X du monstre
                double y = Double.parseDouble(data[2]); // Position Y du monstre 
                double len = Double.parseDouble(data[3]); // Taille du monstre
                int val = Integer.parseInt(data[4]); // Valeur du monstre
                double vit = Double.parseDouble(data[5]); // Vitesse du monstre 

                Monster m = null;
                if (type.equals("Bee")) m = new Bee(x, y, len, val, vit, getCooldownTirs() /30);
                else if (type.equals("Butterfly")) m = new Butterfly(x, y, len, val, vit, getCooldownTirs() /30);
                else if (type.equals("Moth")) m = new Moth(x, y, len, val, vit, getCooldownTirs() /30);

                if (m != null) monstres.add(m);
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Monster> getMonstres() {
        return monstres;
    }

    public void setMonstres(List<Monster> monstres) {
        this.monstres = monstres;
    }

    public int getNiveau() {
        if (this.niveau == null){
            return -1;
        } 
    
        String nbrNiveau = "";
        String l = "Level";
        for (int i = 0 ; i < niveau.length() ; i++) {
            if (i >= l.length()){
                nbrNiveau += niveau.charAt(i);
            }
        }
        return Integer.parseInt(nbrNiveau);
    }

    public void setNiveau(int niveau) {
        this.niveau = ""+niveau;
    }

    public Double getVitesse() {
        return vitesse;
    }

    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
    }

    public int getCooldownAttaques() {
        return cooldownAttaques;
    }

    public void setCooldownAttaques(int cooldownAttaques) {
        this.cooldownAttaques = cooldownAttaques;
    }

    public int getCooldownTirs() {
        return cooldownTirs;
    }

    public void setCooldownTirs(int cooldownTirs) {
        this.cooldownTirs = cooldownTirs;
    }
}
