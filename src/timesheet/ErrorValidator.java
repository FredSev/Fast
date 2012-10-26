package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Guillaume Auger AUGG07098702
 */
public class ErrorValidator {

    public static ArrayList<String> fetchErrors(Employee employee){

        ArrayList<String> erreurs = new ArrayList();
        if( !validateMinOfficeHoursWeek(employee) ){
            erreurs.add(ERR_MIN_OFFICE_HOURS_WEEK);
        }
        if( !validateMaxOfficeHoursWeek(employee) ){
            erreurs.add(ERR_MAX_OFFICE_HOURS_WEEK);
        }
        if( !validateMaxTeleHoursWeek(employee) ){
            erreurs.add(ERR_MAX_TELE_HOURS_WEEK);
        }
        for( Integer i = 0; i < 5; i++ ){
            if( !validateMinOfficeHoursDay(employee.getTempsUnJour(i), employee.getTypeEmploye())){
                erreurs.add(WEEK_DAYS[i]+ERR_MIN_OFFICE_HOURS_DAY);
            }
        }

        return erreurs;
    }

    public static boolean validateMinOfficeHoursWeek(Employee employee){
        return employee.getTotalTempsBureau() >= MIN_OFFICE_HOURS_WEEK[employee.getTypeEmploye()];
    }

    public static boolean validateMaxOfficeHoursWeek(Employee employee){
        return employee.getTotalTempsBureau() <= MAX_OFFICE_HOURS_WEEK;
    }

    public static boolean validateMaxTeleHoursWeek(Employee employee){
        if(employee.isAdmin()){
            return employee.getTotalTempsTele() <= MAX_ADMIN_TELE_HOURS_WEEK;
        } else {
            return true;
        }
    }

    public static boolean validateMinOfficeHoursDay(Integer hours, Integer type){
        return hours > MIN_OFFICE_HOURS_DAY[type];
    }

    final static Integer[] MIN_OFFICE_HOURS_WEEK = {38*60, 36*60};
    final static Integer[] MIN_OFFICE_HOURS_DAY = {6*60, 4*60};
    final static Integer MAX_OFFICE_HOURS_WEEK = 43*60;
    final static Integer MAX_ADMIN_TELE_HOURS_WEEK = 10*60;
    final static String ERR_MIN_OFFICE_HOURS_WEEK = "L'employé n'a pas travaillé le "
            + "nombre d'heures minimal par semaine au bureau.";
    final static String ERR_MAX_OFFICE_HOURS_WEEK = "L'employé a dépassé le nombre "
            + "d'heures de travail au bureau par semaine permis.";
    final static String ERR_MAX_TELE_HOURS_WEEK = "L'employé a dépassé "
            + "le nombre d'heures de télétravail par semaine permis.";
    final static String ERR_MIN_OFFICE_HOURS_DAY = ", l'employé n'a pas travaillé le "
            + "nombre d'heures minimal au bureau.";
    final static String[] WEEK_DAYS = {"Lundi", "Mardi", "Mercredi", "Jeudi",
            "Vendredi"};
}
