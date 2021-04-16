package mainTER.MapPackage;

public class Map {

    private ReadFileMap readFileMap;

    public Map(Collision collision) {


        readFileMap = new ReadFileMap("./src/main/resources/mainTER/MapPackage/Files/Forest.txt");
        collision.setInteractiveObjectArrayList(readFileMap.getInteractiveObjectArrayList());

    }

    public ReadFileMap getReadFileMap() {
        return readFileMap;
    }
}
