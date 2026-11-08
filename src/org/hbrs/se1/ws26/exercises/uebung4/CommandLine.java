package org.hbrs.se1.ws26.exercises.uebung4;

import org.hbrs.se1.ws26.uebung2.Container;
import org.hbrs.se1.ws26.uebung2.ContainerException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.uebung2.Member;

import java.util.Scanner;

public class CommandLine {
    private PersistenceStrategyStream<Member> strategy = new PersistenceStrategyStream<>();
    private Container c = Container.getInstance();
    public void start(){
        Scanner sc = new Scanner(System.in);
        strategy.setLocation("userstories.ser");
        c.setPersistenceStrategy(strategy);
        String input = "";
        while(!input.equals("exit")){
            System.out.print("> ");
            input = sc.nextLine();
            switch (input) {
                default:
                    System.out.println(input+" ist ein Unbekannter Befehl.");
                    break;
                case "exit":
                    System.out.println("Programm wird beendet.");
                break;
                case "load":
                    try{
                        c.load();
                        System.out.println("Load erfolgreich beendet.");
                    }catch(PersistenceException e){
                        System.out.println("Fehler beim Laden: "+e.getMessage());
                    }
                    break;
                case "store":
                    try{
                        c.store();
                        System.out.println("Userstories wurden gespeichert.");
                    }catch(PersistenceException e){
                        System.out.println("Fehler beim Speichern: "+e.getMessage());
                    }
                    break;
                case "dump":
                    MemberView mv = new MemberView();
                    mv.dump(c.getCurrentList());
                    break;
                case "enter":
                    try{
                        enter(sc);
                    }catch(ContainerException e) {
                        System.out.println("Container-Fehler: "+e.getMessage());
                    }catch(IllegalArgumentException e) {
                        System.out.println("Ungültige Eingabe: "+e.getMessage());
                    }
                    break;
                case "help":
                    System.out.println("Mögliche befehle:\n>exit Beendet das Programm\n>enter Trage eine neue UserStory ein\n>store Speicher die zur zeitige Liste\n>load Lade die gespeicherte Liste\n>dump Zeige alle UserStories in der zur zeitigen Liste nach Priorität sortiert an");
                    break;
            }
        }
    }
    private void enter(Scanner sc) throws ContainerException {
        System.out.print("ID: ");
        Integer id = Integer.parseInt(sc.nextLine());
        System.out.print("Titel: ");
        String title = sc.nextLine();
        System.out.print("Acceptance Criteria: ");
        String acceptanceCriteria = sc.nextLine();
        System.out.print("Projekt: ");
        String projekt = sc.nextLine();
        System.out.print("Mehrwert: ");
        double mehrwert = Double.parseDouble(sc.nextLine());
        System.out.print("Strafe: ");
        double strafe = Double.parseDouble(sc.nextLine());
        System.out.print("Aufwand: ");
        double aufwand = Double.parseDouble(sc.nextLine());
        System.out.print("Risiko: ");
        double risiko = Double.parseDouble(sc.nextLine());
        UserStory us = new UserStory(id,title,acceptanceCriteria,mehrwert,strafe,aufwand,risiko,projekt);
        c.addMember(us);
        System.out.println("UserStory hinzugefügt.");
    }
}
