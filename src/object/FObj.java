package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class FObj extends SuperObject {
	GamePanel gp;
	public FObj(GamePanel gp) {
		this.gp=gp;
		name="Flag";
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/objects/flag.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
