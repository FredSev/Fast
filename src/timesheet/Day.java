package timesheet;

import java.util.ArrayList;

/**
 *  @author Émile Plourde-Lavoie PLOE23048908
 *  @modified Frederic Sevillano-Fortin SEVF26078308
 */
public class Day {
    
    private ArrayList<Entry> entries = new ArrayList();
    private int dayIndex;
    public static final String[] daysLogicalNames = {"jour1", "jour2", "jour3", "jour4", "jour5", "weekend1", "weekend2"};
    public static final String[] daysEnglishNames = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    
    private boolean upToDate = false;
    private boolean isEmpty = true;
    
    private int totalMinutesWorked = 0;
    private int totalOfficeMinutes = 0;
    private int totalWorkFromHomeMinutes = 0;

    private int totalHolidayMinutes = 0;
    private int totalSickDayMinutes = 0;
    private int totalVacationMinutes = 0;
    private int totalParentalLeaveMinutes = 0;
    
    private boolean hasSickEntry = false;
    private boolean hasHolidayEntry = false;
    private boolean hasVacationEntry = false;
    private boolean hasParentalLeaveEntry = false;
    
    public Day(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public void addEntry(Entry entry) {
        upToDate = false;
        isEmpty = true;
        this.entries.add(entry);
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }
    
    public int getTotalMinutesWorked() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalMinutesWorked;
    }

    public int getWorkFromHomeMinutes() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalWorkFromHomeMinutes;
    }

    public int getTotalOfficeMinutes() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalOfficeMinutes;
    }

    public int getSickDayMinutes() {
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalSickDayMinutes;
    }
    
    public int getHolidayMinutes() {
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalHolidayMinutes;
    }

    public int getVacationMinutes() {
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalVacationMinutes;
    }
    
    public int getParentalLeaveMinutes() {
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalParentalLeaveMinutes;
    }

    public boolean hasSickEntry() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return hasSickEntry;
    }
    
    public boolean hasHolidayEntry() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return hasHolidayEntry;
    }
    
    public boolean hasVacationEntry() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return hasVacationEntry;
    }
    
    public boolean hasParentalLeaveEntry() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return hasParentalLeaveEntry;
    }

    private void updateDayProperties() {
        clearMinutes();
        clearEntries(); 
        for (Entry entry: entries) {
            updateDayTypes(entry);
            updateWorkFromHomeMinutes(entry);
            updateSpecialDayMinutes(entry);
        }
        totalMinutesWorked = totalWorkFromHomeMinutes + totalOfficeMinutes;
        upToDate = true;
    }

    private void clearMinutes(){
        totalMinutesWorked = 0;
        totalOfficeMinutes = 0;
        
        totalWorkFromHomeMinutes = 0;
        
        totalSickDayMinutes = 0;
        totalHolidayMinutes = 0;
        totalVacationMinutes = 0;
        totalParentalLeaveMinutes = 0;   
    }
    
    private void clearEntries(){    
        hasHolidayEntry = false;
        hasSickEntry = false;
        hasVacationEntry = false;
        hasParentalLeaveEntry = false;
        
        upToDate = false;
        isEmpty = true;
    }

    private void updateDayTypes(Entry entry) {
        if (entry.isSick()) {
            hasSickEntry = true;
        } else if (entry.isHoliday()) {
            hasHolidayEntry = true;
        } else if (entry.isVacation()) {
            hasVacationEntry = true;
        } else if (entry.isParentalLeave()) {
            hasParentalLeaveEntry = true;
        }
    }
    
    private void updateWorkFromHomeMinutes(Entry entry) {
        if (entry.isHome()) {
            totalWorkFromHomeMinutes += entry.getMinutes();
        } 
    }
    
    private void updateSpecialDayMinutes(Entry entry){
        if (entry.isSick()) {
            totalSickDayMinutes += entry.getMinutes();
        } else if (entry.isHoliday()) {
            totalHolidayMinutes += entry.getMinutes();
        } else if (entry.isVacation()) {
            totalVacationMinutes += entry.getMinutes();
        } else if (entry.isParentalLeave()) {
            totalParentalLeaveMinutes += entry.getMinutes();
        }
        totalOfficeMinutes += entry.getMinutes();
    }
    
    @Override
    public String toString() {
        return daysEnglishNames[dayIndex];
    }
}
