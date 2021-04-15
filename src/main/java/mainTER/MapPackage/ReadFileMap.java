package mainTER.MapPackage;

import mainTER.Tools.Coordinate;

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

        String lastCategorie;
        int i = 0;


        MapFieldFromSprite fieldFromSprite;
        MapFieldFromLilPict fieldFromLilPict;
        try {

            String line[] = file[i].split("\\s+");


            if (line[0].equals("floor")) {

                i++;
                line = file[i].split("\\s+");
                while (!line[0].equals("objects")) {




                    spriteName = line[0];

                    double[] doubles = new double[line.length];
                    for (int j = 2; j < line.length; j++) {

                        doubles[j] = Integer.parseInt(line[j]);

                    }


                    if (line[1].equals("L")) {

                        fieldFromLilPict = new MapFieldFromLilPict("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName,
                                new Coordinate(doubles[2], doubles[3]), doubles[4], doubles[5]);
                        mapFieldFormArrayList.add(fieldFromLilPict);

                    } else {

                        fieldFromSprite = new MapFieldFromSprite("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName,
                                new Coordinate(doubles[2], doubles[3]), 100);
                        mapFieldFormArrayList.add(fieldFromSprite);


                    }

                    i++;
                    line = file[i].split("\\s+");



                }


            }

            if (line[0].equals("objects")) {
                i++;
                while (i < file.length) {
                    line = file[i].split("\\s+");

                    double[] doubles = new double[line.length];
                    for (int j = 1; j < line.length; j++) {
                        doubles[j] = Integer.parseInt(line[j]);

                    }
                    switch (line[0]) {//Add case when new object
                        case "crate":
                            interactiveObjectArrayList.add(new Crate(new Coordinate(doubles[1], doubles[2])));

                    }
                    i++;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<MapFieldForm> getMapFieldFormArrayList() {
        return mapFieldFormArrayList;
    }

    public ArrayList<InteractiveObject> getInteractiveObjectArrayList() {
        return interactiveObjectArrayList;
    }
}
