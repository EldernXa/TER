package mainTER.MapPackage;

import mainTER.Tools.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadFileMap {

    private String spriteName;
    private ArrayList<MapFieldFromLilPict> mapFieldFromLilPictArrayList;
    private ArrayList<MapFieldFromSprite> mapFieldFromSpriteArrayList;
    private String file[];
    private String pathName;



    public ReadFileMap(String pathName) {
        this.pathName = pathName;
        mapFieldFromSpriteArrayList = new ArrayList<>();
        mapFieldFromLilPictArrayList = new ArrayList<>();
        Path path = Paths.get(pathName);
        try {
            this.file = Files.readString(path).split("\n");

            read();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void read() {

        for (int i = 0; i < file.length; i++) {

            MapFieldFromSprite fieldFromSprite;
            MapFieldFromLilPict fieldFromLilPict;
            try {

                String line[] = file[i].split("\\s+");

                spriteName = line[0];

                double[] doubles = new double[line.length];
                for (int j=3; j < line.length; j++) {
                    doubles[j] = Integer.parseInt(line[j]);

                }

                if(line[1].equals("F")){

                    if(line[2].equals("L")){

                        fieldFromLilPict = new MapFieldFromLilPict("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName,
                                new Coordinate(doubles[3],doubles[4]),doubles[5],doubles[6]);
                        mapFieldFromLilPictArrayList.add(fieldFromLilPict);

                    }else {

                        fieldFromSprite = new MapFieldFromSprite("./src/main/resources/mainTER/MapPackage/Sprites/Front/" + spriteName,
                                new Coordinate(doubles[3],doubles[4]),100);
                        mapFieldFromSpriteArrayList.add(fieldFromSprite);

                    }
                }
                else {

                    if(line[2].equals("L")){

                        fieldFromLilPict = new MapFieldFromLilPict("./src/main/resources/mainTER/MapPackage/Sprites/Back/" + spriteName,
                                new Coordinate(doubles[3],doubles[4]),doubles[5],doubles[6]);
                        mapFieldFromLilPictArrayList.add(fieldFromLilPict);

                    }else {

                        fieldFromSprite = new MapFieldFromSprite("./src/main/resources/mainTER/MapPackage/Sprites/Back/" + spriteName,
                                new Coordinate(doubles[3],doubles[4]),100);

                        mapFieldFromSpriteArrayList.add(fieldFromSprite);
                    }

                }







            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public ArrayList<MapFieldFromLilPict> getMapFieldFromLilPictArrayList() {
        return mapFieldFromLilPictArrayList;
    }

    public ArrayList<MapFieldFromSprite> getMapFieldFromSpriteArrayList() {
        return mapFieldFromSpriteArrayList;
    }
}
