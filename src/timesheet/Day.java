package timesheet;

import java.util.ArrayList;

/**
 *
 * @author Émile Plourde-Lavoie PLOE23048908
 */
public class Day {
    
    private ArrayList<Entry> entries = new ArrayList();
    
    private int dayIndex;
    public static final String[] daysLogicalNames = {"jour1", "jour2", "jour3", "jour4", "jour5", "weekend1", "weekend2"};
    public static final String[] daysEnglishNames = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    
    private int totalHomeMinutes = 0;
    private int totalOfficeMinutes = 0;
    private int totalMinutes = 0;
    
    private boolean hasHolidayEntry = false;
    private boolean hasSickEntry = false;
    private boolean hasRegularEntry = false;
    private boolean isEmpty = true;
    
    private boolean upToDate = false;
    
    public Day(int dayIndex) {
        this.dayIndex = dayIndex;
    }
    
    public void addEntry(Entry entry) {
        upToDate = false;
        this.entries.add(entry);
    }
    
    private void updateDayProperties() {
        for (Entry entry: entries) {
            isEmpty = false;
            updateDayTypes(entry);
            updateDayMinutes(entry);
        }
        
        totalMinutes = totalHomeMinutes + totalOfficeMinutes;
        
        upToDate = true;
    }
    
    private void updateDayTypes(Entry entry) {
        if (entry.isHoliday()) {
            hasHolidayEntry = true;
        } else if (entry.isSick()) {
            hasSickEntry = true;
        } else {
            hasRegularEntry = true;
        }
    }
    
    private void updateDayMinutes(Entry entry) {
        if (entry.isHome()) {
            totalHomeMinutes += entry.getMinutes();
        } else if (entry.isOffice()) {
            totalOfficeMinutes += entry.getMinutes();
        }
    }
    
    public int getTotalMinutes() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalMinutes;
    }
    
    public int getTotalHomeMinutes() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalHomeMinutes;
    }
    
    public int getTotalOfficeMinutes() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return totalOfficeMinutes;
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
    
    public boolean hasRegularEntry() {        
        if (!upToDate) {
            updateDayProperties();
        }
        
        return hasRegularEntry;
    }
    
    @Override
    public String toString() {
        return daysEnglishNames[dayIndex];
    }
}
