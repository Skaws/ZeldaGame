package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX,worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX =0;
    public int solidAreaDefaultY =0;

    public void draw(Graphics2D g2, GamePanel gp){
        // the world coordinates are relative to the MAP as a whole [text file]
        // but the screen coordinates are where we draw it on the screen. 
        //Thus we subtract the coordinates of it in the world BY the coordinates of where the player is in the world 
        //(e.g. if tile is at 0,0 and player is at 500,500 then the position is -500,-500 relative to the PLAYER)
        //HOWEVER since the player is at the CENTRE of the screen, the player's screen coordinates are not 0,0
        // thus to get a tile's coordinates relative to the SCREEN we add the distance of the player from the screen's 0,0 (screenX and screenY)
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
        //to render the ENTIRE screen however is slow SO we get check if the current tile is WITHIN the screen
        //which is gonna be the player's X/Y coordinates + and - (the screen width/height) DIVIDED BY 2. ScreenX and Screen Y are already the screenwidth/height divided by 2
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                    
            g2.drawImage(image, screenX,screenY,gp.tileSize,gp.tileSize, null);
                }
    }
}
