package jamiebalfour.zpe.ui.elements;

import jamiebalfour.zpe.core.YASSByteCodes;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.types.ZPEString;
import jamiebalfour.zpe.ui.ZPEUIFrameObject;
import jamiebalfour.zpe.ui.core.ZPEUIItemObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ZPEUIComboBoxObject extends ZPEUIItemObject {

  JComboBox<String> comboBox;

  public ZPEUIComboBoxObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject obj) {
    super(z, p, "combo_box", obj);

    comboBox = new JComboBox<>();
    setComponent(comboBox);

    setSuitableActions(new String[]{"change"});

    comboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        respondToAction("change");
      }
    });

    addNativeMethod("add_item", new add_item_Command());
    addNativeMethod("clear", new clear_Command());
    addNativeMethod("get_selected_item", new get_selected_item_Command());
    addNativeMethod("set_selected_item", new set_selected_item_Command());
    addNativeMethod("get_selected_index", new get_selected_index_Command());
  }

  public class add_item_Command implements ZPEObjectNativeMethod {
    @Override
    public String[] getParameterNames() {
      return new String[]{"item"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      comboBox.addItem(parameters.get("item").toString());
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "add_item";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class clear_Command implements ZPEObjectNativeMethod {
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
      comboBox.removeAllItems();
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "clear";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class get_selected_item_Command implements ZPEObjectNativeMethod {
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
      Object item = comboBox.getSelectedItem();
      return new ZPEString(item == null ? "" : item.toString());
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "get_selected_item";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.STRING_TYPE};
    }
  }

  public class set_selected_item_Command implements ZPEObjectNativeMethod {
    @Override
    public String[] getParameterNames() {
      return new String[]{"item"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      comboBox.setSelectedItem(parameters.get("item").toString());
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_selected_item";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class get_selected_index_Command implements ZPEObjectNativeMethod {
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
      return new jamiebalfour.zpe.core.types.ZPENumber(comboBox.getSelectedIndex());
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "get_selected_index";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.NUMBER_TYPE};
    }
  }
}
