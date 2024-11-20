package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
public OBJ_Sword_Normal(GamePanel gp) {
	super(gp);
	name="shusui";
	type=type_sword;
	down1=setup("/objects/shusui",gp.tileSize,gp.tileSize);
	attackValue=3;
	description="["+name+"]"+" 2x damage and \n should be equipped \n by zoro";
}
}
