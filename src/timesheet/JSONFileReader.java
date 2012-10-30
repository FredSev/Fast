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
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Emile Plourde-Lavoie PLOE23048908
 */
public class JSONFileReader {
    
    private String filePath;
    private String fileContent;
    private Employee employee;
    
    public JSONFileReader(String filePath) throws FileNotFoundException, IOException {
        this.filePath = filePath;
        this.fileContent = readFile(this.filePath);
        this.employee = null;
    }
    
    private String readFile(String filePath) throws FileNotFoundException, IOException {
        byte[] fileBuffer = new byte[(int) new File(filePath).length()];
        
        FileInputStream inputFile = new FileInputStream(filePath);
        inputFile.read(fileBuffer);
        
        return new String(fileBuffer);
    }

    public Employee getEmployee() {
        JSONObject employeeJSON = (JSONObject) JSONSerializer.toJSON(fileContent);
        
        int employeeNumber = employeeJSON.getInt("numero_employe");

        employee = new Employee(employeeNumber);
        
        employee.setWeek(fillWeek(employeeJSON));
        
        return employee;
    }
    
    private ArrayList<Day> fillWeek(JSONObject employeeJSON) {
        ArrayList<Day> week = new ArrayList();
        
        for (int d = 0; d < 7; d++) {
            JSONArray dayJSON = employeeJSON.getJSONArray(Day.daysLogicalNames[d]);
            
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