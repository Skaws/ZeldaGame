package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

//subclass of JPanel. This works as the game screen class
public class GamePanel  extends JPanel implements Runnable{
    //SCREEN SETTINGS
    final int originalTileSize = 16; //Default tile size is 16x16 pixels e.g. player char sprites, npc sprites

    //however this is VERY small on a regular monitor so let's scale them up photoshop style
    final int scale = 3; //means they'll be 3 times larger

    public final int tileSize = originalTileSize * scale;// so the actual tileSize is 48x48. Each tile is 48x48 pixels.

    //the screen will have a width of 16 tiles and a height of 12 tiles
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels (48 pixels wide for one tile * 16 total tiles)
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels (48 pixels height for one tile * 12 total tiles)

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    // create a new Keyhandler object
    KeyHandler keyH = new KeyHandler();
    //will be run 60 times per second to be 60fps
    Thread gameThread;
    public CollisionChecker colChecker = new CollisionChecker(this);
    // create a player class and pass this gamepanel into it
    public Player player = new Player(this,keyH);
    // create an array of objects on screen. for now it's a limit of 10 onscreen at once
    public SuperObject objArray[] = new SuperObject[10];
    // create an asset setter object to handle it all
    public AssetSetter aSetter = new AssetSetter(this);

    // set default player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        //set screensize
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        //enabling this improves game rendering performance
        this.setDoubleBuffered(true);

        // create a KeyHandler object to track keypresses (keyH)
        this.addKeyListener(keyH);
        // "Focuses" the GamePanel to recieve key inputs
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    // when the thread gameThread is called, this run method will be called
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount =0;
        // while the game thread exists
        while(gameThread!=null){

            // TLDR delta serves as a counter between frames (in the form of a fraction of a frame) and the second delta reaches 1 it means a frame has passed since the last frame
            currentTime=System.nanoTime();
            timer +=(currentTime-lastTime);


            // let's say the clock is counting in nano seconds. every time this thread is called, the time SINCE the last call is calculated and then turned into a fraction of the interval
            // in this case a fraction of a frame. This is then added to delta so that delta counts how long since the last frame, as a fraction of a frame
            delta+= (currentTime-lastTime)/ drawInterval;
            // set last to current so that it's ready for the NEXT call (as it represents the time of the last function call)
            lastTime=currentTime;
            // as its a fraction of a frame, this conditional asks if a frame or MORE's worth of time has passed, run the function
            if(delta >=1){
                // as the main game loop this has 2 functions
                // 1 UPDATE: update game info such as character positions
                update();
                // 2 DRAW: draw the screen with updated info (call paintComponent method via repaint)
                repaint();
                delta--;
                drawCount++;
            }
            //once the timer reaches 1 second, print how many frames have passed [as per the game's frame timekeeping system]
            if(timer >= 1000000000){
                System.out.println("FPS:" +drawCount);
                drawCount=0;
                timer=0;
            }

        }

        
    }
    
    public void update(){
        // move the player up, down left or right if the respective keys are pressed
        player.update(playerSpeed);
    }
    // built in method in java, standard methods to draw things on JPanel
    // The graphics parameter is a class with many functions to draw objects on screen
    public void paintComponent(Graphics g){
        // to use paint component u need to use super.paintcomponent to have the parent object (JPanel) run the method
        super.paintComponent(g);
        // convert Graphics into Graphics2D variable type
        Graphics2D g2 = (Graphics2D) g;
        // whichever gets drawn first will be the background (e.g tile is layer 0, player will be over it on layer 1)
        tileM.draw(g2);
        for(int i=0; i<objArray.length;i++){
            // if the object exists
            if(objArray[i]!=null){
                objArray[i].draw(g2, this);
            }
        }
        player.draw(g2);
    }
}
