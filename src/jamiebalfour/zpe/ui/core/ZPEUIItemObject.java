package jamiebalfour.zpe.ui.core;

/*
 * To add a button, a button object is added
 */

import jamiebalfour.zpe.ui.ZPEUIActionable;
import jamiebalfour.zpe.ui.ZPEUIFrameObject;
import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.types.ZPEBoolean;
import jamiebalfour.zpe.core.types.ZPEString;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// -------- UI component base wrapper --------
public abstract class ZPEUIItemObject extends ZPEUIActionable {


  public JComponent component;
  protected ZPEUIFrameObject ownerObj;


  public ZPEUIItemObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, String name, ZPEUIFrameObject obj) {
    super(z, p, name);
    this.name = name;
    this.ownerObj = obj;


    addNativeMethod("set_id", new set_id_Command());
    addNativeMethod("get_id", new get_id_Command());
    addNativeMethod("destroy", new destroy_Command());
    addNativeMethod("set_visible", new set_visible_Command());
    addNativeMethod("is_visible", new is_visible_Command());
    addNativeMethod("set_enabled", new set_enabled_Command());
    addNativeMethod("is_enabled", new is_enabled_Command());
    addNativeMethod("set_tooltip", new set_tooltip_Command());
    this.id = this.hashCode() + "";


  }

  public void setComponent(JComponent component) {
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

  public class set_visible_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{"visible"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"boolean"};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      boolean visible = Boolean.parseBoolean(params.get("visible").toString());
      component.setVisible(visible);
      component.revalidate();
      component.repaint();
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_visible";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class is_visible_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      return new ZPEBoolean(component.isVisible());
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "is_visible";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.BOOLEAN_TYPE};
    }
  }

  public class set_enabled_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{"enabled"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"boolean"};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      boolean enabled = Boolean.parseBoolean(params.get("enabled").toString());
      component.setEnabled(enabled);
      component.repaint();
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_enabled";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class is_enabled_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      return new ZPEBoolean(component.isEnabled());
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "is_enabled";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.BOOLEAN_TYPE};
    }
  }

  public class set_tooltip_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{"text"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    public ZPEType run(HashMap<String, ZPEType> params, ZPEObject parent) {
      component.setToolTipText(params.get("text").toString());
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_tooltip";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }
}

