package org.hbrs.se1.ws26.uebung2;

import java.util.List;

public class MemberView {
    public void dump(List<Member> members){
        for (Member m : members){
            System.out.println(m.toString());
        }
    }
}
