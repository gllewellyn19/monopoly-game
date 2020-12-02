package ooga.model.banks;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import ooga.model.Bank;
import ooga.model.players.Player;
import ooga.view.gameView.ErrorPrintable;

/**
 * Class used to initialize the Bank based on the BankType property given in the data files
 * Depends on Bank, Players
 * Example use: Could be used to instantiate a ClassicBank in the ClassicGamePlay class
 * @author Grace Llewellyn, Anna Diemel
 */
public class BankFactory {

  public static final String DEFAULT_BANK_CLASS = "ClassicBank";
  public static final String FILE_PATH_BANK = "ooga.model.banks.";

  /**
   * Used to instantiate a bank for the game to use
   * Assumptions: If a bankType property is not given, it will default to ClassicBank
   * @param bankType is the type of bank to be instantiated
   * @param players is the map of player IDs to players for the game
   * @param errorPrintable is the interface to print errors if an incorrect input is given
   * @return the new Bank that was instantiated
   */
  public Bank createBank(
      Optional<String> bankType, Map<Integer, Player> players, ErrorPrintable errorPrintable) {
    try {
      String bankTypeOrDefault = bankType.isEmpty() ?
          DEFAULT_BANK_CLASS: bankType.get();
      Class bankClass = Class.forName(FILE_PATH_BANK + bankTypeOrDefault);
      return (Bank) bankClass.getDeclaredConstructor(Map.class).
          newInstance(players);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
        IllegalStateException | InvocationTargetException | ClassNotFoundException e) {
      errorPrintable.printErrorMessageAlertWithStringFormat("optionalPropertiesInvalid",
          "bankType");
      return new ClassicBank(players);
    }
  }

}
