package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadFileMap {

    private String spriteName;
    private ArrayList<MapFieldForm> mapFieldFormArrayList;
    private ArrayList<InteractiveObject> interactiveObjectArrayList;
    private String file[];
    private String pathName;


    public ReadFileMap(String pathName) {
        this.pathName = pathName;
        mapFieldFormArrayList = new ArrayList<>();
        interactiveObjectArrayList = new ArrayList<>();
        Path path = Paths.get(pathName);
        try {
            this.file = Files.readString(path).split("\n");

            read();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void read() {

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
                            mapFieldFormArrayList.add(fieldFromLilPict);
                        }
                        else {

                            ImageView picture = new ImageView();
                            picture.setImage(new Image(new File("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName).toURI().toString()));
                            fieldFromSprite = new MapFieldFromSprite("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName, new Coordinate(doubles[2], doubles[3]-picture.getImage().getHeight()), doubles[4]);
                            mapFieldFormArrayList.add(fieldFromSprite);
                        }
                    }
                    else if(lastCategorie.equals("objects")){
                        line = file[i].split("\\s+");

                        double[] doubles = new double[line.length];
                        for (int j = 1; j < line.length; j++) {
                            doubles[j] = Integer.parseInt(line[j]);

                        }
                        switch (line[0]) {//Add case when new object
                            case "crate":
                                interactiveObjectArrayList.add(new Crate(new Coordinate(doubles[1], doubles[2])));
                        }
                    }
                    else{
                    }
                    i++;
            }
        }
    }


    public ArrayList<MapFieldForm> getMapFieldFormArrayList() {
        return mapFieldFormArrayList;
    }

    public ArrayList<InteractiveObject> getInteractiveObjectArrayList() {
        return interactiveObjectArrayList;
    }
}
