package jamiebalfour.zpe.ui.elements;

import jamiebalfour.ui.components.BalfButton;
import jamiebalfour.zpe.ui.ZPEUIFrameObject;
import jamiebalfour.zpe.ui.core.ZPEUIItemObject;
import jamiebalfour.zpe.core.YASSByteCodes;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.objects.ColourObject;

import java.awt.*;
import java.util.HashMap;

public class ZPEUIButtonObject extends ZPEUIItemObject {

  private static final long serialVersionUID = 13L;




  BalfButton btn;

  /**
   * Acts as a wrapper to the JButton UI element. Provides suitable actions of
   * double_click, click, right_click, middle_click and set_text
   * @param z The owner runtime
   * @param p The property wrapper (e.g. function) that created this
   * @param owner The UIBuilder that created this
   * @param text The text on the button
   */
  public ZPEUIButtonObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject owner, String text, int arc) {
    super(z, p, "ZPEButton", owner);



    setSuitableActions(new String[]{"double_click", "click", "middle_click", "right_click", "set_text"});

    BalfButton obj = new BalfButton(text, arc);
    obj.setSize(100, 100);
    obj.setLocation(new Point(100, 100));
    this.btn = obj;



    super.setComponent(btn);

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

    addNativeMethod("set_text", new set_text_Command());
    addNativeMethod("set_foreground", new set_foreground_Command());
    addNativeMethod("set_background", new set_background_Command());
    addNativeMethod("set_font_size", new set_font_size_Command());
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

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public class set_foreground_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"colour"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      if(parameters.containsKey("colour") && parameters.get("colour") instanceof ColourObject){
        ColourObject colour = (ColourObject) parameters.get("colour");
        btn.setForeground(colour.getColour());
      }
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_foreground";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class set_background_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"colour"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      if(parameters.containsKey("colour") && parameters.get("colour") instanceof ColourObject){
        ColourObject colour = (ColourObject) parameters.get("colour");
        btn.setBackground(colour.getColour());
      }
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_background";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class set_font_size_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"size"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"number"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      int size = Integer.parseInt(parameters.get("size").toString());
      Font oldFont = btn.getFont();
      btn.setFont(new Font(oldFont.getName(), oldFont.getStyle(), size));
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_font_size";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }


}
