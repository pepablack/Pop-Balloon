package br.com.pepablack.popballoon.cena;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;
import br.com.pepablack.popballoon.controle.CarregadorRecursos;
import br.com.pepablack.popballoon.controle.ControladorJogo;
import br.com.pepablack.popballoon.fabrica.Baloeiro;
import br.com.pepablack.popballoon.fisica.Mundo;

public class GameScene {

	private Mundo myWorld;
	private Camera myCamera;
	private Scene myScene;
	       	
	private void criaMundoFisico(VertexBufferObjectManager vertex) {
		myWorld = new Mundo(60, ControladorJogo.getInstance().INITIAL_WORLD_GRAVITY, false, myCamera, myScene, vertex);
		myWorld.criarParedeEsquerda();
		myWorld.criarParedeDireita();
	}
	
	public Scene criarCena(Engine engine, Context context, Camera camera,VertexBufferObjectManager vertex) {
		
		CarregadorRecursos.getInstance().carregaTexturasGame(engine, context);
		
		myScene = new Scene();
		
		myCamera = new Camera(0, 0, 480, 800);
		
		criaMundoFisico(vertex);
		criaBackground(camera,vertex);

		Baloeiro baloeiro = new Baloeiro(vertex,myWorld, myScene, myCamera);
		baloeiro.soltarBalaoVermelho(0f, 5);
		baloeiro.soltarBalaoAzul(0f, 5);
		baloeiro.soltarBalaoAmarelo(0f, 5);
		baloeiro.soltarBalaoExplosivo(2f, 3);

		myScene.registerUpdateHandler(baloeiro);
		myScene.setTouchAreaBindingOnActionDownEnabled(true);
		return myScene;
	}

	public void criaBackground(Camera camera, VertexBufferObjectManager vertex) {

		Sprite background = new Sprite(myCamera.getWidth() / 2,	myCamera.getHeight() / 2, CarregadorRecursos.getInstance().texturaBackground, vertex);
		SpriteBackground spriteBackground = new SpriteBackground(background);
		myScene.setBackground(spriteBackground);
		
	}
}
