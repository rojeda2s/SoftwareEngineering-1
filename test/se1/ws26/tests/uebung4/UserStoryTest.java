package se1.ws26.tests.uebung4;

import org.hbrs.se1.ws26.exercises.uebung4.UserStory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserStoryTest {
    @Test
    void testPriorityCalc(){
        //Korrekte berechnung pruefen
        UserStory sy = new UserStory(1,"Login Implementieren","Benutzer kann sich anmelden.",8.0,4.0,2.0,1.0,"TeamMatchAI");
        assertEquals(4.0, sy.getPriority(), 0.0001);
    }
    @Test
    void testNegMehr(){
        assertThrows(IllegalArgumentException.class, () -> {
            new UserStory(2,"Registrierung","Benutzer kann sich registrieren",-5.0,4.0,2.0,1.0,"TeamMatchAI");
        });
    }
    @Test
    void testNegStrafe() {
        assertThrows(IllegalArgumentException.class, () ->
                new UserStory(1, "Login", "Kriterium", 1.0, -2.0, 1.0, 1.0, "Projekt A"));
    }
    @Test
    void testNegAufwand() {
        assertThrows(IllegalArgumentException.class, () ->
                new UserStory(1, "Login", "Kriterium", 1.0, 2.0, -1.0, 1.0, "Projekt A"));
    }
    @Test
    void testNegRisiko() {
        assertThrows(IllegalArgumentException.class, () ->
                new UserStory(1, "Login", "Kriterium", 1.0, 2.0, 1.0, -1.0, "Projekt A"));
    }
    @Test
    void testNullID() {
        assertThrows(IllegalArgumentException.class, () ->
                new UserStory(null, "Login", "Kriterium", 1.0, 2.0, 1.0, 1.0, "Projekt A"));
    }
    @Test
    void testNaN(){
        assertThrows(IllegalArgumentException.class, () -> {
            new UserStory(3,"Profil bearbeiten","Benutzer kann sein Profil bearbeiten.",8.0,Double.NaN,2.0,1.0,"TeamMatchAI");
        });
    }
    @Test
    void testInfinity(){
        assertThrows(IllegalArgumentException.class, () -> {
            new UserStory(4,"Suche","Benutzer kann andere Nutzer suchen.",8.0,4.0,Double.POSITIVE_INFINITY,1.0,"TeamMatchAI");
        });
    }
    @Test
    void testZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            new UserStory(5,"Projekt erstellen", "Benutzer kann ein Projekt erstellen.",8.0,4.0,0.0,0.0,"TeamMatchAI");
        });
    }
}
