package ooga.view.popup;

/**
 * Stores information for MakeTrade and RealEstate popups
 *
 * @author Anna Diemel
 */
public class PopUpInformation {

  private String selectedProperty;
  private String selectedEstateType;
  private String tradeType;
  private String tradePartnerID;
  private String tradeValue;

  /**
   * Initializes blank PopUpInformation
   */
  public PopUpInformation() {}

  /**
   * Initializes with property selected by user
   *
   * @param selectedProperty property selected
   * @param estateType type - house or hotel
   */
  public PopUpInformation(String selectedProperty, String estateType) {
    this.selectedProperty = selectedProperty;
    this.selectedEstateType = estateType;
  }

  /**
   * Returns selected property
   *
   * @return String name of user's selected property
   */
  public String getSelectedProperty() {
    return selectedProperty;
  }

  /**
   * Sets selected property
   *
   * @param selectedProperty user's selected property
   */
  public void setSelectedProperty(String selectedProperty) {
    this.selectedProperty = selectedProperty;
  }

  /**
   * Returns real estate type (house or hotel) or selected property
   * @return real estate type (house or hotel)
   */
  public String getSelectedEstateType() {
    return selectedEstateType;
  }

  /**
   * Sets real estate type
   * @param selectedEstateType real estate type (house or hotel)
   */
  public void setSelectedEstateType(String selectedEstateType) {
    this.selectedEstateType = selectedEstateType;
  }

  /**
   * Returns type of trade (between players or with bank)
   *
   * @return type of trade
   */
  public String getTradeType() {
    return tradeType;
  }

  /**
   * Sets the type of trade
   *
   * @param tradeType type of trade
   */
  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  /**
   * Gets the value of the trade
   *
   * @return trade value
   */
  public String getTradeValue() {
    return tradeValue;
  }

  /**
   * Sets the value of the trade
   *
   * @param tradeValue trade value
   */
  public void setTradeValue(String tradeValue) {
    this.tradeValue = tradeValue;
  }

  /**
   * returns ID of person being traded with
   *
   * @return ID of person being traded with
   */
  public String getTradePartnerID() {
    return tradePartnerID;
  }

  /**
   * Sets person being traded with
   *
   * @param tradePartnerID ID of person being traded with
   */
  public void setTradePartnerID(String tradePartnerID) {
    this.tradePartnerID = tradePartnerID;
  }
}
