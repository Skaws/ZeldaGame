package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        //get the left entity border by getting it's world coordinates and then adding the offset (E.g. for player the offset is 8, 16)
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        // same as the value of left BUT u add the width of the object to get the right border coordinate
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        //find out where it is on the column/row tile GRID [rather than coordinates]
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1,tileNum2;

        switch(entity.direction){
            case "up":
                //calculate where the tile it's moving to is [IN TILE FORM, calculate the coordinates difference and then BAM divide by pixel size to get the tile location]
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                // store the number/location of tile colliding with the top left and top right of the sprites in tileNum1 and tileNum2 respectively
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                // if this tile is a collidable tile
                if((gp.tileM.tile[tileNum1].collision == true)||(gp.tileM.tile[tileNum2].collision == true)){
                    System.out.println("Collision detected at: "+entity.worldX +" and " +entity.worldY);
                    // make the entity collidable too
                    entity.collisionOn=true;
                }
                break;

            case "down":
            //calculate where the tile it's moving to is [IN TILE FORM, calculate the coordinates difference and then BAM divide by pixel size to get the tile location]
            entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
            // store the number/location of tile colliding with the bottom left and bottom right of the sprites in tileNum1 and tileNum2 respectively
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            // if this tile is a collidable tile
            if((gp.tileM.tile[tileNum1].collision == true)||(gp.tileM.tile[tileNum2].collision == true)){
                System.out.println("Collision detected at: "+entity.worldX +" and " +entity.worldY);
                // make the entity collidable too
                entity.collisionOn=true;
            }
            break;  

            case"left":
            //calculate where the tile it's moving to is [IN TILE FORM, calculate the coordinates difference and then BAM divide by pixel size to get the tile location]
            entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
            // store the number/location of tile colliding with the top left and bottom left of the sprites in tileNum1 and tileNum2 respectively
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            // if this tile is a collidable tile
            if((gp.tileM.tile[tileNum1].collision == true)||(gp.tileM.tile[tileNum2].collision == true)){
                System.out.println("Collision detected at: "+entity.worldX +" and " +entity.worldY);
                // make the entity collidable too
                entity.collisionOn=true;
            }
            break;

            case"right":
            //calculate where the tile it's moving to is [IN TILE FORM, calculate the coordinates difference and then BAM divide by pixel size to get the tile location]
            entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
            // store the number/location of tile colliding with the top right and bottom right of the sprites in tileNum1 and tileNum2 respectively
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            // if this tile is a collidable tile
            if((gp.tileM.tile[tileNum1].collision == true)||(gp.tileM.tile[tileNum2].collision == true)){
                System.out.println("Collision detected at: "+entity.worldX +" and " +entity.worldY);
                // make the entity collidable too
                entity.collisionOn=true;
            }
            break;
        }
    }
}
