package ooga.view.buttons;

import ooga.view.gameView.CreateCSSControls;
import ooga.view.gameView.ErrorPrintable;
import ooga.view.gameView.LanguageControls;
import ooga.model.dataReaders.LoadingFolderControls;
import ooga.model.dice.ModelDiceRollable;
import ooga.controller.SceneControls;
import ooga.controller.SetUpable;
import ooga.model.gamePlay.TurnableModel;
import ooga.view.dice.ViewDiceRollable;

/**
 * Contains all button interfaces and used in ButtonsMaintainer and ButtonsDisplayCreator
 *
 * @author Grace Llewellyn
 */
public class InterfacesForButtons {

  private final LanguageControls languageControls;
  private final CreateCSSControls createCSSControls;
  private final ErrorPrintable errorPrintable;
  private final SceneControls sceneControls;
  private final TurnableModel turnableModel;
  private final SetUpable setUpable;
  private final LoadingFolderControls loadingFolderControls;
  private ModelDiceRollable modelDiceRollable;
  private final ViewDiceRollable viewDiceRollable;
  private AccessOtherButtons accessOtherButtons;
  private NumberPlayersChangable numberPlayersChangable;

  public InterfacesForButtons(LanguageControls languageControls, CreateCSSControls createCSSControls,
      ErrorPrintable errorPrintable, SceneControls sceneControls, SetUpable setUpable,
      LoadingFolderControls loadingFolderControls, ViewDiceRollable viewDiceRollable,
      TurnableModel turnableModel) {
    this.languageControls = languageControls;
    this.createCSSControls = createCSSControls;
    this.errorPrintable = errorPrintable;
    this.sceneControls = sceneControls;
    this.setUpable = setUpable;
    this.loadingFolderControls = loadingFolderControls;
    this.viewDiceRollable = viewDiceRollable;
    this.turnableModel = turnableModel;
  }


  /**
   * @return interface for rollable dice
   */
  public ViewDiceRollable getViewDiceRollable() {
    return viewDiceRollable;
  }

  /**
   * @return loading folder controlls interface
   */
  public LoadingFolderControls getLoadingFolderControls() {
    return loadingFolderControls;
  }

  /**
   * @return setupable interface
   */
  public SetUpable getSetUpable() {
    return setUpable;
  }

  /**
   * @return turnable model interface
   */
  public TurnableModel getTurnableModel() {
    return turnableModel;
  }

  /**
   * @return scene controls interface
   */
  public SceneControls getSceneControls() {
    return sceneControls;
  }

  /**
   * @return error printable interfact
   */
  public ErrorPrintable getErrorPrintable() {
    return errorPrintable;
  }

  /**
   * @return CreateCSSControls interface
   */
  public CreateCSSControls getCreateCSSControls() {
    return createCSSControls;
  }

  /**
   * @return language controls interface
   */
  public LanguageControls getLanguageControls() {
    return languageControls;
  }

  /**
   * sets the rollable dice interface
   * @param modelDiceRollable interface
   */
  public void uploadModelDiceRollable(ModelDiceRollable modelDiceRollable) {
    this.modelDiceRollable = modelDiceRollable;
  }

  /**
   * @return the model dice rollable interface
   */
  public ModelDiceRollable getModelDiceRollable() {
    return modelDiceRollable;
  }

  /**
   * @return accessing other buttons interface
   */
  public AccessOtherButtons getAccessOtherButtons() {
    return accessOtherButtons;
  }

  /**
   * @return number of players changeable interface
   */
  public NumberPlayersChangable getNumberPlayersChangable() {
    return numberPlayersChangable;
  }

  /**
   * updates the number of players changable interface
   * @param numberPlayersChangable interface
   */
  public void uploadNumberPlayersChangable(NumberPlayersChangable numberPlayersChangable) {
    this.numberPlayersChangable = numberPlayersChangable;
  }

  /**
   * updates accessOtherButtons interface
   * @param accessOtherButtons interface
   */
  public void uploadAccessToOtherButtons(AccessOtherButtons accessOtherButtons) {
    this.accessOtherButtons = accessOtherButtons;
  }
}
