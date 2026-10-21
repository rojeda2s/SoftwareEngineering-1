package org.hbrs.se1.ws26.uebung2;

public class Client {

    public void start() throws ContainerException{
        Container container = Container.getInstance();

        container.addMember(new ConcreteMember(1));
        container.addMember(new ConcreteMember(2));

        MemberView mv = new MemberView();

        mv.dump(container.getCurrentList());
    }
}
