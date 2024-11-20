package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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
	public Rectangle attackArea=new Rectangle(0,0,0,0);
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
	public boolean attacking=false;
	public boolean cool=true;
	public int coolDownCount=0;
	public boolean alive=true;
	public boolean dying=false;
	public boolean hpBarOn=false;
	int hpBarCounter=0;
	int dyingCounter=0;
	//character status
	public int maxLife;
	public int life;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defence;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int characterType;
	public Entity currentWeapon;
	public Entity currentShield;
	public String currentWeaponName;
	public int maxHaki;
	public int haki;
	public int shotAvailableCounter=0;
	public Projectile projectile;
	
	//item Attributes
	public int attackValue;
	public int defenceValue;
	public String description="";
	public int useCost;
	
	//type
	public int type;//0-player,1-npc,2-monster
	public final int type_player=0;
	public final int type_npc=1;
	public final int type_monster=2;
	public final int type_sword=3;
	public final int type_consumable=4;
	
	public Entity(GamePanel gp) {
		this.gp=gp;
	}

	public void getPlayerAttackImg(GamePanel gp) {}
	public void getPlayerAttackImg() {}
	public void getPlayerImg() {}
	public void use(Entity entity) {}
	public void setAction() {}
	public void damageReaction() {}
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
		
		if(this.type==type_monster && contactPlayer) {
			if(!gp.player.invincible) {
				gp.playSE(8);
				
				int damage=0;
				if(gp.currentCharacter=="Luffy") {
				damage=attack-gp.player.luffy.defence;
				if(damage<0) {
					damage=0;
				}
				gp.player.luffy.life-=damage;
				}else if(gp.currentCharacter=="Zoro") {
					damage=attack-gp.player.zoro.defence;
					if(damage<0) {
						damage=0;
					}
					gp.player.zoro.life-=damage;
					}
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
		if(invincible) {
			invincibleCount++;
			if(invincibleCount>40) {
				invincible=false;
				invincibleCount=0;
			}
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
			if(spriteNum==1){image=up1;}
			if(spriteNum==2) {image=up2;}
			break;
		case "down":
			if(spriteNum==1){image=down1;}
		if(spriteNum==2) {image=down2;}
			break;
		case "left":
			if(spriteNum==1)
			{image=left1;}
			if(spriteNum==2) {image=left2;}
			break;
		case "right":
			if(spriteNum==1)
			{image=right1;}
			if(spriteNum==2) {image=right2;}
			break;
		}
		
		//monster HP bar
		if(type==2 && hpBarOn) {
			double oneScale=(double)gp.tileSize/maxLife;
			double hpBarValue=oneScale*life;
		g2.setColor(new Color(35,45,55));
		g2.fillRect(screenX-2,screenY-17,gp.tileSize+4,10);
		g2.setColor(new Color(255,6,30));
		g2.fillRect(screenX, screenY-15,(int)hpBarValue, 6);
			hpBarCounter++;
			if(hpBarCounter>100) {
				hpBarOn=false;
				hpBarCounter=0;
			}
		}
		
		if(invincible) {
		hpBarOn=true;
		hpBarCounter=0;
		changeAlpha(g2,0.4f);
		
		}
	   if(dying==true) {
			dyingAnimation(g2);
		}
		g2.drawImage(image, screenX, screenY,gp.tileSize,gp.tileSize,null);		

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}
	
}
public void dyingAnimation(Graphics2D g2) {
	dyingCounter++;
	if(dyingCounter<=40) {
		if(dyingCounter%5!=0) {changeAlpha(g2,0f);}
		else {changeAlpha(g2,1f);}
	}else{
		alive=false;
	}
}
public void changeAlpha(Graphics2D g2,float alphaValue) {
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));	
}
}
