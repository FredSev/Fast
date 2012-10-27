package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Guillaume Auger AUGG07098702
 */
public class ErrorValidator {

    private Employee employee;
    private ArrayList<String> errors;

    public ErrorValidator(Employee employee) {
        this.employee = employee;
        fetchErrors();
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        fetchErrors();
    }

    private void fetchErrors() {
        validateMinOfficeHoursWeek();
        validateMaxOfficeHoursWeek();
        validateMaxTeleHoursWeek();
        validateMinOfficeHours();
        validateSickDays();
        validateHolidays();
    }

    private void validateSickDays() {
        if (employee.isSick()) {
            for (Integer i = 0; i < SATURDAY; i++) {
                validateOneSickDay(i);
            }
            if (!validateWeekendSickDay()) {
                errors.add(ERROR_MESSAGE_WEEKEND_SICK_DAY);
            }
        }
    }

    private void validateOneSickDay(Integer day) {
        if (employee.isSickDay(day)) {
            if (employee.getSickHoursDay(day) != SICKDAY_HOURS) {
                errors.add(WEEK_DAYS_STRING[day] + ERROR_MESSAGE_HOURS_SICK_DAY);
            } else if (employee.getTempsUnJour(day) != SICKDAY_HOURS) {
                errors.add(WEEK_DAYS_STRING[day] + ERROR_MESSAGE_NOT_ONLY_SICK_HOURS);
            }
        }
    }

    private boolean validateWeekendSickDay() {
        return (employee.isSickDay(SATURDAY) && employee.isSickDay(SUNDAY));
    }

    private void validateHolidays() {
        if (employee.isHoliday()) {
            for (Integer i = 0; i < SATURDAY; i++) {
                validateOneHoliday(i);
            }
            if (!validateWeekendHoliday()) {
                errors.add(ERROR_MESSAGE_WEEKEND_SICK_DAY);
            }
        }
    }

    private void validateOneHoliday(Integer day) {
        if (employee.isHoliday(day)) {
            if (employee.getHolidayHoursDay(day) != HOLIDAY_HOURS) {
                errors.add(WEEK_DAYS_STRING[day] + ERROR_MESSAGE_HOURS_HOLIDAY);
            } else if (employee.getTempsBureauUnJour(day) != 0) {
                errors.add(WEEK_DAYS_STRING[day] + ERROR_MESSAGE_HOLIDAY_WITH_OFFICE_HOURS);
            }
        }
    }

    private boolean validateWeekendHoliday() {
        return (employee.isHoliday(SATURDAY) && employee.isHoliday(SUNDAY));
    }

    private void validateMinOfficeHoursWeek() {
        if (employee.getTotalTempsBureau() < MIN_OFFICE_HOURS_WEEK[employee.getTypeEmploye()]) {
            errors.add(ERROR_MESSAGE_MIN_OFFICE_HOURS_WEEK);
        }
    }

    private void validateMaxOfficeHoursWeek() {
        if (employee.getTotalTempsBureau() > MAX_OFFICE_HOURS_WEEK) {
            errors.add(ERROR_MESSAGE_MAX_OFFICE_HOURS_WEEK);
        }
    }

    private void validateMaxTeleHoursWeek() {
        if (employee.isAdmin()) {
            if (employee.getTotalTempsTele() > MAX_ADMIN_TELE_HOURS_WEEK) {
                errors.add(ERROR_MESSAGE_MAX_TELE_HOURS_WEEK);
            }
        }
    }

    private void validateMinOfficeHours() {
        for (Integer i = 0; i < 5; i++) {
            validateMinOfficeHoursDay(i);
        }
    }

    private void validateMinOfficeHoursDay(Integer day) {
        if (employee.getTempsBureauUnJour(day) < MIN_OFFICE_HOURS_DAY[employee.getTypeEmploye()]) {
            errors.add(WEEK_DAYS_STRING[day] + ERROR_MESSAGE_MIN_OFFICE_HOURS_DAY);
        }
    }
    // Employee type in FINAL var: {production, admin, exploitation}
    final static Integer[] MIN_OFFICE_HOURS_WEEK = {38 * 60, 36 * 60, 38 * 60};
    final static Integer[] MIN_OFFICE_HOURS_DAY = {6 * 60, 4 * 60, 6 * 60};
    final static Integer MIN_EXPLOITATION_HOURS_WEEK = 38 * 60;
    final static Integer MAX_OFFICE_HOURS_WEEK = 43 * 60;
    final static Integer MAX_ADMIN_TELE_HOURS_WEEK = 10 * 60;
    final static Integer SICKDAY_HOURS = 420;
    final static Integer HOLIDAY_HOURS = 420;
    final static String ERROR_MESSAGE_MIN_OFFICE_HOURS_WEEK = "L'employé n'a pas travaillé le "
            + "nombre d'heures minimal par semaine au bureau.";
    final static String ERROR_MESSAGE_MAX_OFFICE_HOURS_WEEK = "L'employé a dépassé le nombre "
            + "d'heures de travail au bureau par semaine permis.";
    final static String ERROR_MESSAGE_MAX_TELE_HOURS_WEEK = "L'employé a dépassé "
            + "le nombre d'heures de télétravail par semaine permis.";
    final static String ERROR_MESSAGE_MIN_OFFICE_HOURS_DAY = ", l'employé n'a pas travaillé le "
            + "nombre d'heures minimal au bureau.";
    final static String ERROR_MESSAGE_WEEKEND_SICK_DAY = "L'employée a chargé un congé de maladie"
            + " durant la fin de semaine.";
    final static String ERROR_MESSAGE_HOURS_SICK_DAY = " , l'employé n'a pas chargé le"
            + " bon nombre d'heures pour sa journée de maladie.";
    final static String ERROR_MESSAGE_NOT_ONLY_SICK_HOURS = " , l'employé a eu d'autres "
            + "activités de travail.";
    final static String ERROR_MESSAGE_WEEKEND_HOLIDAY = "L'employée a chargé un congé férié"
            + " durant la fin de semaine.";
    final static String ERROR_MESSAGE_HOURS_HOLIDAY = " , l'employé n'a pas chargé le"
            + " bon nombre d'heures pour son congé férié.";
    final static String ERROR_MESSAGE_HOLIDAY_WITH_OFFICE_HOURS = " , l'employé a travaillé au bureau "
            + "pendant son congé férié.";
    final static String[] WEEK_DAYS_STRING = {"Lundi", "Mardi", "Mercredi", "Jeudi",
        "Vendredi", "Samedi", "Dimanche"};
    final static Integer MONDAY = 0;
    final static Integer SATURDAY = 5;
    final static Integer SUNDAY = 6;
}
