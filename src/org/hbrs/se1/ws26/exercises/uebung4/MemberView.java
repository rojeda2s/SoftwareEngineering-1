package org.hbrs.se1.ws26.exercises.uebung4;

import org.hbrs.se1.ws26.uebung2.Member;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MemberView {
    public void dump(List<Member> members) {
        String out = members.stream()
                .filter(member -> member instanceof UserStory) //filtert alle nicht UserStory members aus
                .map(member -> (UserStory)member) //Member werden in UserStory verwandelt
                .sorted(Comparator.comparingDouble(UserStory::getPriority).reversed()) //Absteigend nach Prio sortieren reversed sorgt dafür das höchste Prio als erstes dran kommt
                .map(story -> String.format("ID: %d | Titel: %s | Akzeptanzkriterium: %s | Mehrwert: %.2f | Strafe: %.2f | Aufwand: %.2f | Risiko: %.2f | Projekt: %s | Priorität: %.3f",
                        story.getID(),
                        story.getTitle(),
                        story.getAcceptanceCriteria(),
                        story.getMehrwert(),
                        story.getStrafe(),
                        story.getAufwand(),
                        story.getRisiko(),
                        story.getProject(),
                        story.getPriority())) //UserStories in uniforme Strings umwandeln
                .collect(Collectors.joining("\n")); //Alle Strings mit Zeilenbrüchen verbinden
        System.out.println("Übersicht aller User Stories\n"+
                           "------------------------------------------------------------------------------------------------------------------------");
        System.out.println(out);
    }
}
