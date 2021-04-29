package mainTER.CharacterGameplay;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mainTER.DBManage.PersonDBManager;
import mainTER.DBManage.SkillDBManager;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;
import mainTER.exception.CharacterImageFileDoesntExist;
import mainTER.exception.PersonDataGetException;
import mainTER.exception.PositionDirectoryDoesntExist;
import mainTER.exception.SkillDataGetException;

public class Character {
    private final String name;
    private final Coordinate initialCoordinate;
    private final Characteristics characteristics;
    private final ImageViewSizePos logo ;
    private final ArrayList<Skill> listSkill;

    // TODO Put error on graphics interface.

    public Character(String name, Coordinate coordinate) {
        listSkill = new ArrayList<>();
        this.name = name;
        this.initialCoordinate = coordinate;
        characteristics = initCharacterWithDatabase();
        this.logo = new ImageViewSizePos(Objects.requireNonNull(this.getClass().getResource("/mainTER/CharacterGameplay/Logo/" + name + ".png")).getPath(),60,60);

        initListAnimate();
        initListSkill();
    }

    private Characteristics initCharacterWithDatabase(){
        Characteristics characteristicsToReturn = null;
        PersonDBManager personDBManager = new PersonDBManager();
        try{
            characteristicsToReturn = new Characteristics(personDBManager.getSpeed(name),
                    personDBManager.getWeight(name), personDBManager.getJumpStrength(name), personDBManager.getFallingSpeed(name),
                    personDBManager.getCanJump(name));
        }catch(PersonDataGetException personDataGetException){
            System.out.println("Ce personnage n'existe pas.");
        }
        return characteristicsToReturn;
    }

    public boolean canChangeCharacter(){
        for(Skill skill : listSkill){
            if(skill.getClass() == ActiveSkill.class && !((ActiveSkill) skill).getFinishSkill()){
                return false;
            }
        }
        return true;
    }

    public Characteristics getCharacteristics(){
        return characteristics;
    }

    private void initListSkill(){
        SkillDBManager skillDBManager = new SkillDBManager();
        for(int i=1; i<=skillDBManager.getNumberSkillOfACharacter(name);i++){
            try {
                if(skillDBManager.getCtrlKey(name, i).compareTo("")==0) {
                    listSkill.add(new PassiveSkill(characteristics));
                }
                else{

                        listSkill.add(new ActiveSkill(name, skillDBManager.getNameSkill(name, i),
                                skillDBManager.getCtrlKey(name, i), skillDBManager.getAnimateMvt(name, i),
                                skillDBManager.getAnimateAction(name, i),
                                skillDBManager.getIsMode(name, i), this));

                }
            }catch(SkillDataGetException skillDataGetException){
                System.out.println(skillDataGetException.getMessage());
            }
        }
    }

    public List<Skill> getListSkill(){
        return listSkill;
    }

    private void initListAnimate(){
        for(int i=0; i<Position.values().length; i++){
            characteristics.getListOfPictureOfTheCharacter().add(new ArrayList<>());
        }
        try{
            for(Position pos : Position.values()){
                URL urlCharacter = this.getClass().getResource("/mainTER/CharacterGameplay/images/"+name);
                if(urlCharacter==null){
                    throw new CharacterImageFileDoesntExist(name);
                }
                final String replace = pos.toString().toLowerCase().replace("_", "");
                URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/" + name + "/" + replace);
                if(pos != Position.JUMP && pos != Position.REVERSE_JUMP) {
                    extractImgFromPos(pos, replace, url);
                }
                if((pos==Position.JUMP || pos==Position.REVERSE_JUMP) && canJump()){
                    extractImgFromPos(pos, replace, url);
                }
            }
        }catch(URISyntaxException uriSyntaxException){
            uriSyntaxException.printStackTrace();
        }catch(CharacterImageFileDoesntExist characterImageFileDoesntExist){
            System.out.println("Le dossier du personnage n'existe pas !");
        }catch(PositionDirectoryDoesntExist positionDirectoryDoesntExist){
            System.out.println(positionDirectoryDoesntExist.getMessage());
        }
    }

    private void extractImgFromPos(Position pos, String replace, URL url) throws URISyntaxException, PositionDirectoryDoesntExist {
        File file = Paths.get(url.toURI()).toFile();
        if (file.exists() && file.isDirectory()) {
            for (File fileForOneSprite : Objects.requireNonNull(file.listFiles())) {
                characteristics.getListOfPictureOfTheCharacter().get(pos.ordinal()).add(new ImageView(new Image(fileForOneSprite.toURI().toString())));
            }
        } else {
            throw new PositionDirectoryDoesntExist(replace);
        }
    }

    public String getName() {
        return name;
    }

    public double getSpeed(){
        return characteristics.getSpeed();
    }

    public double getWeight(){
        return characteristics.getWeight();
    }

    public double getJumpStrength(){
        return characteristics.getJumpStrength();
    }

    public boolean canJump(){
        return characteristics.canJump();
    }

    public List<ArrayList<ImageView>> getListOfPictureOfTheCharacter(){
        return characteristics.getListOfPictureOfTheCharacter();
    }


    public Coordinate getInitialCoordinate() {
        return initialCoordinate;
    }


    public ImageViewSizePos getLogo() {
        return logo;
    }
}
