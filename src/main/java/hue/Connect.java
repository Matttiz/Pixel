package hue;


import OperationsOnXML.ReadXML;

public class Connect {
    static ReadXML readXMLFile = new ReadXML().setBridgeAddress().setUsername();

    public static final String BRIDGE_ADDRESS = "http://" + readXMLFile.getBridgeAddress();
    public static final String API = "/api";
    public static final String USERNAME = "/"+ readXMLFile.getUsername();

    public static final String LIGHTS = "/lights";
    public static final String DEN_LIGHTS_ID = "/2";
    public static final String STATE = "/state";



}
