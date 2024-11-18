package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean up,down,left,right,enterPressed;
	@Override
	public void keyTyped(KeyEvent e) {
		int code=e.getKeyCode();
		if(code==KeyEvent.VK_ENTER) {
			enterPressed=false;
		}
	}
	public KeyHandler(GamePanel gp) {
		this.gp=gp;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		//title state
		if(gp.gameState==gp.titleState) {
		titleState(code);
		}
		//play state
		else if(gp.gameState==gp.playState) {
		playState(code);
		}
		//pause state
		else if(gp.gameState==gp.pauseState) {
			pauseState(code);
		}
		else if(gp.gameState==gp.dialogueState) {
			dialogueState(code);
		}
		else if(gp.gameState==gp.characterState) {
			characterState(code);
		}
	}
public void titleState(int code) {
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
public void playState(int code) {
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
	if(code==KeyEvent.VK_C) {
		gp.gameState=gp.characterState;
	}
	
	if(code==KeyEvent.VK_ENTER) {
		enterPressed=true;
	}
	}
public void pauseState(int code) {
	if(code == KeyEvent.VK_P) {
		gp.gameState=gp.playState;
	}
}
public void dialogueState(int code) {
	if(code==KeyEvent.VK_ENTER) {
		gp.gameState=gp.playState;
	}else if(code==KeyEvent.VK_N) {
		gp.npcs[gp.currNPC].speak();
	}	
}
public void characterState(int code) {
	if(code==KeyEvent.VK_C) {
		gp.gameState=gp.playState;
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
