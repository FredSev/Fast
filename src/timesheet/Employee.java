package timesheet;

import java.util.ArrayList;

public class Employee {

    public Employee() {
    }

    public Employee(int numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
        if(numeroEmploye < 1000){
            typeEmploye = ADMIN;
        } else {
            typeEmploye = NORMAL;
        }
    }

    public void setWeek(ArrayList<Day> week) {
        this.week = week;
    }

    public Day getDay(int day){
        return week.get(day);
    }

    public int getNumeroEmploye() {
        return numeroEmploye;
    }

    public int getTypeEmploye() {
        return typeEmploye;
    }

    public int getTotalHomeMinutes(){
        int totalHomeMinutes = 0;

        for (Day day: week) {
            totalHomeMinutes += day.getTotalHomeMinutes();
        }

        return totalHomeMinutes;
    }

    public int getTotalOfficeMinutes() {
        int totalOfficeMinutes = 0;

        for (Day day: week) {
            totalOfficeMinutes += day.getTotalOfficeMinutes();
        }

        return totalOfficeMinutes;
    }

  //  public int getTempsUnJour(int jour){
  //      return week.get(jour).getTotalMinutes();
  //  }

    public boolean isAdmin(){
        if( typeEmploye == ADMIN){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String chaine;

        chaine = "Numéro d'employé : " + numeroEmploye + "\n";
        chaine += "Total temps de télétravail : " + getTotalHomeMinutes() + "\n";
        chaine += "Total temps de bureau : " + getTotalOfficeMinutes() + "\n";
        chaine += "Temps de bureau" + "\n";
        chaine += "Lundi : " + week.get(0).getTotalOfficeMinutes() + "\n";
        chaine += "Mardi : " + week.get(1).getTotalOfficeMinutes() + "\n";
        chaine += "Mercredi : " + week.get(2).getTotalOfficeMinutes() + "\n";
        chaine += "Jeudi : " + week.get(3).getTotalOfficeMinutes() + "\n";
        chaine += "Vendredi : " + week.get(4).getTotalOfficeMinutes() + "\n";
        chaine += "Samedi : " + week.get(5).getTotalOfficeMinutes() + "\n";
        chaine += "Dimanche : " + week.get(6).getTotalOfficeMinutes() + "\n";

        return chaine;
    }

    private int numeroEmploye;

    private ArrayList<Day> week;

    private int typeEmploye;
    public final int NORMAL = 0; // À modifier : PRODUCTION
    public final int ADMIN = 1;
    public final int EXPLOITATION = 2;

}
