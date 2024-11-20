package entity;

import main.GamePanel;
import object.Red_Hawk;

public class Luffy extends Entity{
	public Luffy(GamePanel gp) {
		super(gp);
		this.gp=gp;
//		getPlayerImg();
//		getPlayerAttackImage(gp);
		level=1;
		maxLife=4;
		life=maxLife;
		strength=1;
		dexterity=1;
		exp=0;
		nextLevelExp=5;
		attack=1;
		defence=1;
		
		projectile=new Red_Hawk(gp);
	}
	public void resetImages() {
	    if (gp.player != null) {
	        getPlayerImg();
	        getPlayerAttackImage();
	    }
	}

	 public void getPlayerImg() {
		 if (gp.player != null) {
		 gp.player.down1 = setup("/luffy/down1", gp.tileSize, gp.tileSize);
		 gp.player.down2 = setup("/luffy/down2", gp.tileSize, gp.tileSize);
		 gp.player.up1 = setup("/luffy/up1", gp.tileSize, gp.tileSize);
		 gp.player.up2 = setup("/luffy/up2", gp.tileSize, gp.tileSize);
		 gp.player.left1 = setup("/luffy/left1", gp.tileSize, gp.tileSize);
		 gp.player.left2 = setup("/luffy/left2", gp.tileSize, gp.tileSize);
		 gp.player.right1 = setup("/luffy/right1", gp.tileSize, gp.tileSize);
		 gp.player.right2 = setup("/luffy/right2", gp.tileSize, gp.tileSize);
	    }
	 }

		public void getPlayerAttackImage() {
			 if (gp.player != null) {
			gp.player.attackup1=setup("/luffy/attack_up1",gp.tileSize,gp.tileSize*2);
			gp.player.attackup2=setup("/luffy/attack_up2",gp.tileSize,gp.tileSize*2);
			gp.player.attackdown1=setup("/luffy/attack_down1",gp.tileSize,gp.tileSize*2);
			gp.player.attackdown2=setup("/luffy/attack_down2",gp.tileSize,gp.tileSize*2);
			gp.player.attackleft1=setup("/luffy/attack_left1",gp.tileSize*2,gp.tileSize);
			gp.player.attackleft2=setup("/luffy/attack_left2",gp.tileSize*2,gp.tileSize);
			gp.player.attackright1=setup("/luffy/attack_right1",gp.tileSize*2,gp.tileSize);
			gp.player.attackright2=setup("/luffy/attack_right2",gp.tileSize*2,gp.tileSize);
		}
}
}
