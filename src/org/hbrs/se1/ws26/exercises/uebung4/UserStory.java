package org.hbrs.se1.ws26.exercises.uebung4;

import org.hbrs.se1.ws26.uebung2.Member;
import java.io.Serializable;

public class UserStory implements Member,Serializable {

    private static final long serialVersionUID = 1L;
    //Id und beschreibung
    private Integer id;
    private String title;
    private String acceptanceCriteria;
    private String project;
    //Priority calc
    private double mehrwert;
    private double strafe;
    private double aufwand;
    private double risiko;
    //Priority
    private double priority;

    public UserStory(Integer id, String title,String acceptanceCriteria,double mehrwert, double strafe, double aufwand, double risiko, String project) {
        validateNumber(mehrwert, "Mehrwert");
        validateNumber(strafe, "Strafe");
        validateNumber(aufwand, "Aufwand");
        validateNumber(risiko, "Risiko");
        if(id==null){
            throw new IllegalArgumentException("ID darf nicht null sein.");
        }
        if(aufwand+risiko<=0){
            throw new IllegalArgumentException("Aufwand+Risiko darf nicht 0 sein!");
        }
        this.id = id;
        this.title = title;
        this.acceptanceCriteria = acceptanceCriteria;
        this.mehrwert = mehrwert;
        this.strafe = strafe;
        this.aufwand = aufwand;
        this.risiko = risiko;
        this.project = project;
        this.priority = calculatePriority();
    }
    private double calculatePriority(){
        return (mehrwert+strafe)/(aufwand+risiko);
    }
    private void validateNumber(double number, String name){
        if(!Double.isFinite(number)){
            throw new IllegalArgumentException(name + " muss eine gueltige Zahl sein!");
        }
        if(number < 0){
            throw new IllegalArgumentException(name + " darf nicht negativ sein!");
        }
    }
    @Override
    public Integer getID(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getAcceptanceCriteria(){
        return acceptanceCriteria;
    }
    public String getProject(){
        return project;
    }
    public double getPriority(){
        return priority;
    }
    public double getStrafe(){
        return strafe;
    }
    public double getAufwand(){
        return aufwand;
    }
    public double getRisiko(){
        return risiko;
    }
    public double getMehrwert(){
        return mehrwert;
    }
}
