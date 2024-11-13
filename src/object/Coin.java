package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin extends SuperObject {
		
		public Coin(){
		name="coin";
		try {image=ImageIO.read(getClass().getResourceAsStream("/objects/belly.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
