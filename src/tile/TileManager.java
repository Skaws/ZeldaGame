package tile;
import main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.awt.Graphics2D;
public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	// create a 2D int array
	public int mapTileNum[][];
	public TileManager(GamePanel gp){
		this.gp=gp;
		// create new types of tiles
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/world01.txt");
	}

	public void getTileImage(){
		try{
			tile[0] = new Tile();
			tile[0].image=ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[0].collision=false;
			
			tile[1] = new Tile();
			tile[1].image=ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision=true;

			
			tile[2] = new Tile();
			tile[2].image=ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision=true;

			tile[3] = new Tile();
			tile[3].image=ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			tile[0].collision=false;
			
			tile[4] = new Tile();
			tile[4].image=ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision=true;
			
			tile[5] = new Tile();
			tile[5].image=ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			tile[0].collision=false;
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath){
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			while(col<gp.maxWorldCol && row<gp.maxWorldRow){
				// read a single line from the file
				String line = br.readLine();
				while(col < gp.maxWorldCol){
					// split the line into a string array of individual numbers
					String numbers[] = line.split(" ");
					// select the current index in the array of string numbers and turn it into an int number
					int num = Integer.parseInt(numbers[col]);
					// store the selected number in the text file in the mapTileNum Array
					mapTileNum[col][row] = num;
					col++;
				}
				if(col==gp.maxWorldCol){
					col=0;
					row++;
				}
				
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void draw(Graphics2D g2){
		int worldCol = 0;
		int worldRow = 0;

		// loop through the tile drawing process via counting column/row placing
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
			// find WHICH tile element ID is currently selected to be drawn (e.g. wall is 1, water is 2, grass is 0)
			// then draw it
			int TileNum = mapTileNum[worldCol][worldRow];
			// get the exact X/Y coordinate we're at by multiplying the current column/row index by the size of a tile (48) 
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
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
						
				g2.drawImage(tile[TileNum].image, screenX,screenY,gp.tileSize,gp.tileSize, null);
					}

			worldCol++;
			// if we've reached the end of a column (right hand of the screen), reset and then go down one row
			if(worldCol==gp.maxWorldCol){
				worldCol=0;
				worldRow++;
			}
		}
	}
}
