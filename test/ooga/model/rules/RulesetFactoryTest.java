package ooga.model.rules;

import ooga.model.exceptions.UnsupportedRulesetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesetFactoryTest {

    @Test
    void determineRulesetClassic() {
        RulesetFactory factory = new RulesetFactory();
        String className = factory.determineRuleset("Classic").getClass().getName();
        assertEquals("ooga.model.rules.ClassicRuleset", className);
    }

    @Test
    void determineRulesetJunior(){
        RulesetFactory factory = new RulesetFactory();
        String className = factory.determineRuleset("Junior").getClass().getName();
        assertEquals("ooga.model.rules.JuniorRuleset", className);
    }

    @Test
    void determineRulesetMillionaire(){
        RulesetFactory factory = new RulesetFactory();
        String className = factory.determineRuleset("Millionaire").getClass().getName();
        assertEquals("ooga.model.rules.MillionaireRuleset", className);
    }

    @Test
    void unsupportedRuleset(){
        RulesetFactory factory = new RulesetFactory();
        assertThrows(UnsupportedRulesetException.class, () -> factory.determineRuleset("fail"));
    }
}