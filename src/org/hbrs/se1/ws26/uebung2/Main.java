package org.hbrs.se1.ws26.uebung2;

public class Main {

    public static void main(String[] args) {
        Client client = new Client();

        try{
            client.start();
        }catch(ContainerException e){
            System.out.println(e.getMessage());
        }
    }
}
