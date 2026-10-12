package org.hbrs.se1.ws26.exercises.uebung2;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Container {

    private List<ConcreteMember> members;

    public String deleteMember(Integer id){
        Iterator<ConcreteMember> iterator = members.iterator();
        while(iterator.hasNext()){
            ConcreteMember member = iterator.next();
            if(member.getID().equals(id)){
                iterator.remove();
                return "Member:"+id+"\n Erfolgreich geloescht!";
            }
        }
            return "Member ist nicht in Liste vorhanden";

    }
    public void addMember(Integer id) throws ContainerException {
        for(Member m : members){
            if(m.getID().equals(id)){
                throw new ContainerException("Das Member-Objekt mit der ID "+id+"ist bereits vorhanden!");
            }
        }
        members.add(new ConcreteMember(id));
    }
    public Container() {
        members = new ArrayList<ConcreteMember>();
    }
    public void dump(){
        for (ConcreteMember member : members) {
            System.out.println(member.toString());
        }
    }
    public int size(){return members.size();}
}
