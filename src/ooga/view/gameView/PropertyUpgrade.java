package ooga.view.gameView;

import ooga.model.gamePlay.PropertyUpgradeable;

/**
 * Manages the upgrading of properties.
 * Used by gameView to handle changes to real estate on a property
 */
public class PropertyUpgrade {

    private PropertyUpgradeable propertyUpgradeable;

    public PropertyUpgrade(PropertyUpgradeable propertyUpgradeableIn){
        propertyUpgradeable = propertyUpgradeableIn;
    }
}
