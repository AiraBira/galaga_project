package game.actors.Base;

public class Pair<T1, T2> {
    private T1 key;
    private T2 value;
    
    public Pair(T1 key,T2 value){
        this.key=key;
        this.value=value;
    }
    
    
    public T1 getKey() { // Renvoi le premier élément d'une paire.
        return key;
    }

    public T2 getValue() { // Renvoi le deuxième élément d'une paire.
        // TODO Auto-generated method stub
        return value;
    }

}
