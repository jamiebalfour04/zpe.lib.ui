package jamiebalfour.zpe;

import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.ZPEFunction;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;

import javax.swing.*;

public class ZPEUIListObject extends ZPEUIItemObject {

  private static final long serialVersionUID = 13L;

  JList lst;

  public ZPEUIListObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, UIBuilderObject ownerObject) {
    super(z, p, "ZPEButton", ownerObject);

    setSuitableActions(new String[]{"add_item"});

    JList<String> obj = new JList<>();

    lst = obj;

    ownerObject.addElement("", this, obj);
    super.setComponent(lst);

    addNativeMethod("on", new on_Command());
    addNativeMethod("add_item", new add_item_Command());
    addNativeMethod("destroy", new destroy_Command());
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

  public static class add_item_Command implements ZPEObjectNativeMethod {


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

      //lst.add(parameters.get("text").toString());



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


}
