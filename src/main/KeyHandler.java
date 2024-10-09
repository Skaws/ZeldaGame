package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // get the char value of the key pressed and store as an int
        int code = e.getKeyCode();
        // if the W key is pressed
        if(code== KeyEvent.VK_W){
            upPressed=true;
        }
        // if the W key is pressed
        if(code== KeyEvent.VK_S){
            downPressed=true;
        }
        // if the W key is pressed
        if(code== KeyEvent.VK_A){
            leftPressed=true;
        }
        // if the W key is pressed
        if(code== KeyEvent.VK_D){
            rightPressed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         // get the char value of the key pressed and store as an int
        int code = e.getKeyCode();
        // if the W key is released
        if(code== KeyEvent.VK_W){
            upPressed=false;
        }
        // if the A key is released
        if(code== KeyEvent.VK_S){
            downPressed=false;
        }
        // if the S key is released
        if(code== KeyEvent.VK_A){
            leftPressed=false;
        }
        // if the D key is released
        if(code== KeyEvent.VK_D){
            rightPressed=false;
        }
    }

}
