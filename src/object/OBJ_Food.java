package object;



import entity.Entity;
import main.GamePanel;

public class OBJ_Food extends Entity{
	GamePanel gp;
	int value=3;
	public OBJ_Food(GamePanel gp){
		super(gp);
		this.gp=gp;
		name="meat";
		type=type_consumable;
		down1=setup("/objects/food",gp.tileSize,gp.tileSize);
		description="["+name+"]"+" heals you";

	}
	public void use(Entity entity) {
		gp.gameState=gp.dialogueState;
		gp.ui.currentDialogue="You ate food and healed by "+value;
		entity.life+=value;
		if(entity.life>entity.maxLife) {
			entity.life=entity.maxLife;
		}
	}
	
}
