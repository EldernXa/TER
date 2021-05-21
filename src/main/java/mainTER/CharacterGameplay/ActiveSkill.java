package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import mainTER.DBManage.SkillDBManager;
import mainTER.MapPackage.*;
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
    private boolean cooldownFinished;
    private final SkillDBManager skillDBManager;
    private final int numSkill;
    private EventHandler<KeyEvent> eventHandler;
    private final float timeCooldown;
    private final float timeSkill;
    private boolean threadIsRunning = false;
    private PaladinShieldMode paladinShieldMode = null;
    private ObjectLinker objectLinkerFirst = null;

    public boolean isEnabled(){
        return isEnabled;
    }

    public int getNumSkill(){
        return numSkill;
    }

    public ActiveSkillEnum getSkillEnum(){
        return skill;
    }

    public ActiveSkill(String nameCharacter, String nameSkill, int numSkill, Character character, float timeCooldown, float timeSkill){
        this.nameCharacter = nameCharacter;
        this.nameSkill = nameSkill;
        this.numSkill = numSkill;
        this.character = character;
        this.timeCooldown = timeCooldown;
        this.timeSkill = timeSkill;
        isEnabled = false;
        cooldownFinished = true;
        this.skillDBManager = new SkillDBManager();
        boolean isMode = false;
        try{
            isMode = skillDBManager.getIsMode(nameCharacter, numSkill);
        }catch(SkillDataGetException skillDataGetException){
            System.out.println("Problème dans la récupération des données.");
        }
        skill = ActiveSkillEnum.valueOf(this.nameSkill+(isMode?"_MODE":""));
    }

    public static void changeAnimateForACharacter(Character character, int numSkill){
        if(numSkill!=-1){
            for(Skill skill : character.getListSkill()){
                if(skill.getClass() == ActiveSkill.class && ((ActiveSkill) skill).getNumSkill() == numSkill){
                        changeListAnimate(((ActiveSkill) skill).getSkillEnum(), character, ((ActiveSkill) skill).nameSkill);
                }
            }
        }else{
            try {
                initAnimateForMotionless(character);
                initAnimateForReverseMotionless(character);
                initAnimateForWalk(character);
                initAnimateForReverseWalk(character);
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }

    private static void changeListAnimate(ActiveSkillEnum skill, Character character, String nameSkill){
        if(skill == ActiveSkillEnum.SHIELD_MODE || skill == ActiveSkillEnum.BARRIER_MODE){
            try {
                changeAnimateForWalk(nameSkill, character);
                changeAnimateForMotionless(nameSkill,character);
                changeAnimateForReverseMotionless(nameSkill, character);
                changeAnimateForReverseWalk(skill.name(), character);
            }catch(URISyntaxException exception){
                exception.printStackTrace();
            }
        }else if(skill == ActiveSkillEnum.FLY_MODE){
            try{
                changeAnimateForWalk(nameSkill, character);
                changeAnimateForReverseWalk(nameSkill, character);
            }catch(URISyntaxException exception){
                exception.printStackTrace();
            }
        }
    }

    public void init(){
        isEnabled = false;
        cooldownFinished = true;
        try{
            initAnimateForWalk(character);
            initAnimateForReverseWalk(character);
            initAnimateForMotionless(character);
            initAnimateForReverseMotionless(character);
        }catch(URISyntaxException uriSyntaxException){
            System.out.println("Problème dans le path des images du personnages.");
        }
    }

    public EventHandler<KeyEvent> getEventHandler(){
        return eventHandler;
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
        eventHandler = event -> {
            if (event.getCode().getChar().equalsIgnoreCase(finalCtrlKey)){
                if(skill == ActiveSkillEnum.SHIELD_MODE){
                    shieldSkill(animationCharacter, characterMovementAndDisplayManagement);
                }else if(skill == ActiveSkillEnum.ATTACK){
                    attackSkill(animationCharacter, characterMovementAndDisplayManagement, tpsDuration);
                } else if (skill == ActiveSkillEnum.BARRIER_MODE) {
                    barrierSkill();

                } else if (skill == ActiveSkillEnum.FLY_MODE) {
                    flySkill();
                }
                else if(skill == ActiveSkillEnum.MOULT){
                    moultSkill(characterMovementAndDisplayManagement, animationCharacter);
                }
            }
        };
        return eventHandler;
    }

    private void moultSkill(CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement, AnimationCharacter animationCharacter){
        if(!isEnabled && cooldownFinished &&
                (animationCharacter.getCurrentPosition() == Position.MOTIONLESS || animationCharacter.getCurrentPosition()==Position.REVERSE_MOTIONLESS)){
            boolean isReversed = animationCharacter.getCurrentPosition() == Position.REVERSE_MOTIONLESS ||
                    animationCharacter.getCurrentPosition() == Position.REVERSE_WALK ||
                    animationCharacter.getCurrentPosition() == Position.REVERSE_JUMP;
            isEnabled = true;
            cooldownFinished = false;
            Coordinate coordinateMoult = new Coordinate(
                    characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getX(),
                    characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getY()
            );
            FirstMoultSerpent firstMoultSerpent = new FirstMoultSerpent(coordinateMoult, isReversed);
            ObjectLinker objectLinkerFirstForSerpent = new ObjectLinker(firstMoultSerpent, firstMoultSerpent.clone());
            Map.objectLinkers.add(objectLinkerFirstForSerpent);
            characterMovementAndDisplayManagement.displayOtherNode((ImageView)firstMoultSerpent.getAppropriateNode(), coordinateMoult.getX(), coordinateMoult.getY());
            Thread t = new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ignored) {
                }
                Platform.runLater(()->{
                    characterMovementAndDisplayManagement.removeOtherNode((ImageView)firstMoultSerpent.getAppropriateNode());
                    Map.objectLinkers.remove(objectLinkerFirstForSerpent);
                    SecondMoultSerpent secondMoultSerpent = new SecondMoultSerpent(coordinateMoult, isReversed);
                    ObjectLinker objectLinkerSecond = new ObjectLinker(secondMoultSerpent, secondMoultSerpent.clone());
                    isEnabled = false;
                    Map.objectLinkers.add(objectLinkerSecond);
                    characterMovementAndDisplayManagement.displayOtherNode((ImageView)secondMoultSerpent.getAppropriateNode(), coordinateMoult.getX(),
                            coordinateMoult.getY());
                    Thread thread = new Thread(()->{
                        try{
                            TimeUnit.SECONDS.sleep((int)timeSkill);
                        }catch(Exception ignored){

                        }
                        Platform.runLater(()->{
                            characterMovementAndDisplayManagement.removeOtherNode((ImageView)secondMoultSerpent.getAppropriateNode());
                            Map.objectLinkers.remove(objectLinkerSecond);
                            runThreadForCooldown();
                        });
                    });
                    thread.start();
                });
            });
            t.start();
        }
    }

    private void runThreadForCooldown(){
        Thread threadForCooldown = new Thread(()->{
            try{
                threadIsRunning = true;
                TimeUnit.SECONDS.sleep((int)timeCooldown);
                cooldownFinished = true;
                threadIsRunning = false;
            }catch(Exception ignored){

            }
        });
        threadForCooldown.start();
    }

    private void flySkill() {
        if (!isEnabled && cooldownFinished) {
            isEnabled = true;
            cooldownFinished = false;
            try {
                threadForFlySkill();
            } catch (Exception ignored) {
            }
        } else {
            isEnabled = false;
            try {
                initAnimateForWalk(character);
                initAnimateForReverseWalk(character);
            } catch (Exception ignored) {

            }
            character.getCharacteristics().resetSpeed();
            if(!threadIsRunning){
                runThreadForCooldown();
            }
        }
    }

    private void threadForFlySkill(){
        Thread t = new Thread(()->{
            try {
                changeAnimateForWalk(nameSkill, character);
                changeAnimateForReverseWalk(nameSkill, character);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            character.getCharacteristics().setSpeed(character.getSpeed() * 1.5);
            try {
                TimeUnit.SECONDS.sleep((long)timeSkill);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                initAnimateForWalk(character);
                initAnimateForReverseWalk(character);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            character.getCharacteristics().resetSpeed();
            isEnabled = false;
            runThreadForCooldown();
        });
        t.start();
    }

    private void shieldSkill(AnimationCharacter animationCharacter, CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement){
        if(!isEnabled) {
            boolean isReversed = animationCharacter.getCurrentPosition() == Position.REVERSE_MOTIONLESS ||
                    animationCharacter.getCurrentPosition() == Position.REVERSE_WALK ||
                    animationCharacter.getCurrentPosition() == Position.REVERSE_JUMP;
            Coordinate coordinateMoult = new Coordinate(
                    characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getX(),
                    characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getY()
            );
            paladinShieldMode = new PaladinShieldMode(coordinateMoult, isReversed);
            objectLinkerFirst = new ObjectLinker(paladinShieldMode, paladinShieldMode.clone());
            Map.objectLinkers.add(objectLinkerFirst);
            characterMovementAndDisplayManagement.displayOtherNode((ImageView)paladinShieldMode.getAppropriateNode(), coordinateMoult.getX(), coordinateMoult.getY());
            animationCharacter.setCanMove(false);
            characterMovementAndDisplayManagement.setCannotDisplay();
            isEnabled = true;
            character.getCharacteristics().setCanJump(false);
        }else{
            characterMovementAndDisplayManagement.removeOtherNode((ImageView)paladinShieldMode.getAppropriateNode());
            Map.objectLinkers.remove(objectLinkerFirst);
            characterMovementAndDisplayManagement.setCanDisplay();
            animationCharacter.setCanMove(true);
            isEnabled = false;
            character.getCharacteristics().setCanJump(true);
        }
    }

    private void barrierSkill(){
        if(!isEnabled && cooldownFinished){
            cooldownFinished = false;
            isEnabled = true;
            character.getCharacteristics().setCanJump(false);
            character.setCanDie(false);
            try{
                changeAnimateForWalk(nameSkill, character);
                changeAnimateForReverseWalk(nameSkill, character);
                changeAnimateForMotionless(nameSkill, character);
                changeAnimateForReverseMotionless(nameSkill, character);
            }catch(Exception ignored){

            }
            Thread thread = new Thread(()->{
                try{
                    threadIsRunning = true;
                    TimeUnit.SECONDS.sleep((long) timeSkill);
                    initAnimateForWalk(character);
                    initAnimateForReverseWalk(character);
                    initAnimateForMotionless(character);
                    initAnimateForReverseMotionless(character);
                    character.setCanDie(true);
                    character.getCharacteristics().setCanJump(true);
                    isEnabled = false;
                    threadIsRunning = false;
                }catch(Exception ignored){

                }

                runThreadForCooldown();
            });
            thread.start();
        }else{
            if(!threadIsRunning) {
                isEnabled = false;
                character.setCanDie(true);
                character.getCharacteristics().setCanJump(true);
                try {
                    initAnimateForWalk(character);
                    initAnimateForReverseWalk(character);
                    initAnimateForMotionless(character);
                    initAnimateForReverseMotionless(character);
                } catch (Exception ignored) {

                }
                runThreadForCooldown();
            }
        }
    }

    private void attackSkill(AnimationCharacter animationCharacter, CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement,
                             int tpsDuration){
        if(!isEnabled && cooldownFinished) {
            cooldownFinished = false;
            isEnabled = true;
            animationCharacter.setCanMove(false);
            Coordinate c = characterMovementAndDisplayManagement.getCoordinateOfTheActualImg();
            List<ImageView> listPersonalizedAnimate = null;
            try {
                listPersonalizedAnimate = listPersonalizedAnimate(animationCharacter.getCurrentPosition() == Position.REVERSE_MOTIONLESS);
            } catch (URISyntaxException uriSyntaxException) {
                System.out.println("Problème de path.");
            }
            createNewTimeline(animationCharacter, listPersonalizedAnimate, tpsDuration, characterMovementAndDisplayManagement, c);

        }
    }

    private void createNewTimeline(AnimationCharacter animationCharacter, List<ImageView> listPersonalizedAnimate, int tpsDuration,
                                   CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement, Coordinate c){
        animationCharacter.getTimeline().stop();
        animationCharacter.getTimeline().getKeyFrames().clear();
        AtomicInteger i = new AtomicInteger();
        animationCharacter.setCanMotionLess(false);
        animationCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                        Duration.millis(tpsDuration),
                        tps -> insideTimelineForAttack(listPersonalizedAnimate, i, characterMovementAndDisplayManagement, c, animationCharacter)
                )
        );
        animationCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationCharacter.getTimeline().play();
    }

    private void insideTimelineForAttack(List<ImageView> listPersonalizedAnimate, AtomicInteger i,
                                         CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement, Coordinate c,
                                         AnimationCharacter animationCharacter){
        if (listPersonalizedAnimate != null) {
            if(i.get()< listPersonalizedAnimate.size()) {
                characterMovementAndDisplayManagement.displayNode(listPersonalizedAnimate.get(i.getAndIncrement()),
                        c.getX(), c.getY());
            }else{
                i.incrementAndGet();
            }
            if (i.get() > listPersonalizedAnimate.size()) {
                animationCharacter.setCanMove(true);
                animationCharacter.setCanMotionLess(true);
                animationCharacter.setMotionless();
                characterMovementAndDisplayManagement.displayNode(animationCharacter.nextImage(),
                        c.getX(), c.getY());
                animationCharacter.getTimeline().stop();
                animationCharacter.getTimeline().getKeyFrames().clear();
                isEnabled = false;
                if(!threadIsRunning) {
                    runThreadForCooldown();
                }
            }
        } else {
            animationCharacter.setCanMove(true);
            animationCharacter.setCanMotionLess(true);
            isEnabled = false;
            if(!threadIsRunning) {
                runThreadForCooldown();
            }
        }
    }

    private List<ImageView> listPersonalizedAnimate(boolean isReverse) throws URISyntaxException{
        ArrayList<ImageView> listPersonalizedAnimate = new ArrayList<>();
        final String replace;
        if(!isReverse)
            replace = nameSkill.toLowerCase();
        else
            replace = "reverse"+nameSkill.toLowerCase();
        URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/"+nameCharacter+"/"+replace);
        File file = Paths.get(Objects.requireNonNull(url).toURI()).toFile();
        if(file.exists() && file.isDirectory()){
            for(File fileForOneSprite : Objects.requireNonNull(file.listFiles())){
                listPersonalizedAnimate.add(new ImageView(new Image(fileForOneSprite.toURI().toString())));
            }
        }


        return listPersonalizedAnimate;
    }

    private static void changeAnimate(String replace, Position pos, Character character) throws URISyntaxException {
        URL url = ActiveSkill.class.getResource("/mainTER/CharacterGameplay/images/"+character.getName()+"/"+replace.replace("_mode",""));

        File file = Paths.get(Objects.requireNonNull(url).toURI()).toFile();
        if(file.exists() && file.isDirectory()){
            character.getCharacteristics().getListOfPictureOfTheCharacter().get(pos.ordinal()).clear();
            for(File fileForOneSprite : Objects.requireNonNull(file.listFiles())){
                character.getCharacteristics().getListOfPictureOfTheCharacter().get(pos.ordinal()).add(new ImageView(new Image(fileForOneSprite.toURI().toString())));
            }
        }
    }

    /*** CHANGE ***/

    private static void changeAnimateForWalk(String nameSkill, Character character) throws URISyntaxException {
        final String replace = nameSkill.toLowerCase()+Position.WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.WALK, character);
    }

    private static void changeAnimateForReverseWalk(String nameSkill, Character character) throws URISyntaxException{
        final String replace = nameSkill.toLowerCase()+Position.REVERSE_WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_WALK, character);

    }

    private static void changeAnimateForMotionless(String nameSkill, Character character) throws URISyntaxException {
        final String replace = nameSkill.toLowerCase() + Position.MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.MOTIONLESS, character);

    }


    private static void changeAnimateForReverseMotionless(String nameSkill, Character character) throws URISyntaxException {
        final String replace = nameSkill.toLowerCase() + Position.REVERSE_MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_MOTIONLESS, character);

    }

    /*** INIT ***/
    private static void initAnimateForWalk(Character character) throws URISyntaxException {
        final String replace = Position.WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.WALK, character);
    }

    private static void initAnimateForReverseWalk(Character character) throws URISyntaxException {
        final String replace = Position.REVERSE_WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_WALK, character);
    }

    private static void initAnimateForMotionless(Character character) throws URISyntaxException {
        final String replace = Position.MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.MOTIONLESS, character);
    }

    private static void initAnimateForReverseMotionless(Character character) throws URISyntaxException {
        final String replace = Position.REVERSE_MOTIONLESS.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_MOTIONLESS, character);
    }


}
