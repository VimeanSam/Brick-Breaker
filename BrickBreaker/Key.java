/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrickBreaker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author James Andrews
 * @author Vimean Sam
 */
public class Key implements KeyListener{
    boolean [] keys;
    boolean left, right;

    public Key() {
        this.keys = new boolean[256];
    }

    public void tick() {
        this.left = keys[KeyEvent.VK_LEFT];
        this.right = keys[KeyEvent.VK_RIGHT];
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        keys[ke.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
    }
}
