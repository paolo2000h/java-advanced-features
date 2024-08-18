import jaxb.FootballPlayer;
import jaxb.Players;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("players.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Players.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Players players = (Players) jaxbUnmarshaller.unmarshal(file);

            for (FootballPlayer player : players.getPlayers()) {
                System.out.println("Name: " + player.getName());
                System.out.println("Position: " + player.getPosition());
                System.out.println("Country: " + player.getCountry());
                System.out.println("Age: " + player.getAge());
                System.out.println("Club: " + player.getClub());
                System.out.println();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}


