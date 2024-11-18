package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	
	public OBJ_Heart(GamePanel gp){
	super(gp);
	name="heart";
	image=setup("/objects/full_heart",gp.tileSize,gp.tileSize);
	image2=setup("/objects/half_heart",gp.tileSize,gp.tileSize);
	image3=setup("/objects/null_heart",gp.tileSize,gp.tileSize);
	
	}
}
