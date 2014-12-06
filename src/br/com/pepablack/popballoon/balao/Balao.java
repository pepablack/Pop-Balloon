package br.com.pepablack.popballoon.balao;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import br.com.pepablack.popballoon.controle.CarregadorRecursos;
import br.com.pepablack.popballoon.controle.ControladorJogo;
import br.com.pepablack.popballoon.fabrica.BalaoPool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Balao {

	protected float densidade = Float.valueOf(0.0001f);
	protected float elasticidade = Float.valueOf(1f);
	protected float friccao = Float.valueOf(1f);

	protected Body corpoFisicoBalao;
	protected FixtureDef fixtureBalao;
	protected PhysicsWorld mundoFisico;
	protected int cor;
	
	protected Sprite spriteDoBalao;
	private BalaoPool poolDoBalao;
	
	public Balao(Float posicaoXinicial, Float posicaoYinicial, ITextureRegion texturaBalao, PhysicsWorld mundoFisico,
			VertexBufferObjectManager gerenciadorVertexBuffer, Scene cena, BalaoPool poolDoBalao, final Camera cameraDaCena, int cor) {

		this.spriteDoBalao = new Sprite(posicaoXinicial.floatValue(), posicaoYinicial.floatValue(), texturaBalao, gerenciadorVertexBuffer) {
		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			if (pSceneTouchEvent.isActionDown()) {
				explodir();
				reciclar();
				return true;
			}			
			return false;
		}

		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			super.onManagedUpdate(pSecondsElapsed);
			if (!balaoVisivel(cameraDaCena)){
				reciclar();
			}
		}

		};
		
		this.spriteDoBalao.setCullingEnabled(true);
		this.poolDoBalao = poolDoBalao;
		this.mundoFisico = mundoFisico;
		adicionaFisicaNoBalao();
		adicionarCena(cena);
		this.cor = cor;
	}

	protected void adicionaFisicaNoBalao() {
		if (this.corpoFisicoBalao == null) {
			this.fixtureBalao = PhysicsFactory.createFixtureDef(densidade, elasticidade, friccao);
			this.corpoFisicoBalao = PhysicsFactory.createCircleBody(mundoFisico,spriteDoBalao, BodyType.DynamicBody, this.fixtureBalao);
			this.mundoFisico.registerPhysicsConnector(new PhysicsConnector(this.spriteDoBalao, this.corpoFisicoBalao));
		}
	}

	protected void adicionarCena(Scene cena) {
		cena.attachChild(this.spriteDoBalao);
		cena.registerTouchArea(this.spriteDoBalao);
	}

	public void explodir() {
		CarregadorRecursos.getInstance().somEstouroBalao.play();
		ControladorJogo.getInstance().incrementScore(this.cor);
		return;
	}

	public void reciclar() {
		this.poolDoBalao.recyclePoolItem(this);
		return;
	}
	
	public void habilitar() {
		zerarVelocidade();
		this.corpoFisicoBalao.setAwake(true);
		this.spriteDoBalao.setVisible(true);
		this.spriteDoBalao.setIgnoreUpdate(false);
		return;
	}

	private void zerarVelocidade() {
		this.corpoFisicoBalao.setLinearVelocity(0, 0);
		this.corpoFisicoBalao.setAngularVelocity(0);
	}
	
	public void desabilitar() {
		zerarVelocidade();
		this.corpoFisicoBalao.setAwake(false);
		this.spriteDoBalao.setVisible(false);
		this.spriteDoBalao.setIgnoreUpdate(true);
		return;
	}

	public void mudarPosicaoBalao(Float novaPosicaoX, Float novaPosicaoY) {
		Float novaPosicaoXFisico = Float.valueOf(novaPosicaoX.floatValue() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		Float novaPosicaoYFisico = Float.valueOf(novaPosicaoY.floatValue() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		this.corpoFisicoBalao.setTransform(novaPosicaoXFisico.floatValue(),	novaPosicaoYFisico.floatValue(), 0f);
		return;
	}

	public boolean balaoVisivel(Camera camera) {
		if (this.spriteDoBalao.getY() > (camera.getHeight() + this.spriteDoBalao.getHeight())
				|| this.spriteDoBalao.getX() > (camera.getWidth() + this.spriteDoBalao.getWidth())
				|| this.spriteDoBalao.getX() < (0f - this.spriteDoBalao.getWidth())
				|| this.spriteDoBalao.getY() < (0f - this.spriteDoBalao.getHeight()))
			return false;
		return true;
	}
	
	
	public Float tamanhoY(){
		return Float.valueOf(this.spriteDoBalao.getHeight());
	}
	
	public float getMassa(){
		return this.corpoFisicoBalao.getMass();
	}
	
	public Vector2 getCentro(){
		return this.corpoFisicoBalao.getWorldCenter();
	}
	
	public void aplicarForca(Vector2 force, Vector2 point){
		this.corpoFisicoBalao.applyForce(force, point);
	}
}
