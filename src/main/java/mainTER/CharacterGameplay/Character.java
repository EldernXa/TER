package mainTER.CharacterGameplay;

public class Character {
    private String name;
    private Coordinate initialCoordinate;
    private AnimationCharacter Animation;

    public Character(String name, Coordinate coordinate, AnimationCharacter animation) {
        this.name = name;
        this.initialCoordinate = coordinate;
        Animation = animation;
    }


    public String getName() {
        return name;
    }


    public Coordinate getInitialCoordinate() {
        return initialCoordinate;
    }

    public void setInitialCoordinate(Coordinate initialCoordinate) {
        this.initialCoordinate = initialCoordinate;
    }

    public AnimationCharacter getAnimation() {
        return Animation;
    }

    public void setAnimation(AnimationCharacter animation) {
        Animation = animation;
    }
}
