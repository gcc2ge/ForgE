package macbury.forge.editor.controllers;

import macbury.forge.editor.views.MainMenu;
import macbury.forge.editor.views.MoreToolbarButton;

import javax.swing.*;

/**
 * Created by macbury on 25.10.14.
 */
public class MainToolbarController {
  private final ButtonGroup editorModeButtonGroup;
  private final JToolBar mainToolbar;
  private final MoreToolbarButton moreButton;
  private final JToggleButton editorModeTerrainButton;
  private final JToggleButton editorModeEventsButton;


  public MainToolbarController(JToolBar mainToolbar, MainMenu mainMenu) {
    this.editorModeButtonGroup = new ButtonGroup();
    this.mainToolbar           = mainToolbar;
    moreButton                 = new MoreToolbarButton(mainMenu);

    this.editorModeTerrainButton = buildToogleButton("blocks");
    this.editorModeEventsButton  = buildToogleButton("game_objects");

    editorModeButtonGroup.add(editorModeTerrainButton);
    editorModeButtonGroup.add(editorModeEventsButton);

    mainToolbar.add(moreButton);
    mainToolbar.addSeparator();
    mainToolbar.add(editorModeTerrainButton);
    mainToolbar.add(editorModeEventsButton);
    mainToolbar.add(Box.createHorizontalGlue());
  }

  private JToggleButton buildToogleButton(String iconName) {
    JToggleButton button = new JToggleButton();
    ImageIcon icon = new ImageIcon(getClass().getResource("/icons/"+iconName+".png"));
    button.setFocusable(false);
    button.setHorizontalTextPosition(SwingConstants.LEADING);
    button.setIcon(icon);
    return button;
  }
}
