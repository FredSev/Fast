package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Guillaume Auger AUGG07098702
 */
public class TimesheetValidator {

    final static Integer[] MIN_OFFICE_HOURS_WEEK = {((37 * 60) +30), 38 * 60, ((39 * 60) +30), 43 * 60};
    final static Integer[] MIN_OFFICE_HOURS_DAY = {4 * 60, 6 * 60, 6 * 60, 0 * 60};
    final static Integer[] MAX_OFFICE_HOURS_WEEK = {((41 * 60) +30), 43 * 60, 43 * 60, 999*60};
    final static Integer MAX_ADMIN_WORK_FROM_HOME_HOURS_WEEK = 10 * 60;
    final static Integer MAX_HOURS_DAY = 24 * 60;
    final static Integer SPECIAL_DAYS_HOURS = 8 * 60;
    final static Integer MAX_HOURS_DAY_WITH_SPECIAL = MAX_HOURS_DAY + SPECIAL_DAYS_HOURS;
    final static Integer NUMBER_OF_WEEK_DAYS = 5;
    final static Integer SATURDAY = 5;
    final static Integer SUNDAY = 6;
    final static Integer ADMIN = 0;
    private EmployeeWorkWeek currentWorkWeek;
    private ArrayList<String> errors;

    public TimesheetValidator(EmployeeWorkWeek workWeek) {
        this.currentWorkWeek = workWeek;
        errors = new ArrayList();
        fetchErrors();
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setCurrentWorkWeek(EmployeeWorkWeek currentWorkWeek) {
        this.currentWorkWeek = currentWorkWeek;
        fetchErrors();
    }

    private void fetchErrors() {
        validateDayConstraints();
        validateWeekConstraints();
        validateSpecialDays();
    } 

    private void validateDayConstraints(){
        for (int i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            validateMaxHoursPerDay(i);
            validateMinOfficeHoursADay(i);
        }
    }

    private void validateMaxHoursPerDay(int dayIndex) {
        if (currentWorkWeek.getTotalMinutesWorkedOnDay(dayIndex) > maxHoursThisDay(dayIndex)) {
            errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ", l'employé"
                    + " a déclaré plus de 24 heures pour la journée.");
        }
    }
    
    private int maxHoursThisDay(int dayIndex){
        if(currentWorkWeek.isVacation(dayIndex) || currentWorkWeek.isHoliday(dayIndex)){
            return MAX_HOURS_DAY_WITH_SPECIAL;
        }else{
            return MAX_HOURS_DAY;
        }  
    }
 
    private void validateMinOfficeHoursADay(int dayIndex) {
        if (currentWorkWeek.getTotalOfficeMinutesForDay(dayIndex) < MIN_OFFICE_HOURS_DAY[currentWorkWeek.getEmployeeType()]) {
            errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                    + " l'employé n'a pas travaillé le nombre d'heures minimal au bureau.");
        }
    }
    
    private void validateWeekConstraints(){
        validateMinOfficeHoursWeek();
        validateMaxOfficeHoursWeek();
        
        if (currentWorkWeek.getEmployeeType() == ADMIN){
            validateAdminWorkFromHomeHours();
        }
    } 
  
    private void validateMinOfficeHoursWeek() {
        if (currentWorkWeek.getTotalWeekOfficeMinutes() < MIN_OFFICE_HOURS_WEEK[currentWorkWeek.getEmployeeType()]) {
            errors.add("L'employé n'a pas travaillé le nombre d'heures minimal par semaine au bureau.");
        }
    }

    private void validateMaxOfficeHoursWeek() {
        if (currentWorkWeek.getTotalWeekOfficeMinutes() > MAX_OFFICE_HOURS_WEEK[currentWorkWeek.getEmployeeType()]) {
            errors.add("L'employé a dépassé le nombre d'heures de travail au bureau par semaine permis.");
        }
    }

    private void validateAdminWorkFromHomeHours() {
        if (currentWorkWeek.getTotalWorkFromHomeMinutes() > MAX_ADMIN_WORK_FROM_HOME_HOURS_WEEK) {
            errors.add("L'employé a dépassé le nombre d'heures de télétravail par semaine permis.");
        }
    }

    private void validateSpecialDays(){
        validateWeekend();
        validateNumberOfParentalLeaves();
        
        for (int i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            validateSickDay(i);
            validateHoliday(i);
            validateVacation(i); 
            validateParentalLeave(i);
        }
    }
     
    private void validateWeekend(){
        if (currentWorkWeek.isSickDay(SATURDAY) || currentWorkWeek.isSickDay(SUNDAY)) {
            errors.add("L'employée a chargé un congé de maladie durant la fin de semaine.");
        }
        if (currentWorkWeek.isHoliday(SATURDAY) || currentWorkWeek.isHoliday(SUNDAY)) {
            errors.add("L'employée a chargé un congé férié durant la fin de semaine.");
        }
        if (currentWorkWeek.isVacation(SATURDAY) || currentWorkWeek.isVacation(SUNDAY)) {
            errors.add("L'employée a chargé une journée de vacances durant la fin de semaine.");
        }
        if (currentWorkWeek.isParentalLeave(SATURDAY) || currentWorkWeek.isParentalLeave(SUNDAY)) {
            errors.add("L'employée a chargé un congé parental durant la fin de semaine.");
        }   
    }
    
    private void validateNumberOfParentalLeaves(){
        if(currentWorkWeek.getNumberOfParentalLeaves() > 1){
            errors.add("L'employée a chargé plus d'un congé parental durant la semaine.");
        }  
    }
    
    private void validateSickDay(int dayIndex) {
        if (currentWorkWeek.isSickDay(dayIndex)) {
            if (currentWorkWeek.getSickDayMinutes(dayIndex) != SPECIAL_DAYS_HOURS) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé n'a pas chargé le bon nombre d'heures pour sa journée de maladie.");
            }
            if (currentWorkWeek.getTotalMinutesWorkedOnDay(dayIndex) != currentWorkWeek.getSickDayMinutes(dayIndex)) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé a eu d'autres activités de travail pendant qu'il était malade.");
            }
        }
    }

    private void validateHoliday(int dayIndex) {
        if (currentWorkWeek.isHoliday(dayIndex)) {
            if (currentWorkWeek.getHolidayMinutes(dayIndex) != SPECIAL_DAYS_HOURS) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé n'a pas chargé le bon nombre d'heures pour son congé férié.");
            }
        }
    }
    
    private void validateVacation(int dayIndex) {
        if (currentWorkWeek.isVacation(dayIndex)) {
            if (currentWorkWeek.getVacationMinutes(dayIndex) != SPECIAL_DAYS_HOURS) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé n'a pas chargé le bon nombre d'heures pour sa journée de vacances.");
            }
        }
    }
    
    private void validateParentalLeave(int dayIndex) {
        if (currentWorkWeek.isParentalLeave(dayIndex)) {
            if (currentWorkWeek.getParentalLeaveMinutes(dayIndex) != SPECIAL_DAYS_HOURS) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé n'a pas chargé le bon nombre d'heures pour son congé parental.");
            }
            if (currentWorkWeek.getTotalOfficeMinutesForDay(dayIndex) != currentWorkWeek.getParentalLeaveMinutes(dayIndex)) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                    + " l'employé a travaillé au bureau pendant son congé parental.");
            }
        }
    }    
}
