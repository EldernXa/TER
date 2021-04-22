package mainTER.CharacterGameplay;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class ActiveSkill {
    private final String ctrlKey;
    private final boolean animateMvt;
    private final boolean animateAction;
    private final boolean isMode;
    private final String nameSkill;
    private final Characteristics characteristics;
    private final ActiveSkillEnum skill;
    private boolean isEnabled;

    public ActiveSkill(String nameSkill, String ctrlKey, boolean animateMvt, boolean animateAction, boolean isMode, Characteristics characteristics){
        this.nameSkill = nameSkill;
        this.ctrlKey = ctrlKey;
        this.animateMvt = animateMvt;
        this.animateAction = animateAction;
        this.isMode = isMode;
        this.characteristics = characteristics;
        isEnabled = false;
        skill = ActiveSkillEnum.valueOf(this.nameSkill);
    }

    public EventHandler<KeyEvent> eventForSkill(){
        return event -> {
            if (event.getCode().getChar().equals(ctrlKey)){
                if(skill == ActiveSkillEnum.SHIELD_MODE){
                    if(!isEnabled) {
                        characteristics.setCanJump(false);
                    }else{
                        characteristics.setCanJump(true);
                    }
                }
            }
        };
    }

}
