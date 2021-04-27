package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import mainTER.Tools.CharacterMovementAndDisplayManagement;
import mainTER.Tools.Coordinate;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ActiveSkill implements Skill{
    private final String nameCharacter;
    private final String ctrlKey;
    private final boolean animateMvt;
    private final boolean animateAction;
    private final boolean isMode;
    private final String nameSkill;
    private final Character character;
    private final ActiveSkillEnum skill;
    private boolean isEnabled;

    public ActiveSkill(String nameCharacter, String nameSkill, String ctrlKey, boolean animateMvt, boolean animateAction, boolean isMode, Character character){
        this.nameCharacter = nameCharacter;
        this.nameSkill = nameSkill;
        this.ctrlKey = ctrlKey;
        this.animateMvt = animateMvt;
        this.animateAction = animateAction;
        this.isMode = isMode;
        this.character = character;
        isEnabled = false;
        skill = ActiveSkillEnum.valueOf(this.nameSkill+(isMode?"_MODE":""));
    }

    public void init(){
        isEnabled = false;
        try {
            initAnimateForWalk();
            initAnimateForReverseWalk();
        }catch(URISyntaxException uriSyntaxException){
            System.out.println("Problème dans le path des images du personnages.");
        }
    }

    public EventHandler<KeyEvent> eventForSkill(AnimationCharacter animationCharacter,
                                                CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement, int tpsDuration){
        return event -> {
            if (event.getCode().getChar().equals(ctrlKey)){
                if(skill == ActiveSkillEnum.SHIELD_MODE){
                    shieldSkill();
                }else if(skill == ActiveSkillEnum.ATTACK){
                    attackSkill(animationCharacter, characterMovementAndDisplayManagement, tpsDuration);
                }
            }
        };
    }

    private void shieldSkill(){
        if(!isEnabled) {
            isEnabled = true;
            character.getCharacteristics().setCanJump(false);
            try {
                changeAnimateForWalk();
                changeAnimateForReverseWalk();
            }catch(Exception ignored){

            }
        }else{
            isEnabled = false;
            character.getCharacteristics().setCanJump(true);
            try {
                initAnimateForWalk();
                initAnimateForReverseWalk();
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

    private void changeAnimateForWalk() throws URISyntaxException {
        final String replace = nameSkill.toLowerCase()+Position.WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.WALK);
    }

    private void changeAnimateForReverseWalk() throws URISyntaxException{
        final String replace = nameSkill.toLowerCase()+Position.REVERSE_WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_WALK);

    }

    private void initAnimateForWalk() throws URISyntaxException {
        final String replace = Position.WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.WALK);
    }

    private void initAnimateForReverseWalk() throws URISyntaxException {
        final String replace = Position.REVERSE_WALK.toString().toLowerCase().replace("_", "");
        changeAnimate(replace, Position.REVERSE_WALK);
    }

}
