package org.hbrs.se1.ws26.exercises.uebung3.persistence;
import java.util.List;
import java.io.*;

import static org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException.ExceptionType.ImplementationNotAvailable;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     * Look-up in Google for further help! Good source:
     * https://www.digitalocean.com/community/tutorials/objectoutputstream-java-write-object-file
     * (Last Access: Oct, 12th 2026)
     */
    public void save(List<E> member) throws PersistenceException  {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(location))){
            out.writeObject(member);
        }catch(IOException ioe){
            throw new PersistenceException(ImplementationNotAvailable,"Fehler beim Speichern der Member.\n"+ioe.getMessage());
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    @SuppressWarnings("unchecked")
    public List<E> load() throws PersistenceException  {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(location))){
            Object obj = ois.readObject();
            if(obj instanceof List<?>){
                return (List<E>) obj;
            }
            throw new PersistenceException(ImplementationNotAvailable,"Die gespeicherten Daten sind keine Liste.");
        }catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(ImplementationNotAvailable,e.getMessage());
        }
    }
}
