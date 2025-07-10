package jamiebalfour.zpe;

import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.ui.BalfButton;
import jamiebalfour.zpe.core.ZPEFunction;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;

import javax.swing.*;
import java.awt.*;

public class ZPEUIButtonObject extends ZPEUIItemObject {

  private static final long serialVersionUID = 13L;




  BalfButton btn;

  /**
   * Acts as a wrapper to the JButton UI element. Provides suitable actions of
   * double_click, click, right_click, middle_click and set_text
   * @param z
   * @param p
   * @param owner
   * @param text
   */
  public ZPEUIButtonObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, UIBuilderObject owner, String text) {
    super(z, p, "ZPEButton", owner);

    BalfButton obj = new BalfButton(text, 4);
    obj.setSize(100, 100);
    obj.setLocation(new Point(100, 100));

    setSuitableActions(new String[]{"double_click", "click", "middle_click", "right_click", "set_text"});

    this.btn = obj;

    super.setComponent(btn);

    owner.addElement("", this, obj);

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getComponent() == btn) {

          if(e.getClickCount() == 2){
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

      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        respondToAction("mouseover");
      }
    });

    addNativeMethod("on", new on_Command());
    addNativeMethod("set_text", new set_text_Command());
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      btn.setText(parameters.get("text").toString());
      respondToAction("set_text");

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


}
