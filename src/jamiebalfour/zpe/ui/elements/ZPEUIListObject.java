package jamiebalfour.zpe.ui.elements;

import jamiebalfour.zpe.ui.ZPEUIFrameObject;
import jamiebalfour.zpe.core.YASSByteCodes;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.types.ZPEString;
import jamiebalfour.zpe.ui.core.ZPEUIItemObject;

import javax.swing.*;
import java.util.HashMap;

public class ZPEUIListObject extends ZPEUIItemObject {

  private static final long serialVersionUID = 13L;

  JList<String> lst;
  DefaultListModel<String> model;
  ZPEUIListObject _this = this;

  public ZPEUIListObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject ownerObject) {
    super(z, p, "ZPEUIList", ownerObject);

    setSuitableActions(new String[]{"add_item", "selection_changed", "double_click", "click", "middle_click", "right_click"});


    model = new DefaultListModel<>();
    lst = new JList<>(model);


    ownerObject.addElement("", this, lst);
    super.setComponent(lst);

    addNativeMethod("on", new on_Command());
    addNativeMethod("add_item", new add_item_Command());
    addNativeMethod("destroy", new destroy_Command());
    addNativeMethod("remove_item", new remove_item_Command());
    addNativeMethod("clear", new clear_Command());
    addNativeMethod("get_selected_index", new get_selected_index_Command());
    addNativeMethod("get_selected_item", new get_selected_item_Command());

    lst.addListSelectionListener(e -> respondToAction("selection_changed"));
    lst.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getComponent() == lst) {

          if (e.getClickCount() == 2) {
            respondToAction("double_click");
          } else if (e.getButton() == 1) {
            respondToAction("click");
          } else if (e.getButton() == 2) {
            respondToAction("middle_click");
          } else if (e.getButton() == 3) {
            respondToAction("right_click");
          }

        }
      }
    });
  }

  public class add_item_Command implements ZPEObjectNativeMethod {

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
      model.addElement(parameters.get("text").toString());
      respondToAction("add_item");

      return _this;
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

  public class remove_item_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"index"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"number"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      int index = Integer.parseInt(parameters.get("index").toString());
      if (index >= 0 && index < model.size()) {
        model.remove(index);
      }

      return _this;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "remove_item";
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
      model.clear();

      return _this;
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
      return new ZPEString(lst.getSelectedIndex() + "");
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
      return new byte[]{YASSByteCodes.STRING_TYPE};
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
      Object val = lst.getSelectedValue();
      return val != null ? new ZPEString(val.toString()) : new ZPEString("");
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


}
