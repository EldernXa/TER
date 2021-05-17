package mainTER.Tools;

import mainTER.DBManage.*;
import mainTER.exception.*;

public class DataInsert {

    private DataInsert() {

    }

    public static void insertPerson() {
        PersonDBManager personDBManager = new PersonDBManager();
        if(!personDBManager.verifyDBPersonExist()) {
            personDBManager.createTablePerson();
            try {
                personDBManager.insertIntoTablePerson("Paladin", 5, 20, 5, 5, true);
                personDBManager.insertIntoTablePerson("Demon", 10, 5, 8, 2, true);
                personDBManager.insertIntoTablePerson("Serpent", 8, 10, 0, 3, false);
                personDBManager.insertIntoTablePerson("HommeDragon", 10, 5, 8, 2, true);
            } catch (PersonDataAlreadyExistException personDataAlreadyExistException) {
                System.out.println("Problème dans l'insertion des données des Personnages.");
            } catch (PersonDataNotCorrectException personDataDoesntCorrectException) {
                System.out.println("Les données inséres ne sont pas correcte.");
            }
        }
    }

    public static void insertControls() {
        ControlsDBManager controlsDBManager = new ControlsDBManager();
        if(!controlsDBManager.verifyTableControlsExist()) {
            controlsDBManager.createTableControls();
            try {
                controlsDBManager.insertIntoTableControls("d", "q", " ", "a", "e", "f");
            } catch (ControlsDataAlreadyExistsException controlsDataAlreadyExists) {
                System.out.println("Probleme dans l'insertien de controles");
            }
        }
    }

    public static void insertSkill() {
        SkillDBManager skillDBManager = new SkillDBManager();
        if(!skillDBManager.verifyTableSkillExist()) {
            skillDBManager.createTableSkill();
            try {
                skillDBManager.insertIntoTableSkill("SHIELD", "1", "Paladin", true, false, true,
                        10, 0, "Use the shield to lift other characters");
                skillDBManager.insertIntoTableSkill("ATTACK", "2", "Paladin", false, true, false,
                        1, 0, "Use the sword to break obstacles");
                skillDBManager.insertIntoTableSkill("BARRIER", "3", "Paladin", true, false, true,
                        10, 3, "Create a barrier which absorb all damages for a short \ntime");
                skillDBManager.insertIntoTableSkill("FLY", "1", "Demon", true, false, true,
                        10, 3, "Increase the speed for a short time");
                skillDBManager.insertIntoTableSkill("MOULT", "1", "Serpent", false, false, false,
                        10, 6, "Create a moult of himself. The moult is usable as a \nplatform for one time");
                skillDBManager.insertIntoTableSkill("WALL_JUMP", "1", "HommeDragon", false, false, false,
                        0, 0, "Will grab walls while jumping");
            } catch (SkillAlreadyExistException | SkillCtrlAlreadyUsedException | SkillDataNotCorrectException |
                    SkillCtrlAlreadyUsedByMovementControlException | SkillCharacterNotExistException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public static void insertMap() {
        MapDBManager mapDBManager = new MapDBManager();
        if(!mapDBManager.verifyTableMapExist()) {
            mapDBManager.createTableMap();
            try {
                mapDBManager.insertIntoTableMap("Forest", "Demon", 4500, 600);
                mapDBManager.insertIntoTableMap("Castle", "Demon", 10, 2250);
                mapDBManager.insertIntoTableMap("City", "Demon", 10, 4000);
            } catch (MapCharacterNotExistException | MapAlreadyExistException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void insetCheckpoints() {
        CheckpointsDBManager checkpointsDBManager = new CheckpointsDBManager();
        if(!checkpointsDBManager.verifyTableCheckpointsExist()) {
            checkpointsDBManager.createTableCheckPoints();

            try {
                checkpointsDBManager.insertIntoTableCheckpoints(0, 0, "Paladin", "Forest");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void insertUpgradeSkillsValue() {
        UpgradeSkillDBManager upgradeSkillDBManager = new UpgradeSkillDBManager();
        upgradeSkillDBManager.removeTableUpgradeSkill();
        upgradeSkillDBManager.createTableUpgradeSkill();
        SkillDBManager skillDBManager = new SkillDBManager();

        try {

            upgradeSkillDBManager.insertIntoTableUpgradeSkill("Demon", 1, "Reduce the cooldown",
                    skillDBManager.getTimeCooldown("Demon",1), 1, "reduce the cooldown");
            upgradeSkillDBManager.insertIntoTableUpgradeSkill("Demon", 1, "Increase the duration",
                    skillDBManager.getTimeSkill("Demon",1),1, "Increase the duration");


            upgradeSkillDBManager.insertIntoTableUpgradeSkill("Paladin", 3, "Reduce the cooldown",
                    skillDBManager.getTimeCooldown("Paladin",3), 1, "reduce the barrier cooldown");
            upgradeSkillDBManager.insertIntoTableUpgradeSkill("Paladin", 3, "Increase the duration",
                    skillDBManager.getTimeSkill("Paladin",3), 1, "increase the barrier duration");

            upgradeSkillDBManager.insertIntoTableUpgradeSkill("Paladin", 1, "Reduce the cooldown",
                    skillDBManager.getTimeCooldown("Paladin",1), 1, "reduce the barrier cooldown");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
