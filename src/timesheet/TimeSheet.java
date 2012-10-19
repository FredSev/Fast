package timesheet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author sirzerator
 */
public class TimeSheet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LectureFichierJSON lecteur = null;

        try {
            lecteur = new LectureFichierJSON(args[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Employe> employes = lecteur.getListeEmployes();
        ArrayList<String> erreurs = VerificateurErreur.fetchErreurs(employes.get(0));
        EcritureFichierJSON ecriture = new EcritureFichierJSON(args[1]);
        
        try{
            ecriture.ecritureEmploye(erreurs);
        } catch(IOException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
