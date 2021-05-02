package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import mainTER.DBManage.SkillDBManager;
import mainTER.Tools.CharacterMovementAndDisplayManagement;
import mainTER.Tools.Coordinate;
import mainTER.exception.SkillDataGetException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ActiveSkill implements Skill{
    private final String nameCharacter;
    private final String nameSkill;
    private final Character character;
    private final ActiveSkillEnum skill;
    private boolean isEnabled;
    private boolean finishSkill;
    private final SkillDBManager skillDBManager;
    private final int numSkill;

    public ActiveSkill(String nameCharacter, String nameSkill, int numSkill, Character character){
        this.nameCharacter = nameCharacter;
        this.nameSkill = nameSkill;
        this.numSkill = numSkill;
        this.character = character;
        isEnabled = false;
        finishSkill = true;
        this.skillDBManager = new SkillDBManager();
        boolean isMode = false;
        try{
            isMode = skillDBManager.getIsMode(nameCharacter, numSkill);
        }catch(SkillDataGetException skillDataGetException){
            System.out.println("Problème dans la récupération des données.");
        }
        skill = ActiveSkillEnum.valueOf(this.nameSkill+(isMode?"_MODE":""));
    }

    public boolean getFinishSkill(){
        return finishSkill;
    }

    public void init(){
        isEnabled = false;
        finishSkill = true;
        try {
            initAnimateForWalk();
            initAnimateForReverseWalk();
        }catch(URISyntaxException uriSyntaxException){
            System.out.println("Problème dans le path des images du personnages.");
        }
    }

    public EventHandler<KeyEvent> eventForSkill(AnimationCharacter animationCharacter,
                                                CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement, int tpsDuration){
        String ctrlKey = "";
        try{
            ctrlKey = skillDBManager.getCtrlKey(nameCharacter, numSkill);
        }catch(SkillDataGetException skillDataGetException){
            System.out.println("Problème dans la récupération des données.");
        }
        String finalCtrlKey = ctrlKey;
        return event -> {
            if (event.getCode().getChar().equalsIgnoreCase(finalCtrlKey)){
                if(skill == ActiveSkillEnum.SHIELD_MODE){
                    shieldSkill();
                }else if(skill == ActiveSkillEnum.ATTACK){
                    attackSkill(animationCharacter, characterMovementAndDisplayManagement, tpsDuration);
                } else if (skill == ActiveSkillEnum.BARRIER_MODE) {
                    shieldSkill();

                } else if (skill == ActiveSkillEnum.FLY_MODE) {
                    flySkill();

                }
                else if(skill == ActiveSkillEnum.MOULT){


                }
            }
        };
    }

    private void flySkill() {
        //Bug quand on change de perso et qu'on revient sur le démon, il arrive plus a changer de compétence
        if (!isEnabled) {
            System.out.println("okok");
            isEnabled = true;
            finishSkill = false;
            try {
                Thread t = new Thread(() -> {
                    try {
                        changeAnimateForWalk();
                        changeAnimateForReverseWalk();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    character.getCharacteristics().setSpeed(character.getSpeed() * 3);
                    try {
                        TimeUnit.SECONDS.sleep(3);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        initAnimateForReverseWalk();
                        initAnimateForWalk();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    character.getCharacteristics().resetSpeed();
                    isEnabled = false;
                    finishSkill = true;
                });
                t.start();
            } catch (Exception ignored) {
            }
        } else {
            isEnabled = false;
            try {
                initAnimateForWalk();
                initAnimateForReverseWalk();
                character.getCharacteristics().resetSpeed();
                finishSkill = true;
            } catch (Exception ignored) {

            }
        }
    }

    private void shieldSkill(){
        if(!isEnabled) {
            isEnabled = true;
            character.getCharacteristics().setCanJump(false);
            try {
                changeAnimateForWalk();
                changeAnimateForReverseWalk();
                changeAnimateForMotionless();
                changeAnimateForReverseMotionless();
            }catch(Exception ignored){

            }
        }else{
            isEnabled = false;
            character.getCharacteristics().setCanJump(true);
            try {
                initAnimateForWalk();
                initAnimateForReverseWalk();
                initAnimateForMotionless();
                initAnimateForReverseMotionless();
            }catch(Exception ignored){

            }
        }
    }

    private void attackSkill(AnimationCharacter animationCharacter, CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement,
                             int tpsDuration){
        animationCharacter.setCanMove(false);
        Coordinate c = characterMovementAndDisplayManagement.getCoordinateOfTheActualImg();
        List<ImageView> listPersonalizedAnimate = null;
        try{
            listPersonalizedAnimate = listPersonalizedAnimate();
        }catch(URISyntaxException uriSyntaxException){
            System.out.println("Problème de path.");
        }
        animationCharacter.getTimeline().stop();
        animationCharacter.getTimeline().getKeyFrames().clear();
        List<ImageView> finalListPersonalizedAnimate = listPersonalizedAnimate;
        AtomicInteger i = new AtomicInteger();
        animationCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                        Duration.millis(tpsDuration),
                        tps->{
                            if(finalListPersonalizedAnimate != null){
                                characterMovementAndDisplayManagement.displayNode(finalListPersonalizedAnimate.get(i.getAndIncrement()),
                                        c.getX(),c.getY());
                                if(i.get()>=finalListPersonalizedAnimate.size()){
                                    animationCharacter.setCanMove(true);
                                    animationCharacter.getTimeline().stop();
                                    animationCharacter.getTimeline().getKeyFrames().clear();
                                }
                            }else{
                                animationCharacter.setCanMove(true);
                            }
                        }
                )
        );
        animationCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationCharacter.getTimeline().play();
    }

    private List<ImageView> listPersonalizedAnimate() throws URISyntaxException{
        ArrayList<ImageView> listPersonalizedAnimate = new ArrayList<>();
        final String replace = nameSkill.toLowerCase();
        URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/"+nameCharacter+"/"+replace);
        File file = Paths.get(url.toURI()).toFile();
        if(file.exists() && file.isDirectory()){
            for(File fileForOneSprite : Objects.requireNonNull(file.listFiles())){
                listPersonalizedAnimate.add(new ImageView(new Image(fileForOneSprite.toURI().toString())));
            }
        }


        return listPersonalizedAnimate;
    }

    private void changeAnimate(String replace, Position pos) throws URISyntaxException {
        URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/"+nameCharacter+"/"+replace);

        File file = Paths.get(url.toURI()).toFile();
        if(file.exists() && file.isDirectory()){
            character.getCharacteristics().getListOfPictureOfTheCharacter().get(pos.ordinal()).clear();
            for(File fileForOneSprite : Objects.requireNonNull(file.listFiles())){
                character.getCharacteristics().getListOfPictureOfTheCharacter().get(pos.ordinal()).add(new ImageView(new Image(fileForOneSprite.toURI().toString())));
            }
        }
    }

    /*** CHANGE ***/

    private void changeAnimateForWalk() throws URISyntaxException {
        final String replace = nameSkill.toLowerCase()+Position.WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.WALK);
    }

    private void changeAnimateForReverseWalk() throws URISyntaxException{
        final String replace = nameSkill.toLowerCase()+Position.REVERSE_WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_WALK);

    }

    private void changeAnimateForMotionless() throws URISyntaxException {
        final String replace = nameSkill.toLowerCase() + Position.MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.MOTIONLESS);

    }


    private void changeAnimateForReverseMotionless() throws URISyntaxException {
        System.out.println(nameSkill);
        final String replace = nameSkill.toLowerCase() + Position.REVERSE_MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_MOTIONLESS);

    }

    /*** INIT ***/
    private void initAnimateForWalk() throws URISyntaxException {
        final String replace = Position.WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.WALK);
    }

    private void initAnimateForReverseWalk() throws URISyntaxException {
        final String replace = Position.REVERSE_WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_WALK);
    }

    private void initAnimateForMotionless() throws URISyntaxException {
        final String replace = Position.MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.MOTIONLESS);
    }

    private void initAnimateForReverseMotionless() throws URISyntaxException {
        final String replace = Position.REVERSE_MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_MOTIONLESS);
    }


}
