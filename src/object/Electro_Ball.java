package object;

import entity.Projectile;
import main.GamePanel;

public class Electro_Ball extends Projectile{
GamePanel gp;
	public Electro_Ball(GamePanel gp) {
		super(gp);
		this.gp=gp;
		name="Electro Ball";
		speed=6;
		maxLife=70;
		life=maxLife;
		attack=2;
		useCost=1;
		alive=false;
		getImage();
	}


public void getImage() {
	up1=setup("/projectiles/Electro_up1",gp.tileSize,gp.tileSize);
	up2=setup("/projectiles/Electro_up2",gp.tileSize,gp.tileSize);
	down1=setup("/projectiles/Electro_up1",gp.tileSize,gp.tileSize);
	down2=setup("/projectiles/Electro_up2",gp.tileSize,gp.tileSize);
	left1=setup("/projectiles/Electro_up1",gp.tileSize,gp.tileSize);
	left2=setup("/projectiles/Electro_up2",gp.tileSize,gp.tileSize);
	right1=setup("/projectiles/Electro_up1",gp.tileSize,gp.tileSize);
	right2=setup("/projectiles/Electro_up2",gp.tileSize,gp.tileSize);
}
}