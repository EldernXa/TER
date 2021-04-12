package mainTER;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Version {

    public static String getVersion() {
        Properties properties = new Properties();
        InputStream inputStreamProperties = Version.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            if (inputStreamProperties != null) {
                properties.load(inputStreamProperties);
            } else
                throw new FileNotFoundException();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
        return properties.getProperty("applicationVersion");

    }

}
