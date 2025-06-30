package jamiebalfour.zpe;

/*
 * To add a button, a button object is added
 */

import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.exceptions.BreakPointHalt;
import jamiebalfour.zpe.exceptions.ExitHalt;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// -------- UI component base wrapper --------
public abstract class ZPEUIItemObject extends ZPEStructure {
  protected JComponent component;
  protected UIBuilderObject ownerObj;
  String id = "";
  String name;

  String[] suitableActions = new String[]{};


  private final HashMap<String, ZPEFunction> actions = new HashMap<>();

  public ZPEUIItemObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, String name, UIBuilderObject obj) {
    super(z, p, name);
    this.name = name;
    addNativeMethod("set_id", new set_id_Command());
    addNativeMethod("destroy", new destroy_Command());
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

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
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
  }

  public class destroy_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
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
  }


  public void respondToAction(String a) {
    if (actions.containsKey(a)) {
      ZPEFunction f = actions.get(a);
      try{
        ZPEKit.runFunction(f, new ZPEType[0]);

      } catch (ExitHalt | BreakPointHalt e) {
        //Ignore
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
      ZPE.printError("Action " + action + " not found on type " + name);
    } else{
      actions.put(action, f);
    }

  }

  public void setSuitableActions(String[] suitableActions) {
    this.suitableActions = suitableActions;
  }
}

