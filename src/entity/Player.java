package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Sound;
import main.UI;
import main.UtilityTool;

public class Player extends Entity {
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY; 
	Sound se=new Sound();
	
	public Player(GamePanel gp,KeyHandler keyH) {
		super(gp);
		this.keyH=keyH;
		
		screenX=gp.screenWidth/2-gp.tileSize/2;
		screenY=gp.screenHeight/2-gp.tileSize/2;
		
		solidArea=new Rectangle();
		solidArea.x=8;
		solidArea.y=16;
		solidArea.width=32;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		solidArea.height=32;
		setDefaultValues();
		getPlayerImg();
		getPlayerAttackImage();
	}
	public void setDefaultValues() {
		worldX=gp.tileSize*8;
		worldY=gp.tileSize*5;
		direction="down";
		speed=4;
		
		//player status
		maxLife=10;
		life=maxLife;
	}
	public void getPlayerImg() {
			up1=setup("/player/up1",gp.tileSize,gp.tileSize);
			up2=setup("/player/up2",gp.tileSize,gp.tileSize);
			down1=setup("/player/down1",gp.tileSize,gp.tileSize);
			down2=setup("/player/down2",gp.tileSize,gp.tileSize);
			left1=setup("/player/left1",gp.tileSize,gp.tileSize);
			left2=setup("/player/left2",gp.tileSize,gp.tileSize);
			right1=setup("/player/right1",gp.tileSize,gp.tileSize);
			right2=setup("/player/right2",gp.tileSize,gp.tileSize);
}
	
	public void getPlayerAttackImage() {
		attackup1=setup("/player/attack_up1",gp.tileSize,gp.tileSize*2);
		attackup2=setup("/player/attack_up2",gp.tileSize,gp.tileSize*2);
		attackdown1=setup("/player/attack_down1",gp.tileSize,gp.tileSize*2);
		attackdown2=setup("/player/attack_down2",gp.tileSize,gp.tileSize*2);
		attackleft1=setup("/player/attack_left1",gp.tileSize*2,gp.tileSize);
		attackleft2=setup("/player/attack_left2",gp.tileSize*2,gp.tileSize);
		attackright1=setup("/player/attack_right1",gp.tileSize*2,gp.tileSize);
		attackright2=setup("/player/attack_right2",gp.tileSize*2,gp.tileSize);
	}

	public void update() {
		
		if(attacking) {
			attacking();
		}
		
		if(keyH.up||keyH.down||keyH.left||keyH.right||keyH.enterPressed) {
		if(keyH.up) {
			direction="up";
			
		}
		if(keyH.down) {
			direction="down";
			
		}
		if(keyH.left) {
			direction="left";
			
		}
		if(keyH.right) {
			direction="right";
			
		}
		// check tile collision
		collisionOn=false;
		gp.checker.checkTile(this);
		
		//check object collision
		int objInd=gp.checker.checkObj(this, true);
		pickUp(objInd);
		
		//check NPC collision
		int npcIndex=gp.checker.checkEntity(this,gp.npcs);
		interactNPC(npcIndex);
		
	
		//check monster collision
		int monsterIndex=gp.checker.checkEntity(this,gp.monster);
		contactMonster(monsterIndex);
		//check event collision
	
		gp.eHandler.checkEvent();
		
	
		
		//if false player moves
		if(collisionOn==false && !keyH.enterPressed) {
			switch(direction) {
			case "up":worldY=worldY-speed;
				break;
			case "down":worldY+=speed;
				break;
			case "left":worldX=worldX-speed;
				break;
			case "right":worldX+=speed;
				break;
			}
		}
		gp.keyH.enterPressed=false;
		spriteCounter++;
		if(spriteCounter>10) {
			if(spriteNum==1) {
				spriteNum=2;
			}else if(spriteNum==2){
				spriteNum=1;
			}
			spriteCounter=0;
		}
		}
		else {
			stand++;
			if(stand==20) {
			spriteNum=1;
			stand=0;
			}
		} 
		//invincibility
		if(invincible) {
			invincibleCount++;
			if(invincibleCount>60) {
				invincible=false;
				invincibleCount=0;
			}
		}
	}
	
	public void attacking() {
		spriteCounter++;
		if(spriteCounter<=5) {
			spriteNum=1;
		}
		if(spriteCounter>5&&spriteCounter<=25) {
			spriteNum=2;
		}
		if(spriteCounter>25) {
			spriteNum=1;
			spriteCounter=0;
			attacking=false;
		}
	}
	public void pickUp(int i) {
		if(i!=999) {
			String objName=gp.obj[i].name;
			switch(objName) {
			case "pit":
				gp.obj[i]=null;
				gp.ui.currentDialogue="You fell into a pit";
				gp.gameState=gp.dialogueState;
				break;
		}			
			
		}
	}
	public UI ui=new UI(gp);
	public void interactNPC(int i) {
	if(i!=999) {
		if(gp.firstTime) {
		gp.ui.currentDialogue="Press Enter to Interact with characters \n from next time, \n N->for next dialogue \n Enter->for exiting dialogue";
		gp.gameState=gp.dialogueState;
		gp.firstTime=false;
		}
		if(gp.keyH.enterPressed) {
		gp.gameState=gp.dialogueState;
		gp.npcs[i].speak();
		gp.currNPC=i;
		}	
	}
	
	}
	public void contactMonster(int i) {
		if(i!=999) {
			invincibleCount++;
			if(!invincible) {
				life-=1;
				invincible=true;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image=null;
		switch(direction) {
		case "up": 
			if(!attacking) {
			if(spriteNum==1){image=up1;}
			if(spriteNum==2){image=up2;}
			}else {
			if(spriteNum==1){image=attackup1;}
			if(spriteNum==2){image=attackup2;}
			}
			break;
		case "down":
			if(!attacking) {
			if(spriteNum==1){image=down1;}
		    if(spriteNum==2) {image=down2;}
			}else {
			if(spriteNum==1){image=attackdown1;}
			if(spriteNum==2) {image=attackdown2;}	
			}
			break;
		case "left":
			if(!attacking) {
			if(spriteNum==1){image=left1;}
			if(spriteNum==2){image=left2;}
			}else {
			if(spriteNum==1){image=attackleft1;}
		    if(spriteNum==2){image=attackleft2;}
			}
			break;
		case "right":
			if(!attacking) {
			if(spriteNum==1){image=right1;}
			if(spriteNum==2){image=right2;}
			}else{
			if(spriteNum==1){image=attackright1;}
			if(spriteNum==2){image=attackright2;}
			}
			break;
				
		}
		
		if(invincible){
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		}
		g2.drawImage(image, screenX, screenY,null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
	
	}
}
