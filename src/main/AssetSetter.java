package main;

import entity.NPC_Oldman;
import monster.MON_Dragon;
import monster.MON_GreenSlime;
import monster.MON_Snake;
import object.OBJ_BOOTS;
import object.OBJ_KEY;

public class AssetSetter {
	
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
	}
	public void setNPC() {
		gp.npc[0] = new NPC_Oldman(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	
	public void setMonster() {
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster[0].worldY = gp.tileSize*36;
		
		gp.monster[1] = new MON_GreenSlime(gp);
		gp.monster[1].worldX = gp.tileSize*23;
		gp.monster[1].worldY = gp.tileSize*37;
		
		gp.monster[2] = new MON_Snake(gp);
		gp.monster[2].worldX = gp.tileSize*36;
		gp.monster[2].worldY = gp.tileSize*23;
		
		gp.monster[3] = new MON_Snake(gp);
		gp.monster[3].worldX = gp.tileSize*35;
		gp.monster[3].worldY = gp.tileSize*23;
		
		gp.monster[4] = new MON_Dragon(gp);
		gp.monster[4].worldX = gp.tileSize*35;
		gp.monster[4].worldY = gp.tileSize*10;
	}
}
