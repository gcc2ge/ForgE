package macbury.forge.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Disposable;
import macbury.forge.voxel.ChunkMap;

/**
 * Created by macbury on 28.10.14.
 */
public class LevelEnv {
  public final DirectionalLight mainLight;
  public final Color ambientLight;
  public final Color skyColor;
  public ChunkMap terrainMap;
  public Texture  windDisplacementTexture;

  public LevelEnv() {
    skyColor     = Color.valueOf("3498db");
    mainLight    = new DirectionalLight();
    mainLight.set(0.8f, 0.8f, 0.8f,-1, -1, 0.5f);

    ambientLight = Color.valueOf("cccccc");
  }

}
