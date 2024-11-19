package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Armour extends Entity{

	public OBJ_Armour(GamePanel gp) {
		super(gp);
		name="Armour";
		down1=setup("/objects/Armour",gp.tileSize,gp.tileSize);
		defenceValue=1;
		description="["+name+"]"+" 2x defence";
	}
}
