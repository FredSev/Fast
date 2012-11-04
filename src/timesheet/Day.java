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
    
    private int totalWorkFromHomeMinutes = 0;
    private int totalOfficeMinutes = 0;
    private int totalMinutesWorked = 0;
    private int totalHolidayMinutes = 0;
    private int totalSickDayMinutes = 0;
    
    private boolean hasHolidayEntry = false;
    private boolean hasSickEntry = false;
    private boolean hasRegularEntry = false;
    private boolean upToDate = false;
    
    public Day(int dayIndex) {
        this.dayIndex = dayIndex;
    }
    
    public void addEntry(Entry entry) {
        upToDate = false;
        this.entries.add(entry);
    }
    
    private void updateDayProperties() {
        clearDayProperties();
        for (Entry entry: entries) {
            updateDayTypes(entry);
            updateDayMinutes(entry);
        }
        totalMinutesWorked = totalWorkFromHomeMinutes + totalOfficeMinutes;
        upToDate = true;
    }
    
    private void clearDayProperties(){
        totalWorkFromHomeMinutes = 0;
        totalOfficeMinutes = 0;
        totalMinutesWorked = 0;
        totalHolidayMinutes = 0;
        totalSickDayMinutes = 0;
        hasHolidayEntry = false;
        hasSickEntry = false;
        hasRegularEntry = false;
    }
    
    private void updateDayTypes(Entry entry) {
        if (entry.isHoliday()) hasHolidayEntry = true;
        else if (entry.isSick()) hasSickEntry = true;
        else hasRegularEntry = true;
    }
    
    private void updateDayMinutes(Entry entry) {
        if (entry.isHome()) totalWorkFromHomeMinutes += entry.getMinutes();
        else if (entry.isOffice()) totalOfficeMinutes += entry.getMinutes();
       
        if (entry.isSick()) totalSickDayMinutes += entry.getMinutes();
        else if (entry.isHoliday()) totalHolidayMinutes += entry.getMinutes();
    }
    
    public int getTotalMinutesWorked() {        
        if (!upToDate) updateDayProperties();
        return totalMinutesWorked;
    }
    
    public int getWorkFromHomeMinutes() {        
        if (!upToDate) updateDayProperties();
        return totalWorkFromHomeMinutes;
    }
    
    public int getTotalDayOfficeMinutes() {        
        if (!upToDate) updateDayProperties();
        return totalOfficeMinutes;
    }
    
    public int getSickDayMinutes() {
        if (!upToDate) updateDayProperties();
        return totalSickDayMinutes;
    }
    
    public int getHolidayMinutes() {
        if (!upToDate) updateDayProperties();
        return totalHolidayMinutes;
    }
    
    public boolean hasSickEntry() {        
        if (!upToDate) updateDayProperties();
        return hasSickEntry;
    }
    
    public boolean hasHolidayEntry() {        
        if (!upToDate) updateDayProperties();
        return hasHolidayEntry;
    }
    
    public boolean hasRegularEntry() {        
        if (!upToDate) updateDayProperties();
        return hasRegularEntry;
    }
    
    @Override
    public String toString() {
        return daysEnglishNames[dayIndex];
    }
}
