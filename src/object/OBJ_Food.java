package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Food extends SuperObject{
	GamePanel gp;
	public OBJ_Food(GamePanel gp){
		this.gp=gp;
		name="food";
		try {image=ImageIO.read(getClass().getResourceAsStream("/objects/food.png"));
		uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		}catch(IOException e) {
			e.printStackTrace();
		}
		}
}
