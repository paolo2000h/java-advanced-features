package jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player")
public class FootballPlayer {
    private String name;
    private String position;
    private String country;
    private int age;
    private String club;

    public String getName() {
        return name;
    }

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    @XmlElement(name = "position")
    public void setPosition(String position) {
        this.position = position;
    }

    public String getCountry() {
        return country;
    }

    @XmlElement(name = "country")
    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    @XmlElement(name = "age")
    public void setAge(int age) {
        this.age = age;
    }

    public String getClub() {
        return club;
    }

    @XmlElement(name = "club")
    public void setClub(String club) {
        this.club = club;
    }
}
