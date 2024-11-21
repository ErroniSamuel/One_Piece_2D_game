package entity;

import main.GamePanel;
import object.Pound_Ho;

public class Zoro extends Entity {
	
	public Zoro(GamePanel gp) {
		super(gp);
		this.gp=gp;
//		getPlayerImg();
//		getPlayerAttackImage();
		// TODO Auto-generated constructor stub
		level=1;
		maxLife=4;
		life=maxLife;
		maxHaki=1;
		haki=maxHaki;
		strength=1;
		dexterity=1;
		exp=0;
		nextLevelExp=5;
		attack=1;
		defence=1;
		projectile=new Pound_Ho(gp);
				
		
	}
	public void resetImages() {
	    if (gp.player != null) {
	        getPlayerImg();
	        getPlayerAttackImage();
	    }
	}
	 public void getPlayerImg() {
		 if(gp.player!=null) {
	        gp.player.down1 = setup("/zoro/down1", gp.tileSize, gp.tileSize);
	        gp.player.down2 = setup("/zoro/down2", gp.tileSize, gp.tileSize);
	        gp.player.up1 = setup("/zoro/up1", gp.tileSize, gp.tileSize);
	        gp.player.up2 = setup("/zoro/up2", gp.tileSize, gp.tileSize);
	        gp.player.left1 = setup("/zoro/left1", gp.tileSize, gp.tileSize);
	        gp.player.left2 = setup("/zoro/left2", gp.tileSize, gp.tileSize);
	        gp.player.right1 = setup("/zoro/right1", gp.tileSize, gp.tileSize);
	        gp.player.right2 = setup("/zoro/right2", gp.tileSize, gp.tileSize);
	    }
	 }
	 public void getPlayerAttackImage() {
		if(gp.player!=null) {
		 gp.player.attackup1=setup("/zoro/attack_up1",gp.tileSize,gp.tileSize*2);
		 gp.player.attackup2=setup("/zoro/attack_up2",gp.tileSize,gp.tileSize*2);
		 gp.player.attackdown1=setup("/zoro/attack_down1",gp.tileSize,gp.tileSize*2);
		 gp.player.attackdown2=setup("/zoro/attack_down2",gp.tileSize,gp.tileSize*2);
		 gp.player.attackleft1=setup("/zoro/attack_left1",gp.tileSize*2,gp.tileSize);
		 gp.player.attackleft2=setup("/zoro/attack_left2",gp.tileSize*2,gp.tileSize);
		 gp.player.attackright1=setup("/zoro/attack_right1",gp.tileSize*2,gp.tileSize);
		 gp.player.attackright2=setup("/zoro/attack_right2",gp.tileSize*2,gp.tileSize);
		
	 }
	 }
}
