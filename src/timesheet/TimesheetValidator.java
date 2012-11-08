package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Guillaume Auger AUGG07098702
 */
public class TimesheetValidator {
    
    final static Integer[] MIN_OFFICE_HOURS_WEEK = {36 * 60, 38 * 60, 38 * 60};
    final static Integer[] MIN_OFFICE_HOURS_DAY = {4 * 60, 6 * 60, 6 * 60};
    final static Integer MAX_OFFICE_HOURS_WEEK = 43 * 60;
    final static Integer MAX_ADMIN_WORK_FROM_HOME_HOURS_WEEK = 10 * 60;
    final static Integer MAX_HOURS_DAY = 24 * 60;
    final static Integer SICKDAY_HOURS = 420;
    final static Integer HOLIDAY_HOURS = 420;
    final static Integer NUMBER_OF_WEEK_DAYS = 5;
    final static Integer SATURDAY = 5;
    final static Integer SUNDAY = 6;

    private EmployeeWorkWeek currentWorkWeek;
    private ArrayList<String> errors;

    public TimesheetValidator(EmployeeWorkWeek workWeek) {
        this.currentWorkWeek = workWeek;
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
        errors = new ArrayList();
        
        validateMaxHoursPerDay();
        validateMinOfficeHoursWeek();
        validateMaxOfficeHoursWeek();
        validateMaxWorkFromHomeHoursWeek();
        validateMinOfficeHours();
        validateSickDays();
        validateHolidays();
    }

    private void validateMaxHoursPerDay() {
        for (int i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            if (currentWorkWeek.getTotalMinutesWorkedOnDay(i) > MAX_HOURS_DAY) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(i) + ", l'employé"
            + " a déclaré plus de 24 heures pour la journée.");
            }
        }
    }

    private void validateSickDays() {
        for (int i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            validateOneSickDay(i);
        }
        if (validateWeekendSickDay()) {
            errors.add("L'employée a chargé un congé de maladie durant la fin de semaine.");
        }
    }

    private void validateOneSickDay(int dayIndex) {
        if (currentWorkWeek.isSickDay(dayIndex)) {
            if (currentWorkWeek.getSickDayMinutes(dayIndex) != SICKDAY_HOURS) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé n'a pas chargé le bon nombre d'heures pour"
                        + " sa journée de maladie.");
            } else if (currentWorkWeek.getTotalMinutesWorkedOnDay(dayIndex) != SICKDAY_HOURS) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé a eu d'autres activités de travail pendant"
                        + " qu'il était malade.");
            }
        }
    }

    private boolean validateWeekendSickDay() {
        return (currentWorkWeek.isSickDay(SATURDAY) ||
                currentWorkWeek.isSickDay(SUNDAY));
    }

    private void validateHolidays() {
        for (int i = 0; i < SATURDAY; i++) {
            validateOneHoliday(i);
        }
        if (validateWeekendHoliday()) {
            errors.add("L'employée a chargé un congé férié durant la fin de"
                    + " semaine.");
        }
    }

    private void validateOneHoliday(int dayIndex) {
        if (currentWorkWeek.isHoliday(dayIndex)) {
            if (currentWorkWeek.getHolidayMinutes(dayIndex) != HOLIDAY_HOURS) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé n'a pas chargé le bon nombre d'heures pour"
                        + " son congé férié.");
            } else if (currentWorkWeek.getTotalOfficeMinutesForDay(dayIndex) != 0) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                        + " l'employé a travaillé au bureau pendant son congé"
                        + " férié.");
            }
        }
    }

    private boolean validateWeekendHoliday() {
        return (currentWorkWeek.isHoliday(SATURDAY) ||
                currentWorkWeek.isHoliday(SUNDAY));
    }

    private void validateMinOfficeHoursWeek() {
        if (currentWorkWeek.getTotalWeekOfficeMinutes() < MIN_OFFICE_HOURS_WEEK[currentWorkWeek.getEmployeeType()]) {
            errors.add("L'employé n'a pas travaillé le nombre d'heures minimal"
                    + " par semaine au bureau.");
        }
    }

    private void validateMaxOfficeHoursWeek() {
        if (currentWorkWeek.getTotalWeekOfficeMinutes() > MAX_OFFICE_HOURS_WEEK) {
            errors.add("L'employé a dépassé le nombre d'heures de travail au"
                    + " bureau par semaine permis.");
        }
    }

    private void validateMaxWorkFromHomeHoursWeek() {
        if (currentWorkWeek.isAdmin()) {
            if (currentWorkWeek.getTotalWorkFromHomeMinutes() > MAX_ADMIN_WORK_FROM_HOME_HOURS_WEEK) {
                errors.add("L'employé a dépassé le nombre d'heures de télétravail"
                        + " par semaine permis.");
            }
        }
    }

    private void validateMinOfficeHours() {
        for (int i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            validateMinOfficeHoursDay(i);
        }
    }

    private void validateMinOfficeHoursDay(int dayIndex) {
        if (currentWorkWeek.getTotalOfficeMinutesForDay(dayIndex) < MIN_OFFICE_HOURS_DAY[currentWorkWeek.getEmployeeType()]) {
            errors.add(currentWorkWeek.getSpecificDayOfWeek(dayIndex) + ","
                    + " l'employé n'a pas travaillé le nombre d'heures minimal au" 
                    + " bureau.");
        }
    }

}
