package kass.concurrente.commit;

import kass.concurrente.modelos.InfoCommit;

public class Memento {

    private InfoCommit state;

    public Memento(InfoCommit state){
        this.state = state;
    }

    public InfoCommit getState(){
        return state;
    }
    
}
