package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Food extends SuperObject{
	public OBJ_Food(){
		name="food";
		try {image=ImageIO.read(getClass().getResourceAsStream("/objects/food.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		}
}
