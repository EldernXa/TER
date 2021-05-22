package mainTER.CharacterGameplay;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

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
    private final Characteristics characteristics;
    private final ImageViewSizePos logo ;
    private final ArrayList<Skill> listSkill;
    private boolean canDie = true;

    // TODO Put error on graphics interface.

    /**
     * Constructor for creating a new character.
     * @param name the name of the character we want to create (the name must be in the databases).
     */
    public Character(String name) {
        listSkill = new ArrayList<>();
        this.name = name;
        characteristics = initCharacterWithDatabase();
        this.logo = new ImageViewSizePos("/mainTER/CharacterGameplay/Logo/" + name + ".png",new Coordinate(0,0));

        initListAnimate();
        initListSkill();
    }

    /**
     * Initialise characteristics for a character thanks to databases.
     * @return the characteristics for a character.
     */
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

    /**
     *
     * @return true if the character can die, false otherwise.
     */
    public boolean canDie(){
        return canDie;
    }

    /**
     * set if the character can die or not.
     * @param canDie if the character can die or not.
     */
    public void setCanDie(boolean canDie){
        this.canDie = canDie;
    }

    /**
     *
     * @return true if we can change character (if skill are not used), false otherwise.
     */
    public boolean canChangeCharacter(){
        for(Skill skill : listSkill){
            if(skill.getClass() == ActiveSkill.class && ((ActiveSkill) skill).isEnabled()){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return characteristics of the character.
     */
    public Characteristics getCharacteristics(){
        return characteristics;
    }

    /**
     * Initialise all the skill of a character.
     */
    private void initListSkill(){
        int nbActiveSkill = 1;
        SkillDBManager skillDBManager = new SkillDBManager();
        for(int i=1; i<=skillDBManager.getNumberSkillOfACharacter(name);i++){
            try {
                if(skillDBManager.getCtrlKey(name, i).compareTo("")==0) {
                    listSkill.add(new PassiveSkill(characteristics));
                }
                else{

                        listSkill.add(new ActiveSkill(name, skillDBManager.getNameSkill(name, i),
                                nbActiveSkill, this, skillDBManager.getTimeCooldown(name, i),
                                skillDBManager.getTimeSkill(name,i)));
                        nbActiveSkill++;

                }
            }catch(SkillDataGetException skillDataGetException){
                System.out.println(skillDataGetException.getMessage());
            }
        }
    }

    /**
     *
     * @return a list containing all skill of a character.
     */
    public List<Skill> getListSkill(){
        return listSkill;
    }

    /**
     * Initialise the list containing all image for the animation purpose.
     */
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
                    extractImgFromPos(pos, replace, Objects.requireNonNull(url));
                }
                if((pos==Position.JUMP || pos==Position.REVERSE_JUMP) && canJump()){
                    extractImgFromPos(pos, replace, Objects.requireNonNull(url));
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

    /**
     * Getting all image for a position.
     * @param pos the position we want the image.
     * @param replace the name of the folder.
     * @param url the path of the images.
     * @throws URISyntaxException if the path doesn't exist.
     * @throws PositionDirectoryDoesntExist if the directory for this position doesn't exist.
     */
    private void extractImgFromPos(Position pos, String replace, URL url) throws URISyntaxException, PositionDirectoryDoesntExist {
        File file = Paths.get(url.toURI()).toFile();
        if (file.exists() && file.isDirectory()) {
            File[] listFile = Objects.requireNonNull(file.listFiles());
            Arrays.sort(listFile);
            for (File fileForOneSprite : listFile) {
                characteristics.getListOfPictureOfTheCharacter().get(pos.ordinal()).add(new ImageView(new Image(fileForOneSprite.toURI().toString())));
            }
        } else {
            throw new PositionDirectoryDoesntExist(replace);
        }
    }

    /**
     * get the name of the character.
     * @return the name of the character.
     */
    public String getName() {
        return name;
    }

    /**
     * get the speed of the character.
     * @return the speed of the character.
     */
    public double getSpeed(){
        return characteristics.getSpeed();
    }

    /**
     * get the weight of the character.
     * @return the weight of the character.
     */
    public double getWeight(){
        return characteristics.getWeight();
    }

    /**
     * get the jump strength of the character.
     * @return the jump strength of the character.
     */
    public double getJumpStrength(){
        return characteristics.getJumpStrength();
    }

    /**
     * if the character can jump or not.
     * @return true if the character can jump, false otherwise.
     */
    public boolean canJump(){
        return characteristics.canJump();
    }

    /**
     * get a list containing all image for animation purpose.
     * @return a list containing all image for animation purpose.
     */
    public List<ArrayList<ImageView>> getListOfPictureOfTheCharacter(){
        return characteristics.getListOfPictureOfTheCharacter();
    }

    /**
     * get a logo for a character.
     * @return a logo for a character.
     */
    public ImageViewSizePos getLogo() {
        return logo;
    }
}
