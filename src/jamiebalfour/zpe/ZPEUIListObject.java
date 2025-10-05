package jamiebalfour.zpe;

import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.ZPEFunction;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;
import jamiebalfour.zpe.types.ZPEString;

import javax.swing.*;

public class ZPEUIListObject extends ZPEUIItemObject {

  private static final long serialVersionUID = 13L;

  JList<String> lst;
  DefaultListModel<String> model;

  public ZPEUIListObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject ownerObject) {
    super(z, p, "ZPEButton", ownerObject);

    setSuitableActions(new String[]{"add_item"});


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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {
      model.addElement(parameters.get("text").toString());
      respondToAction("add_item");
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "add_item";
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {
      int index = Integer.parseInt(parameters.get("index").toString());
      if (index >= 0 && index < model.size()) {
        model.remove(index);
      }
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "remove_item";
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {
      model.clear();
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "clear";
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {
      return new ZPEString(lst.getSelectedIndex() + "");
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "get_selected_index";
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {
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
  }


}
