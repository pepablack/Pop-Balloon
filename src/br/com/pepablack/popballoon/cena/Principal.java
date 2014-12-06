package br.com.pepablack.popballoon.cena;
import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;

import br.com.pepablack.popballoon.controle.CarregadorRecursos;
import br.com.pepablack.popballoon.controle.ControladorJogo;

public class Principal extends SimpleBaseGameActivity {

	private Camera myCamera;
	private GameScene myScene;
	private HUD gameHUD;
	private Text scoreText;
	private Text redColorText;
	private Text blueColorText;
	private Text yellowColorText;
	private static final int LARGURA_CAMERA = 600;
	private static final int ALTURA_CAMERA = 480;
	
	       	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		
		myCamera = new Camera(0, 0, ALTURA_CAMERA, LARGURA_CAMERA);
		EngineOptions myEngineOptions = new EngineOptions(true,	ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), myCamera);
		myEngineOptions.getAudioOptions().setNeedsSound(true);
		myEngineOptions.getAudioOptions().getSoundOptions().setMaxSimultaneousStreams(2);
		myEngineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return myEngineOptions;
	}

	@Override
	protected void onCreateResources() throws IOException {
		CarregadorRecursos.getInstance().carregaTexturasGame(mEngine, getApplicationContext());
		CarregadorRecursos.getInstance().carregaSom(mEngine, getApplicationContext());
		CarregadorRecursos.getInstance().carregaFontes(mEngine);
	}

	@Override
	protected Scene onCreateScene() {
		myScene = new GameScene();
		createHUD();
		return myScene.criarCena(mEngine, getApplicationContext(), myCamera, getVertexBufferObjectManager());
	}
	
	private void createHUD()
	{
		gameHUD = new HUD();
	    
	    scoreText = new Text(20, 460, CarregadorRecursos.getInstance().mFont, "Pontos: 1234567890124567890", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
	    scoreText.setAnchorCenter(0, 0);
	    scoreText.setText("Pontos: 0");
	    
	    redColorText = new Text(myCamera.getWidth()/2, myCamera.getHeight() - 50, CarregadorRecursos.getInstance().mFontRed, "VERMELHO!", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
	    blueColorText = new Text(myCamera.getWidth()/2, myCamera.getHeight() - 50, CarregadorRecursos.getInstance().mFontBlue, "AZUL!", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
	    yellowColorText = new Text(myCamera.getWidth()/2, myCamera.getHeight() - 50, CarregadorRecursos.getInstance().mFontYellow, "AMARELO", new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
	    	    
	    gameHUD.attachChild(scoreText);
	    gameHUD.attachChild(redColorText);
	    gameHUD.attachChild(blueColorText);
	    gameHUD.attachChild(yellowColorText);
	    
	    ControladorJogo.scoreTextController = scoreText;
	    ControladorJogo.redColorTextController = redColorText;
	    ControladorJogo.blueColorTextController = blueColorText;
	    ControladorJogo.yellowColorTextController = yellowColorText;
	    ControladorJogo.escolheCor();
	    
	    myCamera.setHUD(gameHUD);
	}

	
}
