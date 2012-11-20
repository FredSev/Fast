package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Frederic Sevillano-Fortin SEVF26078308
 */
public class EmployeeWorkWeek {
    
    private ArrayList<Day> week;
    private int employeeNumber;
    private int employeeType;
    
    public final int ADMIN = 0;
    public final int DEVELOPMENT  = 1;
    public final int EXPLOITATION = 2;
    public final int DIRECTOR = 3;

    public EmployeeWorkWeek() {
    }

    public EmployeeWorkWeek(int employeeNumber) {
        this.employeeNumber = employeeNumber;
        this.week = new ArrayList<Day>();
        setEmployeeType();
    }
    
    public boolean isEmpty() {        
        return week.isEmpty();
    }
    
    private void setEmployeeType(){
        if(employeeNumber < 1000){
            employeeType = ADMIN;
        } else if (employeeNumber >= 1000 && employeeNumber < 2000) {
            employeeType = DEVELOPMENT;
        } else if (employeeNumber >= 2000 && employeeNumber <= 5000) {
            employeeType = EXPLOITATION;
        } else {
            employeeType = DIRECTOR;
        }
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
        
        for (Day day: week) {
            totalWorkFromHomeMinutes += day.getWorkFromHomeMinutes();
        }
        
        return totalWorkFromHomeMinutes;
    }

    public int getTotalWeekOfficeMinutes() {
        int totalWeekOfficeMinutes = 0;
        
        for (Day day: week) {
            totalWeekOfficeMinutes += day.getTotalOfficeMinutes();
        }
        
        return totalWeekOfficeMinutes;
    }
    
    public int getNumberOfParentalLeaves(){
        int totaNumberOfParentalLeaves = 0;
        
        for (Day day: week) {
            if(day.hasParentalLeaveEntry()){
                totaNumberOfParentalLeaves += 1;
            }  
        }
        
        return totaNumberOfParentalLeaves;
    }
    
    int getTotalMinutesWorkedOnDay(int dayIndex) {
        return week.get(dayIndex).getTotalMinutesWorked();
    }
    
    int getTotalOfficeMinutesForDay(int dayIndex) {
        return week.get(dayIndex).getTotalOfficeMinutes();
    }

    boolean isSickDay(int dayIndex) {
        return week.get(dayIndex).hasSickEntry();
    }

    boolean isHoliday(int dayIndex) {
        return week.get(dayIndex).hasHolidayEntry();
    }
    
    boolean isVacation(int dayIndex) {
        return week.get(dayIndex).hasVacationEntry();
    }
    
    boolean isParentalLeave(int dayIndex) {
        return week.get(dayIndex).hasParentalLeaveEntry();
    }
    
    int getSickDayMinutes(int dayIndex) {
        return week.get(dayIndex).getSickDayMinutes();
    }
    
    int getHolidayMinutes(int dayIndex) {
        return week.get(dayIndex).getHolidayMinutes();
    }
    
    int getVacationMinutes(int dayIndex) {
        return week.get(dayIndex).getVacationMinutes();
    }
    
    int getParentalLeaveMinutes(int dayIndex) {
        return week.get(dayIndex).getParentalLeaveMinutes();
    }
    
    @Override
    public String toString() {
        String chaine;

        chaine = "Numéro d'employé : " + employeeNumber + "\n";
        chaine += "Total temps de télétravail : " + getTotalWorkFromHomeMinutes() + "\n";
        chaine += "Total temps de bureau : " + getTotalWeekOfficeMinutes() + "\n";
        chaine += "Temps de bureau" + "\n";
        chaine += "Lundi : " + week.get(0).getTotalOfficeMinutes() + "\n";
        chaine += "Mardi : " + week.get(1).getTotalOfficeMinutes() + "\n";
        chaine += "Mercredi : " + week.get(2).getTotalOfficeMinutes() + "\n";
        chaine += "Jeudi : " + week.get(3).getTotalOfficeMinutes() + "\n";
        chaine += "Vendredi : " + week.get(4).getTotalOfficeMinutes() + "\n";
        chaine += "Samedi : " + week.get(5).getTotalOfficeMinutes() + "\n";
        chaine += "Dimanche : " + week.get(6).getTotalOfficeMinutes() + "\n";
        
        return chaine;
    }
}
