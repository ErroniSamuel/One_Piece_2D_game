package main;


public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX,previousEventY;
	boolean canTouchEvent=true;
	
	public boolean firstBoat=true;
	public EventHandler(GamePanel gp) {
		this.gp=gp;
		eventRect=new EventRect[gp.maxWorldCol][gp.maxWorldRow];

		int col=0;
		int row=0;
		while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
			eventRect[col][row]=new EventRect();
			eventRect[col][row].x=23;
			eventRect[col][row].y=23;
			eventRect[col][row].width=2;
			eventRect[col][row].height=2;
			eventRect[col][row].eventRectDefaultX=eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY=eventRect[col][row].y;
			
			col++;
			if(col==gp.maxWorldCol) {
			col=0;
				row++;
			}
		}
		
	}
	public void checkEvent() {
		//check if player is off tile
		int xDistance=Math.abs(gp.player.worldX-previousEventX);
        int yDistance=Math.abs(gp.player.worldY-previousEventY);
        int distance=Math.max(xDistance,yDistance);
        if(distance>gp.tileSize) {
        	canTouchEvent=true;
        }
        
//		if(canTouchEvent) {
//			if(hit(12,2,"up")) {
//				//event occurs
//				damagePit(12,2,gp.dialogueState);
//			}
			if(hit(7,5,"left")) {
				healingBoat(gp.dialogueState);
//			}
//			if(hit(15,8,"down")) {
//				gp.playSE(1);
//				teleport(15,11,gp.dialogueState);
//			}
//			if(hit(15,11,"up")) {
//				gp.playSE(1);
//				teleport(15,8,gp.dialogueState);
//			}
//			if(hit(13,2,"any")) {
//				burn(13,2,gp.dialogueState);
//			}
		
		}
		
		
		
	}
	public boolean hit(int col,int row,String reqDirection) {
		boolean hit=false;
		
		gp.player.solidArea.x=gp.player.worldX+gp.player.solidArea.x;
		gp.player.solidArea.y=gp.player.worldY+gp.player.solidArea.y;
		eventRect[col][row].x=col*gp.tileSize+eventRect[col][row].x;
		eventRect[col][row].y=row*gp.tileSize+eventRect[col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
			if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")) {
				hit=true;
				
				previousEventX=gp.player.worldX;
				previousEventY=gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x=gp.player.solidAreaDefaultX;
		gp.player.solidArea.y=gp.player.solidAreaDefaultY;
		eventRect[col][row].x=eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y=eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	public void burn(int col,int row,int gameState) {
		gp.gameState=gameState;
		gp.ui.currentDialogue="It's Lava!!";
		gp.player.life-=2;
		canTouchEvent=false;
		
	}
	public void damagePit(int col,int row,int gameState) {
		gp.gameState=gameState;
		gp.ui.currentDialogue="You fell into a pit";
		gp.player.life-=1;
		// if you want to happen only once type eventRect[col][row].eventDone=true;
		canTouchEvent=false;
	}
	public void teleport(int x,int y,int gameState) {
		gp.gameState=gameState;
		gp.ui.currentDialogue="Teleported";
		gp.player.worldX=gp.tileSize*x;
		gp.player.worldY=gp.tileSize*y;
		canTouchEvent=false;
	}
	public void healingBoat(int gameState) {
		if(firstBoat) {
			gp.gameState=gameState;
			gp.ui.currentDialogue="You can come back to the boat anytime \n to heal fully, you have to press ENTER \n while going towards boat";
		firstBoat=false;
		}
		if(gp.keyH.enterPressed) {
		gp.gameState=gameState;
		gp.ui.currentDialogue="You had rest and now are ready to go";
		gp.playSE(4);
		gp.player.attackCancelled=true;
		gp.player.luffy.life=gp.player.luffy.maxLife;
		gp.player.zoro.life=gp.player.zoro.maxLife;
		gp.player.luffy.haki=gp.player.luffy.maxHaki;
		gp.player.zoro.haki=gp.player.zoro.maxHaki;
		gp.as.setMonster();
		}
		canTouchEvent=false;
	}
}
