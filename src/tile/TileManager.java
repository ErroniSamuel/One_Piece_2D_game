package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp=gp;
		tile=new Tile[10];
		mapTileNum=new int[gp.maxWorldCol][gp.maxWorldRow];
		getTile(); 
		loadMap("/maps/map1.txt");
	}
	public void loadMap(String map) {
		
		try {
			InputStream is=getClass().getResourceAsStream(map);
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			
			int col=0;
			int row=0;
			
			while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
				String line=br.readLine();
				
				while(col<gp.maxWorldCol) {
					String numbers[]=line.split(" ");
					mapTileNum[col][row]=Integer.parseInt(numbers[col]);
					col++;
				}
				if(col==gp.maxWorldCol) {
					row++;
					col=0;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}
		
		
	}
	public void getTile() {
			setup(0,"grass",false);
			setup(1,"tree",true);
			setup(2,"walln",true);
	}
	
	public void setup(int index,String imagePath,boolean collision) {
		UtilityTool uTool=new UtilityTool();
		try {
			tile[index]=new Tile();
			tile[index].image=ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
			tile[index].image=uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision=collision;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldRow=0;
		int worldCol=0;
		while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			int tileNum=mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX=worldX-gp.player.worldX + gp.player.screenX;
			int screenY=worldY-gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize>gp.player.worldX-gp.player.screenX &&
			   worldX - gp.tileSize<gp.player.worldX+gp.player.screenX &&
			   worldY + gp.tileSize>gp.player.worldY-gp.player.screenY &&
			   worldY - gp.tileSize<gp.player.worldY+gp.player.screenY) {
				g2.drawImage(tile[tileNum].image,screenX,screenY,null);
			}
			
			worldCol++;
	
			if(worldCol==gp.maxWorldCol) {
				worldCol=0;
	
				worldRow++;
			
			}
		}
	}
}
