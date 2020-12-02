package ooga.model.cards;

/**
 * The Card objects stored in Cards. There are multiple types of Cards
 * which prompt different actions.
 * @author delaneydemark
 */
public abstract class Card {

  public enum CardType {
    GetMoney, Move, PayMoney, OutJail,
    InJail, MoveGetMoney,GetMoneyFromPlayers,PayPerProperty,
    GetProperty
  }
  protected int id;
  protected String cardMessage;
  protected CardType action;
  protected int moneyChange;
  protected int positionChange;

  /**
   * Creates the card given data input from CSV
   * @param data array of card data from a CSV
   */
  public Card(String[] data){
    cardMessage = data[0];
    id = Integer.parseInt(data[1]);
    action = CardType.valueOf(data[2]);
    moneyChange = Integer.parseInt(data[3]);
    positionChange = Integer.parseInt(data[4]);
  }

  /**
   * @return the card's id
   */
  public int getId(){
    return id;
  }

  /**
   * @return the message on the card
   */
  public String getCardMessage(){
    return cardMessage;
  }

  /**
   * @return the money change that the card results in
   */
  public int getMoneyChange(){
    return moneyChange;
  }

  /**
   * @return the position change on the board that the card results in
   */
  public int getPositionChange(){
    return positionChange;
  }

  /**
   * @return the type of card determined by the action that the card prompts
   */
  public CardType getCardType(){
    return action;
  }
}
