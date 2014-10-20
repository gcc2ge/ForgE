package macbury.forge.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import macbury.forge.components.BoundBox;
import macbury.forge.components.Position;
import macbury.forge.components.Visible;
import macbury.forge.graphics.DebugShape;
import macbury.forge.graphics.batch.VoxelBatch;
import macbury.forge.level.map.ChunkMap;

/**
 * Created by macbury on 20.10.14.
 */
public class DebugSystem extends IteratingSystem {
  private static final Color BOUNDING_BOX_COLOR = Color.RED;
  private final VoxelBatch batch;
  private final PerspectiveCamera camera;
  private ComponentMapper<Position> pm   = ComponentMapper.getFor(Position.class);
  private ComponentMapper<BoundBox> bbm  = ComponentMapper.getFor(BoundBox.class);
  private ComponentMapper<Visible>    vm = ComponentMapper.getFor(Visible.class);
  private final BoundingBox tempBox;
  private final Vector3     tempVec;

  public DebugSystem(VoxelBatch batch, PerspectiveCamera camera) {
    super(Family.getFor(ComponentType.getBitsFor(Position.class), ComponentType.getBitsFor(BoundBox.class, Visible.class), ComponentType.getBitsFor()));
    this.batch   = batch;
    this.camera  = camera;
    this.tempBox = new BoundingBox();
    this.tempVec = new Vector3();
  }

  @Override
  public boolean checkProcessing() {
    return false;
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    boolean render             = true;
    Visible  visibleComponent  = vm.get(entity);
    BoundBox boundBoxComponent = bbm.get(entity);
    Position positionComponent = pm.get(entity);

    if (boundBoxComponent != null) {
      tempBox.set(boundBoxComponent.box);
    } else {
      tempBox.set(positionComponent.vector, tempVec.set(ChunkMap.TILE_SIZE).add(positionComponent.vector));
    }

    if (visibleComponent != null)
      render = visibleComponent.visible;

    if (render) {
      batch.shapeRenderer.setProjectionMatrix(camera.combined);
      batch.shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {
        batch.shapeRenderer.setColor(BOUNDING_BOX_COLOR);
        DebugShape.draw(batch.shapeRenderer, tempBox);
      } batch.shapeRenderer.end();
    }

  }
}
