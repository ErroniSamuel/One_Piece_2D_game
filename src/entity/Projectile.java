package entity;

import main.GamePanel;

public class Projectile extends Entity{

	Entity user;
	int count=0;
	public Projectile(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
	}
	public void set(int worldX,int worldY,String direction,boolean alive,Entity user) {
		this.worldX=worldX;
		this.worldY=worldY;
		this.direction=direction;
		this.alive=alive;
		this.user=user;
		this.life=this.maxLife;
		this.count=0;
		this.collisionOn = false;
	}
	public void update() {
	    if (user == gp.player.luffy || user == gp.player.zoro) {
	        // Check if projectile hits a monster
	        int monsterIndex = gp.checker.checkEntity(this, gp.monster);
	        if (monsterIndex != 999) {
	            gp.player.damageMonster(monsterIndex, attack+gp.player.currentCharacter.attack);
	            alive = false; // Set alive to false, but do not reset life
	        }
	    } else {
	        // Check if projectile hits the player
	        boolean contactPlayer = gp.checker.checkPlayer(this);
	        if (!gp.player.invincible && contactPlayer) {
	            damagePlayer(attack);
	            alive = false;
	        }
	    }

	    // Check for tile collisions
	    gp.checker.checkTile(this);
	    if (collisionOn) {
	        count++;
	        if (count > 5) { // If stuck in a collision for too long
	            alive = false;
	            collisionOn = false;
	            count = 0;
	        }
	    }

	    // Move the projectile
	    switch (direction) {
	        case "up": worldY -= speed; break;
	        case "down": worldY += speed; break;
	        case "left": worldX -= speed; break;
	        case "right": worldX += speed; break;
	    }

	    // Decrease life over time
	    life--;
	    if (life <= 0) {
	        alive = false; // Set alive to false, but do not reset life here
	    }

	    // Animate the sprite
	    spriteCounter++;
	    if (spriteCounter > 12) {
	        spriteNum = (spriteNum == 1) ? 2 : 1;
	        spriteCounter = 0;
	    }
	}

}
