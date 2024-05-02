package kass.concurrente.commit;
import kass.concurrente.modelos.InfoCommit;

public class Originator {
    private InfoCommit state;

    public void setState(InfoCommit state){
        this.state = state;
    }

    public InfoCommit getState(){
        return state;
    }

    public Memento guardar(){
        return new Memento(state);
    }

    public void restaurar(Memento m){
        this.state = m.getState();
    }

    
}
