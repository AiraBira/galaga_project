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
            reader.readLine();

            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split(" ");
                String type = data[0];
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);

                Monster m = null;
                if (type.equals("Bee")) m = new Bee(x, y);
                else if (type.equals("Butterfly")) m = new Butterfly(x, y);
                else if (type.equals("Moth")) m = new Moth(x, y);

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
}
