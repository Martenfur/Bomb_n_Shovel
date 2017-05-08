/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import java.util.Random;
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
      buttonSpacing;
  
  Client client;
  
  /*
  0 - Main menu.
  1 - Waiting room.
  */
  int state=0;
  
  public Lobby()
  {
    super();
    
    buttonsAm=3;
    button_w=201;
    button_h=69;
    buttonSpacing=16;
    
    buttons=new Sprite[buttonsAm];
    
    
    for(int i=0; i<buttonsAm; i+=1)
    {buttons[i]=new Sprite(Spr.menu_buttons);}
    
    for(int xx=0; xx<Game.scr_w+128; xx+=128+48)
    {
      for(int yy=0; yy<Game.scr_h+128; yy+=128+48)
      {new Paper(xx+Mathe.irandom(-32,32),yy+Mathe.irandom(-32,32));}
    }
    System.out.println(Obj.objCount(Obj.oid.paper));
  }
  
  
  @Override
  public void STEP()
  {
    //MAIN MENU/////////////////////////////////////////////////////////////////
    if (state==0)
    {
      int bx=Game.scr_w/2,
          by=(Game.scr_h-(button_h+buttonSpacing)*buttonsAm+buttonSpacing)/2;
    
      int bPress=-1;
    
    
      if (Input.mbCheckRelease)
      {
        for(int i=0; i<buttonsAm; i+=1)
        {
          if (Mathe.pointInRectangle(Input.mouse_xgui,Input.mouse_ygui,bx-button_w/2,by,
                                                                       bx+button_w/2,by+button_h))
          {
            bPress=i;
            break;
          }
          by+=button_h+buttonSpacing;
        }
      }
    
      switch(bPress)
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
          state=1; //Going to lobby.
          client=new Client();
          break;
        }
      }
    }
    //MAIN MENU/////////////////////////////////////////////////////////////////
    
    if (state==1)
    {
      if (client.reader.isReceived())
      {
        System.out.println("Sup.");
        Cmd cmd=(Cmd)client.reader.read();
        if (cmd.cmp("generate"))
        {createNetworkGame(cmd);}
        Obj.objDestroy(this);
      }
      
    }
    
  }
  
  
  
  
  @Override
  public void DRAW_GUI()
  {
    Draw.setDepth(0);
    
    //MAIN MENU/////////////////////////////////////////////////////////////////
    if (state==0)
    {
      int bx=Game.scr_w/2,
          by=(Game.scr_h-(button_h+buttonSpacing)*buttonsAm+buttonSpacing)/2;
    
      for(int i=0; i<buttonsAm; i+=1)
      {
        Draw.drawSprite(buttons[i],i,bx,by+button_h/2);
       
        by+=button_h+buttonSpacing;
      }
    }    
    //MAIN MENU/////////////////////////////////////////////////////////////////
    
    if (state==1)
    {Draw.drawSprite(new Sprite(Spr.kitten),0,Game.scr_w/2,Game.scr_h/2,1,1,Game.currentTime*180);}
  }
  
  
  private boolean createLocalGame()
  {
    TurnManager turnManager=new TurnManager();
    turnManager.playerAdd(new LocalPlayer());
    turnManager.playerAdd(new LocalPlayer());
    
    long seed=new Random(System.nanoTime()).nextLong(); 
    
    Terrain terrain=new Terrain(seed,turnManager);
    
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
    
    new Terrain(seed,turnManager);

  }
  
}
