package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author James Andrews
 * @author Vimean Sam
 */
public class GameState implements Runnable{
  private boolean running = false;
  private Thread thread;
  Graphics2D gr2d;
  BufferStrategy buffer;
  GameFrame frame;
  BufferedImage level_background;
  Key keylistener;

  private int playerX = 500;
  private int ball_x = 500;
  private int ball_y = 400;
  private int ball_xVel = -1;
  private int ball_yVel = 2;
  private int speed = 2;
  private int angle = 0;
  private boolean collide, rhsCollision, bossisHit,wingame, endgame = false;
  private boolean firsthit = true;
  private int damage = 10;
  private int score = 0;

  Player player;
  Ball ball;
  Rectangle prec, brec;
  Map map;
  Boss lvlBoss = new Boss();
  Level level = new Level();


  public void init() {
    this.frame = new GameFrame(1000, 600);
    this.frame.createWindow();
    this.level_background = ImageLoader.loadImage(this.level.levelBackgroundChange());
    this.keylistener = new Key();
    this.frame.addKeyListener(keylistener);
    this.player = new Player();
    this.ball = new Ball();
    this.map = new Map(this.lvlBoss.getBoss());
    this.map.loadMap(this.level.levelChange());
    this.player.setLives(3);
    this.lvlBoss.setHP(100);
  }
  public void restart() {
    playerX = 500;
    ball_x = 500;
    ball_y = 400;
    ball_xVel = -1;
    ball_yVel = 2;
    speed = 2;
    angle = 0;
    firsthit = true;
  }

  public void gameLogic() {
      if(player.getLives() == 0) {
          endgame = true;
      }
  }

  public void tick() {
    gameLogic();

    if(!endgame){
    this.keylistener.tick();
    rotation();
    ballDeflection();
    collisionCheck();
    ball_x+=speed*ball_xVel;
    ball_y+=speed*ball_yVel;

    if(this.keylistener.left){
        playerX-=10;
        checkBoundary();
    }

    if(this.keylistener.right){
        playerX+=10;
        checkBoundary();
      }
    }
  }

  public void checkBoundary() {
      if(playerX <= 0){
          playerX = 0;
      }
      if(playerX >= 820){
          playerX = 820;
      }
  }

  public void ballDeflection() {
      if(ball_x < 0){
          ball_xVel = -ball_xVel;
      }
      if(ball_y < 0){
          ball_yVel = -ball_yVel;
      }
      if(ball_x > 975){
          ball_xVel = -ball_xVel;
      }
      if(ball_y > 580){
          player.setLives(player.getLives()-1);
          restart();
      }
  }

  public void rotation() {
      if(ball_xVel < 0){
          angle-=10;
      }
      if(ball_xVel > 0){
          angle+=10;
      }
  }

  public void collisionCheck() {
      prec = new Rectangle(playerX, 550, player.bar.getWidth(), player.bar.getHeight());
      brec = new Rectangle(ball_x, ball_y, ball.ball.getWidth(), ball.ball.getHeight());
      Rectangle rhs = new Rectangle(playerX+player.bar.getWidth()/2, 550, player.bar.getWidth()/2, player.bar.getHeight());
      if(prec.intersects(brec)){
          if(!collide){
              collide = !collide;
              ball_yVel = -ball_yVel;
              ball_xVel = -1;
              if(!rhsCollision){
                if(rhs.intersects(brec)){
                   ball_xVel = 1;
                   rhsCollision = !rhsCollision;
                }
              }
          }
      }
      if(collide && !prec.intersects(brec)){
          collide = !collide;
      }

      if(rhsCollision && !rhs.intersects(brec)){
          rhsCollision = !rhsCollision;
      }

      for(int i = 0; i < map.bricks.size(); i++){
          if(brec.intersects(map.bricks.get(i))){
            Rectangle temp = map.bricks.get(i);
            int temp_x = (int)temp.getX()/100;
            int temp_y = (int)temp.getY()/30;
            removeBricks(temp_x, temp_y);
            map.bricks.remove(i);
            score+=5;
            if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
                ball_xVel = -ball_xVel;
                break;
            }else{
                ball_yVel = -ball_yVel;
                break;
            }
          }
      }

       for(int i = 0; i < map.bossRec.size(); i++){
          if(brec.intersects(map.bossRec.get(i))){
              if(!bossisHit){
                bossisHit = !bossisHit;
                speed = 4;
                Rectangle temp = map.bossRec.get(i);
                int temp_x = (int)temp.getX()/100;
                int temp_y = (int)temp.getY()/30;
                if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
                   ball_xVel = -ball_xVel;
                   lvlBoss.setHP(lvlBoss.getHP()-damage);
                }else{
                   ball_yVel = -ball_yVel;
                   lvlBoss.setHP(lvlBoss.getHP()-damage);
                }
                if(this.lvlBoss.getHP() <= 0){
                  try {
                    this.restart();
                    this.level.levelNum++;
                    this.map.loadMap(this.level.levelChange());
                    this.map.removeAllRectangles();
                    this.ball.ball = ImageLoader.loadImage("/Assets/ball.png");
                    damage = 10;
                    this.lvlBoss.newBoss();
                    this.lvlBoss.resetHP();
                    this.level_background = ImageLoader.loadImage(this.level.levelBackgroundChange());
                    this.map.boss = this.lvlBoss.getBoss();
                    score += 100;
                  }catch(Exception e) {
                    endgame = true;
                    wingame = true;
                    System.out.println(e.getMessage());
                  }
                }
              }
          }
          if(bossisHit){
              bossisHit = !bossisHit;
          }
      }

      for(int i = 0; i < map.unbreakableblock.size(); i++) {
          if(brec.intersects(map.unbreakableblock.get(i))) {
            Rectangle temp = map.unbreakableblock.get(i);
            int temp_x = (int)temp.getX()/100;
            int temp_y = (int)temp.getY()/30;
            if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
                ball_xVel = -ball_xVel;
                break;
            }else{
                ball_yVel = -ball_yVel;
                break;
            }
          }
      }

      for(int i = 0; i < map.slowblock.size(); i++) {
          if(brec.intersects(map.slowblock.get(i))){
            speed = 1;
            Rectangle temp = map.slowblock.get(i);
            int temp_x = (int)temp.getX()/100;
            int temp_y = (int)temp.getY()/30;
            removeBricks(temp_x, temp_y);
            map.slowblock.remove(i);
            score+=10;
            if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
                ball_xVel = -ball_xVel;
                break;
            }else{
                ball_yVel = -ball_yVel;
                break;
            }
          }
      }

      for(int i = 0; i < map.speedblock.size(); i++){
          if(brec.intersects(map.speedblock.get(i))){
            if(speed < 4){
                speed = 3;
            }
            Rectangle temp = map.speedblock.get(i);
            int temp_x = (int)temp.getX()/100;
            int temp_y = (int)temp.getY()/30;
            removeBricks(temp_x, temp_y);
            map.speedblock.remove(i);
            score+=10;
            if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
                ball_xVel = -ball_xVel;
                break;
            }else{
                ball_yVel = -ball_yVel;
                break;
            }
          }
      }

      for(int i = 0; i < map.lifeblock.size(); i++) {
          if(brec.intersects(map.lifeblock.get(i))) {
            Rectangle temp = map.lifeblock.get(i);
            int temp_x = (int)temp.getX()/100;
            int temp_y = (int)temp.getY()/30;
            removeBricks(temp_x, temp_y);
            map.lifeblock.remove(i);
            player.setLives(player.getLives()+1);
            score+=10;
            if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
                ball_xVel = -ball_xVel;
                break;
            }else{
                ball_yVel = -ball_yVel;
                break;
            }
          }
      }

      for(int i = 0; i < map.spikeblock.size(); i++) {
          if(brec.intersects(map.spikeblock.get(i))) {
            damage = 20;
            Rectangle temp = map.spikeblock.get(i);
            int temp_x = (int)temp.getX()/100;
            int temp_y = (int)temp.getY()/30;
            removeBricks(temp_x, temp_y);
            map.spikeblock.remove(i);
            score+=10;
            ball.ball = ImageLoader.loadImage("/Assets/spikeball.png");
            if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
                ball_xVel = -ball_xVel;
                break;
            }else{
                ball_yVel = -ball_yVel;
                break;
            }
          }
      }

      for(int i = 0; i < map.questionblock.size(); i++) {
          if(brec.intersects(map.questionblock.get(i))) {
              Rectangle temp = map.questionblock.get(i);
              int temp_x = (int)temp.getX()/100;
              int temp_y = (int)temp.getY()/30;
              removeBricks(temp_x, temp_y);
              map.questionblock.remove(i);
              firsthit = false;
              Random rand = new Random();
              int tilenum = 4+rand.nextInt(4);
              map.tiles[temp_y][temp_x] = tilenum;
              if(tilenum == 4){
                map.slowblock.add(new Rectangle((int)temp.getX(),(int)temp.getY(), 100, 30));
              }
              if(tilenum == 5){
                map.speedblock.add(new Rectangle((int)temp.getX(),(int)temp.getY(), 100, 30));
              }
              if(tilenum == 6){
                map.lifeblock.add(new Rectangle((int)temp.getX(),(int)temp.getY(), 100, 30));
              }
              if(tilenum == 7){
                map.spikeblock.add(new Rectangle((int)temp.getX(),(int)temp.getY(), 100, 30));
              }

          if(ball_x <= temp.getX() || ball_x >= temp.getX()+temp.getWidth()){
              ball_xVel = -ball_xVel;
              break;
          }else{
              ball_yVel = -ball_yVel;
              break;
          }
         }
      }
  }

  public void removeBricks(int x, int y){
      map.tiles[y][x] = 0;
  }

  public void render() {
    this.buffer = this.frame.returnCanvas().getBufferStrategy();

    if(this.buffer == null) {
      this.frame.returnCanvas().createBufferStrategy(3);
      return;
    }

    gr2d = (Graphics2D) buffer.getDrawGraphics();
    gr2d.clearRect(0, 0, 1000, 600);
    gr2d.drawImage(level_background, 0, 0, 1000, 600, null);
    map.renderMap(gr2d);
    player.updatePosition(playerX, 550);
    player.render(gr2d);
    ball.updatePosition(ball_x, ball_y, angle);
    ball.render(gr2d);
    Color bossStatus = Color.GREEN;
    if(lvlBoss.getHP() >= 50 && lvlBoss.getHP() < 70){
        bossStatus = Color.ORANGE;
    }
    if(lvlBoss.getHP() >= 0 && lvlBoss.getHP() < 50){
        bossStatus = Color.RED;
    }
    gr2d.setColor(Color.white);
    gr2d.setFont(new Font(gr2d.getFont().getFontName(), Font.PLAIN, 30));
    gr2d.drawString("Boss: ", 850, 580);
    gr2d.setColor(bossStatus);
    gr2d.drawString(Integer.toString(lvlBoss.getHP()), 930, 580);

    gr2d.setColor(Color.white);
    gr2d.setFont(new Font(gr2d.getFont().getFontName(), Font.PLAIN, 30));
    gr2d.drawString("Lives: ", 15, 580);
    gr2d.drawString(Integer.toString(player.getLives()), 100, 580);

    frame.setScore(score);

    if(endgame) {
      gr2d.setColor(Color.BLACK);
      gr2d.fillRect(350, 200, 350, 150);
      if(wingame){
        gr2d.setColor(Color.YELLOW);
        gr2d.setFont(new Font(gr2d.getFont().getFontName(), Font.PLAIN, 40));
        gr2d.drawString("YOU WIN!!", 420, 250);
      }else{
        gr2d.setColor(Color.RED);
        gr2d.setFont(new Font(gr2d.getFont().getFontName(), Font.PLAIN, 40));
        gr2d.drawString("GAME OVER", 420, 250);
      }
      gr2d.setColor(Color.white);
      gr2d.setFont(new Font(gr2d.getFont().getFontName(), Font.PLAIN, 20));
      gr2d.drawString("Final Score: ", 420, 290);
      gr2d.drawString(Integer.toString(score), 530, 290);
    }
    buffer.show();
    gr2d.dispose();
  }

  public synchronized void start() {
    if(running) {
      return;
    }
    this.running = true;
    this.thread = new Thread(this);
    this.thread.start();
  }

  public synchronized void stop() {
    if(!running) {
      return;
    }
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public void run() {
    init();
    int fps = 60;

    double timePerTick = 1000000000 / fps;
    double delta = 0;
    long currentTime;
    long pastTime = System.nanoTime();
    long timer = 0;
    int ticks = 0;

    while(running) {
      currentTime = System.nanoTime();
      delta += (currentTime - pastTime) / timePerTick;
      timer += (currentTime - pastTime);
      pastTime = currentTime;

    if(delta >= 1) {
    	tick();
    	render();
    	ticks++;
    	delta--;
    }
    if(timer >= 1000000000) {
      ticks = 0;
      timer = 0;
    }
    }
    stop();
  }

  public static void main(String[] args) {
      new GameState().start();
  }
}