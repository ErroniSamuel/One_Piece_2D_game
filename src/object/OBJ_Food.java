package object;



import entity.Entity;
import main.GamePanel;

public class OBJ_Food extends Entity{
	
	public OBJ_Food(GamePanel gp){
		super(gp);
		name="food";
		
		down1=setup("/objects/food",gp.tileSize,gp.tileSize);
		
	}
}
