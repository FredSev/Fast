/*
 * Quelques principes inspirés de
 * Copyright 2011 Jacques Berger.
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
 * @author sirzerator
 */
public class LectureFichierJSON {
    
    private String cheminFichier = "";
    private String contenuFichier;
    private ArrayList<Employe> listeEmployes;
    
    public LectureFichierJSON() {
        this.cheminFichier = "";
        this.listeEmployes = new ArrayList();
    }
    
    public LectureFichierJSON(String cheminFichier) throws FileNotFoundException, IOException {
        this.cheminFichier = cheminFichier;
        this.contenuFichier = lectureFichier(this.cheminFichier);
        this.listeEmployes = new ArrayList();
    }
    
    private String lectureFichier(String cheminFichier) throws FileNotFoundException, IOException {
        byte[] tamponFichier = new byte[(int) new File(cheminFichier).length()];
        
        FileInputStream entreeFichier = new FileInputStream(cheminFichier);
        entreeFichier.read(tamponFichier);
        
        return new String(tamponFichier);
    }

    public ArrayList<Employe> getListeEmployes() {
        JSONObject racine = (JSONObject) JSONSerializer.toJSON(contenuFichier);
        
        int nbEmployes = racine.size();
        
        for (int e = 0; e < nbEmployes; e++) {
            //JSONObject employeJSON = racine.getJSONObject(e);
            JSONObject employeJSON = racine;
            
            int noEmploye = employeJSON.getInt("numero_employe");
            
            Employe employe = new Employe(noEmploye);
            
            informerEmploye(employe, employeJSON);
            
            listeEmployes.add(employe);
        }
        
        return listeEmployes;
    }
    
    private void informerEmploye(Employe employe, JSONObject employeJSON) {
        for (int j = 1; j <= 5; j++) {
            JSONArray jour = employeJSON.getJSONArray("jour" + j);

            int nbEntrees = jour.size();

            for (int p = 0; p < nbEntrees; p++) {
                JSONObject entree = jour.getJSONObject(p);

                int projet = entree.getInt("projet");
                int minutes = entree.getInt("minutes");

                employe.setTempsTravaille(j-1, projet, minutes);
            }
        }

        for (int j = 1; j <= 2; j++) {
            JSONArray jour = employeJSON.getJSONArray("weekend" + j);

            int nbEntrees = jour.size();

            for (int p = 0; p < nbEntrees; p++) {
                JSONObject entree = jour.getJSONObject(p);

                int projet = entree.getInt("projet");
                int minutes = entree.getInt("minutes");

                employe.setTempsTravaille(4 + j, projet, minutes);
            }
        }
    }
}