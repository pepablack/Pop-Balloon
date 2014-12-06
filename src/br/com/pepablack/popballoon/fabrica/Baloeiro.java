package br.com.pepablack.popballoon.fabrica;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import br.com.pepablack.popballoon.balao.BalaoBomba;
import br.com.pepablack.popballoon.controle.CarregadorRecursos;

public class Baloeiro implements IUpdateHandler {

	private Camera camera;
	private BalaoPool poolBalaoVermelho;
	private BalaoPool poolBalaoAzul;
	private BalaoPool poolBalaoAmarelo;
	private BalaoPool poolBalaoExplosivo;

	private Float intervaloBalaoVermelho, 
				  intervaloBalaoAzul,
			      intervaloBalaoAmarelo, 
			      intervaloBalaoExplosivo, 
			      auxIntervaloBalaoVermelho,
			      auxIntervaloBalaoAmarelo,
			      auxIntervaloBalaoAzul,
			      auxIntervaloBalaoExplosivo;

	private Integer numeroMaxBaloesVermelhos, 
			        numeroMaxBaloesAzuis,
			        numeroMaxBaloesAmarelos, 
			        numeroMaxBaloesExplosiveis;

	public Baloeiro(VertexBufferObjectManager gerenciadorVertexBuffer,
			PhysicsWorld mundoFisico, Scene cena, Camera camera) {

		this.poolBalaoVermelho = new BalaoPool(
				CarregadorRecursos.getInstance().texturaBalaoVermelho,
				gerenciadorVertexBuffer, mundoFisico, cena, camera,
				BalaoPool.getTipoBalaoColorido(),this);

		this.poolBalaoAzul = new BalaoPool(
				CarregadorRecursos.getInstance().texturaBalaoAzul,
				gerenciadorVertexBuffer, mundoFisico, cena, camera,
				BalaoPool.getTipoBalaoColorido(),this);

		this.poolBalaoAmarelo = new BalaoPool(
				CarregadorRecursos.getInstance().texturaBalaoAmarelo,
				gerenciadorVertexBuffer, mundoFisico, cena, camera,
				BalaoPool.getTipoBalaoColorido(),this);

		this.poolBalaoExplosivo = new BalaoPool(
				CarregadorRecursos.getInstance().texturaBalaoExplosivo,
				gerenciadorVertexBuffer, mundoFisico, cena, camera,
				BalaoPool.getTipoBalaoExplosivo(),this);

		this.intervaloBalaoVermelho = Float.valueOf(0);
		this.intervaloBalaoAzul = Float.valueOf(0);
		this.intervaloBalaoAmarelo = Float.valueOf(0);
		this.intervaloBalaoExplosivo = Float.valueOf(0);
	
		this.numeroMaxBaloesVermelhos = Integer.valueOf(0);
		this.numeroMaxBaloesAzuis = Integer.valueOf(0);
		this.numeroMaxBaloesAmarelos = Integer.valueOf(0);
		this.numeroMaxBaloesExplosiveis = Integer.valueOf(0);

		this.auxIntervaloBalaoVermelho = Float.valueOf(0);
		this.auxIntervaloBalaoAzul = Float.valueOf(0);
		this.auxIntervaloBalaoAmarelo = Float.valueOf(0);
		this.auxIntervaloBalaoExplosivo = Float.valueOf(0);
		this.camera = camera;
	}

	public void soltarBalaoVermelho(Float intervaloSegundos,Integer numeroMaxBaloes) {
		this.intervaloBalaoVermelho = intervaloSegundos;
		this.numeroMaxBaloesVermelhos = numeroMaxBaloes;
	}

	public void soltarBalaoAzul(Float intervaloSegundos,Integer numeroMaxBaloes) {
		this.intervaloBalaoAzul = intervaloSegundos;
		this.numeroMaxBaloesAzuis = numeroMaxBaloes;
	}

	public void soltarBalaoAmarelo(Float intervaloSegundos,Integer numeroMaxBaloes) {
		this.intervaloBalaoAmarelo = intervaloSegundos;
		this.numeroMaxBaloesAmarelos = numeroMaxBaloes;
	}

	public void soltarBalaoExplosivo(Float intervaloSegundos,Integer numeroMaxBaloes) {
		this.intervaloBalaoExplosivo = intervaloSegundos;
		this.numeroMaxBaloesExplosiveis = numeroMaxBaloes;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		criaBalaoVermelho(pSecondsElapsed);
		criaBalaoAzul(pSecondsElapsed);
		criaBalaoAmarelo(pSecondsElapsed);
		criaBalaoExplosivo(pSecondsElapsed);
	}

	private void criaBalaoExplosivo(float pSecondsElapsed) {
		this.auxIntervaloBalaoExplosivo = this.auxIntervaloBalaoExplosivo + pSecondsElapsed;
		if (this.auxIntervaloBalaoExplosivo >= this.intervaloBalaoExplosivo) {
			obtemBalaoDaPool(this.numeroMaxBaloesExplosiveis, this.poolBalaoExplosivo);
			this.auxIntervaloBalaoExplosivo  = Float.valueOf(0);
		}
	}

	private void criaBalaoAmarelo(float pSecondsElapsed) {
		this.auxIntervaloBalaoAmarelo = this.auxIntervaloBalaoAmarelo + pSecondsElapsed;
		if (this.auxIntervaloBalaoAmarelo >= this.intervaloBalaoAmarelo) {
			obtemBalaoDaPool(this.numeroMaxBaloesAmarelos, this.poolBalaoAmarelo);
			this.auxIntervaloBalaoAmarelo  = Float.valueOf(0);
		}
	}

	private void criaBalaoAzul(float pSecondsElapsed) {
		this.auxIntervaloBalaoAzul = this.auxIntervaloBalaoAzul + pSecondsElapsed;
		if (this.auxIntervaloBalaoAzul >= this.intervaloBalaoAzul) {
				obtemBalaoDaPool(this.numeroMaxBaloesAzuis, this.poolBalaoAzul);
				this.auxIntervaloBalaoAzul  = Float.valueOf(0);
		}
	}

	private void criaBalaoVermelho(float pSecondsElapsed) {
		this.auxIntervaloBalaoVermelho = this.auxIntervaloBalaoVermelho + pSecondsElapsed;
		if (this.auxIntervaloBalaoVermelho >= this.intervaloBalaoVermelho) {
			obtemBalaoDaPool(this.numeroMaxBaloesVermelhos, this.poolBalaoVermelho);
			this.auxIntervaloBalaoVermelho = Float.valueOf(0);
		}
	}

	private void obtemBalaoDaPool(Integer numeroMaxBalao, BalaoPool poolBalao) {

		if (poolBalao.getUnrecycledItemCount() < numeroMaxBalao) {

				Random r = new Random();
				Float posicaoX = Float.valueOf(r.nextInt((int) this.camera.getWidth()));
				Float posicaoY = Float.valueOf(150f);

				poolBalao.obtainPoolItem(posicaoX, posicaoY);
			}
	}

	@Override
	public void reset() {
		this.camera = null;
		this.poolBalaoVermelho = null;
		this.poolBalaoAzul = null;
		this.poolBalaoAmarelo = null;
		this.poolBalaoExplosivo =null; 
		return;
	}

	public void explodirBalaoBomba(BalaoBomba balaoBomba, Float forcaExplosao) {
		poolBalaoVermelho.explodirBalao(balaoBomba,forcaExplosao);
		poolBalaoAzul.explodirBalao(balaoBomba,forcaExplosao);
		poolBalaoAmarelo.explodirBalao(balaoBomba,forcaExplosao);
		poolBalaoExplosivo.explodirBalao(balaoBomba,forcaExplosao);
	}
}	

