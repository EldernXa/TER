package mainTER.DBManage;

import mainTER.exception.SkillAlreadyExistException;
import mainTER.exception.SkillCtrlAlreadyUsedException;
import mainTER.exception.SkillDataNotCorrectException;
import mainTER.exception.SkillDataGetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSkillDBManager {

    private final SkillDBManager skillDBManager = new SkillDBManager("testDB");
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
    }

    @AfterEach
    public void afterTest(){
        skillDBManager.removeTableSkill();
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
            skillDBManager.insertIntoTableSkill(nameSkill1, ctrlKey1, nameCharacter, animateMvt, animateAction, isMode);
            assertTrue(true);
        }catch(Exception exception)
        {
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
            assertThrows(SkillCtrlAlreadyUsedException.class, ()->{
                skillDBManager.modifyCtrlOfACharacter(nameCharacter, nameSkill2, ctrlKey1);
            });
        }catch(Exception exception){
            fail();
        }
    }

    private void insertValueIntoSkill() throws SkillAlreadyExistException, SkillCtrlAlreadyUsedException, SkillDataNotCorrectException {
        skillDBManager.createTableSkill();
        skillDBManager.insertIntoTableSkill(nameSkill1, ctrlKey1, nameCharacter, animateMvt, animateAction, isMode);

    }

}
