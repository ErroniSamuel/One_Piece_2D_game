package object;

import entity.Projectile;
import main.GamePanel;

public class Pound_Ho extends Projectile{
	GamePanel gp;
	public Pound_Ho(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="Pound_Ho";
		speed=8;
		maxLife=80;
		life=maxLife;
		attack=8;
		useCost=1;
		alive=false;
		getImage();
	}
	public void getImage() {
		up1=setup("/projectiles/PH_up1",gp.tileSize,gp.tileSize);
		up2=setup("/projectiles/PH_up2",gp.tileSize,gp.tileSize);
		down1=setup("/projectiles/PH_down1",gp.tileSize,gp.tileSize);
		down2=setup("/projectiles/PH_down2",gp.tileSize,gp.tileSize);
		left1=setup("/projectiles/PH_left1",gp.tileSize,gp.tileSize);
		left2=setup("/projectiles/PH_left2",gp.tileSize,gp.tileSize);
		right1=setup("/projectiles/PH_right1",gp.tileSize,gp.tileSize);
		right2=setup("/projectiles/PH_right2",gp.tileSize,gp.tileSize);
	}

}
