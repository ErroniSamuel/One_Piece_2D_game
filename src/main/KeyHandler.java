package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean up,down,left,right;
	@Override
	public void keyTyped(KeyEvent e) {
	}
	public KeyHandler(GamePanel gp) {
		this.gp=gp;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			up=true;
		}else if(code == KeyEvent.VK_S) {
			down=true;
		}else if(code == KeyEvent.VK_A) {
			left=true;
		}else if(code == KeyEvent.VK_D) {
			right=true;
		}else if(code == KeyEvent.VK_P) {
			if(gp.gameState==gp.playState) {
			gp.gameState=gp.pauseState;
			}else {
				gp.gameState=gp.playState;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			up=false;
		}else if(code == KeyEvent.VK_S) {
			down=false;
		}else if(code == KeyEvent.VK_A) {
			left=false;
		}else if(code == KeyEvent.VK_D) {
			right=false;
		}
	}

}
