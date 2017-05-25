/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pkg.engine.*;
import pkg.net.Client;
import pkg.net.Cmd;
import pkg.terrain.Terrain;
import pkg.turns.*;
/**
 *
 * @author gn.fur
 */
public class Lobby extends GameObject
{
  Sprite[] buttons;
  int buttonsAm,
      button_w,button_h,
      buttonSpacing,
      buttonBackSize;
  
  Client client;
  
  /*
  0 - Main menu.
  1 - Waiting room.
  */
  int    state=     0,
         gamemode= -1,
         connectAl=-1;
  double cam_y=     0,
         cam_ytar=  0;
  
  boolean timerEn;
  
  boolean replayAvailable;
  int replayCheck;
  
  public Lobby()
  {
    super();
    System.out.println("Lobby created!");
    
    buttonsAm=4;
    button_w=201;
    button_h=69;
    buttonSpacing=16;
    buttonBackSize=94;
    
    buttons=new Sprite[buttonsAm];
    
    for(int i=0; i<buttonsAm; i+=1)
    {buttons[i]=new Sprite(Spr.menu_buttons);}
    
    //Background paper.
    for(int xx=0; xx<Game.scr_w+128; xx+=128+48)
    {
      for(int yy=-Game.scr_h; yy<Game.scr_h+128; yy+=128+48)
      {new Paper(xx+Mathe.irandom(-32,32),yy+Mathe.irandom(-32,32));}
    }
    //Background paper.
    
    timerEn=false;
    
    replayAvailable=false;
    replayCheck=30;
  }
  
  
  @Override
  public void STEP()
  {
    
    if (replayCheck>0)
    {
      replayCheck-=1;
      File file=new File("C:\\\\D\\log.txt");
      replayAvailable=file.exists() && file.length()!=0;
    }
    
    //MAIN MENU/////////////////////////////////////////////////////////////////
    if (state==0)
    {
      int bx=Game.scr_w/2,
          by=(Game.scr_h-(button_h+buttonSpacing)*buttonsAm+buttonSpacing)/2;
    
    
      if (Input.mbCheckRelease && gamemode==-1)
      {
        for(int i=0; i<buttonsAm; i+=1)
        {
          if (Mathe.pointInRectangle(Input.mouse_xgui,Input.mouse_ygui,bx-button_w/2,by,
                                                                       bx+button_w/2,by+button_h))
          {
            cam_ytar=-Game.scr_h;
            gamemode=i;
            connectAl=90;
            break;
          }
          by+=button_h+buttonSpacing;
        }
        
        //Timer toggle.
        if (Mathe.pointInRectangle(Input.mouse_xgui,Input.mouse_ygui,8,   Camera.scr_h-64-8-cam_y,
                                                                     8+64,Camera.scr_h-64-8-cam_y+64))
        {timerEn=!timerEn;} 
        //Timer toggle.
        
        //Replay button.
        if (replayAvailable && Mathe.pointInRectangle(Input.mouse_xgui,Input.mouse_ygui,16+64,   Camera.scr_h-64-8-cam_y,
                                                                                        16+64+64,Camera.scr_h-64-8-cam_y+64))
        {
          cam_ytar=-Game.scr_h;
          gamemode=4;
        }
        //Replay button.
      }
    }
    //MAIN MENU/////////////////////////////////////////////////////////////////
    
    //LOBBY/////////////////////////////////////////////////////////////////////
    if (state==1)
    {
      if (connectAl>-1)
      {connectAl-=1;}
      
      if (connectAl==0)
      {
        client=new Client();
        if (!client.connected)
        {
          client=null;
          state=0;
          cam_ytar=0;
        }
      }
      
      if (client!=null && client.reader.isReceived())
      {
        Cmd cmd=(Cmd)client.reader.read();
        if (cmd.cmp("generate"))
        {createNetworkGame(cmd);}
        for(ObjIter it=new ObjIter(Obj.oid.paper); it.end(); it.inc())
        {((Paper)it.get()).disappear=true;}
        Obj.objDestroy(this);
      }
      
      if (Input.mbCheckRelease)
      {
        if (Mathe.pointInRectangle(Input.mouse_xgui,Input.mouse_ygui,0,-buttonBackSize-3-16-cam_y,
                                                                     buttonBackSize,  -3-16-cam_y))
        {
          cam_ytar=0;
          state=0;
          if (client!=null)
          {
            client.reader.running=false;
            client=null;
          }
        }
      }
    }
    //LOBBY/////////////////////////////////////////////////////////////////////
    
    if (cam_y!=cam_ytar)
    {
      double dy;
      if (Math.abs(cam_ytar-cam_y)>4)
      {dy=(cam_ytar-cam_y)/6.0;}
      else
      {dy=cam_ytar-cam_y;}
      
      for(ObjIter it=new ObjIter(Obj.oid.paper); it.end(); it.inc())
      {((Paper)it.get()).y-=dy;}
      
      cam_y+=dy;
      if (cam_y==cam_ytar)
      {
        if (cam_y==0)
        {gamemode=-1;}
        //CAMERA STOPPED
        switch(gamemode)
        {
          case 0:
          {
            createLocalGame();
            Obj.objDestroy(this);
            for(ObjIter it=new ObjIter(Obj.oid.paper); it.end(); it.inc())
            {((Paper)it.get()).disappear=true;}
            break;
          }
          case 1:
          {
            state=1;
            connectAl=60*4;
            break;
          }
          case 2:
          {
            createBotGame();
            Obj.objDestroy(this);
            for(ObjIter it=new ObjIter(Obj.oid.paper); it.end(); it.inc())
            {((Paper)it.get()).disappear=true;}
            break;
          }
          case 3:
          {
            createAutomaticGame();
            Obj.objDestroy(this);
            for(ObjIter it=new ObjIter(Obj.oid.paper); it.end(); it.inc())
            {((Paper)it.get()).disappear=true;}
            break;
          }
          case 4:
          {
            createReplayGame();
            Obj.objDestroy(this);
            for(ObjIter it=new ObjIter(Obj.oid.paper); it.end(); it.inc())
            {((Paper)it.get()).disappear=true;}
            break;
          }
        }  
        //CAMERA STOPPED
      }
    }
    
  }
  
  
  
  
  @Override
  public void DRAW_GUI()
  {
    Draw.setDepth(100);
    Draw.setColor(Color.rgb(222,238,214));
    Draw.drawRectangle(new Rectangle(),0,0,Camera.scr_w,Camera.scr_h,false);
    
    Draw.setDepth(0); 
    //MAIN MENU/////////////////////////////////////////////////////////////////
    if (state==0)
    {
      int bx=Game.scr_w/2,
          by=(Game.scr_h-(button_h+buttonSpacing)*buttonsAm+buttonSpacing)/2;
    
      for(int i=0; i<buttonsAm; i+=1)
      {
        Draw.drawSprite(buttons[i],i,bx,by+button_h/2-cam_y);
        by+=button_h+buttonSpacing;
      }
    
      Draw.drawSprite(new Sprite(Spr.timer_buttons),(timerEn)?1:0,8,Camera.scr_h-64-8-cam_y);
      if (replayAvailable)
      {Draw.drawSprite(new Sprite(Spr.timer_buttons),2,16+64,Camera.scr_h-64-8-cam_y);}
    }    
    //MAIN MENU/////////////////////////////////////////////////////////////////
    
    if (gamemode==1)
    {
      Draw.drawSprite(new Sprite(Spr.kitten),0,Camera.scr_w/2,Camera.scr_h/2-Camera.scr_h-cam_y,1,1,Game.currentTime*180);
      Draw.drawSprite(new Sprite(Spr.button_back),0,-buttonBackSize-3-16-cam_y);
    }
  }
  
  
  
  
  
  
  
  private boolean createLocalGame()
  {
    TurnManager turnManager=new TurnManager();
    turnManager.playerAdd(new LocalPlayer());
    turnManager.playerAdd(new LocalPlayer());
    
    long seed=new Random(System.nanoTime()).nextLong(); 
    
    Terrain terrain=new Terrain(seed,turnManager,timerEn);
    
    return true;
  }
  
  private void createNetworkGame(Cmd cmd)
  {
    long seed=(long)cmd.get(0);
      
    TurnManager turnManager=new TurnManager();
    if (cmd.get(1)==0)
    { 
      turnManager.playerAdd(new LocalPlayer(client));
      turnManager.playerAdd(new NetworkPlayer(client));
    }
    else
    {
      turnManager.playerAdd(new NetworkPlayer(client));
      turnManager.playerAdd(new LocalPlayer(client));
    }
    
    Terrain terrain=new Terrain(seed,turnManager,false);
    
  }
  
  private void createBotGame()
  {
    TurnManager turnManager=new TurnManager();
    turnManager.playerAdd(new LocalPlayer());
    turnManager.playerAdd(new BotPlayer());
    
    long seed=new Random(System.nanoTime()).nextLong(); 
    
    Terrain terrain=new Terrain(seed,turnManager,timerEn);
  }
  
  private void createAutomaticGame()
  {
    TurnManager turnManager=new TurnManager();
    turnManager.playerAdd(new BotPlayer());
    turnManager.playerAdd(new BotPlayer());
    
    long seed=new Random(System.nanoTime()).nextLong(); 
    
    Terrain terrain=new Terrain(seed,turnManager,false);
  }
  
  private void createReplayGame()
  {
    Logger logger=new Logger("C:\\\\D\\log.txt",1);
    ArrayList<Cmd> commands=logger.read();
            
    TurnManager turnManager=new TurnManager();
    turnManager.playerAdd(new ReplayPlayer(commands));
    turnManager.playerAdd(new ReplayPlayer(commands));
    
    long seed=(long)logger.seed; 
    
    Terrain terrain=new Terrain(seed,turnManager,false);
  }
  
}
