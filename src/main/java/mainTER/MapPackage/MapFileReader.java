package mainTER.MapPackage;

import javafx.scene.image.Image;
import mainTER.Tools.Coordinate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Read a file where you can data from map.
 */
public class MapFileReader {


    public static ArrayList<DetectableObject> detectableObjectArrayList;
    private String[] file;
    private String pathName;
    static public ArrayList<Checkpoint> checkpointArrayList;

    /**
     * @param pathName Map file path
     */
    public MapFileReader(String url, String pathName,boolean bool) {
        if(bool){
            checkpointArrayList = new ArrayList<>();
            detectableObjectArrayList = new ArrayList<>();
            this.pathName = pathName;
            Path path = Paths.get(url + pathName + ".txt");
            try {
                this.file = Files.readString(path).split("\n");

                read();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if(detectableObjectArrayList == null){
                checkpointArrayList = new ArrayList<>();
                detectableObjectArrayList = new ArrayList<>();
                this.pathName = pathName;
                Path path = Paths.get(url + pathName + ".txt");
                try {
                    this.file = Files.readString(path).split("\n");

                    read();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    /**
     * Read the file and put data in lists
     */
    private void read() {
        String spriteName;
        String lastCategorie = "";
        int i = 0;
        String[] line;
        MapFieldFromSprite fieldFromSprite;
        MapFieldFromLilPict fieldFromLilPict;

        while (i < file.length) {
            line = file[i].split("\\s+");

            switch (line[0]) {//Add case if you add section
                case "floor":
                    lastCategorie = "floor";
                    i++;
                    break;
                case "objects":
                    lastCategorie = "objects";
                    i++;
                    break;
                case "levers":
                    lastCategorie = "levers";
                    i++;
                    break;
                case "checkpoints":
                    lastCategorie = "checkpoints";
                    i++;
                    break;
                default: //Add else if if you add section
                    if (lastCategorie.equals("floor")) {
                        line = file[i].split("\\s+");
                        spriteName = line[0];
                        double[] doubles = new double[line.length];

                        for (int j = 2; j < line.length; j++) {
                            doubles[j] = Integer.parseInt(line[j]);
                        }

                        if (line[1].equals("L")) {
                            fieldFromLilPict = new MapFieldFromLilPict(pathName, spriteName, new Coordinate(doubles[2], doubles[3]), doubles[4], doubles[5]);
                            detectableObjectArrayList.add(fieldFromLilPict);
                        } else {

                            double imageHeight = heightFromName(spriteName);
                            fieldFromSprite = new MapFieldFromSprite(pathName, spriteName, new Coordinate(doubles[2], doubles[3] - imageHeight), doubles[4]);
                            detectableObjectArrayList.add(fieldFromSprite);
                        }
                    } else if (lastCategorie.equals("objects")) {
                        line = file[i].split("\\s+");

                        double[] doubles = new double[line.length];
                        for (int j = 1; j < line.length; j++) {
                            doubles[j] = Integer.parseInt(line[j]);

                        }

                        double imageHeight2;

                        switch (line[0]) {//Add case when new object
                            case "crate":
                            case "lilCrate":
                            case "metalCrate":
                            case "bigCrate":
                                detectableObjectArrayList.add(new Crate(new Coordinate(doubles[1], doubles[2]),line[0]));
                                break;
                            case "trunk":
                                imageHeight2 = heightFromName(line[0]);
                                detectableObjectArrayList.add(new RndObj(pathName, line[0], new Coordinate(doubles[1], doubles[2] - imageHeight2)));
                                break;
                            case "car":
                            case "forgottensword":
                            case "sword":
                                detectableObjectArrayList.add(new EndObject(line[0], new Coordinate(doubles[1], doubles[2])));
                                break;

                            case "eau1":
                            case "lava":
                            case "spikes1":
                            case "spikes2":
                            case "spikes3":
                            case "wireWall":
                            case "spikes":
                                detectableObjectArrayList.add(new DeathObject(line[0], new Coordinate(doubles[1], doubles[2])));

                        }
                    } else if (lastCategorie.equals("levers")) {
                        line = file[i].split("\\s+");

                        double[] doubles = new double[line.length];
                        for (int j = 0; j < line.length; j++) {
                            if (j != 2) {
                                doubles[j] = Integer.parseInt(line[j]);
                            }
                        }

                        switch (line[2]) {
                            case "portcullis":
                                Portcullis portcullis = new Portcullis(new Coordinate(doubles[3], doubles[4]));
                                detectableObjectArrayList.add(new Lever(portcullis, new Coordinate(doubles[0], doubles[1])));
                                detectableObjectArrayList.add(portcullis);
                                break;

                            case "shield":
                            case "metalDoor":
                                HideOnActionObject hideOnActionObject = new HideOnActionObject(new Coordinate(doubles[3], doubles[4]), line[2]);
                                detectableObjectArrayList.add(new Lever(hideOnActionObject, new Coordinate(doubles[0], doubles[1])));
                                detectableObjectArrayList.add(hideOnActionObject);
                                break;

                        }


                    } else if (lastCategorie.equals("checkpoints")) {
                        line = file[i].split("\\s+");
                        double[] doubles = new double[line.length];
                        for (int j = 0; j < line.length; j++) {
                            if (j != 2) {
                                doubles[j] = Integer.parseInt(line[j]);
                            }
                        }
                        Checkpoint checkpoint = new Checkpoint(new Coordinate(doubles[0], doubles[1]), pathName);
                        checkpointArrayList.add(checkpoint);
                        detectableObjectArrayList.add(checkpoint);
                    }
                    i++;
            }
        }
    }

    /**
     * @param name Name of the picture
     * @return Height of the picture
     */
    private double heightFromName(String name) {
        Image image = new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + pathName + "/" + name + ".png").toURI().toString());
        return image.getHeight();
    }

    /**
     * @return ArrayList of CollideObject
     */
    public ArrayList<DetectableObject> getCollisionObjectArrayList() {
        return detectableObjectArrayList;
    }
}
