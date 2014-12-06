package br.com.pepablack.popballoon.controle;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class CarregadorRecursos {

	private static CarregadorRecursos INSTANCE;
	
	public ITextureRegion texturaBackground, texturaBackgroundMenu;
	public ITextureRegion texturaPlayButton;
	public ITextureRegion texturaOptionButton;
	public ITextureRegion texturaBalaoVermelho, texturaBalaoAzul, texturaBalaoAmarelo, texturaBalaoExplosivo;
	private BuildableBitmapTextureAtlas myBuildableBitmapTextureAtlas;
	
	public Sound somEstouroBalao;
	public Sound somBalaoExplosivo;
	public Font	mFont;
	public Font mFontRed;
	public Font mFontBlue;
	public Font mFontYellow;

	CarregadorRecursos(){
	}

	public synchronized static CarregadorRecursos getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CarregadorRecursos();
		}
		return INSTANCE;
	}

	public synchronized void carregaTexturasMenu(Engine pEngine, final Context pContext){

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		myBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(pEngine.getTextureManager(), 1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		texturaPlayButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext, "play.png"); 		
		texturaOptionButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext, "options.png");
		texturaBackgroundMenu = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext, "menu_background.png");
	
		try {
			myBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			myBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
	
	public synchronized void carregaTexturasGame(Engine pEngine, final Context pContext){

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		myBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(pEngine.getTextureManager(), 1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		texturaBalaoVermelho = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext,"redBalloonTexture.png");
		texturaBalaoAzul = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext,"blueBalloonTexture.png");
		texturaBalaoAmarelo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext, "yellowBalloonTexture.png");
		texturaBalaoExplosivo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext, "blackBalloonTexture.png");
		texturaBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBuildableBitmapTextureAtlas, pContext, "background.png");
		
		try {
			myBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			myBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
	}

	public synchronized void carregaSom(Engine pEngine, Context pContext){
		SoundFactory.setAssetBasePath("sfx/");
		 try {
			 this.somEstouroBalao = SoundFactory.createSoundFromAsset(pEngine.getSoundManager(), pContext, "blop_mark_diangelo.mp3");
			 this.somBalaoExplosivo = SoundFactory.createSoundFromAsset(pEngine.getSoundManager(), pContext, "blast_mike_koenig.mp3");
			 
		 } catch (final IOException e) {
             Log.v("Carregar Som","Exception:" + e.getMessage());
		 }
	}	
	
	public synchronized void carregaFontes(Engine pEngine){
		FontFactory.setAssetBasePath("fonts/");
		
		mFont = FontFactory.create(pEngine.getFontManager(), pEngine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),  32f, true, Color.WHITE_ARGB_PACKED_INT);
		mFontBlue = FontFactory.create(pEngine.getFontManager(), pEngine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),  32f, true, Color.BLUE_ARGB_PACKED_INT);
		mFontRed = FontFactory.create(pEngine.getFontManager(), pEngine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),  32f, true, Color.RED_ARGB_PACKED_INT);
		mFontYellow = FontFactory.create(pEngine.getFontManager(), pEngine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),  32f, true, Color.YELLOW_ARGB_PACKED_INT);
		
		mFontBlue.load();
		mFontRed.load();
		mFontYellow.load();
		mFont.load();
	}
}