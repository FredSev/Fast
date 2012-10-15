package timesheet;

public class Employe {
    
    public Employe() {
    }
    
    public Employe(int numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }
    
    public void setNumeroEmploye(int numEmp){
        numeroEmploye = numEmp;
    }
    
    public void setTempsTravaille(int jours, int project, int minutes){
        if(project > 900){
            totalTempsTele += minutes;
        }else{
            totalTempsBureau += minutes;
            tempsBureauParJours[jours] += minutes;
        }
    }
    
    public int getNumeroEmploye(){
        return numeroEmploye;
    }
    
    public int getTotalTempsTele(){
        return totalTempsTele;
    }
    
    public int getTotalTempsBureau(){
        return totalTempsBureau;
    }
    
    public int getTempsUnJour(int jour){
        return tempsBureauParJours[jour];
    }
    
    public int[] getTempsBureauParJours(){
        return tempsBureauParJours;
    }
    
    @Override
    public String toString() {
        String chaine;
        
        chaine = "Numéro d'employé : " + numeroEmploye + "\n";
        chaine += "Total temps de télétravail : " + totalTempsTele + "\n";
        chaine += "Total temps de bureau : " + totalTempsBureau + "\n";
        chaine += "Temps de bureau" + "\n";
        chaine += "Lundi : " + tempsBureauParJours[0] + "\n";
        chaine += "Mardi : " + tempsBureauParJours[1] + "\n";
        chaine += "Mercredi : " + tempsBureauParJours[2] + "\n";
        chaine += "Jeudi : " + tempsBureauParJours[3] + "\n";
        chaine += "Vendredi : " + tempsBureauParJours[4] + "\n";
        chaine += "Samedi : " + tempsBureauParJours[5] + "\n";
        chaine += "Dimanche : " + tempsBureauParJours[6] + "\n";
        
        return chaine;
    }
    
    private int numeroEmploye;
    private int totalTempsTele = 0;
    private int totalTempsBureau = 0;
    private int tempsBureauParJours[] = {0,0,0,0,0,0,0};
    
}
