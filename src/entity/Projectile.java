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
	}
	public void update(){
		if(user==gp.player.luffy ||user==gp.player.zoro) {
		int monsterIndex=gp.checker.checkEntity(this, gp.monster);
		if(monsterIndex!=999) {
			gp.player.damageMonster(monsterIndex,attack);
			alive=false;
		}
		}else {
			
		}
		gp.checker.checkTile(this);
		if(collisionOn) {
			count++;
			if(count>5) {
			alive=false;
			collisionOn=false;
			count=0;
			}
		}
		if(!alive) {
			count=0;
		}
		switch(direction) {
		case "up":worldY-=speed;break;
		case "down":worldY+=speed;break;
		case "left":worldX-=speed;break;
		case "right":worldX+=speed;break;
		}
		
		life--;
		if(life<=0) {
			alive=false;
		}
		spriteCounter++;
		if(spriteCounter>12) {
			if(spriteNum==1) {
				spriteNum=2;
			}else if(spriteNum==2) {
				spriteNum=1;
			}
			spriteCounter=0;
		}
	}

}
