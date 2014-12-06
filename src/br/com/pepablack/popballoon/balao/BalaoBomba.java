package br.com.pepablack.popballoon.balao;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import br.com.pepablack.popballoon.controle.CarregadorRecursos;
import br.com.pepablack.popballoon.controle.ControladorJogo;
import br.com.pepablack.popballoon.fabrica.BalaoPool;
import br.com.pepablack.popballoon.fabrica.Baloeiro;

public class BalaoBomba extends Balao {
	
	private Baloeiro meuBaloeiro;
	private static Float forcaExplosao = Float.valueOf(10000f);
	
	public BalaoBomba(Float posicaoXinicial, Float posicaoYinicial,
			ITextureRegion texturaBalao, PhysicsWorld mundoFisico,
			VertexBufferObjectManager gerenciadorVertexBuffer, Scene cena,
			BalaoPool poolDoBalao, Baloeiro baloeiro, Camera cameraDaCena, int cor) {
		super(posicaoXinicial, posicaoYinicial, texturaBalao, mundoFisico,
				gerenciadorVertexBuffer, cena, poolDoBalao, cameraDaCena,cor);

		this.meuBaloeiro = baloeiro;
		this.densidade = Float.valueOf(1f);
		this.elasticidade = Float.valueOf(0f);
		this.friccao = Float.valueOf(0f);
	}
	
	@Override
	public void explodir() {
		CarregadorRecursos.getInstance().somBalaoExplosivo.play();
		ControladorJogo.getInstance().incrementScore(-1);
		this.meuBaloeiro.explodirBalaoBomba(this,forcaExplosao);
	}
}
	