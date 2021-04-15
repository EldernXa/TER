package mainTER.MapPackage;

public class Map {

    private ReadFileMap readFileMap;

    public Map() {


        readFileMap = new ReadFileMap("./src/main/resources/mainTER/MapPackage/Files/Forest.txt");

    }

    public ReadFileMap getReadFileMap() {
        return readFileMap;
    }
}
