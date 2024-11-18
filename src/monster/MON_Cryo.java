package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Cryo extends Entity {
	GamePanel gp;
	public MON_Cryo(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="Cryo";
		
		speed=1;
		maxLife=4;
		life=maxLife;
		type=2;
		
		solidArea.x=2;
		solidArea.y=16;
		solidArea.width=44;
		solidArea.height=32;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		getImage();
		
	}
	public void getImage() {
		up1=setup("/monsters/cryo_down1",gp.tileSize,gp.tileSize);
		up2=setup("/monsters/cryo_down2",gp.tileSize,gp.tileSize);
		down1=setup("/monsters/cryo_down1",gp.tileSize,gp.tileSize);
		down2=setup("/monsters/cryo_down2",gp.tileSize,gp.tileSize);
		left1=setup("/monsters/cryo_down1",gp.tileSize,gp.tileSize);
		left2=setup("/monsters/cryo_down2",gp.tileSize,gp.tileSize);
		right1=setup("/monsters/cryo_down1",gp.tileSize,gp.tileSize);
		right2=setup("/monsters/cryo_down2",gp.tileSize,gp.tileSize);
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
	}

}