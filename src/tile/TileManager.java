package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	ArrayList<String> fileNames=new ArrayList<>();
	ArrayList<String> collisionStatus=new ArrayList<>();
	
	public TileManager(GamePanel gp) {
		this.gp=gp;
		
		InputStream is = getClass().getResourceAsStream("/maps/tiledata3.txt");
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		
		String line;
		try {
			while((line=br.readLine())!=null){
				fileNames.add(line);
				collisionStatus.add(br.readLine());
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		tile=new Tile[fileNames.size()];
		getTile(); 
		
		is=getClass().getResourceAsStream("/maps/sample3.txt");
		br=new BufferedReader(new InputStreamReader(is));
		
		try {
			String line2=br.readLine();
			String maxTile[]=line2.split(" ");
			
			gp.maxWorldCol=maxTile.length;
			gp.maxWorldRow=maxTile.length;
			mapTileNum=new int[gp.maxWorldCol][gp.maxWorldRow];
			br.close();
		}catch(IOException e) {
			System.out.println("Error");
		}
		
		
		loadMap("/maps/sample3.txt");
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
		
		for(int i=0;i<fileNames.size();i++) {
			String fileName;
			boolean collision;
			
			//get filename
			fileName=fileNames.get(i);
			if(collisionStatus.get(i).equals("true")) {
				collision=true;
			}else {
				collision=false;
			}
			setup(i,fileName,collision);
		}
//			setup(0,"grass",false);
//			setup(1,"grass",false);
//			setup(2,"grass",false);
//			setup(3,"grass",false);
//			setup(4,"grass",false);
//			setup(5,"grass",false);
//			setup(6,"grass",false);
//			setup(7,"grass",false);
//			setup(8,"grass",false);
//			setup(9,"grass",false);
//			setup(10,"grass",false);
//			setup(11,"tree",true);
//			setup(12,"walln",true);
//			setup(13,"c1_lake",true);
//			setup(14,"top_lake",true);
//			setup(15,"c2_lake",true);
//			setup(16,"right_lake",true);
//			setup(17,"c3_lake",true);
//			setup(18,"bottom_lake",true);
//			setup(19,"c4_lake",true);
//			setup(20,"left_lake",true);
//			setup(21,"waves_lake",true);
//			setup(22,"lake",true);
//			setup(23,"fish_lake",true);
//			setup(24,"boat_up",true);
//			setup(25,"boat_down",true);
//			setup(26,"portal",false);
//			setup(27,"sea_1",true);
//			setup(28,"sea_2",true);
//			setup(29,"sea_3",true);
//			setup(30,"sea_4",true);
//			setup(31,"pit",false);
//			setup(32,"lava",false);
//			setup(33,"path_rl",false);
//			setup(34,"path_ud",false);
//			setup(35,"pathc_1",false);
//			setup(36,"pathc_2",false);
//			setup(37,"pathc_3",false);
//			setup(38,"pathc_4",false);
//			
//			
			
			
			
			
			
	}
	
	public void setup(int index,String imagePath,boolean collision) {
		UtilityTool uTool=new UtilityTool();
		try {
			tile[index]=new Tile();
			tile[index].image=ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath));
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
