package kass.concurrente;

import java.util.Date;

import kass.concurrente.hash.Hash;
import kass.concurrente.modelos.Carpeta;
import kass.concurrente.modelos.Commit;
import kass.concurrente.modelos.InfoCommit;
import kass.concurrente.snapshot.snapshot_imp.WFSnapshotList;

/**
 * Clase que unifica todo lo hecho en la practica
 * @author Kassandra Mirael
 * @version 1.0
 */
public class MiniGit {
    private static MiniGit single = null;
    private WFSnapshotList<Carpeta> git;
    private Hash hash;
    //Variable donde se guarda el add, posible carpeta
    //Variable del stash

    private MiniGit(){
        git = new WFSnapshotList<Carpeta>();
    }

    /**
     * Metodo que genera un singleton de nuestro MiniGit
     * @return El objeto instanciado
     */
    public static MiniGit creaSingleton(){
        if(single == null){
            single = new MiniGit();
        }
        return single;
    }


    /**
     * Metodo que ejecuta un determinado comando
     * @param opcion La opcion del comando a ejecutar.
     */
    public void ejecutaComando(int opcion){
        switch (opcion) {
            case 1://add .
                
                break;
            case 2://commit
                InfoCommit info = new InfoCommit();
                String nombre;
                String description = "";
                Date fecha = new Date();
                String hashV = hash.generaHash("",description);
                int checkpoint;
                Commit com;//Metemos el InfoCommit, y los archivos
                /**
                 */
                break;
            case 3://push
                //Lo metemos en su correspondientee Lugar
                //Actualizamos la variable
                //Guardamos para que sea persisteente
                break;
            case 4://help
                break;
            default:
                break;
        }
    }


    
}
