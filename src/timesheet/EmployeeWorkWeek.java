package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Frederic Sevillano-Fortin SEVF26078308
 */
public class EmployeeWorkWeek {
    
    private ArrayList<Day> week;
    private boolean admin = false;
    private int employeeNumber;
    private int employeeType;
    
    public final int ADMIN = 0;
    public final int PRODUCTION = 1;
    public final int EXPLOITATION = 2;

    public EmployeeWorkWeek() { 
    }

    public EmployeeWorkWeek(int employeeNumber) {
        this.employeeNumber = employeeNumber;
        setEmployeeType();
    }
    
    private void setEmployeeType(){
        if(employeeNumber < 1000){
            employeeType = ADMIN;
            admin = true;
        }else if(employeeNumber >= 2000) employeeType = EXPLOITATION;    
        else employeeType = PRODUCTION;  
    }
    
    public void setWeek(ArrayList<Day> week) {
        this.week = week;
    }

    public Day getSpecificDayOfWeek(int day){
        return week.get(day);
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public int getEmployeeType() {
        return employeeType;
    }

    public int getTotalWorkFromHomeMinutes(){
        int totalWorkFromHomeMinutes = 0;
        for (Day day: week) totalWorkFromHomeMinutes += day.getWorkFromHomeMinutes();
        
        return totalWorkFromHomeMinutes;
    }

    public int getTotalWeekOfficeMinutes() {
        int totalWeekOfficeMinutes = 0;
        for (Day day: week) totalWeekOfficeMinutes += day.getTotalDayOfficeMinutes();
        
        return totalWeekOfficeMinutes;
    }

    public boolean isAdmin(){
        return admin;
    }

    @Override
    public String toString() {
        String chaine;

        chaine = "Numéro d'employé : " + employeeNumber + "\n";
        chaine += "Total temps de télétravail : " + getTotalWorkFromHomeMinutes() + "\n";
        chaine += "Total temps de bureau : " + getTotalWeekOfficeMinutes() + "\n";
        chaine += "Temps de bureau" + "\n";
        chaine += "Lundi : " + week.get(0).getTotalDayOfficeMinutes() + "\n";
        chaine += "Mardi : " + week.get(1).getTotalDayOfficeMinutes() + "\n";
        chaine += "Mercredi : " + week.get(2).getTotalDayOfficeMinutes() + "\n";
        chaine += "Jeudi : " + week.get(3).getTotalDayOfficeMinutes() + "\n";
        chaine += "Vendredi : " + week.get(4).getTotalDayOfficeMinutes() + "\n";
        chaine += "Samedi : " + week.get(5).getTotalDayOfficeMinutes() + "\n";
        chaine += "Dimanche : " + week.get(6).getTotalDayOfficeMinutes() + "\n";
        
        return chaine;
    }
}
