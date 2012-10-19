package timesheet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.sf.json.JSONArray;

/**
 *
 * @author Frederic Sevillano SEVF26078308
 */
public class EcritureFichierJSON {
    private String cheminFichier = "";
    
    public EcritureFichierJSON() {
        this.cheminFichier = "";
    }
    
    public EcritureFichierJSON(String cheminFichier){
        this.cheminFichier = cheminFichier;
    }
    
    public void ecritureEmploye(ArrayList<String> erreurs) throws IOException{
        
        JSONArray listeDesErreurs = new JSONArray();
   
        Iterator iterationErreurs = erreurs.iterator();
        while (iterationErreurs.hasNext()) {
            listeDesErreurs.add(iterationErreurs.next());
        }
             
        FileWriter fichierSortie = new FileWriter(cheminFichier);
	listeDesErreurs.write(fichierSortie);
	fichierSortie.close(); 
    }
}
