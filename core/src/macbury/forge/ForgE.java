package macbury.forge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import macbury.forge.assets.AssetsManager;
import macbury.forge.blocks.BlocksProvider;
import macbury.forge.db.GameDatabase;
import macbury.forge.graphics.GraphicsUtils;
import macbury.forge.input.InputManager;
import macbury.forge.screens.ScreenManager;
import macbury.forge.shaders.utils.ShadersManager;
import macbury.forge.storage.StorageManager;

public class ForgE extends Game {
  public static GraphicsUtils   graphics;
  public static ScreenManager   screens;
  public static AssetsManager   assets;
  public static ShadersManager  shaders;
  public static Config          config;
  public static StorageManager  storage;
  public static GameDatabase    db;
  public static BlocksProvider  blocks;
  public static InputManager    input;
  private Array<ForgEBootListener> bootListeners;


  public ForgE(Config config) {
    super();
    this.config = config;
    this.bootListeners = new Array<ForgEBootListener>();
  }

  @Override
  public void create () {
    storage  = new StorageManager();

    db       = storage.loadOrInitializeDB();
    graphics = new GraphicsUtils();
    screens  = new ScreenManager(this);
    assets   = new AssetsManager();
    shaders  = new ShadersManager();
    input    = new InputManager();
    blocks   = new BlocksProvider();
    Gdx.input.setInputProcessor(input);
    for (ForgEBootListener listener : bootListeners) {
      listener.afterEngineCreate(this);
    }
  }

  public void addBootListener(ForgEBootListener bootListener) {
    this.bootListeners.add(bootListener);
  }
}
