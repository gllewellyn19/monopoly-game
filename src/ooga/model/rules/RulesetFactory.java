package ooga.model.rules;

import ooga.model.Ruleset;
import ooga.model.dataReaders.InfoForGamePlay;
import ooga.model.exceptions.UnsupportedRulesetException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Creates the ruleset based on the endType provided
 * in the properties file
 * @author delaneydemark
 */
public class RulesetFactory {

    private static final String PACKAGE_PATH = "ooga.model.rules.";
    private static final String CLASS_ENDING = "Ruleset";

    /**
     * Initializes the proper ruleset for the game based on endType of the game
     * @param endType is the name for rules for ending the game
     * @return the created rulest
     */
    public Ruleset determineRuleset(String endType) {
        Ruleset rules;
        try {
            Class<?> ruleClass = Class.forName(PACKAGE_PATH + endType + CLASS_ENDING);
            Class[] parameters = null;
            Constructor<?> ruleConstructor = ruleClass.getDeclaredConstructor(parameters);
            rules = (Ruleset) ruleConstructor.newInstance();
            return rules;
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
            throw new UnsupportedRulesetException("Ruleset not supported");
        }
    }
}
