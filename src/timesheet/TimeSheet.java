package timesheet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Pas de fichier d'entrée spécifié.", ex);
            System.exit(1);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Fichier introuvable.", ex);
        } catch (IOException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Erreur d'entrée/sortie.", ex);
        }

        ArrayList<Employe> employes = lecteur.getListeEmployes();
        ArrayList<String> erreurs = VerificateurErreur.fetchErreurs(employes.get(0));
        EcritureFichierJSON ecriture = null;
        try {
            ecriture = new EcritureFichierJSON(args[1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Pas de fichier de sortie spécifié.", ex);
            System.exit(1);
        }
        
        try{
            ecriture.ecritureEmploye(erreurs);
        } catch(IOException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Erreur d'entrée/sortie.", ex);
        }
    }
}
