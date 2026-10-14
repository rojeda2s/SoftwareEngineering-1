package se1.ws26.tests.uebung2;

import org.hbrs.se1.ws26.uebung2.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {

    @Test
    public void testContainerState() throws ContainerException {

        Container c = new Container();
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = System.out;
        try{
            System.setOut(new PrintStream(baos));
            c.dump();
        }finally{
            System.setOut(ps);
        }
        String expect = "Member (ID = 1)"+System.lineSeparator()+"Member (ID = 2)"+System.lineSeparator();
        assertEquals(expect, baos.toString());
        //selbe member nochmal hinzufügen sollte exception werfen
        assertThrows(ContainerException.class, () -> c.addMember(new ConcreteMember(1)));
        //size sollte sich nicht ändern, wenn doppelte id versucht wurde hinzuzufügen
        assertEquals(2, c.size());
        //Erste member löschen
        c.deleteMember(1);
        assertEquals(1, c.size());
        //Zweite member löschen
        c.deleteMember(2);
        assertEquals(0, c.size());
        //Zweite member hinzufügen
        c.addMember(m2);
        assertEquals(1, c.size());
        //Erste Member hinzufügen
        c.addMember(m1);
        assertEquals(2, c.size());
        //dump testen ob member in richtige reihenfolge gezeigt werden
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        PrintStream ps2 = System.out;
        try {
            System.setOut(new PrintStream(baos2));
            c.dump();
        } finally {
            System.setOut(ps2);
        }
        String expect2 = "Member (ID = 2)"+System.lineSeparator()+"Member (ID = 1)"+System.lineSeparator();
        assertEquals(expect2, baos2.toString());
    }
}
