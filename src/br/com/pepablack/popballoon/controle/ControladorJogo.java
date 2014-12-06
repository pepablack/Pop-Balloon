package br.com.pepablack.popballoon.controle;

import java.util.Random;

import org.andengine.entity.text.Text;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

public class ControladorJogo {
	
	private static ControladorJogo INSTANCE;
	
	public final int BALLOON_COLORS = 3;
	public static Text scoreTextController;
	public static Text redColorTextController;
	public static Text blueColorTextController;
	public static Text yellowColorTextController;
	public static int currentColor=0;
	
	private int mCurrentScore;
	public Vector2 INITIAL_WORLD_GRAVITY = new Vector2(0f,5f);
	private String CURRENT_COLOR;
	
	ControladorJogo(){
	}
	

	public static ControladorJogo getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ControladorJogo();
		}
		return INSTANCE;
	}
	
	
	public void incrementScore(int pIncrementBy){
		if (currentColor == pIncrementBy && pIncrementBy != -1){
			mCurrentScore+=1;
			 ControladorJogo.escolheCor();
			 scoreTextController.setText("Score: " + mCurrentScore);
			 return;
		}
		else if (currentColor != pIncrementBy && pIncrementBy != -1){
				ControladorJogo.escolheCor();
				mCurrentScore-=2;
				scoreTextController.setText("Score: " + mCurrentScore);
				return;
		}
		else if (currentColor != pIncrementBy && pIncrementBy == -1){
			ControladorJogo.escolheCor();
			mCurrentScore-=10;
			scoreTextController.setText("Score: " + mCurrentScore);
			return;
	}
		Log.d("currentStore",mCurrentScore + "");
		
	}
	
	public static void escolheCor(){
		
		Random a = new Random();
		currentColor = a.nextInt(3);
			
		switch (currentColor) {
			case 0:{
				redColorTextController.setVisible(true);
				blueColorTextController.setVisible(false);
				yellowColorTextController.setVisible(false);
			}
			break;
			case 1: {
				redColorTextController.setVisible(false);
				yellowColorTextController.setVisible(true);
				blueColorTextController.setVisible(false);
			}break;
			case 2:{
				redColorTextController.setVisible(false);
				yellowColorTextController.setVisible(false);
				blueColorTextController.setVisible(true);
			}break;
			default:
				break;
			}
	}

	public String getCurrentColors() {
		return CURRENT_COLOR;
	}
				
}