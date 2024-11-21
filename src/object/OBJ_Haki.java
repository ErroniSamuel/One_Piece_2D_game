package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Haki extends Entity{
	GamePanel gp;
	public OBJ_Haki(GamePanel gp) {
		super(gp);
		this.gp=gp;
		
		name="Haki";
		image=setup("/objects/haki_full",gp.tileSize,gp.tileSize);
		image2=setup("/objects/haki_null",gp.tileSize,gp.tileSize);
	}

}
