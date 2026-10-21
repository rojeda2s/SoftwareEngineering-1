package se1.ws26.tests.uebung3;


import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws26.uebung2.ConcreteMember;
import org.hbrs.se1.ws26.uebung2.Container;
import org.hbrs.se1.ws26.uebung2.ContainerException;
import org.hbrs.se1.ws26.uebung2.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
private Container container;
    @BeforeEach
    void setUp() {
        //Singleton-Instanz holen
        container = Container.getInstance();
        //Member liste vor jedem Test leeren
        container.deleteAllMembers();
        //Persistenzstrategie zuruecksetzen
        container.setPersistenceStrategy(null);
    }

    @Test
    void testMongoDBNotImplementedSolution() {
        container.setPersistenceStrategy(new PersistenceStrategyMongoDB<Member>());
        PersistenceException storeException = assertThrows(PersistenceException.class,()-> container.store());
        assertEquals(PersistenceException.ExceptionType.ImplementationNotAvailable,storeException.getExceptionTypeType());
        assertNotNull(storeException.getMessage());
        assertFalse(storeException.getMessage().isBlank());
        PersistenceException loadException = assertThrows(PersistenceException.class,()-> container.load());
        assertEquals(PersistenceException.ExceptionType.ImplementationNotAvailable,loadException.getExceptionTypeType());
        assertNotNull(loadException.getMessage());
        assertEquals(0,container.size());
    }

    @Test
    void testNoStrategeySet() {
        assertEquals(0,container.size());
        PersistenceException storeException = assertThrows(PersistenceException.class,()->container.store());
        assertEquals("Es wurde keine Persistenzstrategie gesetzt.",storeException.getMessage());
        PersistenceException loadException = assertThrows(PersistenceException.class,()->container.load());
        assertEquals(PersistenceException.ExceptionType.NoStrategyIsSet,loadException.getExceptionTypeType());
        assertEquals(0,container.size());
    }

    @Test
    void testWrongLocationOfFile() {
        PersistenceStrategyStream<Member> strategy = new PersistenceStrategyStream<>();
        strategy.setLocation(System.getProperty("java.io.tmpdir"));
        container.setPersistenceStrategy(strategy);
        PersistenceException exception = assertThrows(PersistenceException.class, ()-> container.store());
        assertEquals(PersistenceException.ExceptionType.ImplementationNotAvailable,exception.getExceptionTypeType());
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isBlank());
        assertEquals(0,container.size());
    }

    @Test
    void testStoreDeleteAndLoad() throws ContainerException, PersistenceException {
        container.deleteAllMembers();
        assertEquals(0,container.size());

        PersistenceStrategyStream<Member> strategy = new PersistenceStrategyStream<>();
        container.setPersistenceStrategy(strategy);

        ConcreteMember member = new ConcreteMember(69);
        container.addMember(member);
        assertEquals(1,container.size());
        assertEquals(Integer.valueOf(69),container.getCurrentList().get(0).getID());

        container.store();
        assertEquals(1,container.size());

        container.deleteMember(69);
        assertEquals(0,container.size());

        container.load();
        assertEquals(1,container.size());
        assertEquals(Integer.valueOf(69),container.getCurrentList().get(0).getID());
    }

}