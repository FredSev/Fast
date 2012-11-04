package timesheet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import net.sf.json.JSONArray;

/**
 *
 * @author Frederic Sevillano SEVF26078308
 * @author Emile Plourde-Lavoie PLOE23048908
 */
public class JSONFileWriter {
    
    private String filePath;

    public JSONFileWriter(String filePath){
        this.filePath = filePath;
    }

    public void writeErrors(ArrayList<String> errors) throws IOException{
        JSONArray outputJSON = new JSONArray();
   
        for (String error : errors) outputJSON.add(error);
        
        FileWriter outputFile = new FileWriter(filePath);
	outputJSON.write(outputFile);
	outputFile.close();
    }
}
