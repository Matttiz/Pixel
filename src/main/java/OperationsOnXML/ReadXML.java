package OperationsOnXML;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import hue.Client;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadXML {
    private static String bridgeAddress;
    private static String username;
    private static NodeList nodeList;
    private static Document doc;

    @SneakyThrows
    public ReadXML() {
            File file = new File(Paths.get("").toAbsolutePath().toString() + "/config.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();
            nodeList = doc.getElementsByTagName("appSettings");

    }

    public ReadXML setBridgeAddress(){
        Node node =  nodeList.item(0);
        if(((Element) node).getElementsByTagName("bridgeAddress").item(0).getTextContent().equals("")){
            System.out.println("Please insert bridge ip.");
            Scanner scan = new Scanner(System.in);
            String ipAddress =  scan.nextLine();
            node.setTextContent(ipAddress);
            bridgeAddress= ipAddress;
        }else{
            bridgeAddress = ((Element) node).getElementsByTagName("bridgeAddress").item(0).getTextContent();
        }
        return this;
    }

    @SneakyThrows
    public ReadXML setUsername(){
        Node node = (Element) nodeList.item(0);
        String usernameFromFile;
        if(((Element) node).getElementsByTagName("username").item(0).getTextContent().equals("")){
            Client client = new Client();
            do {
                client.createUser(this.getBridgeAddress());
                usernameFromFile = client.username();
            }while (usernameFromFile.equals(""));
            usernameFromFile = usernameFromFile.substring(usernameFromFile.indexOf(":\""));
            usernameFromFile = usernameFromFile.replace("\"", "");
            usernameFromFile = usernameFromFile.replace(":", "");
            node.setTextContent(usernameFromFile);
            username = usernameFromFile;
        }else {
            username = ((Element) node).getElementsByTagName("username").item(0).getTextContent();
        }
        return this;
    }

    public String getBridgeAddress() {
        return bridgeAddress;
    }

    public String getUsername() {
        return username;
    }
}
