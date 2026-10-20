package org.hbrs.se1.ws26.uebung2;

import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import static org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException.ExceptionType.NoStrategyIsSet;

public class Container {

    private List<Member> members;
    private static Container instance;
    private PersistenceStrategy<Member> persistenceStrategy;

    public void setPersistenceStrategy(PersistenceStrategy<Member> strategy) {
        persistenceStrategy = strategy;
    }
    public String deleteMember(Integer id){
        Iterator<Member> iterator = members.iterator();
        while(iterator.hasNext()){
            Member member = iterator.next();
            if(member.getID().equals(id)){
                iterator.remove();
                return "Member:"+id+"\n Erfolgreich geloescht!";
            }
        }
            return "Member ist nicht in Liste vorhanden";

    }
    public void addMember(Member member) throws ContainerException {
        for(Member m : members){
            if(m.getID().equals(member.getID())){
                throw new ContainerException("Das Member-Objekt mit der ID "+member.getID()+" ist bereits vorhanden!");
            }
        }
        members.add(member);
    }
    private Container() {
        members = new ArrayList<Member>();
    }
    public static Container getInstance() {
        if(instance == null){
            instance = new Container();
        }
        return instance;
    }
    public void dump(){
        for (Member member : members) {
            System.out.println(member.toString());
        }
    }
    public int size(){return members.size();}
    public void store() throws PersistenceException{
        if(persistenceStrategy == null){
            throw new PersistenceException(NoStrategyIsSet,"Es wurde keine Persistenzstrategie gesetzt.");
        }
        persistenceStrategy.save(members);
    }
    public void load() throws PersistenceException{
        if(persistenceStrategy == null){
            throw new PersistenceException(NoStrategyIsSet,"Es wurde keine Persistenzstrategie gesetzt.");
        }
        members.clear();
        members.addAll(persistenceStrategy.load());
    }
}
