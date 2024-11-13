package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Sound;
import main.UtilityTool;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey=0;
	public int coins=0; 
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
			up1=setup("up1");
			up2=setup("up2");
			down1=setup("down1");
			down2=setup("down2");
			left1=setup("left1");
			left2=setup("left2");
			right1=setup("right1");
			right2=setup("right2");
}

	
	public BufferedImage setup(String imageName) {
		UtilityTool uTool=new UtilityTool();
		BufferedImage scaledImage=null;
		
		try {
			scaledImage=uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png")),gp.tileSize,gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
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
		else {
			stand++;
			if(stand==20) {
			spriteNum=1;
			stand=0;
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
				gp.ui.showMessage("Got key");
				break;
			case "door":
				if(hasKey>0) {
					gp.obj[i]=null;
					gp.playSE(2);
					hasKey--;
				}else {
					gp.ui.showMessage("Not enough keys");
				}
				break;
			case "food":
				gp.playSE(3);
				speed+=3;
				gp.obj[i]=null;
				gp.ui.showMessage("Boost");
				 new Thread(() -> {
				        try {
				            Thread.sleep(5000);  // 5 seconds delay
				            speed -= 3;  // Reduce the speed back after 5 seconds
				        } catch (InterruptedException e) {
				            e.printStackTrace();
				        }
				    }).start();
				break;
			case "chest":
				gp.playSE(4);
				gp.obj[i]=null;
				coins+=30;
				gp.ui.showMessage("got treasure");
				gp.ui.gameFinished=true;
				break;
			case "coin":
				gp.playSE(5);
				gp.obj[i]=null;
				coins++;
				gp.ui.showMessage("got coin");
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
		g2.drawImage(image, screenX, screenY,null);
	}
}
