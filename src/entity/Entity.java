package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	public int worldX,worldY;
	public int speed;
	GamePanel gp;
	
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	public BufferedImage attackup1,attackup2,attackright2,attackright1,attackleft2,attackleft1,attackdown1,attackdown2;
	public String direction="down";
	public int spriteCounter=0;
	public int spriteNum=1;
	public int stand=0;
	public Rectangle solidArea=new Rectangle(0,0,48,48);
	public int solidAreaDefaultX,solidAreaDefaultY;
	public boolean collisionOn=false;
	public int actionLookCounter=0;
	public boolean invincible=false;
	public int invincibleCount=0;
	String dialogues[]=new String[50];
	public int dialogueIndex=0;
	public BufferedImage image,image2,image3;
	public String name;
	public boolean collision=false;
	public int type;//0-player,1-npc,2-monster
	public boolean attacking=false;
	
	//character status
	public int maxLife;
	public int life;
	
	
	public Entity(GamePanel gp) {
		this.gp=gp;
	}
	
	public void setAction() {}
	public void speak() {
		if(dialogues[dialogueIndex]==null) {
			gp.gameState=gp.playState;
			dialogueIndex=0;
			}else {
			gp.ui.currentDialogue=dialogues[dialogueIndex];
			dialogueIndex++;
			}
			switch(gp.player.direction) {
			case "up":
				direction="down";
				break;
			case "left":
				direction="right";
				break;
			case "right":
				direction="left";
				break;
			case "down":
				direction="up";
				break;
					
			
			}
	}
	public void update() {
		
		setAction();
		
		collisionOn=false;
		gp.checker.checkTile(this);
		gp.checker.checkObj(this,false);
		gp.checker.checkEntity(this, gp.npcs);
		gp.checker.checkEntity(this, gp.monster);
		boolean contactPlayer=gp.checker.checkPlayer(this);
		
		if(this.type==2 && contactPlayer) {
			if(!gp.player.invincible) {
				gp.player.life-=1;
				gp.player.invincible=true;
			}
		}
		if(collisionOn==false) {
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


public BufferedImage setup(String imagePath,int width,int height) {
	UtilityTool uTool=new UtilityTool();
	BufferedImage scaledImage=null;
	
	try {
		scaledImage=uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream(imagePath+".png")),width,height);
	}catch(IOException e) {
		e.printStackTrace();
	}
	return scaledImage;
}
public void draw(Graphics2D g2) {
	BufferedImage image=null;
	int screenX=worldX-gp.player.worldX + gp.player.screenX;
	int screenY=worldY-gp.player.worldY + gp.player.screenY;
	
	if(worldX + gp.tileSize>gp.player.worldX-gp.player.screenX &&
	   worldX - gp.tileSize<gp.player.worldX+gp.player.screenX &&
	   worldY + gp.tileSize>gp.player.worldY-gp.player.screenY &&
	   worldY - gp.tileSize<gp.player.worldY+gp.player.screenY) {
		g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
		switch(direction) {
		case "up":
			if(spriteNum==1)
			{image=up1;}
			if(spriteNum==2) {
				image=up2;
			}
			break;
		case "down":
			if(spriteNum==1)
		{image=down1;}
		if(spriteNum==2) {
			image=down2;
		}
			break;
		case "left":
			if(spriteNum==1)
			{image=left1;}
			if(spriteNum==2) {
				image=left2;
			}
			break;
		case "right":
			if(spriteNum==1)
			{image=right1;}
			if(spriteNum==2) {
				image=right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY,gp.tileSize,gp.tileSize,null);		
		}
	
}
}
