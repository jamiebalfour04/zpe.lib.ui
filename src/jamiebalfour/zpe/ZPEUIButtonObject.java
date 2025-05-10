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
import java.awt.*;
import java.util.HashMap;

public class ZPEUIButtonObject extends ZPEUIItemObject {

  private final HashMap<String, ZPEFunction> actions = new HashMap<>();

  private static final long serialVersionUID = 13L;

  JButton btn;

  public ZPEUIButtonObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, UIBuilderObject owner, String text) {
    super(z, p, "ZPEButton", owner);

    JButton obj = new JButton(text);
    obj.setSize(100, 100);
    obj.setLocation(new Point(100, 100));

    this.btn = obj;

    super.setComponent(btn);

    owner.addElement("", this, obj);

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getComponent() == btn) {

          if (e.getButton() == 1) {
            respondToAction("click");
          }
          if (e.getButton() == 2) {
            respondToAction("middle-click");
          }
          if (e.getButton() == 3) {
            respondToAction("right-click");
          }

        }
      }

      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        respondToAction("mouseover");
      }
    });

    addNativeMethod("on", new on_Command());
    addNativeMethod("set_text", new set_text_Command());
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

  public class set_text_Command implements ZPEObjectNativeMethod {


    @Override
    public String[] getParameterNames() {
      return new String[]{"text"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      btn.setLabel(parameters.get("text").toString());


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_text";
    }

  }

  public class destroy_Command implements ZPEObjectNativeMethod {


    @Override
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      btn.getParent().remove(btn);


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "destroy";
    }

  }


}
