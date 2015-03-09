package macbury.forge.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import macbury.forge.ForgE;
import macbury.forge.voxel.ChunkMap;

/**
 * Created by macbury on 19.10.14.
 * For loading level from disk
 */
public class LevelState {
  private static final String MAP_NAME_PREFIX = "MAP_";
  private int width;
  private int depth;
  private int height;

  public LevelEnv env;
  public ChunkMap terrainMap;
  public int id;
  public String name;


  public LevelState() {
    id     = ForgE.db.uid();
    name   = MAP_NAME_PREFIX + id;
    width  = ChunkMap.CHUNK_SIZE * 5;
    depth  = ChunkMap.CHUNK_SIZE * 5;
    height = ChunkMap.CHUNK_SIZE * 2;
    env    = new LevelEnv();
  }

  /**
   * Initialize arrays and textures
   */
  public void bootstrap() {
    terrainMap              = new ChunkMap(ChunkMap.TERRAIN_TILE_SIZE, ForgE.blocks);
    terrainMap.initialize(width,height,depth);
    terrainMap.buildFloor();
    env.terrainMap              = terrainMap;
    env.windDisplacementTexture = ForgE.assets.getTexture("textures/wind_bump.jpg");
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getDepth() {
    return depth;
  }

  public int getHeight() {
    return height;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
