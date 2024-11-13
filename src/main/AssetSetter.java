package main;

import object.*;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp=gp;
	} 
	public void setObject() {
		gp.obj[0]=new OBJ_Key(gp);
		gp.obj[0].worldX=27*gp.tileSize;
		gp.obj[0].worldY=4*gp.tileSize;
		
		gp.obj[1]=new FObj(gp);
		gp.obj[1].worldX=24*gp.tileSize;
		gp.obj[1].worldY=41*gp.tileSize;
		
		gp.obj[2]=new FObj(gp);
		gp.obj[2].worldX=26*gp.tileSize;
		gp.obj[2].worldY=41*gp.tileSize;
		
		gp.obj[3]=new OBJ_Door(gp);
		gp.obj[3].worldX=25*gp.tileSize;
		gp.obj[3].worldY=42*gp.tileSize;
		
		gp.obj[4]=new OBJ_Chest(gp);
		gp.obj[4].worldX=15*gp.tileSize;
		gp.obj[4].worldY=47*gp.tileSize;
		
		gp.obj[5]=new OBJ_Food(gp);
		gp.obj[5].worldX=30*gp.tileSize;
		gp.obj[5].worldY=47*gp.tileSize;
		
		gp.obj[6]=new Coin(gp);
		gp.obj[6].worldX=17*gp.tileSize;
		gp.obj[6].worldY=47*gp.tileSize;
		
		
		
		
		
	}
}
