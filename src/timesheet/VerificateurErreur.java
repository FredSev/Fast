package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Guillaume Auger AUGG07098702
 */
public class VerificateurErreur {

    public static ArrayList<String> fetchErreurs(Employee employe){

        ArrayList<String> erreurs = new ArrayList();
        if( !verifierMinHeuresBureauSemaine(employe) ){
            erreurs.add(ERR_MIN_HEURES_BUREAU_SEMAINE);
        }
        if( !verifierMaxHeuresBureauSemaine(employe) ){
            erreurs.add(ERR_MAX_HEURES_BUREAU_SEMAINE);
        }
        if( !verifierMaxHeuresTeletravailSemaine(employe) ){
            erreurs.add(ERR_MAX_HEURES_TELETRAVAIL_SEMAINE);
        }
        for( Integer i = 0; i < 5; i++ ){
            if( !verifierMinHeuresBureauJour(employe.getTempsUnJour(i), employe.getTypeEmploye())){
                erreurs.add(JOURS_SEMAINE[i]+ERR_MIN_HEURES_BUREAU_JOUR);
            }
        }

        return erreurs;
    }

    public static boolean verifierMinHeuresBureauSemaine(Employee employe){
        return employe.getTotalTempsBureau() >= MIN_HEURES_BUREAU_SEMAINE[employe.getTypeEmploye()];
    }

    public static boolean verifierMaxHeuresBureauSemaine(Employee employe){
        return employe.getTotalTempsBureau() <= MAX_HEURES_BUREAU_SEMAINE;
    }

    public static boolean verifierMaxHeuresTeletravailSemaine(Employee employe){
        if(employe.isAdmin()){
            return employe.getTotalTempsTele() <= MAX_ADMIN_HEURES_TELE_SEMAINE;
        } else {
            return true;
        }
    }

    public static boolean verifierMinHeuresBureauJour(Integer heures, Integer type){
        return heures > MIN_HEURES_BUREAU_JOUR[type];
    }
    
    final static Integer[] MIN_HEURES_BUREAU_SEMAINE = {38*60, 36*60};
    final static Integer[] MIN_HEURES_BUREAU_JOUR = {6*60, 4*60};
    final static Integer MAX_HEURES_BUREAU_SEMAINE = 43*60;
    final static Integer MAX_ADMIN_HEURES_TELE_SEMAINE = 10*60;
    final static String ERR_MIN_HEURES_BUREAU_SEMAINE = "L'employé n'a pas travaillé le "
            + "nombre d'heures minimal par semaine au bureau.";
    final static String ERR_MAX_HEURES_BUREAU_SEMAINE = "L'employé a dépassé le nombre "
            + "d'heures de travail au bureau par semaine permis.";
    final static String ERR_MAX_HEURES_TELETRAVAIL_SEMAINE = "L'employé a dépassé "
            + "le nombre d'heures de télétravail par semaine permis.";
    final static String ERR_MIN_HEURES_BUREAU_JOUR = ", l'employé n'a pas travaillé le "
            + "nombre d'heures minimal au bureau.";
    final static String[] JOURS_SEMAINE = {"Lundi", "Mardi", "Mercredi", "Jeudi",
            "Vendredi"};
}
