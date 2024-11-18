package main;

import entity.NPC_Coby;
import monster.*;
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
		int i=0;
		gp.monster[i]=new MON_Cryo(gp);
		gp.monster[i].worldX=gp.tileSize*25;
		gp.monster[i].worldY=gp.tileSize*5;
		i++;

		gp.monster[i]=new MON_Cryo(gp);
		gp.monster[i].worldX=gp.tileSize*20;
		gp.monster[i].worldY=gp.tileSize*6;
		i++;
		
		gp.monster[i]=new MON_Dendro(gp);
		gp.monster[i].worldX=gp.tileSize*25;
		gp.monster[i].worldY=gp.tileSize*10;
		i++;

		gp.monster[i]=new MON_Dendro(gp);
		gp.monster[i].worldX=gp.tileSize*20;
		gp.monster[i].worldY=gp.tileSize*10;
		i++;
		
		gp.monster[i]=new MON_Pyro(gp);
		gp.monster[i].worldX=gp.tileSize*30;
		gp.monster[i].worldY=gp.tileSize*5;
		i++;

		gp.monster[i]=new MON_Pyro(gp);
		gp.monster[i].worldX=gp.tileSize*30;
		gp.monster[i].worldY=gp.tileSize*10;
		i++;
		
		
		gp.monster[i]=new MON_Hydro(gp);
		gp.monster[i].worldX=gp.tileSize*25;
		gp.monster[i].worldY=gp.tileSize*15;
		i++;

		gp.monster[i]=new MON_Hydro(gp);
		gp.monster[i].worldX=gp.tileSize*20;
		gp.monster[i].worldY=gp.tileSize*16;
		i++;
		
		
		gp.monster[i]=new MON_Electro(gp);
		gp.monster[i].worldX=gp.tileSize*25;
		gp.monster[i].worldY=gp.tileSize*20;
		i++;

		gp.monster[i]=new MON_Electro(gp);
		gp.monster[i].worldX=gp.tileSize*20;
		gp.monster[i].worldY=gp.tileSize*26;
		i++;
	}
}
