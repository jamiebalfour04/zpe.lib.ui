package jamiebalfour.zpe;

/*
 * To add a button, a button object is added
 */

import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.ZPEStructure;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;

import javax.swing.*;
import java.awt.*;

// -------- UI component base wrapper --------
public abstract class ZPEUIItemObject extends ZPEStructure {
  protected JComponent component;
  protected UIBuilderObject ownerObj;
  String id = "";

  public ZPEUIItemObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, String name, UIBuilderObject obj) {
    super(z, p, name);
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
}

