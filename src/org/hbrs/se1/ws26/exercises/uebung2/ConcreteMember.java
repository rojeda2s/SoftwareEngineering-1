package org.hbrs.se1.ws26.exercises.uebung2;

public class ConcreteMember implements Member {
    private Integer id;

    @Override
    public Integer getID() {return id;}

    public ConcreteMember(Integer id) {this.id = id;}

    @Override
    public String toString() {
        return "Member (" + "ID = " + id + ")";
    }

}
