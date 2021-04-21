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
                            fieldFromLilPict = new MapFieldFromLilPict("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName, new Coordinate(doubles[2], doubles[3]), doubles[4], doubles[5]);
                            collideObjectArrayList.add(fieldFromLilPict);
                        }
                        else {

                            ImageView picture = new ImageView();
                            picture.setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName).toURI().toString()));
                            fieldFromSprite = new MapFieldFromSprite("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName, new Coordinate(doubles[2], doubles[3]-picture.getImage().getHeight()), doubles[4]);
                            collideObjectArrayList.add(fieldFromSprite);
                        }
                    }
                    else if(lastCategorie.equals("objects")){
                        line = file[i].split("\\s+");

                        double[] doubles = new double[line.length];
                        for (int j = 1; j < line.length; j++) {
                            doubles[j] = Integer.parseInt(line[j]);

                        }
                        ImageView picture2 = new ImageView();
                        picture2.setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Front/trunk.png").toURI().toString()));

                        switch (line[0]) {//Add case when new object
                            case "crate":
                                collideObjectArrayList.add(new Crate(new Coordinate(doubles[1], doubles[2])));
                                break;
                            case "rndobj" :
                                collideObjectArrayList.add(new RndObj("./src/main/resources/mainTER/MapPackage/Sprites/Front/trunk.png",new Coordinate(doubles[1], doubles[2]-picture2.getImage().getHeight())));
                                break;
                        }
                    }
                    else{
                    }
                    i++;
            }
        }
    }

    //TODO generaliser le calcul des pos Y

    public ArrayList<CollideObject> getCollisionObjectArrayList() {
        return collideObjectArrayList;
    }
}
