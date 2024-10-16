package entity;

import main.KeyHandler;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    public int hasKey=0;

    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        // make the player Hurtbox smaller than the sprite as a 32x32 rectangle in the middle bottom of the sprite
        solidArea = new Rectangle(12, 12, 24, 30);
        // record the default values just in case they can be changed later
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

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
            moving=true;
        }
        else if(keyH.downPressed==true){
            direction="down";
            moving=true;
        }
        else if(keyH.leftPressed==true){
            direction="left";
            moving=true;
        }
        else if(keyH.rightPressed==true){
            direction="right";
            moving=true;
        }
        
        if(moving==true){
            // sprite gets updated every 12 frames

            // CHECK TILE COLLISION
            collisionOn=false;
            gp.colChecker.checkTile(this);
            
            // CHECK OBJECT COLLISION
            int objIndex = gp.colChecker.checkObject(this, true);
            pickUpObject(objIndex);
            // IF COLLISION IS FALSE PLAYER CAN MOVE
            if(collisionOn==false){
                switch(direction){
                case"up":
                    worldY-=speed;
                    break;
                case"down":
                    worldY+=speed;
                    break;
                case"left":
                    worldX-=speed;
                    break;
                case"right":
                    worldX+=speed;
                    break;
                }
            }

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

    public void pickUpObject(int i){
        // if it's an actual object, find out which kind and go from there
        if(i!=999){
            String objName = gp.objArray[i].name;
            switch (objName) {
                case "Key":
                
                gp.playSE(1);
                hasKey++;
                gp.objArray[i]=null;
                gp.ui.showMessage("You got a key!");
                System.out.println("Player has: "+hasKey+ " keys!");
                    break;
            
                case"Door":
                if(hasKey>0){
                    gp.playSE(3);
                    gp.objArray[i]=null;
                    hasKey--;
                    gp.ui.showMessage("You opened a Door!");
                }
                else{
                    gp.ui.showMessage("You need a key!");
                }
                System.out.println("Key used. \nPlayer has: "+hasKey+ " keys!");
                    break;

                case "Boots":
                gp.playSE(4);
                System.out.println("Movement Speed Increase!");
                speed+=2;
                gp.objArray[i]=null;
                
                gp.ui.showMessage("Speed Up!");
                
                break;
                case "Chest":
                gp.ui.gameFinished=true;
                gp.stopMusic();
                gp.playSE(5);
                break;
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
