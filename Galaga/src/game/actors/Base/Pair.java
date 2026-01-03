package game.actors.base;

/**
 * La classe {@code Pair} représente une paire de deux éléments génériques.
 * Elle permet de stocker et de manipuler deux objets liés ensemble.
 *
 * @param <T1> le type du premier élément de la paire
 * @param <T2> le type du deuxième élément de la paire
 */
public class Pair<T1, T2> {
    /** Le premier élément de la paire */
    private T1 key;

    /** Le deuxième élément de la paire */
    private T2 value;
    
    /** 
     * Construit une paire avec les éléments spécifiés.
     *
     * @param key   le premier élément de la paire
     * @param value le deuxième élément de la paire
     */
    public Pair(T1 key,T2 value){
        this.key=key;
        this.value=value;
    }
    
    /** 
     * Récupère le premier élément de la paire.
     *
     * @return le premier élément de la paire
     */
    public T1 getKey() { // Renvoi le premier élément d'une paire.
        return key;
    }

    /** 
     * Récupère le deuxième élément de la paire.
     *
     * @return le deuxième élément de la paire
     */
    public T2 getValue() { // Renvoi le deuxième élément d'une paire.
        return value;
    }
}
