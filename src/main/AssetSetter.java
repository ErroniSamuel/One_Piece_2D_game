package main;

import entity.NPC_Coby;
import monster.MON_Cryo;
import object.*;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp=gp;
	} 
	public void setObject() {
		
	}
	public void setNPC() {
		gp.npcs[0]=new NPC_Coby(gp);
		gp.npcs[0].worldX=gp.tileSize*25;
		gp.npcs[0].worldY=gp.tileSize*4;
		

	}
	public void setMonster() {
		gp.monster[0]=new MON_Cryo(gp);
		gp.monster[0].worldX=gp.tileSize*25;
		gp.monster[0].worldY=gp.tileSize*5;
		

		gp.monster[1]=new MON_Cryo(gp);
		gp.monster[1].worldX=gp.tileSize*20;
		gp.monster[1].worldY=gp.tileSize*6;
		
	}
}
