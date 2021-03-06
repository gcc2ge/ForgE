package macbury.forge.editor.controllers.tools.inspector;

import com.badlogic.gdx.Gdx;
import macbury.forge.editor.controllers.ProjectController;
import macbury.forge.editor.controllers.tools.ToolsController;
import macbury.forge.editor.controllers.tools.inspector.properties.DefaultBeanBinder;
import macbury.forge.editor.controllers.tools.inspector.properties.EditorScreenBeanInfo;
import macbury.forge.editor.controllers.listeners.OnMapChangeListener;
import macbury.forge.editor.screens.EditorScreen;
import macbury.forge.editor.systems.SelectionSystem;
import macbury.forge.editor.undo_redo.ChangeManager;
import macbury.forge.editor.undo_redo.ChangeManagerListener;
import macbury.forge.editor.undo_redo.actions.PropertyChangeable;
import macbury.forge.editor.views.MapPropertySheet;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * Created by macbury on 15.03.15.
 */
public class InspectorController implements OnMapChangeListener, DefaultBeanBinder.PropertyChangeListener, ChangeManagerListener, ToolsController.ToolControllerListener {
  private static final String TAG = "InspectorController";
  private final MapPropertySheet inspectorSheetPanel;
  private EditorScreen screen;
  private DefaultBeanBinder binder;
  private ChangeManager changeManager;
  private boolean pause;

  public InspectorController(JPanel mapSettingsPanel) {
    this.inspectorSheetPanel = new MapPropertySheet();
    mapSettingsPanel.add(inspectorSheetPanel);
  }

  @Override
  public void onCloseMap(ProjectController controller, EditorScreen screen) {
    screen.changeManager.removeListener(this);
    this.screen = null;
    if (binder != null){
      binder.unbind();
    }
  }

  @Override
  public void onNewMap(ProjectController controller, EditorScreen screen) {
    this.screen   = screen;
    changeManager = screen.changeManager;

    changeManager.addListener(this);
  }

  @Override
  public void onProjectStructureChange(ProjectController controller) {

  }

  @Override
  public void onMapSaved(ProjectController projectController, EditorScreen editorScreen) {

  }
/*
  @Override
  public void onPropertyChange(DefaultBeanBinder binder, PropertyChangeEvent event, Object object) {
    if (com.badlogic.gdx.graphics.Color.class.isInstance(event.getNewValue())) {
      PropertyChangeable propertyChangeable = new PropertyChangeable(object, event);
      changeManager.addChangeable(propertyChangeable).apply();
      Gdx.app.log(TAG, "Color change: "+ event.getNewValue() + " from " + event.getOldValue() + " for " + object.toString());
    } else {
      this.pause = PropertyChangeable.class.isInstance(changeManager.getCurrent());
      if (!pause) {
        PropertyChangeable propertyChangeable = new PropertyChangeable(object, event);
        changeManager.addChangeable(propertyChangeable).apply();
      }
      pause = false;
      Gdx.app.log(TAG, "Property change: "+ event.getNewValue() + " from " + event.getOldValue() + " for " + object.toString());
    }

  }*/

  @Override
  public void onChangeManagerChange(ChangeManager changeManager) {
    inspectorSheetPanel.updateUI();
  }

  @Override
  public void onToolPaneUnSelected(SelectionSystem system) {
    if (binder != null) {
      binder.unbind();
    }
    this.binder = null;
  }

  @Override
  public void onToolPaneSelected(ToolsController.ToolControllerListener selectedToolController, SelectionSystem system) {
    if (selectedToolController == this) {
      this.binder   = new DefaultBeanBinder(new EditorScreenBeanInfo.EditorScreenBean(screen), inspectorSheetPanel, new EditorScreenBeanInfo());
      this.binder.setListener(this);
      inspectorSheetPanel.updateUI();
    }
  }

  @Override
  public void onPropertyChange(DefaultBeanBinder binder, PropertyChangeEvent event, Object object) {
    PropertyChangeable propertyChangeable = new PropertyChangeable(object, event);
    propertyChangeable.apply();
  }
}
