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
import object.OBJ_Armour;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int attackCooldown=0;
	public boolean attackCancelled=false;
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
		
		attackArea.width=36;
		attackArea.height=36;
		
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
		level=1;
		maxLife=4;
		life=maxLife;
		strength=1;
		dexterity=1;
		exp=0;
		nextLevelExp=10;
		coin=0;
		currentHaki= new OBJ_Sword_Normal(gp);
		currentShield=new OBJ_Armour(gp);
		attack=getAttack();
		defence=getDefence();
	}
	public int getAttack() {
		return attack=strength*currentHaki.attackValue;
	}
	public int getDefence() {
		return defence=dexterity*currentShield.defenseValue;
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
		
		 if (attackCooldown > 0) {
	            attackCooldown--; // Decrease cooldown each frame
	        }

	        if (attacking) {
	            if (attackCooldown == 0) { // Only play sound if cooldown is over
	                gp.playSE(7); // Adjust this to your attack sound effect
	                attackCooldown = 30; // Cooldown duration in frames (adjust as needed)
	                 
	            }
	           attacking();
	            return;
	        }
	
		
		if(keyH.up||keyH.down||keyH.left||keyH.right||keyH.enterPressed) {
		if(keyH.up) {direction="up";}
		if(keyH.down) {direction="down";}
		if(keyH.left) {direction="left";}
		if(keyH.right) {direction="right";}
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
		
		if(keyH.enterPressed && !attackCancelled) {
			attacking=true;
//			spriteCounter=0;
		}
		attackCancelled=false;
		
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
//		else {
//			stand++;
//			if(stand==20) {
//			spriteNum=1;
//			stand=0;
//			}
//		} 
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
		if(spriteCounter>5&&spriteCounter<=10) {
			spriteNum=2;
			
			int currentWorldX=worldX;
			int currentWorldY=worldY;
			int solidAreaWidth=solidArea.width;
			int solidAreaHeight=solidArea.height;
			
			//adjust x,y for attack area
			switch(direction) {
			case "up":worldY-=attackArea.height;break;
			case "down":worldY+=attackArea.height;break;
			case "left":worldX-=attackArea.width;break;
			case "right":worldX+=attackArea.width;break;
			}
			//attack area become solidArea
			solidArea.width=attackArea.width;
			solidArea.height=attackArea.height;
			
			//check monster collision
			int monsterIndex=gp.checker.checkEntity(this,gp.monster);
			
			worldX=currentWorldX;
			worldY=currentWorldY;
			solidArea.width=solidAreaWidth;
			solidArea.height=solidAreaHeight;
			damageMonster(monsterIndex);
		}
		if(spriteCounter>15) {
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
		if(gp.firstTime && i!=999) {
			gp.ui.currentDialogue="Press Enter to Interact with characters \n from next time, \n N->for next dialogue \n Enter->for exiting dialogue";
			gp.gameState=gp.dialogueState;
			gp.firstTime=false;
			}
		if(gp.keyH.enterPressed) {
		if(i!=999) {
		attackCancelled=true;
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
				gp.playSE(8);
				int damage=gp.monster[i].attack-defence;
				if(damage<0) {
					damage=1;
				}
				life-=damage;
				invincible=true;
			}
		}
	}
	public void damageMonster(int i) {
		if(i!=999) {
			if(!gp.monster[i].invincible) {
				gp.playSE(6);
				
				int damage=attack-gp.monster[i].defence;
				if(damage<0) {
					damage=1;
				}
				gp.monster[i].life-=damage;
				gp.ui.addMessage(damage+" damage");
				gp.monster[i].invincible=true;
				gp.monster[i].damageReaction();
				if(gp.monster[i].life<=0) {
					gp.monster[i].dying=true;
					gp.ui.addMessage("killed "+gp.monster[i].name);
					exp+=gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
	}
	public void checkLevelUp() {
		if(exp>=nextLevelExp) {
			level++;
			nextLevelExp=nextLevelExp*2;
			maxLife+=2;
			strength++;
			dexterity++;
			attack=getAttack();
			defence=getDefence();
			
			gp.playSE(9);
			gp.gameState=gp.dialogueState;
			gp.ui.currentDialogue="                        Level "+level;
//			updateMonsters();
		}
	}
//	public void updateMonsters() {
//		for(int i=0;i<gp.monster.length;i++) {
//			if(gp.monster[i]!=null) {
//			gp.monster[i].maxLife+=2;
//			gp.monster[i].exp+=2;
//			}
//		}
//	}
	public void draw(Graphics2D g2) {
		BufferedImage image=null;
		int tempScreenX=screenX;
		int tempScreenY=screenY;
		switch(direction) {
		case "up": 
			if(!attacking) {
			if(spriteNum==1){image=up1;}
			if(spriteNum==2){image=up2;}
			}else {
			tempScreenY=screenY-gp.tileSize;
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
				tempScreenX=screenX-gp.tileSize;
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
			changeAlpha(g2,0.4f);
			}
	
		g2.drawImage(image, tempScreenX, tempScreenY,null);
		changeAlpha(g2,1f);
	
	}
}
