package jamiebalfour.zpe.ui;

import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.core.exceptions.BreakPointHalt;
import jamiebalfour.zpe.core.exceptions.ExitHalt;
import jamiebalfour.zpe.core.exceptions.ZPERuntimeException;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.ui.core.ZPEUIItemObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ZPEUIActionable extends ZPEStructure{

  static ArrayList<ZPEUIFrameObject> FRAMES = new ArrayList<>();
  protected String[] suitableActions = new String[]{};
  protected String id = "";
  protected String name;

  private final HashMap<String, ZPEFunction> actions = new HashMap<>();

  public ZPEUIActionable(ZPERuntimeEnvironment z, ZPEPropertyWrapper parent, String name) {
    super(z, parent, name);

    addNativeMethod("on", new ZPEUIItemObject.on_Command());
  }

  public class on_Command implements ZPEObjectNativeMethod {


    @Override
    public String[] getParameterNames() {
      return new String[]{"action", "method"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string", "function"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      if (parameters.get("method") instanceof ZPEFunction) {
        ZPEFunction zf = (ZPEFunction) parameters.get("method");
        addAction(parameters.get("action").toString(), zf);
      }


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "on";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }


  }

  public void addAction(String action, ZPEFunction f) {
    boolean found = false;
    for(int i = 0; i < suitableActions.length; i++) {
      if(suitableActions[i].equals(action)) {
        found = true;
      }
    }
    if(!found) {
      ZPECore.printError("Action " + action + " not found on type " + name);
    } else{
      actions.put(action, f);
    }

  }



  public void respondToAction(String a) {
    if (actions.containsKey(a)) {
      ZPEFunction f = actions.get(a);
      try{
        ZPEKit.runFunction(f, new ZPEType[0]);

      } catch (ExitHalt e) {
        //Ignore
        if(FRAMES != null){
          for(ZPEUIFrameObject frm : FRAMES){
            frm.frame.dispose();
          }
        }
      } catch (BreakPointHalt e) {
        //Ignore

      } catch (ZPERuntimeException e){
        System.err.println(e);
        //throw e;
      }
    }
  }


  public void setSuitableActions(String[] suitableActions) {
    this.suitableActions = suitableActions;
  }
}
