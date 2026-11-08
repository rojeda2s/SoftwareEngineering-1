package se1.ws26.tests.uebung4;

import org.hbrs.se1.ws26.exercises.uebung4.UserStory;
import org.hbrs.se1.ws26.exercises.uebung4.MemberView;
import org.hbrs.se1.ws26.uebung2.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MemberViewTest {
    private List<Member> m;
    @BeforeEach
    public void setUp(){
        m = new ArrayList<>();
        m.add(new UserStory(1,"Login","Login Funktioniert",5,5,5,5,"Projekt A"));
        m.add(new UserStory(2, "Datenbank", "Datenbank funktioniert", 10, 10, 2, 2, "Projekt B"));
    }
    @Test
    void testDumpSortsByPrio(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psso = System.out;
        try {
            System.setOut(new PrintStream(baos));
            MemberView mv = new MemberView();
            mv.dump(m);
        }finally {
            System.setOut(psso);
        }
        String res = baos.toString();
        assertTrue(res.indexOf("Datenbank")<res.indexOf("Login"));
    }
    @Test
    void testDumpContainsAllUserStoryData(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psso = System.out;
        try {
            System.setOut(new PrintStream(baos));
            new MemberView().dump(m);
        }finally {
            System.setOut(psso);
        }
        String res = baos.toString();
        assertTrue(res.contains("Login"));
        assertTrue(res.contains("Projekt A"));
        assertTrue(res.contains("Priorität"));
        assertTrue(res.contains("Login Funktioniert"));
    }
    @Test
    void testDumpWithEmptyList(){
        List<Member> emptyList = new ArrayList<>();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));
            new MemberView().dump(emptyList);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(resultContainsHeader(output));
    }
    private boolean resultContainsHeader(ByteArrayOutputStream output) {
        return output.toString().contains("Übersicht aller User Stories");
    }
}
