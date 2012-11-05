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
        for (Integer i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            if (currentWorkWeek.getSpecificDayOfWeek(i).getTotalMinutesWorked() > MAX_HOURS_DAY) {
                errors.add(currentWorkWeek.getSpecificDayOfWeek(i) + ", l'employé a déclaré plus de 24 heures"
            + " pour la journée. (" + (currentWorkWeek.getSpecificDayOfWeek(i).getTotalMinutesWorked()/60) + " heures)");
            }
        }
    }

    private void validateSickDays() {
        for (Integer i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            validateOneSickDay(currentWorkWeek.getSpecificDayOfWeek(i));
        }
        if (validateWeekendSickDay()) {
            errors.add("L'employée a chargé un congé de maladie durant la fin de semaine.");
        }
    }

    private void validateOneSickDay(Day day) {
        if (day.hasSickEntry()) {
            if (day.getSickDayMinutes() != SICKDAY_HOURS) {
                errors.add(day + ", l'employé n'a pas chargé le bon nombre d'heures pour "
                        + "sa journée de maladie. (" + (day.getSickDayMinutes()/60) + " heures)");
            } else if (day.getTotalMinutesWorked() != SICKDAY_HOURS) {
                errors.add(day + ", l'employé a eu d'autres activités de travail pendant "
                        + "qu'il était malade.");
            }
        }
    }

    private boolean validateWeekendSickDay() {
        return (currentWorkWeek.getSpecificDayOfWeek(SATURDAY).hasSickEntry() ||
                currentWorkWeek.getSpecificDayOfWeek(SUNDAY).hasSickEntry());
    }

    private void validateHolidays() {
        for (Integer i = 0; i < SATURDAY; i++) {
            validateOneHoliday(currentWorkWeek.getSpecificDayOfWeek(i));
        }
        if (validateWeekendHoliday()) {
            errors.add("L'employée a chargé un congé férié durant la fin de "
                    + "semaine.");
        }
    }

    private void validateOneHoliday(Day day) {
        if (day.hasHolidayEntry()) {
            if (day.getHolidayMinutes() != HOLIDAY_HOURS) {
                errors.add(day + ", l'employé n'a pas chargé le bon nombre d'heures pour "
                        + "son congé férié. (" + (day.getHolidayMinutes()/60) + " heures)");
            } else if (day.getTotalDayOfficeMinutes() != 0) {
                errors.add(day + ", l'employé a travaillé au bureau pendant son congé "
                        + "férié.");
            }
        }
    }

    private boolean validateWeekendHoliday() {
        return (currentWorkWeek.getSpecificDayOfWeek(SATURDAY).hasHolidayEntry() ||
                currentWorkWeek.getSpecificDayOfWeek(SUNDAY).hasHolidayEntry());
    }

    private void validateMinOfficeHoursWeek() {
        if (currentWorkWeek.getTotalWeekOfficeMinutes() < MIN_OFFICE_HOURS_WEEK[currentWorkWeek.getEmployeeType()]) {
            errors.add("L'employé n'a pas travaillé le nombre d'heures minimal par semaine au "
                    + "bureau. (" + (currentWorkWeek.getTotalWeekOfficeMinutes()/60) + " heures)");
        }
    }

    private void validateMaxOfficeHoursWeek() {
        if (currentWorkWeek.getTotalWeekOfficeMinutes() > MAX_OFFICE_HOURS_WEEK) {
            errors.add("L'employé a dépassé le nombre d'heures de travail au bureau par semaine "
                    + "permis. (" + (currentWorkWeek.getTotalWeekOfficeMinutes()/60) + " heures)");
        }
    }

    private void validateMaxWorkFromHomeHoursWeek() {
        if (currentWorkWeek.isAdmin()) {
            if (currentWorkWeek.getTotalWorkFromHomeMinutes() > MAX_ADMIN_WORK_FROM_HOME_HOURS_WEEK) {
                errors.add("L'employé a dépassé le nombre d'heures de télétravail par semaine "
                        + "permis. (" + (currentWorkWeek.getTotalWorkFromHomeMinutes()/60) + " heures)");
            }
        }
    }

    private void validateMinOfficeHours() {
        for (Integer i = 0; i < NUMBER_OF_WEEK_DAYS; i++) {
            validateMinOfficeHoursDay(currentWorkWeek.getSpecificDayOfWeek(i));
        }
    }

    private void validateMinOfficeHoursDay(Day day) {
        if (day.getTotalDayOfficeMinutes() < MIN_OFFICE_HOURS_DAY[currentWorkWeek.getEmployeeType()]) {
            errors.add(day + ", l'employé n'a pas travaillé le nombre d'heures minimal au "
                    + "bureau. (" + (day.getTotalDayOfficeMinutes()/60) + " heures)");
        }
    }

}
