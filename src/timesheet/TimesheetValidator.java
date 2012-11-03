package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Guillaume Auger AUGG07098702
 */
public class TimesheetValidator {
    // Employee type in FINAL var: {production, admin, exploitation}
    final static Integer[] MIN_OFFICE_HOURS_WEEK = {38 * 60, 36 * 60, 38 * 60};
    final static Integer[] MIN_OFFICE_HOURS_DAY = {6 * 60, 4 * 60, 6 * 60};
    final static Integer MIN_EXPLOITATION_HOURS_WEEK = 38 * 60;
    final static Integer MAX_OFFICE_HOURS_WEEK = 43 * 60;
    final static Integer MAX_ADMIN_TELE_HOURS_WEEK = 10 * 60;
    final static Integer MAX_HOURS_DAY = 24 * 60;
    final static Integer SICKDAY_HOURS = 420;
    final static Integer HOLIDAY_HOURS = 420;
    final static Integer MONDAY = 0;
    final static Integer SATURDAY = 5;
    final static Integer SUNDAY = 6;


    private Employee employee;
    private ArrayList<String> errors;

    public TimesheetValidator(Employee employee) {
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
        validateMaxHoursPerDay();
        validateMinOfficeHoursWeek();
        validateMaxOfficeHoursWeek();
        validateMaxTeleHoursWeek();
        validateMinOfficeHours();
        validateSickDays();
        validateHolidays();
    }

    private void validateMaxHoursPerDay() {
        for (Integer i = 0; i < SUNDAY; i++) {
            if (employee.getDay(i).getTotalMinutes() > MAX_HOURS_DAY) {
                errors.add(employee.getDay(i) + ", l'employé a déclaré plus de 24 heures"
            + " pour la journée.");
            }
        }
    }

    private void validateSickDays() {
        for (Integer i = 0; i < SATURDAY; i++) {
            validateOneSickDay(employee.getDay(i));
        }
        if (!validateWeekendSickDay()) {
            errors.add("L'employée a chargé un congé de maladie durant la fin de semaine.");
        }
    }

    private void validateOneSickDay(Day day) {
        if (day.hasSickEntry()) {
            if (employee.getSickHoursDay(day) != SICKDAY_HOURS) {
                errors.add(day + ", l'employé n'a pas chargé le bon nombre d'heures pour "
                        + "sa journée de maladie.");
            } else if (day.getTotalMinutes() != SICKDAY_HOURS) {
                errors.add(day + ", l'employé a eu d'autres activités de travail pendant "
                        + "qu'il était malade.");
            }
        }
    }

    private boolean validateWeekendSickDay() {
        return (employee.getDay(SATURDAY).hasSickEntry() &&
                employee.getDay(SUNDAY).hasSickEntry());
    }

    private void validateHolidays() {
        for (Integer i = 0; i < SATURDAY; i++) {
            validateOneHoliday(employee.getDay(i));
        }
        if (!validateWeekendHoliday()) {
            errors.add("L'employée a chargé un congé de maladie durant la fin de "
                    + "semaine.");
        }
    }

    private void validateOneHoliday(Day day) {
        if (day.hasHolidayEntry()) {
            if (employee.getHolidayHoursDay(day) != HOLIDAY_HOURS) {
                errors.add(day + ", l'employé n'a pas chargé le bon nombre d'heures pour "
                        + "son congé férié.");
            } else if (day.getTotalOfficeMinutes() != 0) {
                errors.add(day + ", l'employé a travaillé au bureau pendant son congé "
                        + "férié.");
            }
        }
    }

    private boolean validateWeekendHoliday() {
        return (employee.getDay(SATURDAY).hasHolidayEntry() &&
                employee.getDay(SUNDAY).hasHolidayEntry());
    }

    private void validateMinOfficeHoursWeek() {
        if (employee.getTotalOfficeMinutes() < MIN_OFFICE_HOURS_WEEK[employee.getTypeEmploye()]) {
            errors.add("L'employé n'a pas travaillé le nombre d'heures minimal par semaine au "
                    + "bureau.");
        }
    }

    private void validateMaxOfficeHoursWeek() {
        if (employee.getTotalOfficeMinutes() > MAX_OFFICE_HOURS_WEEK) {
            errors.add("L'employé a dépassé le nombre d'heures de travail au bureau par semaine "
                    + "permis.");
        }
    }

    private void validateMaxTeleHoursWeek() {
        if (employee.isAdmin()) {
            if (employee.getTotalHomeMinutes() > MAX_ADMIN_TELE_HOURS_WEEK) {
                errors.add("L'employé a dépassé le nombre d'heures de télétravail par semaine "
                        + "permis.");
            }
        }
    }

    private void validateMinOfficeHours() {
        for (Integer i = 0; i < 5; i++) {
            validateMinOfficeHoursDay(employee.getDay(i));
        }
    }

    private void validateMinOfficeHoursDay(Day day) {
        if (day.getTotalOfficeMinutes() < MIN_OFFICE_HOURS_DAY[employee.getTypeEmploye()]) {
            errors.add(day + ", l'employé n'a pas travaillé le nombre d'heures minimal au "
                    + "bureau.");
        }
    }

}
