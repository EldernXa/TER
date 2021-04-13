package mainTER.MapPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadFileMap {
    
    private ArrayList<String> type;
    private ArrayList<Integer> xCoord;
    private ArrayList<Integer> yCoord;
    private ArrayList<Integer> height;
    private ArrayList<Integer> width;
    private String file[];
    private String pathName;



    public ReadFileMap(String pathName) {
        this.pathName = pathName;
        type = new ArrayList<>();
        xCoord = new ArrayList<>();
        yCoord = new ArrayList<>();

        Path path = Paths.get(pathName);
        try {
            this.file = Files.readString(path).split("\n");
            read();

        } catch (IOException e) {

        }
    }



    private void read() {

        for (int i = 0; i < file.length; i++) {

            try {
                String line[] = file[i].split("\\s+");
                type.add(line[0]);
                xCoord.add(Integer.parseInt(line[1]));
                yCoord.add(Integer.parseInt(line[2]));


            } catch (Exception e) {
            }
        }


    }


    public ArrayList<String> getType() {
        return type;
    }

    public ArrayList<Integer> getxCoord() {
        return xCoord;
    }

    public ArrayList<Integer> getyCoord() {
        return yCoord;
    }


}
