package br.com.pepablack.popballoon.fabrica;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;
import br.com.pepablack.popballoon.balao.Balao;
import br.com.pepablack.popballoon.balao.BalaoBomba;
import br.com.pepablack.popballoon.controle.CarregadorRecursos;

import com.badlogic.gdx.math.Vector2;

public class BalaoPool extends GenericPool<Balao> {

	private ITextureRegion texturaBalao;
	private VertexBufferObjectManager gerenciadorVertexBuffer;
	private Scene cena;
	private Camera camera;
	private PhysicsWorld mundoFisico;
	private static String tipoBalaoColorido = new String("Colorido");
	private static String tipoBalaoExplosivo = new String("Explosivo");
	private String tipoBalao;
	private Baloeiro meuBaloeiro;
	private ArrayList<Balao> listaBalaoAtivo;
	
	public BalaoPool(ITextureRegion texturaBalao,
			VertexBufferObjectManager gerenciadorVertexBuffer,
			PhysicsWorld mundoFisico, Scene cena, Camera camera, String tipoBalao, Baloeiro meuBaloeiro) {

		this.texturaBalao = texturaBalao;
		this.mundoFisico = mundoFisico;
		this.gerenciadorVertexBuffer = gerenciadorVertexBuffer;
		this.cena = cena;
		this.camera = camera;
		this.tipoBalao = tipoBalao;
		this.meuBaloeiro = meuBaloeiro;
		this.listaBalaoAtivo = new ArrayList<Balao>();
	}

	@Override
	protected Balao onAllocatePoolItem() {
			
		Balao novoBalao = null;
		int colorBalao = 0;
		if (this.tipoBalao.equals(tipoBalaoColorido)) {
		
			if (texturaBalao == CarregadorRecursos.getInstance().texturaBalaoVermelho){
				colorBalao = 0;
			}

			if (texturaBalao == CarregadorRecursos.getInstance().texturaBalaoAmarelo){
				colorBalao = 1;
			}
			
			if (texturaBalao == CarregadorRecursos.getInstance().texturaBalaoAzul){
				colorBalao = 2;
			}
			
			if (texturaBalao == CarregadorRecursos.getInstance().texturaBalaoExplosivo){
				colorBalao = -1;
			}
			
			novoBalao = new Balao(0f, 0f, texturaBalao, mundoFisico,
				gerenciadorVertexBuffer, cena, this, camera,colorBalao);
		}
		
		if (this.tipoBalao.equals(tipoBalaoExplosivo)) {
			novoBalao = new BalaoBomba(0f, 0f, texturaBalao, mundoFisico,
					gerenciadorVertexBuffer, cena, this, meuBaloeiro, camera,colorBalao);
		}
		return novoBalao;
	}

	public synchronized Balao obtainPoolItem(final float pX, final float pY) {
		Balao mBalloon = super.obtainPoolItem();
		mBalloon.mudarPosicaoBalao(pX, pY);
		mBalloon.habilitar();
		listaBalaoAtivo.add(mBalloon);
		return mBalloon;
	}

	@Override
	protected synchronized void onHandleRecycleItem(Balao pBalloon) {
		super.onHandleRecycleItem(pBalloon);
		pBalloon.mudarPosicaoBalao(camera.getWidth(), camera.getHeight()+(pBalloon.tamanhoY()*3));
		pBalloon.desabilitar();
		listaBalaoAtivo.remove(pBalloon);
	}
	
	public static String getTipoBalaoColorido() {
		return tipoBalaoColorido;
	}

	public static String getTipoBalaoExplosivo() {
		return tipoBalaoExplosivo;
	}

	public void explodirBalao(final BalaoBomba balaoBomba, final Float forcaExplosao) {
		Log.d("tamanho", listaBalaoAtivo.size() + "");
		
					Iterator<Balao> i = listaBalaoAtivo.iterator();
					
					while(i.hasNext()){
						
						Balao balao = i.next();
						
						if (!balao.equals(balaoBomba)) {
					
							Vector2 bodyPos = balao.getCentro();
							Vector2 force = new Vector2(0,0);
							Vector2 bombPos = balaoBomba.getCentro();
							Vector2 dir = bodyPos.sub(bombPos);
							float dist = dir.len2();
							dir = dir.nor();
							float magnitude = forcaExplosao *(balaoBomba.getMassa()/dist);
							force = force.add(dir.mul(magnitude));
							balao.aplicarForca(force, bodyPos);
					    
				        Log.d("explosao",magnitude + "");
						}
					}
					
		};
	}
