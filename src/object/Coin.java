package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Coin extends SuperObject {
		GamePanel gp;
		public Coin(GamePanel gp){
			this.gp=gp;
		name="coin";
		try {image=ImageIO.read(getClass().getResourceAsStream("/objects/belly.png"));
		uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
