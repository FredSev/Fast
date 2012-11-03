package timesheet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frederic Sevillano SEVF26078308
 * @author Emile Plourde-Lavoie PLOE23048908
 * @author Guillaume Auger
 */
public class TimeSheet {

    static JSONFileReader reader = null;
    static JSONFileWriter writer = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            reader = new JSONFileReader(args[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Pas de fichier d'entrée spécifié.", ex);
            System.exit(1);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Fichier introuvable.", ex);
        } catch (IOException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Erreur d'entrée/sortie.", ex);
        }

        Employee employee = reader.getEmployee();
        TimesheetValidator validator = new TimesheetValidator(employee);
        ArrayList<String> errors = validator.getErrors();
        try {
            writer = new JSONFileWriter(args[1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Pas de fichier de sortie spécifié.", ex);
            System.exit(1);
        }

        try {
            writer.writeErrors(errors);
        } catch (IOException ex) {
            Logger.getLogger(TimeSheet.class.getName()).log(Level.SEVERE, "Erreur d'entrée/sortie.", ex);
        }
    }
}
