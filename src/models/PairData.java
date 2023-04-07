package models;

public class PairData<K, E>{
    public K first;
    public E second;

    public PairData(K first, E second){
        this.first = first;
        this.second = second;
    }
}
