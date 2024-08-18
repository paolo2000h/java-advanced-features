package jaxp;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLParser {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("players.xml");
            Document document = builder.parse(xmlFile);

            Element root = document.getDocumentElement();

            NodeList playerList = root.getElementsByTagName("player");

            for (int i = 0; i < playerList.getLength(); i++) {
                Element playerElement = (Element) playerList.item(i);
                String name = getTextValue(playerElement, "name");
                String position = getTextValue(playerElement, "position");
                String country = getTextValue(playerElement, "country");
                int age = getIntValue(playerElement, "age");
                String club = getTextValue(playerElement, "club");

                System.out.println("Player " + (i + 1) + ":");
                System.out.println("Name: " + name);
                System.out.println("Position: " + position);
                System.out.println("Country: " + country);
                System.out.println("Age: " + age);
                System.out.println("Club: " + club);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTextValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return "";
    }

    private static int getIntValue(Element element, String tagName) {
        String textValue = getTextValue(element, tagName);
        try {
            return Integer.parseInt(textValue);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

