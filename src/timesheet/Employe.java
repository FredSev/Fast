package timesheet;

public class Employe {
    
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
    
    private int numeroEmploye;
    private int totalTempsTele = 0;
    private int totalTempsBureau = 0;
    private int tempsBureauParJours[] = {0,0,0,0,0,0,0};
    
}
