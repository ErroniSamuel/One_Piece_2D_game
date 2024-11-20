package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Sound;
import main.UI;
import main.UtilityTool;
import object.*;

public class Player extends Entity {
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int attackCooldown=0;
	public boolean attackCancelled=false;
	public Entity luffy;
	public Entity zoro;
	public Entity currentCharacter;
	public ArrayList<Entity> inventory=new ArrayList<>();
	public final int maxInventorySize=20;
	public int characterNum=1;
	
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
		
		luffy=new Luffy(gp);
		zoro=new Zoro(gp);
		
		setDefaultValues();
//		getPlayerImg();
//		getPlayerAttackImage();
		setItems();
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
		nextLevelExp=5;
		attack=1;
		defence=1;
		coin=0;
		
		currentShield=new OBJ_Armour(gp);
		
	}
	public void setItems() {
	}
	
	public int getAttack(int i) {
		if(i==1) {
			return luffy.attack=luffy.strength*((currentWeapon==null)?1:currentWeapon.attackValue);
		}else if(i==2) {
			return zoro.attack=zoro.strength*((currentWeapon==null)?1:currentWeapon.attackValue);
		}
		return 0;
	}
	public int getDefence(int i) {
		if(i==1) {
			return luffy.defence=luffy.dexterity*currentShield.defenceValue;
		}else if(i==2) {
			return zoro.defence=zoro.dexterity*currentShield.defenceValue;
		}
		return 0;
	}
	public void getPlayerImg() {
		  if (currentCharacter instanceof Luffy) {
		        currentCharacter.getPlayerImg();  // Call Luffy's method to load images
		    } else if (currentCharacter instanceof Zoro) {
		        currentCharacter.getPlayerImg();  // Call Zoro's method to load images
		    }}
	
	public void getPlayerAttackImage() {
		if (currentCharacter instanceof Luffy) {
	        currentCharacter.getPlayerAttackImg();  // Call Luffy's method to load images
	    } else if (currentCharacter instanceof Zoro) {
	        currentCharacter.getPlayerAttackImg();  // Call Zoro's method to load images
	    }}
public void update() {
	if(currentWeapon!=null) {
		currentWeaponName=currentWeapon.name;
	}
    if (attackCooldown > 0) {
        attackCooldown--; // Decrease cooldown each frame
    }
    if(gp.currentCharacter.equals("Luffy")) {
    	currentCharacter=luffy;
    	characterNum=1;
        projectile=luffy.projectile;
    }else if(gp.currentCharacter.equals("Zoro")) {
    	currentCharacter=zoro;
    	characterNum=2;
        projectile=zoro.projectile;
    }
    if (attacking) {
        if (attackCooldown == 0) { // Only play sound if cooldown is over
            if(characterNum==1) {
            	gp.playSE(7); // Adjust this to your attack sound effect
            }else if(characterNum==2){
            	gp.playSE(8);
            }
            attackCooldown = 30; // Cooldown duration in frames (adjust as needed)
        }
        attacking();
        return;
    }
updateStats();


       

    // Handle movement and collision detection
    if (keyH.up || keyH.down || keyH.left || keyH.right || keyH.enterPressed) {
        if (keyH.up) { direction = "up"; }
        if (keyH.down) { direction = "down"; }
        if (keyH.left) { direction = "left"; }
        if (keyH.right) { direction = "right"; }

        // Check tile collision
        collisionOn = false;
        gp.checker.checkTile(this);

        // Check object collision
        int objInd = gp.checker.checkObj(this, true);
        pickUp(objInd);

        // Check NPC collision
        int npcIndex = gp.checker.checkEntity(this, gp.npcs);
        interactNPC(npcIndex);

        // Check monster collision
        int monsterIndex = gp.checker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);

        // Check event collision
        gp.eHandler.checkEvent();

        // If no collision, move the player
        if (!collisionOn && !keyH.enterPressed) {
            switch (direction) {
                case "up": worldY = worldY - speed; break;
                case "down": worldY += speed; break;
                case "left": worldX = worldX - speed; break;
                case "right": worldX += speed; break;
            }
        }

        // If attack is triggered
        if (keyH.enterPressed && !attackCancelled) {
            attacking = true;
        }

        attackCancelled = false;
        gp.keyH.enterPressed = false;

        // Handle sprite animation (walking)
        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    
    if(projectile!=null && gp.keyH.projectilePressed && !projectile.alive && shotAvailableCounter==60) {
   
    projectile.set(worldX,worldY,direction,true,currentCharacter);   
    gp.projectileList.add(projectile);
    
    shotAvailableCounter=0;
    }
    
    // Handle invincibility after being hit
    if (invincible) {
        invincibleCount++;
        if (invincibleCount > 60) {
            invincible = false;
            invincibleCount = 0;
        }
    }
    
    if(shotAvailableCounter<60) {
    	shotAvailableCounter++;
    }
    
    
    if(characterNum==1) {
    	luffy=currentCharacter;
    }else if(characterNum==2) {
    	zoro=currentCharacter;
    }
}
public void updateStats() {
	if(currentCharacter instanceof Luffy) {
		level=luffy.level;
		maxLife=luffy.maxLife;
		life=luffy.maxLife;
		strength=luffy.strength;
		dexterity=luffy.dexterity;
		exp=luffy.exp;
		nextLevelExp=luffy.nextLevelExp;
		attack=luffy.attack;
		defence=luffy.defence;
	}else if(currentCharacter instanceof Zoro) {
		level=zoro.level;
		maxLife=zoro.maxLife;
		life=zoro.maxLife;
		strength=zoro.strength;
		dexterity=zoro.dexterity;
		exp=zoro.exp;
		nextLevelExp=zoro.nextLevelExp;
		attack=zoro.attack;
		defence=zoro.defence;
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
			damageMonster(monsterIndex,currentCharacter.attack);
		}
		if(spriteCounter>15) {
			spriteNum=1;
			spriteCounter=0;
			attacking=false;
		}
	}
	public void pickUp(int i) {
		if(i!=999) {
			String text;
			if(inventory.size()!=maxInventorySize) {
				inventory.add(gp.obj[i]);
				text="Got a "+gp.obj[i].name+"!";
			}else {
				text="your inventory is full";
			}
			gp.ui.addMessage(text);
			gp.obj[i]=null;
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
			if(!invincible && !gp.monster[i].dying) {
				gp.playSE(8);
				int damage=0;
				if(characterNum==1) {
	            	damage=gp.monster[i].attack-currentCharacter.defence; 
	            	if(damage<0) {
						damage=1;
					}
					currentCharacter.life-=damage;
					// Adjust this to your attack sound effect
	            }else if(characterNum==2){
	            	damage=gp.monster[i].attack-zoro.defence;
	            	if(damage<0) {
						damage=1;
					}
					zoro.life-=damage;
					invincible=true;
	            }
				invincible=true;
				
			}
		}
	}
	public void damageMonster(int i,int attack) {
		if(i!=999) {
			if(!gp.monster[i].invincible) {
				gp.playSE(6);
				
				int damage=0;
			
				damage=attack-gp.monster[i].defence;
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
					
					if(characterNum==1) {
						luffy.exp+=gp.monster[i].exp;
						checkLevelUp(1);
						}
					if(characterNum==2) {
						zoro.exp+=gp.monster[i].exp;
						checkLevelUp(2);
						}
				}
			}
		}
	}
	public void checkLevelUp(int i) {
		if(i==1) {
		if(luffy.exp>=luffy.nextLevelExp) {
			luffy.level++;
			luffy.nextLevelExp=luffy.nextLevelExp*3;
			luffy.maxLife+=2;
			luffy.strength++;
			luffy.dexterity++;
			luffy.attack=getAttack(1);
			luffy.defence=getDefence(1);
			
			gp.playSE(9);
			gp.gameState=gp.dialogueState;
			gp.ui.currentDialogue="                 luffy Level "+luffy.level;
//			updateMonsters();
		}
		}else if(i==2) {
			if(zoro.exp>=zoro.nextLevelExp) {
			zoro.level++;
			zoro.nextLevelExp=zoro.nextLevelExp*3;
			zoro.maxLife+=2;
			zoro.strength++;
			zoro.dexterity++;
			zoro.attack=getAttack(2);
			zoro.defence=getDefence(2);
			
			gp.playSE(9);
			gp.gameState=gp.dialogueState;
			gp.ui.currentDialogue="                  zoro  Level "+zoro.level;
		}
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
	public void selectItem() {
		int itemIndex=gp.ui.getItemIndex();
		
		if(itemIndex<inventory.size()) {
			Entity selectedItem=inventory.get(itemIndex);
			if(selectedItem.type==type_sword && characterNum==2) {	
			currentWeapon=selectedItem;
				attack=getAttack(2);
			}
			
			if(selectedItem.type==type_consumable) {
				//later
				if(characterNum==2) {
					selectedItem.use(zoro);
				}else if(characterNum==1) {
					selectedItem.use(luffy);
				}
				inventory.remove(itemIndex);
			}
		}
		gp.ui.drawInventory();
	}
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
