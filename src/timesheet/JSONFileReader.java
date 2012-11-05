/*
 * Quelques principes inspirés de
 * (c) 2011 Jacques Berger.
 * 
 */
package timesheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Emile Plourde-Lavoie PLOE23048908
 */
public class JSONFileReader {
    
    private String filePath;
    private String fileContent;
    private EmployeeWorkWeek workWeek;
    private JSONObject workWeekJSON;
    
    public JSONFileReader(String filePath) throws FileNotFoundException, IOException {
        this.filePath = filePath;
        this.fileContent = readFile(this.filePath);
        this.workWeek = null;
        this.workWeekJSON = null;
    }
    
    private String readFile(String filePath) throws FileNotFoundException, IOException {
        byte[] fileBuffer = new byte[(int) new File(filePath).length()];
        
        FileInputStream inputFile = new FileInputStream(filePath);
        inputFile.read(fileBuffer);
        
        return new String(fileBuffer);
    }

    public EmployeeWorkWeek getWorkWeek() {
        try {
            workWeekJSON = (JSONObject) JSONSerializer.toJSON(fileContent);
            int employeeNumber = workWeekJSON.getInt("numero_employe");
            workWeek = new EmployeeWorkWeek(employeeNumber);
            workWeek.setWeek(fillWeek(workWeekJSON));
        } catch (JSONException ex) {
            workWeekJSON = (JSONObject) JSONSerializer.toJSON("{}");
            System.err.println("Erreur de syntaxe JSON.");
            workWeek = new EmployeeWorkWeek(0);
        }
        
        return workWeek;
    }
    
    private ArrayList<Day> fillWeek(JSONObject workWeekJSON) {
        ArrayList<Day> week = new ArrayList();
        
        for (int d = 0; d < 7; d++) {
            JSONArray dayJSON = workWeekJSON.getJSONArray(Day.daysLogicalNames[d]);
            
            week.add(fillDay(dayJSON, d));
        }
        
        return week;
    }
    
    private Day fillDay(JSONArray dayJSON, int dayIndex) {
        Day day = new Day(dayIndex);
        
        int entriesCount = dayJSON.size();
        
        for (int e = 0; e < entriesCount; e++) {
            JSONObject entry = dayJSON.getJSONObject(e);

            int project = entry.getInt("projet");
            int minutes = entry.getInt("minutes");

            day.addEntry(new Entry(project, minutes));
        }
        
        return day;
    }
}