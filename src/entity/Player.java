
package entity;

import entity.Entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobPriority;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_FIREBALL;

public class Player extends Entity{
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    public boolean attackCanceled = false;
   
    
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2-(gp.tileSize/2);
        screenY = gp.screenHeight/2-(gp.tileSize/2);
        
        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        attackArea.width = 36;
        attackArea.height = 36;
        
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    
    public void setDefaultValues(){
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
        
        //Player Status
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        projectile= new OBJ_FIREBALL(gp);
        
        
    }
    
    public void getPlayerImage(){
    	//Updated by Hogan
        up1 = setup("/player2/tile012", gp.tileSize, gp.tileSize);
        up2 = setup("/player2/tile013", gp.tileSize, gp.tileSize);
        up3 = setup("/player2/tile014", gp.tileSize, gp.tileSize);
        up4 = setup("/player2/tile015", gp.tileSize, gp.tileSize);
        down1 = setup("/player2/tile000", gp.tileSize, gp.tileSize);
        down2 = setup("/player2/tile001", gp.tileSize, gp.tileSize);
        down3 = setup("/player2/tile002", gp.tileSize, gp.tileSize);
        down4 = setup("/player2/tile003", gp.tileSize, gp.tileSize);
        left1 = setup("/player2/tile004", gp.tileSize, gp.tileSize);
        left2 = setup("/player2/tile005", gp.tileSize, gp.tileSize);
        left3 = setup("/player2/tile006", gp.tileSize, gp.tileSize);
        left4 = setup("/player2/tile007", gp.tileSize, gp.tileSize);
        right1 = setup("/player2/tile008", gp.tileSize, gp.tileSize);
        right2 = setup("/player2/tile009", gp.tileSize, gp.tileSize);
        right3 = setup("/player2/tile010", gp.tileSize, gp.tileSize);
        right4 = setup("/player2/tile011", gp.tileSize, gp.tileSize);
    }
    
    public void getPlayerAttackImage() {
    	attackUp1 = setup("/player2/Attack/attack_up1", gp.tileSize, gp.tileSize*2);
    	attackUp2 = setup("/player2/Attack/attack_up2", gp.tileSize, gp.tileSize*2);
    	attackUp3 = setup("/player2/Attack/attack_up3", gp.tileSize, gp.tileSize*2);
    	attackUp4 = setup("/player2/Attack/attack_up4", gp.tileSize, gp.tileSize*2);
    	attackDown1 = setup("/player2/Attack/attack_down1", gp.tileSize, gp.tileSize*2);
    	attackDown2 = setup("/player2/Attack/attack_down2", gp.tileSize, gp.tileSize*2);
    	attackDown3 = setup("/player2/Attack/attack_down3", gp.tileSize, gp.tileSize*2);
    	attackDown4 = setup("/player2/Attack/attack_down4", gp.tileSize, gp.tileSize*2);
    	attackLeft1 = setup("/player2/Attack/attack_left1", gp.tileSize*2, gp.tileSize);
    	attackLeft2 = setup("/player2/Attack/attack_left2", gp.tileSize*2, gp.tileSize);
    	attackLeft3 = setup("/player2/Attack/attack_left3", gp.tileSize*2, gp.tileSize);
    	attackLeft4 = setup("/player2/Attack/attack_left4", gp.tileSize*2, gp.tileSize);
    	attackRight1 = setup("/player2/Attack/attack_right1", gp.tileSize*2, gp.tileSize);
    	attackRight2 = setup("/player2/Attack/attack_right2", gp.tileSize*2, gp.tileSize);
    	attackRight3 = setup("/player2/Attack/attack_right3", gp.tileSize*2, gp.tileSize);
    	attackRight4 = setup("/player2/Attack/attack_right4", gp.tileSize*2, gp.tileSize);
    	
    }
    
    public void update(){
    	
    	if(attacking == true) {
    		attacking();
    	}
    	
    	if(keyH.upPressed == true || keyH.downPressed == true 
    		|| keyH.leftPressed == true || keyH.rightPressed == true||keyH.enterPressed==true
    		|| keyH.Jpressed == true) {
    		if(keyH.upPressed == true){
                direction = "up";
                
            }
            else if(keyH.downPressed == true){
                direction = "down";
                
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }
    		
    		//Check tile collsion
    		collisionOn = false;
    		gp.cChecker.checkTile(this);
    		//Check obj collision
    		int objIndex = gp.cChecker.checkObject(this, true);
    		pickUpObject(objIndex);
    		//Check npc collision
    		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
    		interactNPC(npcIndex);
    		//check monster collision
    		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
    		contactMonster(monsterIndex);
    		
    		//Check event
    		gp.eventHandler.checkEvent();
    		
    		//If collision = false, player can move
    		if(collisionOn == false && keyH.enterPressed==false && keyH.Jpressed == false) {
    			switch(direction) {
	    			case "up": worldY -= speed; break;
	    			case "down": worldY += speed; break;
	    			case "right": worldX += speed; break;
	    			case "left": worldX -= speed; break;
    			}
    		}
    		
    		if(keyH.Jpressed == true && attackCanceled==false) {
    			attacking = true;
    			spriteCounter = 0;
    		}

    		gp.keyH.enterPressed = false;
            gp.keyH.Jpressed = false;
            
            spriteCounter++;
            if(spriteCounter > 5) {
            	if(spriteNum == 1) 
            		spriteNum = 2;
            	else if(spriteNum == 2)
            		spriteNum = 3;
            	else if(spriteNum == 3)
            		spriteNum = 4;
            	else if(spriteNum == 4)
            		spriteNum = 1;
            	spriteCounter = 0;
            }
    	} 
		if (gp.keyH.shotKeyExpressed==true&&projectile.alive==false) {
			//set up projectile
			projectile.set(worldX,worldY,direction,true,this);
			//add to list
			gp.projectileList.add(projectile);
		}
    	//Invincible time
    	if(invincible == true) {
    		invicibleCounter++;
    		if(invicibleCounter>60) {
    			invincible = false;
    			invicibleCounter = 0;
    		}
    	}
    }
    
    public void attacking() {
    	spriteCounter++;
    	
    	if(spriteCounter<= 5) {
    		spriteNum = 1;
    	}
    	if(spriteCounter > 5&&spriteCounter<=10) {
    		spriteNum =2;
    		
    		int currentWorldX = worldX;
    		int currentWorldY = worldY;
    		int solidAreaWidth = solidArea.width;
    		int solidAreaHeight = solidArea.height;
    		
    		switch(direction) {
    		case "up": worldY-=attackArea.height; break;
    		case "down": worldY+=attackArea.height;break;
    		case "left": worldX-=attackArea.width;break;
    		case "right": worldX+=attackArea.width; break;
    		}
    		
    		solidArea.width = attackArea.width;
    		solidArea.height = attackArea.height;
    		
    		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
    		damageMonster(monsterIndex);
    		
    		worldX = currentWorldX;
    		worldY = currentWorldY;
    		solidArea.width = solidAreaWidth;
    		solidArea.height = solidAreaHeight;
    	
    	}

    	if(spriteCounter > 10&&spriteCounter<=15) {

    		spriteNum =3;
    		
    		int currentWorldX = worldX;
    		int currentWorldY = worldY;
    		int solidAreaWidth = solidArea.width;
    		int solidAreaHeight = solidArea.height;
    		
    		switch(direction) {
    		case "up": worldY-=attackArea.height; break;
    		case "down": worldY+=attackArea.height;break;
    		case "left": worldX-=attackArea.width;break;
    		case "right": worldX+=attackArea.width; break;
    		}
    		
    		solidArea.width = attackArea.width;
    		solidArea.height = attackArea.height;
    		
    		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
    		damageMonster(monsterIndex);
    		
    		worldX = currentWorldX;
    		worldY = currentWorldY;
    		solidArea.width = solidAreaWidth;
    		solidArea.height = solidAreaHeight;
    	
    	}

    	if(spriteCounter > 15&&spriteCounter<=20) {

    		spriteNum =4;
    		
    		int currentWorldX = worldX;
    		int currentWorldY = worldY;
    		int solidAreaWidth = solidArea.width;
    		int solidAreaHeight = solidArea.height;
    		
    		switch(direction) {
    		case "up": worldY-=attackArea.height; break;
    		case "down": worldY+=attackArea.height;break;
    		case "left": worldX-=attackArea.width;break;
    		case "right": worldX+=attackArea.width; break;
    		}
    		
    		solidArea.width = attackArea.width;
    		solidArea.height = attackArea.height;
    		
    		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
    		damageMonster(monsterIndex);
    		
    		worldX = currentWorldX;
    		worldY = currentWorldY;
    		solidArea.width = solidAreaWidth;
    		solidArea.height = solidAreaHeight;
    	
    	}
    	if(spriteCounter>20) {
    		spriteNum = 1;
    		spriteCounter = 0;
    		attacking = false;
    	}
    	
    }
    
    public void pickUpObject(int i) {
    	if(gp.keyH.enterPressed) {
    		if(i!=999) {
        		//if index is not 999, we're touching an object
    			String objectName = gp.obj[i].name;
    			
    			switch(objectName) {
    			case "key":
    				hasKey++;
    				gp.playSE(1);
    				gp.obj[i] = null;
    				gp.ui.showMessage("You get a key");
    				break;
    				
    			case "door":
    				if(hasKey > 0) {
    					gp.playSE(1);
    					gp.obj[i]=null;
    					hasKey--;
    				}
    				else {
    					gp.ui.showMessage("You need a key");
    				}
    				break;
    			
    			case "boots":
    				gp.obj[i]=null;
    				gp.playSE(2);
    				speed += 2;
    				gp.ui.showMessage("Power up");
    			break;
        	    }
            }
    	}
    }
    public void interactNPC(int i) {
    	
    	if(gp.keyH.Jpressed == true) {
    		if(i!=999) {
    			attackCanceled = true;
    		}else {
    			gp.playSE(7);
    			attacking = true;
    		}
    	}
    	gp.keyH.Jpressed = false;
    	if(gp.keyH.enterPressed == true) {
    		if(i!=999) {
    			attackCanceled = true;
    			gp.gameState = gp.dialogueState;
    			gp.npc[i].speak();
    		}
    	}
    	gp.keyH.enterPressed = false;
    }
    public void contactMonster(int i) {
    	if(i!=999) {
    		if(invincible == false) {
    			gp.playSE(6);
    			life-=1;
    			invincible = true;
    		}
    	}
    }
    public void damageMonster(int i) {
    	if(i!=999) {
    		if(gp.monster[i].invincible == false) {
    			
    			gp.playSE(5);
    			gp.monster[i].life -=1;
    			gp.monster[i].invincible = true;
    			
    			if(gp.monster[i].life<=0) {
    				gp.monster[i].died = true;
    			}
    		}
    	}
    	else {
		}
    }
    
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        switch (direction) {
            case "up":
            	if(attacking == false) {
            		if(spriteNum == 1){image = up1;}
            		if(spriteNum == 2){image = up2;}
            		if(spriteNum == 3){image = up3;}
            		if(spriteNum == 4){image = up4;}
            	}
            	if(attacking == true) {
            		tempScreenY = screenY - gp.tileSize;
            		if(spriteNum == 1){image = attackUp1;}
            		if(spriteNum == 2){image = attackUp2;}
            		if(spriteNum == 3){image = attackUp3;}
            		if(spriteNum == 4){image = attackUp4;}
            	}
            	break;
            case "down":
            	if(attacking == false) {
            		if(spriteNum == 1){image = down1;}
            		if(spriteNum == 2){image = down2;}
            		if(spriteNum == 3){image = down3;}
            		if(spriteNum == 4){image = down4;}
            	}
            	if(attacking == true) {
            		if(spriteNum == 1){image = attackDown1;}
            		if(spriteNum == 2){image = attackDown2;}
            		if(spriteNum == 3){image = attackDown3;}
            		if(spriteNum == 4){image = attackDown4;}
            	}
                break;
            case "left":
            	if(attacking == false) {
            		if(spriteNum == 1){image = left1;}
            		if(spriteNum == 2){image = left2;}
            		if(spriteNum == 3){image = left3;}
            		if(spriteNum == 4){image = left4;}
            	}
            	if(attacking == true) {
            		tempScreenX = screenX - gp.tileSize;
            		if(spriteNum == 1){image = attackLeft1;}
            		if(spriteNum == 2){image = attackLeft2;}
            		if(spriteNum == 3){image = attackLeft3;}
            		if(spriteNum == 4){image = attackLeft4;}
            	}
                break;
            case "right":
            	if(attacking == false) {
            		if(spriteNum == 1){image = right1;}
            		if(spriteNum == 2){image = right2;}
            		if(spriteNum == 3){image = right3;}
            		if(spriteNum == 4){image = right4;}
            	}
            	if(attacking == true) {
            		if(spriteNum == 1){image = attackRight1;}
            		if(spriteNum == 2){image = attackRight2;}
            		if(spriteNum == 3){image = attackRight3;}
            		if(spriteNum == 4){image = attackRight4;}
            	}
                break;
        }
        
        int x = screenX;
        int y = screenY;
        
        if(screenX>worldX) {
        	x = worldX;
        }
        if(screenY>worldY) {
        	y = worldY;
        }
        int rightOffset = gp.screenWidth-screenX;
		if(rightOffset > gp.worldWidth-worldX) {
			x = gp.screenWidth-(gp.worldWidth-worldX);
		}
		int bottomOffset = gp.screenHeight-screenY;
		if(bottomOffset>gp.worldHeight-worldY) {
			y = gp.screenHeight-(gp.worldHeight-worldY);
		}
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		}
        
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
    }
}
