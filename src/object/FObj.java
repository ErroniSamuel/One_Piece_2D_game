package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class FObj extends SuperObject {
	public FObj() {
		name="Flag";
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/objects/flag.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
