package timesheet;

/**
 *
 * @author Émile Plourde-Lavoie PLOE23048908
 */
public class Entry {
    
    private int project;
    private int minutes;
    
    public Entry(int project, int minutes) {
        this.project = project;
        this.minutes = minutes;
    }
    
    public int getMinutes() {
        return minutes;
    }
    
    public boolean isSick() {
        return project == 999;
    }
    
    public boolean isHoliday() {
        return project == 998;
    }
    
    public boolean isVacation() {
        return project == 997;
    } 
        
    public boolean isParentalLeave() {
        return project == 996;
    } 
    
    public boolean isOffice() {
        return project <= 900;
    }
    
    public boolean isHome() {
        return ((project >= 901 && project <= 995) || (project >= 1000));
    }
}
