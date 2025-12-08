package game.actors;

import engine.StdDraw;

public class missiles {
    Player tireur1;
    Monster tireur2;
    Pair<Double, Double> pointOrigine;

    public missiles(Monster tireur2, Pair<Double, Double> pointOrigine) {
        this.tireur2 = tireur2;
        this.pointOrigine = pointOrigine;
    }

    public missiles(Player tireur1, Pair<Double, Double> pointOrigine) {
        this.tireur1 = tireur1;
        this.pointOrigine = pointOrigine;
    }

    public Player getTireur1() {
        return tireur1;
    }

    public Monster getTireur2() {
        return tireur2;
    }

    public Pair<Double, Double> getPointOrigine() {
        return pointOrigine;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(getPointOrigine().getKey(), getPointOrigine().getValue(), 0.1);
    }

}
