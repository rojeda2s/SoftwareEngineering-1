package org.hbrs.se1.ws26.uebung2;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Container {

    private List<Member> members;

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
    public Container() {
        members = new ArrayList<Member>();
    }
    public void dump(){
        for (Member member : members) {
            System.out.println(member.toString());
        }
    }
    public int size(){return members.size();}
}
