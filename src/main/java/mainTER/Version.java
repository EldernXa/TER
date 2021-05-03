package mainTER;

public class Version {

    private Version(){

    }

    public static String getVersion() {

        return PropertiesReader.getValue("applicationVersion");

    }

}
