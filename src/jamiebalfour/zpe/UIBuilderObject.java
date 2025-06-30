package jamiebalfour.zpe;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;

import jamiebalfour.HelperFunctions;
import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;
import jamiebalfour.zpe.types.ZPEMap;
import jamiebalfour.zpe.types.ZPEString;

public class UIBuilderObject extends ZPEStructure {

  public class TurtlePanel extends JPanel {
    private final java.util.List<Line2D> lines = new ArrayList<>();

    public void addLine(int x1, int y1, int x2, int y2) {
      synchronized (lines) {
        lines.add(new Line2D.Float(x1, y1, x2, y2));
      }
      repaint();
    }

    public void clear(){
      lines.clear();
      repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      // Defensive copy to avoid ConcurrentModificationException
      java.util.List<Line2D> safeLines;
      synchronized (lines) {
        safeLines = new ArrayList<>(lines); // clone it safely
      }

      for (Line2D line : safeLines) {
        g2.draw(line);
      }
    }
  }

  private static final long serialVersionUID = 13L;

  final JFrame frame = new JFrame();
  TurtlePanel panel = new TurtlePanel();
  UIBuilderObject _this = this;
  boolean shutdownOnClose = false;
  private final ZPEMap elements = new ZPEMap();

  public static void main(String[] args) {
    new UIBuilderObject(null, null);
  }


  public UIBuilderObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p) {
    super(z, p, "UIBuilderObject");
    if (jamiebalfour.zpe.core.ZPEHelperFunctions.isHeadless()) {
      System.err.println("UIBuilder object cannot be created because it is being run in a headless (non-GUI supporting) environment.");
      return;
    }

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      //Ignored
    }


    addNativeMethod("_construct", new _construct_Command());
    addNativeMethod("set_title", new set_title_Command());
    addNativeMethod("create_turtle", new create_turtle_Command());
    addNativeMethod("set_size", new set_size_Command());
    addNativeMethod("set_on_close", new set_on_close_Command());
    addNativeMethod("get_element_by_id", new get_element_by_id_Command());
    addNativeMethod("create_button", new create_button_Command());
    addNativeMethod("create_list", new create_list_Command());
    addNativeMethod("create_quadratic", new create_quadratic_Command());
    addNativeMethod("alert", new alert_Command());
    addNativeMethod("set_always_on_top", new set_always_on_top_Command());
    addNativeMethod("show", new show_Command());
    addNativeMethod("hide", new hide_Command());
  }

  public void addElement(String id, ZPEObject element, JComponent component) {
    panel.add(component);
    this.elements.put(new ZPEString(id), element);
  }

  public void changeId(String id, String newId, ZPEObject element){
    elements.put(new ZPEString(newId), element);
    if(elements.containsKey(new ZPEString(id))){
      elements.remove(new ZPEString(id));
    }

  }

  public class _construct_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"title"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      frame.setSize(300, 300);
      frame.add(panel);
      panel.setLayout(new FlowLayout());
      if (parameters.containsKey("title"))
        frame.setTitle(parameters.get("title").toString());



      frame.addWindowListener(
              new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                  if (shutdownOnClose) {
                    System.exit(0);
                  }
                  frame.dispose();
                }
              });


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "_construct";
    }

  }

  public class set_title_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"title"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {
      frame.setTitle(parameters.get("title").toString());


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_title";
    }

  }

  public class set_size_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"x", "y"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"number", "number"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      frame.setSize(HelperFunctions.stringToInteger(parameters.get("x").toString()), HelperFunctions.stringToInteger(parameters.get("y").toString()));

      return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_size";
    }

  }

  public class create_button_Command implements ZPEObjectNativeMethod {

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

      return new ZPEUIButtonObject(getRuntime(), parent, _this, parameters.get("text").toString());
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_button";
    }

  }

  public class create_list_Command implements ZPEObjectNativeMethod {

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

      return new ZPEUIListObject(getRuntime(), parent, _this);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_list";
    }

  }

  public class create_quadratic_Command implements ZPEObjectNativeMethod {

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

      int y;
      int a = 2;
      int b = 3;
      int c = 5;

      Graphics g = frame.getGraphics();
      //int lastY = -99;
      //int lastX = -99;
      for (int x = 0; x < 100; x++) {
        //The quadratic equation, so sexy
        y = a * (x * x) + b * x + c;

				/*if(lastY == -99) {
					g.drawLine(x, y, x, y);
				} else {
					g.drawLine(lastX, lastY, x, y);
				}*/
        g.drawOval(x, y, 1, 1);

        //lastX = x;
        //lastY = y;
      }

      return new ZPEString("");

    }

    public String getName() {
      return "create_quadratic";
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

  }

  public class set_on_close_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"value"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"boolean"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      shutdownOnClose = jamiebalfour.zpe.core.ZPEHelperFunctions.ToBoolean(parameters.get("value").toString());

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_on_close";
    }

  }

  public class get_element_by_id_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"id"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      String id = parameters.get("id").toString();
      return elements.get(new ZPEString(id));
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "get_element_by_id";
    }

  }

  public class create_turtle_Command implements ZPEObjectNativeMethod {

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

      return new ZPEUITurtleObject(getRuntime(), parent, _this);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_turtle";
    }
  }

  public class alert_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"text", "title"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string", "string"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      JOptionPane.showMessageDialog(frame.getContentPane(), parameters.get("text").toString(), parameters.get("title").toString(), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ZPEHelperFunctions.getLogo().getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "alert";
    }

  }

  public class set_always_on_top_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"enabled"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"boolean"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      String enabled = parameters.get("enabled").toString();


      frame.setAlwaysOnTop(ZPEHelperFunctions.ToBoolean(enabled));

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_always_on_top";
    }

  }

  public class show_Command implements ZPEObjectNativeMethod {

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
      frame.setVisible(true);

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "show";
    }

  }

  public class hide_Command implements ZPEObjectNativeMethod {

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
      frame.setVisible(false);

      frame.dispose();

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "hide";
    }

  }








}
