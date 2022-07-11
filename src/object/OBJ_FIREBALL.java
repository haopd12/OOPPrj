package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_FIREBALL extends Projectile {
	
	GamePanel gp;
	
	public OBJ_FIREBALL(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 6;
		maxLife = 80;
		life = maxLife;
		getImage();

	}
	public void getImage()
	{
		down1 = setup("/monster/fireball", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/fireball1", gp.tileSize, gp.tileSize);
		up1 = setup("/monster/fireball", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/fireball1", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/fireball", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/fireball1", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/fireball", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/fireball1", gp.tileSize, gp.tileSize);
	}
}
