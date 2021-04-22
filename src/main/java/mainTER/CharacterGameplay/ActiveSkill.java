package mainTER.CharacterGameplay;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class ActiveSkill {
    private final String ctrlKey;
    private final boolean isAnimate;
    private final String nameSkill;
    private final Characteristics characteristics;
    private final ActiveSkillEnum skill;

    public ActiveSkill(String nameSkill, String ctrlKey, boolean isAnimate, Characteristics characteristics){
        this.nameSkill = nameSkill;
        this.ctrlKey = ctrlKey;
        this.isAnimate = isAnimate;
        this.characteristics = characteristics;
        skill = ActiveSkillEnum.valueOf(this.nameSkill);
    }

    public EventHandler<KeyEvent> eventForSkill(){
        return event -> {
            if (event.getCode().getChar().equals(ctrlKey)){
                if(skill == ActiveSkillEnum.SHIELD_MODE){
                    characteristics.setCanJump(false);
                }
            }
        };
    }

}
