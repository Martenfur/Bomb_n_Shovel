package pkg.turns;

import java.util.ArrayList;
import pkg.Peasant;
import pkg.foxoft.bombnshovel.net.Cmd;

/**
 * 
 */
public class ReplayPlayer extends Player
{
  static int cmdCur;
  
  ArrayList<Cmd> cmdList;
  
  public ReplayPlayer(ArrayList<Cmd> cmdList_arg)
  {
    cmdList=cmdList_arg;
    cmdCur=0;
  }
  
  @Override
  public void STEP()
  {
    super.STEP();
    
    if (isMyTurn() && !peasants.isEmpty() && peasants.size()>peasantCur)
    {
      if (cmdCur<cmdList.size())
      {
        Peasant pCur=peasants.get(peasantCur);
        if (!pCur.moving)
        {
          pCur.command=cmdList.get(cmdCur);
          cmdCur+=1;
        }
      }
    }
  }
}
