package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Coby extends Entity {
	public NPC_Coby(GamePanel gp){
		super(gp);
		
		direction="down";
		speed=2;
		solidArea.x=0;
		solidArea.y=16;
		solidArea.width=48;
		solidArea.height=32;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		getImg();
		setDialogue();
	} 
	public void getImg() {
		up1=setup("/npc/Coby_up1",gp.tileSize,gp.tileSize);
		up2=setup("/npc/Coby_up2",gp.tileSize,gp.tileSize);
		down1=setup("/npc/Coby_down1",gp.tileSize,gp.tileSize);
		down2=setup("/npc/Coby_down2",gp.tileSize,gp.tileSize);
		left1=setup("/npc/Coby_left1",gp.tileSize,gp.tileSize);
		left2=setup("/npc/Coby_left2",gp.tileSize,gp.tileSize);
		right1=setup("/npc/Coby_right1",gp.tileSize,gp.tileSize);
		right2=setup("/npc/Coby_right2",gp.tileSize,gp.tileSize);
}
	public void setDialogue() {
		dialogues[0]="Coby: Ar...Are you a pirate?....";
		dialogues[1]="Coby: Im Scared of them..";
		dialogues[2]="Luffy: I'm a pirate too!";
		dialogues[3]="Coby: OMG you are also a Pirate?";
		dialogues[4]="Coby: Please don't hurt me i will do \n chores for you on your ship";
		dialogues[5]="Luffy: I don't have a ship";
		dialogues[6]="Coby: you don't have a ship? \n and you are a pirate? hahaha...";
		dialogues[7]="Luffy: Why are you scared of pirates?";
		dialogues[8]="Coby: They do evil things that scare me";
		dialogues[9]="Luffy: You are dressedd like my grandpa";
		dialogues[10]="Coby: This is a marine uniform!! not any \n grandpa's outfit";
		dialogues[11]="Luffy: You are a marine and scared of pirates?";
		dialogues[12]="Coby: Don't laugh at me!! I'm not a marine yet \n I want to be marine one day!!";
		dialogues[13]="Luffy: That's great";
		dialogues[14]="Coby: what compelled you to become a pirate?";
		dialogues[15]="Luffy: I'm gonna be the King of the pirates!! ^v^";
		dialogues[16]="Coby: You-u can't be serious you need to find \n one piece for that!!";
		dialogues[17]="Coby: There are thousands of pirates searching \n for it you can't find it there's no way!!";
		dialogues[18]="Luffy: *smacks* it's not about if I can do it or\n not, I'm doing it because I want to";
		dialogues[19]="Luffy: I've decided to become the King of the \n pirates, so even if I die fighting for that,\n thats fine with me! ^u^";
		
		
	}
	public void setAction() {
		
		actionLookCounter++;
		
		if(actionLookCounter>=120) {
		Random random=new Random();
		int i=random.nextInt(100)+1; //pick random from 1 to 100
		if(i<=25) {
			direction="up";
		}else
		if(i>25 && i<=50) {
			direction="down";
		}
		else if(i>50 && i<=75) {
			direction="left";
		}else {
			direction="right";
		}
		actionLookCounter=0;
		}
		
	}

	public void speak() {
		super.speak();
	}
}
