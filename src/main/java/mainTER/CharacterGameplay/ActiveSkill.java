package mainTER.CharacterGameplay;

public class ActiveSkill {
    private final char ctrlKey;
    private final boolean isAnimate;
    private final String nameSkill;
    private final Characteristics characteristics;
    private final ActiveSkillEnum skill;

    public ActiveSkill(String nameSkill, char ctrlKey, boolean isAnimate, Characteristics characteristics){
        this.nameSkill = nameSkill;
        this.ctrlKey = ctrlKey;
        this.isAnimate = isAnimate;
        this.characteristics = characteristics;
        skill = ActiveSkillEnum.valueOf(this.nameSkill);
    }

    public void enableSkill(){
        if(skill == ActiveSkillEnum.SHIELD_MODE){
            characteristics.setCanJump(false);
        }
    }

}
