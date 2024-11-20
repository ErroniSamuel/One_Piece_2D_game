package object;

import entity.Projectile;
import main.GamePanel;

public class Red_Hawk extends Projectile{
	GamePanel gp;
	public Red_Hawk(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="Pound_Ho";
		speed=7;
		maxLife=80;
		life=maxLife;
		attack=10;
		useCost=1;
		alive=false;
		getImage();
	}
	public void getImage() {
		up1=setup("/projectiles/RH_up1",gp.tileSize,gp.tileSize);
		up2=setup("/projectiles/RH_up2",gp.tileSize,gp.tileSize);
		down1=setup("/projectiles/RH_down1",gp.tileSize,gp.tileSize);
		down2=setup("/projectiles/RH_down2",gp.tileSize,gp.tileSize);
		left1=setup("/projectiles/RH_left1",gp.tileSize,gp.tileSize);
		left2=setup("/projectiles/RH_left2",gp.tileSize,gp.tileSize);
		right1=setup("/projectiles/RH_right1",gp.tileSize,gp.tileSize);
		right2=setup("/projectiles/RH_right2",gp.tileSize,gp.tileSize);
	}

}
