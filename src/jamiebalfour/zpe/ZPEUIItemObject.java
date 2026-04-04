package jamiebalfour.zpe;

/*
 * To add a button, a button object is added
 */

import jamiebalfour.generic.JBBinarySearchTree;
import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.core.exceptions.BreakPointHalt;
import jamiebalfour.zpe.core.exceptions.ExitHalt;
import jamiebalfour.zpe.core.exceptions.ZPERuntimeException;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.types.ZPEString;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// -------- UI component base wrapper --------
public abstract class ZPEUIItemObject extends ZPEStructure {
  protected JComponent component;
  protected ZPEUIFrameObject ownerObj;
  String id = "";
  String name;

  String[] suitableActions = new String[]{};


  private final HashMap<String, ZPEFunction> actions = new HashMap<>();

  public ZPEUIItemObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, String name, ZPEUIFrameObject obj) {
    super(z, p, name);
    this.name = name;
    addNativeMethod("set_id", new set_id_Command());
    addNativeMethod("get_id", new get_id_Command());
    addNativeMethod("destroy", new destroy_Command());
    this.id = this.hashCode() + "";
  }

  void setComponent(JComponent component) {
    this.component = component;
  }

  public class set_id_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{"id"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      String new_id = params.get("id").toString();
      ownerObj.changeId(id, new_id, parent);
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_id";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }


  }

  public class get_id_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      return new ZPEString(id);
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "get_id";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.STRING_TYPE};
    }
  }

  public class destroy_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      if (component != null) {


        Container parentContainer = component.getParent();
        if (parentContainer != null) {
          parentContainer.remove(component);
          parentContainer.revalidate();
          parentContainer.repaint();
        }
      }
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "destroy";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }


  public void respondToAction(String a) {
    if (actions.containsKey(a)) {
      ZPEFunction f = actions.get(a);
      try{
        ZPEKit.runFunction(f, new ZPEType[0]);

      } catch (ExitHalt | BreakPointHalt e) {
        //Ignore
      } catch (ZPERuntimeException e){
        System.err.println(e);
        //throw e;
      }
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

  public void setSuitableActions(String[] suitableActions) {
    this.suitableActions = suitableActions;
  }
}

