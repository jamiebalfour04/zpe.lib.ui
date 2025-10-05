package jamiebalfour.zpe;

import jamiebalfour.HelperFunctions;
import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.ZPEStructure;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;

public class ZPEUITurtleObject extends ZPEStructure {
  private final ZPEUIFrameObject.TurtlePanel panel;
  private int x = 0, y = 0;
  private double angle = 0; // Facing right
  private boolean penDown = true;

  public ZPEUITurtleObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject ownerObject) {
    super(z, p, "ZPETurtle");

    panel = ownerObject.panel;
    ownerObject.frame.revalidate();
    ownerObject.frame.repaint();

    addNativeMethod("center", new center_Command());
    addNativeMethod("move", new move_Command());
    addNativeMethod("turn", new turn_Command());
    addNativeMethod("pen_up", new pen_up_Command());
    addNativeMethod("pen_down", new pen_down_Command());
    addNativeMethod("clear", new clear_Command());
  }

  public class center_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
      boolean tmpPenDown = penDown;
      penDown = false;

      x = panel.getWidth() / 2;
      y = panel.getHeight() / 2;

      penDown = tmpPenDown;

      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "move";
    }
  }

  public class move_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{"distance"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"number"};
    }

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
      int distance = HelperFunctions.stringToInteger(params.get("distance").toString());
      int newX = x + (int) (distance * Math.cos(Math.toRadians(angle)));
      int newY = y + (int) (distance * Math.sin(Math.toRadians(angle)));

      if (penDown) panel.addLine(x, y, newX, newY);

      x = newX;
      y = newY;
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "move";
    }
  }

  public class turn_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{"angle"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"number"};
    }

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
      angle += HelperFunctions.stringToInteger(params.get("angle").toString());
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "turn";
    }
  }

  public class pen_up_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
      penDown = false;
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "pen_up";
    }
  }

  public class pen_down_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
      penDown = true;
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "pen_down";
    }
  }

  public class clear_Command implements ZPEObjectNativeMethod {
    public String[] getParameterNames() {
      return new String[]{};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{};
    }

    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
      penDown = false;
      panel.clear();
      return parent;
    }

    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "clear";
    }
  }
}
