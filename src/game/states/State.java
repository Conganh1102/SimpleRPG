package game.states;

import java.awt.Graphics;

import game.Handler;


public abstract class State {

	private static State currentState = null;
	
	//getter and setter
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	
	protected Handler handler;
	//constructor
	public State(Handler handler){
		this.handler = handler;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
