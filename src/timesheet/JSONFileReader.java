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
    private final String[] daysLogicalNames = {"jour1", "jour2", "jour3", "jour4", "jour5", "weekend1", "weekend2"};
    
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
        
        fillEmployee(employeeJSON);
        
        return employee;
    }
    
    private void fillEmployee(JSONObject employeeJSON) {
        for (int d = 0; d < 7; d++) {
            JSONArray day = employeeJSON.getJSONArray(daysLogicalNames[d]);

            if (!day.isEmpty()) {
                fillDay(day, d);
            }
        }
    }
    
    private void fillDay(JSONArray day, int dayIndex) {
        int entriesCount = day.size();
        
        for (int e = 0; e < entriesCount; e++) {
            JSONObject entry = day.getJSONObject(e);

            int project = entry.getInt("projet");
            int minutes = entry.getInt("minutes");

            employee.setTempsTravaille(dayIndex, project, minutes);
        }
    }
}