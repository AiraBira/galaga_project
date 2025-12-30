package game.actors.Base;

public abstract class Mouvements {

    double posX;
    double posY;
    double length;
    double vitesse;
    double longueur;
    double largeur;

    public Mouvements(double posX, double posY, double vitesse, double length) {
        this.posX = posX;
        this.posY = posY;
        this.vitesse = vitesse;
        this.length = length;
    }

    public Mouvements(double posX, double posY, double vitesse, double longueur, double largeur) {
        this.posX = posX;
        this.posY = posY;
        this.vitesse = vitesse;
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLongueur() {
        return longueur;
    }

    public double getLargeur() {
        return largeur;
    }
    
    public void mouvementDroit() {
        setPosX(getPosX() + getVitesse());

        if (getPosX()-(getLength()/2) <= 0.0) { // Ne peuvent pas dépasser les limites.
            setPosX(0+(getLength()/2));
        } else if (getPosX()+(getLength()/2) >= 1.0) {
            setPosX(1.0-(getLength()/2));
        }

    }

    public void mouvementGauche() {
        setPosX(getPosX() - getVitesse());

        if (getPosX()-(getLength()/2) <= 0.0) {
            setPosX(0+(getLength()/2));
        } else if (getPosX()+(getLength()/2) >= 1.0) {
            setPosX(1.0-(getLength()/2));
        }

    }

    public void mouvementHaut() {
        setPosY(getPosY() + getVitesse());

        if (getPosY()-(getLength()/2) <= 0.0) {
            setPosY(0+(getLength()/2));
        }
        else if (getPosY()+(getLength()/2)>= 1.0) {
            setPosY(1.0-(getLength()/2));
        }
    }

    public void mouvementBas() {
        setPosY(getPosY() - getVitesse());

        if (getPosY()-(getLength()/2) <= 0.0) {
            setPosY(0+(getLength()/2));
        }
        else if (getPosY()+(getLength()/2) >= 1.0) {
            setPosY(1.0-(getLength()/2));
        }
    }

    

    public void mouvementMoth() {
    }

    public void mouvementButterfly() {
    }

    public void mouvementBee() {
    }

   

}
