package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Sound;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	int hasKey=0;
	Sound se=new Sound();
	
	public Player(GamePanel gp,KeyHandler keyH) {
		this.gp=gp;
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
	}
	public void setDefaultValues() {
		worldX=gp.tileSize*12;
		worldY=gp.tileSize*17;
		direction="down";
		speed=4;
	}
	public void getPlayerImg() {
		try {
			up1=ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
			up2=ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
			down1=ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
			down2=ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
			left1=ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
			left2=ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
			right1=ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
			right2=ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void update() {
		if(keyH.up||keyH.down||keyH.left||keyH.right) {
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
	}
	
	public void pickUp(int i) {
		if(i!=999) {
			String objName=gp.obj[i].name;
			
			switch(objName) {
			case "key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i]=null;
				break;
			case "door":
				gp.playSE(2);
				if(hasKey>0) {
					gp.obj[i]=null;
					hasKey--;
				}
				break;
			case "food":
				gp.playSE(3);
				speed+=3;
				gp.obj[i]=null;
				break;
			case "chest":
				gp.playSE(4);
				gp.obj[i]=null;
				break;
			}
			
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image=null;
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
