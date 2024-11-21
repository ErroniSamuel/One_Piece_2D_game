package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.Lava;

public class MON_Pyro extends Entity {
	GamePanel gp;
	public MON_Pyro(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="pyro";
		
		speed=1;
		maxLife=10;
		life=maxLife;
		type=type_monster;
		attack=6;
		defence=0;
		exp=4;
		projectile=new Lava(gp);
		
		solidArea.x=2;
		solidArea.y=16;
		solidArea.width=44;
		solidArea.height=32;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		getImage();
		
	}
	public void getImage() {
		up1=setup("/monsters/pyro1",gp.tileSize,gp.tileSize);
		up2=setup("/monsters/pyro2",gp.tileSize,gp.tileSize);
		down1=setup("/monsters/pyro1",gp.tileSize,gp.tileSize);
		down2=setup("/monsters/pyro2",gp.tileSize,gp.tileSize);
		left1=setup("/monsters/pyro1",gp.tileSize,gp.tileSize);
		left2=setup("/monsters/pyro2",gp.tileSize,gp.tileSize);
		right1=setup("/monsters/pyro1",gp.tileSize,gp.tileSize);
		right2=setup("/monsters/pyro2",gp.tileSize,gp.tileSize);
	}
	public void setAction() {
	actionLookCounter++;
		
		if(actionLookCounter>=120) {
		Random random=new Random();
		int i=random.nextInt(100)+1; //pick random from 1 to 100
		if(i<=25) {
			direction="up";
		}else
		if(i>25 && i<=50) {
			direction="down";
		}
		else if(i>50 && i<=75) {
			direction="left";
		}else {
			direction="right";
		}
		actionLookCounter=0;
		}
		int i=new Random().nextInt(100)+1;
		if(i>99 && !projectile.alive && shotAvailableCounter==30) {
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			shotAvailableCounter=0;
		}
	}
	public void damageReaction() {
		actionLookCounter=0;
		direction=gp.player.direction;
	}
}
