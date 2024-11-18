package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean up,down,left,right,enterPressed;
	@Override
	public void keyTyped(KeyEvent e) {
	}
	public KeyHandler(GamePanel gp) {
		this.gp=gp;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		//title state
		if(gp.gameState==gp.titleState) {
			if(code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum=2;
			}
			}else if(code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum>2) {
				gp.ui.commandNum=0;
			}
			}
			if(code==KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum==0) {
					gp.gameState=gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum==1) {
					//do load game later
				}
				if(gp.ui.commandNum==2) {
					System.exit(0);
				}
			}
		}
		//play state
		if(gp.gameState==gp.playState) {
			if(code!=KeyEvent.VK_E) {
				gp.player.attacking=false;
			}
		if(code == KeyEvent.VK_W) {
			up=true;
		}else if(code == KeyEvent.VK_S) {
			down=true;
		}else if(code == KeyEvent.VK_A) {
			left=true;
		}else if(code == KeyEvent.VK_D) {
			right=true;
		}else if(code == KeyEvent.VK_P) {
			gp.gameState=gp.pauseState;
		}
		if(code==KeyEvent.VK_E) {
			gp.player.attacking=true;
		} 
		if(code==KeyEvent.VK_ENTER) {
			enterPressed=true;
		}
		}
		//pause state
		else if(gp.gameState==gp.pauseState) {
			if(code == KeyEvent.VK_P) {
				gp.gameState=gp.playState;
			}
		}else if(gp.gameState==gp.dialogueState) {
			if(code==KeyEvent.VK_ENTER) {
				gp.gameState=gp.playState;
			}else if(code==KeyEvent.VK_N) {
				gp.npcs[gp.currNPC].speak();
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
