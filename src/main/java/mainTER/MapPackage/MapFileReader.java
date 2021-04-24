package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MapFileReader {


    public static ArrayList<CollideObject> collideObjectArrayList;
    private String file[];


    public MapFileReader(String pathName) {
        collideObjectArrayList = new ArrayList<>();
        Path path = Paths.get(pathName);
        try {
            this.file = Files.readString(path).split("\n");

            read();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void read() {
        String spriteName;
        String lastCategorie = "";
        int i = 0;
        String line[];
        MapFieldFromSprite fieldFromSprite;
        MapFieldFromLilPict fieldFromLilPict;

        while(i < file.length){
            line = file[i].split("\\s+");

            switch (line[0]){//Add case if you add section
                case "floor":
                    lastCategorie = "floor";
                    i++;
                    break;
                case "objects":
                    lastCategorie = "objects";
                    i++;
                    break;
                default: //Add else if if you add section
                    if(lastCategorie.equals("floor")){
                        line = file[i].split("\\s+");
                        spriteName = line[0];
                        double[] doubles = new double[line.length];

                        for (int j = 2; j < line.length; j++) {
                            doubles[j] = Integer.parseInt(line[j]);
                        }

                        if (line[1].equals("L")) {
                            fieldFromLilPict = new MapFieldFromLilPict(spriteName, new Coordinate(doubles[2], doubles[3]), doubles[4], doubles[5]);
                            collideObjectArrayList.add(fieldFromLilPict);
                        }
                        else {

                            double imageHeight = heightFromName(spriteName);
                            System.out.println(spriteName);
                            fieldFromSprite = new MapFieldFromSprite(spriteName, new Coordinate(doubles[2], doubles[3]-imageHeight), doubles[4]);
                            collideObjectArrayList.add(fieldFromSprite);
                        }
                    }
                    else if(lastCategorie.equals("objects")){
                        line = file[i].split("\\s+");

                        double[] doubles = new double[line.length];
                        for (int j = 1; j < line.length; j++) {
                            doubles[j] = Integer.parseInt(line[j]);

                        }

                        double imageHeight2;

                        switch (line[0]) {//Add case when new object
                            case "crate":
                                collideObjectArrayList.add(new Crate(new Coordinate(doubles[1], doubles[2])));
                                break;
                            case "trunk" :
                                imageHeight2 = heightFromName(line[0]);
                                collideObjectArrayList.add(new RndObj(line[0],new Coordinate(doubles[1], doubles[2]-imageHeight2)));
                                break;
                        }
                    }
                    else{
                    }
                    i++;
            }
        }
    }

    private double heightFromName(String name){
        Image image = new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Front/"+name+".png").toURI().toString());
        return image.getHeight();
    }

    public ArrayList<CollideObject> getCollisionObjectArrayList() {
        return collideObjectArrayList;
    }
}
