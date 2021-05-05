package mainTER.DBManage;

import mainTER.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSkillDBManager {

    private final SkillDBManager skillDBManager = new SkillDBManager("testDB");
    private final ControlsDBManager controlsDBManager = new ControlsDBManager("testDB");
    private final PersonDBManager personDBManager = new PersonDBManager("testDB");
    private final String nameSkill1 = "SHIELD";
    private final int numSkill1 = 1;
    private final String ctrlKey1 = "R";
    private final String nameCharacter = "Paladin";
    private final boolean animateMvt = true;
    private final boolean animateAction = false;
    private final boolean isMode = true;
    private final String nameSkill2 = "Test";
    private final String ctrlKey2 = "F";

    @BeforeEach
    public void init(){
        skillDBManager.dropCascade();
        personDBManager.dropCascade();
        personDBManager.createTablePerson();
        controlsDBManager.createTableControls();
    }

    @AfterEach
    public void afterTest(){
        skillDBManager.removeTableSkill();
        personDBManager.removeTablePerson();
        controlsDBManager.removeTableControls();
    }

    @Test
    public void testCreateTableSkill(){
        try{
            skillDBManager.createTableSkill();
            assertTrue(true);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertIntoSkill(){
        try {
            skillDBManager.createTableSkill();
            personDBManager.insertIntoTablePerson(nameCharacter, 1.0, 2.0, 1.0, 5.0, true);
            skillDBManager.insertIntoTableSkill(nameSkill1, ctrlKey1, nameCharacter, animateMvt, animateAction, isMode);
            assertTrue(true);
        }catch(Exception exception)
        {
            fail();
        }
    }

    @Test
    public void testInsertIntoSkillACharacterWhoDoesntExistThrowException(){
        try{
            insertValueIntoSkill();
            assertThrows(SkillCharacterNotExistException.class,
                    ()->skillDBManager.insertIntoTableSkill("Planer", ctrlKey1, "Serpent", animateMvt, animateAction, isMode));
        }catch (Exception exception){
            fail();
        }
    }

    @Test
    public void testGetListNameSkill(){
        try{
            insertValueIntoSkill();
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertEquals(2, skillDBManager.getListSkillName().size());
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGetListNameCharacter(){
        try{
            insertValueIntoSkill();
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertEquals(1, skillDBManager.getListNameCharacterWithSkill().size());
            assertEquals(nameCharacter, skillDBManager.getListNameCharacterWithSkill().get(0));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGetNumberOfSkillOfACharacter(){
        try{
            insertValueIntoSkill();
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertEquals(2, skillDBManager.getNumberSkillOfACharacter(nameCharacter));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingNameSkill(){
        try{
            insertValueIntoSkill();
            assertEquals(nameSkill1, skillDBManager.getNameSkill(nameCharacter, numSkill1));
        }catch(Exception exception){
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGettingFalseNameSkillThrowException(){
        try {
            insertValueIntoSkill();
            assertThrows(SkillDataGetException.class, () -> skillDBManager.getNameSkill(nameCharacter, 5));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingCtrlKey(){
        try{
            insertValueIntoSkill();
            assertEquals(ctrlKey1, skillDBManager.getCtrlKey(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingFalseCtrlKeyThrowException(){
        try {
            insertValueIntoSkill();
            assertThrows(SkillDataGetException.class, () -> skillDBManager.getCtrlKey(nameCharacter, 5));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingAnimateMvt(){
        try{
            insertValueIntoSkill();
            assertEquals(animateMvt, skillDBManager.getAnimateMvt(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingFalseAnimateMvtThrowException(){
        try {
            insertValueIntoSkill();
            assertThrows(SkillDataGetException.class, () -> skillDBManager.getAnimateMvt(nameCharacter, 5));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingAnimateAction(){
        try{
            insertValueIntoSkill();
            assertEquals(animateAction, skillDBManager.getAnimateAction(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingFalseAnimateActionThrowException(){
        try {
            insertValueIntoSkill();
            assertThrows(SkillDataGetException.class, () -> skillDBManager.getAnimateAction(nameCharacter, 5));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingIsMode(){
        try{
            insertValueIntoSkill();
            assertEquals(isMode, skillDBManager.getIsMode(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingFalseIsModeThrowException(){
        try {
            insertValueIntoSkill();
            assertThrows(SkillDataGetException.class, () -> skillDBManager.getIsMode(nameCharacter, 5));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testThrowSkillAlreadyExist(){
        try{
            insertValueIntoSkill();
            assertThrows(SkillAlreadyExistException.class, ()->skillDBManager.insertIntoTableSkill(nameSkill1, "P", nameCharacter, animateMvt, animateAction, isMode));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testThrowSkillCtrlAlreadyUsed(){
        try{
            insertValueIntoSkill();
            assertThrows(SkillCtrlAlreadyUsedException.class, ()->skillDBManager.insertIntoTableSkill("test", ctrlKey1, nameCharacter, animateMvt, animateAction, isMode));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testModifyCtrlOfASkill(){
        try{
            insertValueIntoSkill();
            String newCtrlKey = "P";
            assertEquals(ctrlKey1, skillDBManager.getCtrlKey(nameCharacter, numSkill1));
            skillDBManager.modifyCtrlOfACharacter(nameCharacter, nameSkill1, newCtrlKey);
            assertEquals(newCtrlKey, skillDBManager.getCtrlKey(nameCharacter, numSkill1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testModifyCtrlOfASkillWithACtrlAlreadyUsedByTheSameCharacterThrowException(){
        try{
            insertValueIntoSkill();
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertThrows(SkillCtrlAlreadyUsedException.class, ()-> skillDBManager.modifyCtrlOfACharacter(nameCharacter, nameSkill2, ctrlKey1));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testInsertSameCtrlThatMovement(){
        try{
            insertValueIntoSkill();
            controlsDBManager.insertIntoTableControls("N", "A", "B", "R", "T");
            assertThrows(SkillCtrlAlreadyUsedByMovementControlException.class, ()-> skillDBManager.insertIntoTableSkill(nameSkill2, "T", nameCharacter, animateMvt, animateAction, isMode));
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testModifyWithASameCtrlThanControlMovementThrowException(){
        try{
            insertValueIntoSkill();
            controlsDBManager.insertIntoTableControls("N", "A", "B", "R", "T");
            skillDBManager.insertIntoTableSkill(nameSkill2, ctrlKey2, nameCharacter, animateMvt, animateAction, isMode);
            assertThrows(SkillCtrlAlreadyUsedByMovementControlException.class, ()->
                    skillDBManager.modifyCtrlOfACharacter(nameCharacter, nameSkill2, "A"));
        }catch(Exception exception){
            exception.printStackTrace();
            fail();
        }
    }

    private void insertValueIntoSkill() throws SkillAlreadyExistException, SkillCtrlAlreadyUsedException, SkillDataNotCorrectException,
            SkillCtrlAlreadyUsedByMovementControlException, SkillCharacterNotExistException, PersonDataAlreadyExistException,
            PersonDataNotCorrectException {
        skillDBManager.createTableSkill();
        personDBManager.insertIntoTablePerson(nameCharacter, 1.0, 2.0, 1.0, 5.0, true);
        skillDBManager.insertIntoTableSkill(nameSkill1, ctrlKey1, nameCharacter, animateMvt, animateAction, isMode);

    }

}
