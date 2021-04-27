package mainTER.CharacterGameplay;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class ActiveSkill implements Skill{
    private final String nameCharacter;
    private final String ctrlKey;
    private final boolean animateMvt;
    private final boolean animateAction;
    private final boolean isMode;
    private final String nameSkill;
    private final Characteristics characteristics;
    private final ActiveSkillEnum skill;
    private boolean isEnabled;

    public ActiveSkill(String nameCharacter, String nameSkill, String ctrlKey, boolean animateMvt, boolean animateAction, boolean isMode, Characteristics characteristics){
        this.nameCharacter = nameCharacter;
        this.nameSkill = nameSkill;
        this.ctrlKey = ctrlKey;
        this.animateMvt = animateMvt;
        this.animateAction = animateAction;
        this.isMode = isMode;
        this.characteristics = characteristics;
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

    public EventHandler<KeyEvent> eventForSkill(){
        return event -> {
            if (event.getCode().getChar().equals(ctrlKey)){
                if(skill == ActiveSkillEnum.SHIELD_MODE){
                    if(!isEnabled) {
                        isEnabled = true;
                        characteristics.setCanJump(false);
                        try {
                            changeAnimateForWalk();
                            changeAnimateForReverseWalk();
                        }catch(Exception ignored){

                        }
                    }else{
                        isEnabled = false;
                        characteristics.setCanJump(true);
                        try {
                            initAnimateForWalk();
                            initAnimateForReverseWalk();
                        }catch(Exception ignored){

                        }
                    }
                }
            }
        };
    }

    private void changeAnimate(String replace, Position pos) throws URISyntaxException {
        URL url = this.getClass().getResource("/mainTER/CharacterGameplay/images/"+nameCharacter+"/"+replace);

        File file = Paths.get(url.toURI()).toFile();
        if(file.exists() && file.isDirectory()){
            characteristics.getListOfPictureOfTheCharacter().get(pos.ordinal()).clear();
            for(File fileForOneSprite : Objects.requireNonNull(file.listFiles())){
                characteristics.getListOfPictureOfTheCharacter().get(pos.ordinal()).add(new ImageView(new Image(fileForOneSprite.toURI().toString())));
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
