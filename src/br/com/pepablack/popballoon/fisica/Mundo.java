package br.com.pepablack.popballoon.fisica;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Mundo extends FixedStepPhysicsWorld {

	private static final FixtureDef mWallFixtureDef = PhysicsFactory.createFixtureDef(1f, 0f, 1f);
	private Camera mCamera;
	private Scene mScene;
	private VertexBufferObjectManager mVertexBufferObjectManager;

	public Mundo(Integer mFixedStepsPerSecond, Vector2 mGravity,
			Boolean mAllowSleep, Camera pCamera, Scene pScene,
			VertexBufferObjectManager pVertexBufferObjectManager) {

		super(mFixedStepsPerSecond.intValue(), mGravity, mAllowSleep
				.booleanValue());
		this.mCamera = pCamera;
		this.mScene = pScene;
		this.mVertexBufferObjectManager = pVertexBufferObjectManager;
		pScene.registerUpdateHandler(this);
	}

	public void criarParedeEsquerda() {
		criarParedeEsquerda(Float.valueOf(1f));
	}

	public void criarParedeDireita() {
		criarParedeDireita(Float.valueOf(1f));
	}

	public void criarTeto() {
		criarTeto(Float.valueOf(1f));
	}

	public void criarPiso() {
		criarPiso(Float.valueOf(1f));
	}

	public void criarParedeEsquerda(Float pWidth) {
		Rectangle left = new Rectangle(1f, this.mCamera.getHeight() / 2,
				pWidth, this.mCamera.getHeight(),
				this.mVertexBufferObjectManager);
		PhysicsFactory.createBoxBody(this, left, BodyType.StaticBody,
				mWallFixtureDef);
		left.setColor(Color.TRANSPARENT);
		this.mScene.attachChild(left);
		return;
	}

	public void criarParedeDireita(Float pWidth) {
		Rectangle right = new Rectangle(this.mCamera.getWidth() - 1f,
				this.mCamera.getHeight() / 2f, pWidth,
				this.mCamera.getHeight(), this.mVertexBufferObjectManager);
		PhysicsFactory.createBoxBody(this, right, BodyType.StaticBody,
				mWallFixtureDef);
		right.setColor(Color.TRANSPARENT);
		this.mScene.attachChild(right);
		return;
	}

	public void criarTeto(Float pHeight) {
		Rectangle top = new Rectangle(this.mCamera.getWidth() / 2f,
				this.mCamera.getHeight() - 1f, this.mCamera.getWidth(),
				pHeight, this.mVertexBufferObjectManager);
		PhysicsFactory.createBoxBody(this, top, BodyType.StaticBody,
				mWallFixtureDef);
		top.setColor(Color.TRANSPARENT);
		this.mScene.attachChild(top);
		return;
	}

	public void criarPiso(Float pHeight) {
		Rectangle bottom = new Rectangle(this.mCamera.getWidth() / 2f, 1f,
				this.mCamera.getWidth(), pHeight,
				this.mVertexBufferObjectManager);
		PhysicsFactory.createBoxBody(this, bottom, BodyType.StaticBody,
				mWallFixtureDef);
		bottom.setColor(Color.TRANSPARENT);
		this.mScene.attachChild(bottom);
		return;
	}
}
