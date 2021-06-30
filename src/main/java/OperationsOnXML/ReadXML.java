package OperationsOnXML;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
    private static String filePath = "/config.xml";
    private static String bridgeAddress;
    private static String username;
    private Document doc;
    boolean writeFile = false;

    @SneakyThrows
    public ReadXML() {
        File file = new File(Paths.get("").toAbsolutePath().toString() + filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
        doc.getDocumentElement().normalize();
        setBridgeAddress();
        setUsername();
        saveFile();
    }

    private ReadXML setBridgeAddress() {
        Node bridgeAddressNode = doc.getElementsByTagName("bridgeAddress").item(0);
        if (bridgeAddressNode.getTextContent().equals("")) {
            System.out.println("Please insert bridge ip.");
            Scanner scan = new Scanner(System.in);
            String ipAddress = scan.nextLine();
            bridgeAddressNode.setTextContent(ipAddress);
            bridgeAddress = ipAddress;
            writeFile = true;
        } else {
            bridgeAddress = bridgeAddressNode.getTextContent();
        }
        return this;
    }

    private ReadXML setUsername() {
        Node usernameNode = doc.getElementsByTagName("username").item(0);
        String usernameFromFile;
        if (usernameNode.getTextContent().equals("")) {
            Client client = new Client();
            do {
                client.createUser(this.getBridgeAddress());
                usernameFromFile = client.username();
            } while (usernameFromFile.equals(""));
            usernameFromFile = usernameFromFile.substring(usernameFromFile.indexOf(":\""));
            usernameFromFile = usernameFromFile.replace("\"", "");
            usernameFromFile = usernameFromFile.replace(":", "");
            usernameNode.setTextContent(usernameFromFile);
            username = usernameFromFile;
            writeFile = true;
        } else {
            username = usernameNode.getTextContent();
        }
        return this;
    }

    @SneakyThrows
    private ReadXML saveFile() {
        if (writeFile) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            doc.setXmlStandalone(true);
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            System.out.println("File saved!");
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
