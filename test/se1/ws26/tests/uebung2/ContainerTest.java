package se1.ws26.tests.uebung2;

import org.hbrs.se1.ws26.uebung2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {
    private Container c;
    @BeforeEach
    public void setUp() {
        c = Container.getInstance();
        c.deleteAllMembers();
        c.setPersistenceStrategy(null);
    }
    @Test
    public void testContainerState() throws ContainerException {

        Container c = Container.getInstance();
        Member m1 = new ConcreteMember(1);
        Member m2 = new ConcreteMember(2);

        //Test if Container is Empty
        assertEquals(0, c.size());

        //Erstes member hinzufügen
        c.addMember(m1);
        assertEquals(1, c.size());
        //Zweites member hinzufügen
        c.addMember(m2);
        assertEquals(2, c.size());
        //dump testen ob member in richtige reihenfolge gezeigt werden
        MemberView view = new MemberView();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try(PrintStream ps = new PrintStream(baos,true,StandardCharsets.UTF_8)){
            System.setOut(ps);
            view.dump(c.getCurrentList());
        }finally{
            System.setOut(originalOut);
        }
        String expect = "Member (ID = 1)"+System.lineSeparator()+"Member (ID = 2)"+System.lineSeparator();
        assertEquals(expect, baos.toString(StandardCharsets.UTF_8));
        //selbe member nochmal hinzufügen sollte exception werfen
        assertThrows(ContainerException.class, () -> c.addMember(new ConcreteMember(1)));
        //size sollte sich nicht ändern, wenn doppelte id versucht wurde hinzuzufügen
        assertEquals(2, c.size());
        // nicht existierendes member loeschen
        String s = c.deleteMember(3);
        assertEquals("Member ist nicht in Liste vorhanden", s);
        //Erste member löschen
        c.deleteMember(1);
        assertEquals(1, c.size());
        //Zweite member löschen
        c.deleteMember(2);
        assertEquals(0, c.size());
        //nicht vorhandenen Member löschen
        s = c.deleteMember(2);
        assertEquals("Member ist nicht in Liste vorhanden", s);
        //Zweite member hinzufügen
        c.addMember(m2);
        assertEquals(1, c.size());
        //Erste Member hinzufügen
        c.addMember(m1);
        assertEquals(2, c.size());
        //dump testen ob member in richtige reihenfolge gezeigt werden
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        PrintStream originalOut2 = System.out;
        try (PrintStream ps2 = new PrintStream(baos2, true, StandardCharsets.UTF_8)) {
            System.setOut(ps2);
            view.dump(c.getCurrentList());
        } finally {
            System.setOut(originalOut2);
        }
        String expected2 = "Member (ID = 2)" + System.lineSeparator() + "Member (ID = 1)" + System.lineSeparator();
        assertEquals(expected2, baos2.toString(StandardCharsets.UTF_8));
    }
}
