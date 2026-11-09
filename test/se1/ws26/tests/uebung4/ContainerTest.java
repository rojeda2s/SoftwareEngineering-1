package se1.ws26.tests.uebung4;

import org.hbrs.se1.ws26.exercises.uebung4.UserStory;
import org.hbrs.se1.ws26.uebung2.Container;
import org.hbrs.se1.ws26.uebung2.ContainerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws26.uebung2.Member;
import java.io.File;

public class ContainerTest {

    private Container container;
    @BeforeEach
    public void setUp() {
        container = Container.getInstance();
        container.deleteAllMembers();
    }

    private UserStory createUserStory(Integer id) {
        return new UserStory(id,"Login","Benutzer kann sich anmelden",5,3,2,1,"Projekt A");
    }
    @Test
    void testAddUserStory() throws ContainerException {
        UserStory story = createUserStory(1);
        container.addMember(story);
        assertEquals(1,container.size());
    }
    @Test
    void testDuplicateID() throws ContainerException {
        UserStory story1 = createUserStory(1);
        UserStory story2 = createUserStory(1);
        container.addMember(story1);
        assertThrows(ContainerException.class, () -> container.addMember(story2));
        assertEquals(1, container.size());
    }

    @Test
    void testAddNullMember() {
        assertThrows(ContainerException.class, () -> container.addMember(null));assertEquals(0, container.size());
    }

    @Test
    void testAddUserStoryWithoutID() {
        UserStory story = createUserStory(null);
        assertThrows(ContainerException.class, () -> container.addMember(story));
        assertEquals(0, container.size());
    }

    @Test
    void testDeleteUserStory() throws ContainerException {
        UserStory story = createUserStory(1);
        container.addMember(story);
        assertEquals(1, container.size());
        container.deleteMember(1);
        assertEquals(0, container.size());
    }

    @Test
    void testDeleteNonExistingUserStory() throws ContainerException {
        container.addMember(createUserStory(1));
        container.deleteMember(99);
        assertEquals(1, container.size());
    }
    @Test
    void testStoreAndLoad() throws Exception {
        // UserStory zum Container hinzufügen
        UserStory story = createUserStory(1);
        try {
            container.addMember(story);
            PersistenceStrategyStream<Member> strategy = new PersistenceStrategyStream<>();
            strategy.setLocation("test-objects.ser");
            container.setPersistenceStrategy(strategy);
            container.store();
            container.deleteAllMembers();
            assertEquals(0, container.size());
            container.load();
            assertEquals(1, container.size());
            UserStory loadedStory = (UserStory) container.getCurrentList().get(0);
            assertEquals("Login", loadedStory.getTitle());
            assertEquals("Benuzter kann sich anmelden", loadedStory.getAcceptanceCriteria());
            assertEquals(2, loadedStory.getAufwand());
            assertEquals(1, loadedStory.getRisiko());
        }finally {
            new File("test-objects.ser").delete();
        }
    }
    @Test
    void testDuplicateUserStoryIDIsRejected() throws ContainerException {
        UserStory us1 = createUserStory(1);
        UserStory us2 = new UserStory(1, "Registrierung", "Kriterium 2", 5.0, 2.0, 1.0, 1.0, "Projekt B");
        container.addMember(us1);
        assertThrows(ContainerException.class, () -> container.addMember(us2));
        assertEquals(1, container.size());
    }
}
