package jamiebalfour.zpe.ui.core;

import jamiebalfour.ui.components.BalfPanel;
import jamiebalfour.zpe.ui.ZPEUIFrameObject;
import jamiebalfour.zpe.core.YASSByteCodes;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.objects.ColourObject;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;

public class ZPEUIContainer extends ZPEUIItemObject {

  BalfPanel panel;
  HashMap<String, ZPEUIItemObject> children = new HashMap<>();

  public ZPEUIContainer(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject obj) {
    super(z, p, "container", obj);

    panel = new BalfPanel();
    setComponent(panel);

    addNativeMethod("_construct", new _construct_Command());
    addNativeMethod("add", new add_Command());
    addNativeMethod("set_background", new set_background_Command());
    addNativeMethod("set_layout", new set_layout_Command());
    addNativeMethod("set_visible", new set_visible_Command());
    addNativeMethod("set_size", new set_size_Command());
    addNativeMethod("set_border", new set_border_Command());
    addNativeMethod("clear", new clear_Command());

  }

  public class _construct_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"arc"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"number"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      if(parameters.containsKey("arc")){
        int arc = Integer.parseInt(parameters.get("arc").toString());
        panel.setCornerRadius(arc);
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

  public class add_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"component", "position"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"object", "string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      if (parameters.get("component") instanceof ZPEUIItemObject) {
        ZPEUIItemObject obj = (ZPEUIItemObject) parameters.get("component");
        children.put(obj.getId(), obj);

        if (parameters.containsKey("position")) {
          String position = parameters.get("position").toString().toLowerCase();

          if (panel.getLayout() instanceof BorderLayout) {
            switch (position) {
              case "north":
                panel.add(obj.component, BorderLayout.NORTH);
                break;
              case "south":
                panel.add(obj.component, BorderLayout.SOUTH);
                break;
              case "east":
                panel.add(obj.component, BorderLayout.EAST);
                break;
              case "west":
                panel.add(obj.component, BorderLayout.WEST);
                break;
              case "center":
                panel.add(obj.component, BorderLayout.CENTER);
                break;
              default:
                panel.add(obj.component);
                break;
            }
          } else {
            panel.add(obj.component);
          }
        } else {
          panel.add(obj.component);
        }
      }

      panel.revalidate();
      panel.repaint();

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "add";
    }

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
        panel.setBackground(colour.getColour());
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

    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public class set_layout_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"layout"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      String layout = parameters.get("layout").toString().toLowerCase();
      switch (layout) {
        case "flow":
          panel.setLayout(new FlowLayout());
          break;
        case "border":
          panel.setLayout(new BorderLayout());
          break;
        case "grid":
          panel.setLayout(new GridLayout());
          break;
        default:
          // no-op or throw
          break;
      }
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_layout";
    }

    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class set_visible_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"visible"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"boolean"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      boolean visible = Boolean.parseBoolean(parameters.get("visible").toString());
      panel.setVisible(visible);
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_visible";
    }

    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class set_size_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"width", "height"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"number", "number"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      int width = Integer.parseInt(parameters.get("width").toString());
      int height = Integer.parseInt(parameters.get("height").toString());
      panel.setPreferredSize(new Dimension(width, height));
      panel.revalidate();
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_size";
    }

    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class set_border_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"colour", "thickness"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
      String colour = parameters.get("colour").toString();
      int thickness = Integer.parseInt(parameters.get("thickness").toString());
      panel.setBorder(new LineBorder(Color.decode(colour), thickness, true));
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_border";
    }

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
      panel.removeAll();
      children.clear();
      panel.revalidate();
      panel.repaint();
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "clear";
    }

    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  @Override
  public String toString() {

    String layout = panel.getLayout() instanceof FlowLayout ? "flow" : panel.getLayout() instanceof BorderLayout ? "border" : panel.getLayout() instanceof GridLayout ? "grid" : "unknown";

    return "ZPEUIContainer{layout : " + layout + "}";
  }
}
