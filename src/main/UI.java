package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.*;


public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font Obelix,MaruMonica,Original;
	BufferedImage full_heart,half_heart,null_heart,haki_full,haki_null;
	public boolean messageOn=false;
//	public String message="";
	public boolean gameFinished=false;
	public String currentDialogue;
	public int commandNum=0;
	ArrayList<String> message=new ArrayList<>();
	ArrayList<Integer> messageCounter=new ArrayList<>();
	public int slotCol=0;
	public int slotRow=0;
	
	public UI(GamePanel gp) {
		this.gp=gp;

		try {
			InputStream is=getClass().getResourceAsStream("/font/Obelix.ttf");
			Obelix=Font.createFont(Font.TRUETYPE_FONT, is);

			is=getClass().getResourceAsStream("/font/MaruMonica.ttf");
			MaruMonica=Font.createFont(Font.TRUETYPE_FONT, is);

			is=getClass().getResourceAsStream("/font/Original.ttf");
			Original=Font.createFont(Font.TRUETYPE_FONT, is);
		}catch(FontFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		//create hud object
		Entity heart=new OBJ_Heart(gp);
		full_heart=heart.image;
		half_heart=heart.image2;
		null_heart=heart.image3;
		
		Entity haki=new OBJ_Haki(gp);
		haki_full=haki.image;
		haki_null=haki.image2;
	}
	
	public void addMessage(String text) {
	
		message.add(text);
		messageCounter.add(0);
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2=g2;
		
		g2.setFont(MaruMonica);
		g2.setColor(Color.white);
		
		//titleState
		if(gp.gameState==gp.titleState) {
			drawTitleScreen();
		}
		
		//Playstate
		if(gp.gameState==gp.playState) {
			drawPlayerLife();
			drawMessage();
		}
		
		//pausestate
		if(gp.gameState==gp.pauseState) {
		drawPlayerLife();
		drawPauseScreen();	
		}
		//dialogue state
		if(gp.gameState==gp.dialogueState) {
			drawDialogueScreen();
		}
		//char state
		if(gp.gameState==gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
		
	
	}
	public void drawTitleScreen() {
		g2.setColor(Color.red);
		g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
		g2.setFont(Original);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,100F));
		String text="One Piece Adventure";
		int x=getXforCenteredText(text);
		int y=gp.tileSize*2;
		
		// shadow
		g2.setColor(Color.black);
		g2.drawString(text,x+5,y+5);
		
		//main
		g2.setColor(Color.white);
		g2.drawString(text,x,y);
		
		//luffy
		
		x=gp.screenWidth/2;
		y+=gp.tileSize*2;
		g2.drawImage(gp.player.down1, x-40, y-25, gp.tileSize*2, gp.tileSize*2,null);
		
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text="New Game";
		x=getXforCenteredText(text);
		y+=gp.tileSize*3;
		g2.drawString(text, x, y);
		if(commandNum==0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text="Load Game";
		x=getXforCenteredText(text);
		y+=gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum==1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text="Quit";
		x=getXforCenteredText(text);
		y+=gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum==2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	public void drawMessage() {
		int messageX=gp.tileSize;
		int messageY=gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
		for(int i=0;i<message.size();i++) {
			if(message.get(i)!=null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				int counter=messageCounter.get(i)+1;
				messageCounter.set(i, counter);
				messageY+=50;
				
				if(messageCounter.get(i)>180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text="Paused";
		int x=getXforCenteredText(text);
		int y=gp.screenHeight/2;
		g2.drawString(text, x, y);
		
	}
	public void drawPlayerLife() {
		int x=gp.tileSize/2;
		int y=gp.tileSize/3;
		int i=0;
		
		
			//blank heart
			while(i<gp.player.currentCharacter.maxLife/2) {
				g2.drawImage(null_heart, x, y,null);
				i++;
				x+=gp.tileSize;
			}
			 x=gp.tileSize/2;
		     y=gp.tileSize/3;
			 i=0;
			 
			 //draw current life
			 while(i<gp.player.currentCharacter.life) {
				 g2.drawImage(half_heart, x, y,null);
				 i++;
				 if(i<gp.player.currentCharacter.life) {
					 g2.drawImage(full_heart,x,y,null);
				 }
				 i++;
				 x+=gp.tileSize;
		}
			 //draw haki
			 
			 x=gp.tileSize/2;
			 y=gp.tileSize+10;
			 i=0;
			 while(i<gp.player.currentCharacter.maxHaki) {
				 g2.drawImage(haki_null,x,y,null);
				 i++;
				 x+=gp.tileSize;
			 }
			 x=gp.tileSize/2;
			 y=gp.tileSize+10;
			 i=0;
			 while(i<gp.player.currentCharacter.haki) {
				 g2.drawImage(haki_full,x,y,null);
				 i++;
				 x+=gp.tileSize;
			 }
	}
	public void drawDialogueScreen() {
		int x=gp.tileSize*2;
		int y=gp.tileSize/3;
		int width=gp.screenWidth-(gp.tileSize*4);
		int height=gp.tileSize*4;
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
		x+=gp.tileSize;
		y+=gp.tileSize+20;
		
		for(String line:currentDialogue.split("\n")) {
		g2.drawString(line, x, y-10);
		y+=40;
		}
	}

	public void drawCharacterScreen() {
		//frame
		final int frameX=gp.tileSize;
		final int frameY=gp.tileSize;
		final int frameWidth=gp.tileSize*5;
		final int frameHeight=gp.tileSize*10;
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX=frameX+20;
		int textY=frameY+gp.tileSize;
		final int lineHeight=36;
		
		g2.drawString("Level", textX, textY);
		textY+=lineHeight;
		g2.drawString("Life", textX, textY);
		textY+=lineHeight;
		g2.drawString("Strength", textX, textY);
		textY+=lineHeight;
		g2.drawString("Haki", textX, textY);
		textY+=lineHeight;
		g2.drawString("Attack", textX, textY);
		textY+=lineHeight;
		g2.drawString("Defense", textX, textY);
		textY+=lineHeight;
		g2.drawString("Exp", textX, textY);
		textY+=lineHeight;
		g2.drawString("NextLevel", textX, textY);
		textY+=lineHeight;
		g2.drawString("Coin", textX, textY);
		textY+=lineHeight*2;
		if(gp.currentCharacter=="Zoro") {
		g2.drawString("Weapon", textX, textY);
		textY+=lineHeight;
		}
		
		
		int tailX=(frameX+frameWidth)-30;
		
		textY=frameY+gp.tileSize;
	String value;
	value=String.valueOf(gp.player.level);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	
	value=String.valueOf(gp.player.life +"/"+gp.player.maxLife);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	
	value=String.valueOf(gp.player.strength);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	
	value=String.valueOf(gp.player.dexterity);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	
	value=String.valueOf(gp.player.attack);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	
	value=String.valueOf(gp.player.defence);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	
	value=String.valueOf(gp.player.exp);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	
	value=String.valueOf(gp.player.nextLevelExp);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;

	value=String.valueOf(gp.player.coin);
	textX=getXforAlignToRight(value,tailX);
	g2.drawString(value, textX, textY);
	textY+=lineHeight;
	if(gp.currentCharacter=="Zoro" && gp.player.currentWeapon!=null) {
	g2.drawImage(gp.player.currentWeapon.down1, textX-30, textY,null);
	
	}
//	BufferedImage image=setup("/objects/shusui",gp.tileSize,gp.tileSize);
//	if(gp.currentCharacter=="Zoro") {
//		g2.drawImage(full_heart, frameX, frameY, frameWidth, frameHeight, textX, textY, lineHeight, tailX, gp)
//	}
	

	}
	public void drawInventory() {
		//frame
		int frameX=gp.tileSize*9;
		int frameY=gp.tileSize;
		int frameWidth=gp.tileSize*6;
		int frameHeight=gp.tileSize*5;
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		//slot
		final int slotXstart=frameX+20;
		final int slotYstart=frameY+20;
		int slotX=slotXstart;
		int slotY=slotYstart;
		int slotSize=gp.tileSize+3;
		
		//draw player items
		for(int i=0;i<gp.player.inventory.size();i++) {
			
			if(gp.player.currentWeapon!=null && gp.player.inventory.get(i).name.equals(gp.player.currentWeapon.name)) {
				g2.setColor(new Color(248,180,8));
				g2.fillRoundRect(slotX, slotY, gp.tileSize,gp.tileSize,10,10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
			
			slotX+=slotSize;
			if(i==4 || i==9 ||i==14) {
				slotX=slotXstart;
				slotY+=slotSize;
			}
		}
		
		//cursor
		int cursorX=slotXstart+(slotSize*slotCol);
		int cursorY=slotYstart+(slotSize*slotRow);
		int cursorWidth=gp.tileSize;
		int cursorHeight=gp.tileSize;
		//draw cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);
		
		//description frame
		int dFrameX=frameX;
		int dFrameY=frameY+frameHeight;
		int dFrameWidth=frameWidth;
		int dFrameHeight=gp.tileSize*3;
		//description text
		int textX=dFrameX+20;
		int textY=dFrameY+gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int itemIndex=getItemIndex();
		if(itemIndex<gp.player.inventory.size()) {
			drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

			for(String line:gp.player.inventory.get(itemIndex).description.split("\n")) {
			g2.drawString(line,textX,textY);
			textY+=32;
			}
		}
		
	}
	public int getItemIndex() {
		return slotCol+(slotRow*5);
	}
	public void drawSubWindow(int x,int y,int width,int height) {
		Color c=new Color(0,0,0,220);
		g2.setColor(c);
		
		g2.fillRoundRect(x, y, width, height, 35,35);
		
		c=new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
	}
	public int getXforAlignToRight(String text,int tailX) {
		int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x=tailX-length;
		return x;
	}

	public int getXforCenteredText(String text) {
		int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x=gp.screenWidth/2-length/2;
		return x;
	}
}
