package entity;

import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import main.GamePanel;
public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    boolean moving;
    
    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(new FileInputStream("res/player/link_up_1.png"));
            up2 = ImageIO.read(new FileInputStream("res/player/link_up_2.png"));
            down1 = ImageIO.read(new FileInputStream("res/player/link_down_1.png"));
            down2 = ImageIO.read(new FileInputStream("res/player/link_down_2.png"));
            left1 = ImageIO.read(new FileInputStream("res//player/link_left_1.png"));
            left2 = ImageIO.read(new FileInputStream("res//player/link_left_2.png"));
            right1 = ImageIO.read(new FileInputStream("res//player/link_right_1.png"));
            right2 = ImageIO.read(new FileInputStream("res//player/link_right_2.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(int Player){
        moving=false;
        if(keyH.upPressed==true){
            direction="up";
            worldY-=speed;
            moving=true;
        }
        else if(keyH.downPressed==true){
            direction="down";
            worldY+=speed;
            moving=true;
        }
        else if(keyH.leftPressed==true){
            direction="left";
            worldX-=speed;
            moving=true;
        }
        else if(keyH.rightPressed==true){
            direction="right";
            worldX+=speed;
            moving=true;
        }
        if(moving==true){
            // sprite gets updated every 12 frames
            spriteCounter++;
            if(spriteCounter>12){
                if(spriteNum==1){
                    spriteNum=2;
                }
                else if(spriteNum==2){
                    spriteNum=1;
                }
                spriteCounter=0;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch (direction) {
            case "up":
            if(spriteNum == 1){
                image=up1;
            }
            if(spriteNum == 2){
                image=up2;
            }
            break;
            case "down":
            if(spriteNum == 1){
                image=down1;
            }
            if(spriteNum == 2){
                image=down2;
            }
                break;
            case "left":
            
            if(spriteNum == 1){
                image=left1;
            }
            if(spriteNum == 2){
                image=left2;
            }
                break;
            case "right":
            
            if(spriteNum == 1){
                image=right1;
            }
            if(spriteNum == 2){
                image=right2;
            }
                break;
        }
        // player is ALWAYS drawn at the centre of the screen, world moves AROUND him
        g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize,null);
    }
}
