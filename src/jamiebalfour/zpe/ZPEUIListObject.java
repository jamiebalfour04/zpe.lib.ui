package jamiebalfour.zpe;

import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.ZPEFunction;
import jamiebalfour.zpe.core.ZPEKit;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.exceptions.BreakPointHalt;
import jamiebalfour.zpe.exceptions.ExitHalt;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;

import javax.swing.*;
import java.util.HashMap;

public class ZPEUIListObject extends ZPEUIItemObject {

  private final HashMap<String, ZPEFunction> actions = new HashMap<>();

  private static final long serialVersionUID = 13L;

  JList lst;

  public ZPEUIListObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, UIBuilderObject ownerObject) {
    super(z, p, "ZPEButton", ownerObject);

    JList<String> obj = new JList<>();

    lst = obj;

    ownerObject.addElement("", this, obj);
    super.setComponent(lst);

    addNativeMethod("on", new on_Command());
    addNativeMethod("add_item", new add_item_Command());
    addNativeMethod("destroy", new destroy_Command());
  }

  public void respondToAction(String a) {
    if (actions.containsKey(a)) {
      ZPEFunction f = actions.get(a);
      try {
        ZPEKit.runFunction(f, new ZPEType[0]);
      } catch (ExitHalt | BreakPointHalt e) {
        //Ignore
      }
    }
  }

  public class on_Command implements ZPEObjectNativeMethod {


    @Override
    public String[] getParameterNames() {
      return new String[]{"action", "method"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      if (parameters.get("method") instanceof ZPEFunction) {
        ZPEFunction zf = (ZPEFunction) parameters.get("method");
        actions.put(parameters.get("action").toString(), zf);
      }
      System.out.println(actions.get("click").getParent());


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
