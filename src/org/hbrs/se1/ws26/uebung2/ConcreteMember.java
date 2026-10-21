package org.hbrs.se1.ws26.uebung2;

import java.io.Serializable;

public class ConcreteMember implements Member, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    @Override
    public Integer getID() {return id;}

    public ConcreteMember(Integer id) {this.id = id;}

    @Override
    public String toString() {
        return "Member (" + "ID = " + id + ")";
    }

}
