package jamiebalfour.zpe.ui.elements;


import jamiebalfour.zpe.core.YASSByteCodes;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.types.ZPEBoolean;
import jamiebalfour.zpe.core.types.ZPEString;
import jamiebalfour.zpe.ui.ZPEUIFrameObject;
import jamiebalfour.zpe.ui.core.ZPEUIItemObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ZPEUICheckBoxObject extends ZPEUIItemObject {

  JCheckBox checkBox;

  public ZPEUICheckBoxObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject obj) {
    super(z, p, "checkbox", obj);

    checkBox = new JCheckBox();
    setComponent(checkBox);

    setSuitableActions(new String[]{"click", "change"});

    checkBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        respondToAction("click");
        respondToAction("change");
      }
    });

    addNativeMethod("_construct", new _construct_Command());
    addNativeMethod("set_text", new set_text_Command());
    addNativeMethod("get_text", new get_text_Command());
    addNativeMethod("set_checked", new set_checked_Command());
    addNativeMethod("is_checked", new is_checked_Command());
  }

  public class _construct_Command implements ZPEObjectNativeMethod {
    @Override
    public String[] getParameterNames() {
      return new String[]{"text", "checked"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string", "boolean"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      if(parameters.containsKey("text")){
        checkBox.setText(parameters.get("text").toString());
      }
      if(parameters.containsKey("checked")){
        checkBox.setSelected(Boolean.parseBoolean(parameters.get("checked").toString()));
      }
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "_construct";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class set_text_Command implements ZPEObjectNativeMethod {
    @Override
    public String[] getParameterNames() {
      return new String[]{"text"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      checkBox.setText(parameters.get("text").toString());
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_text";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class get_text_Command implements ZPEObjectNativeMethod {
    @Override
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      return new ZPEString(checkBox.getText());
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "get_text";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.STRING_TYPE};
    }
  }

  public class set_checked_Command implements ZPEObjectNativeMethod {
    @Override
    public String[] getParameterNames() {
      return new String[]{"checked"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"boolean"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      checkBox.setSelected(Boolean.parseBoolean(parameters.get("checked").toString()));
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_checked";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class is_checked_Command implements ZPEObjectNativeMethod {
    @Override
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      return new ZPEBoolean(checkBox.isSelected());
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "is_checked";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.BOOLEAN_TYPE};
    }
  }
}
