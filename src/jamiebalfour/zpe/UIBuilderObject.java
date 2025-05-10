package jamiebalfour.zpe;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jamiebalfour.HelperFunctions;
import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.exceptions.BreakPointHalt;
import jamiebalfour.zpe.exceptions.ExitHalt;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;
import jamiebalfour.zpe.types.ZPEMap;
import jamiebalfour.zpe.types.ZPEString;

public class UIBuilderObject extends ZPEStructure {

  private static final long serialVersionUID = 13L;

  JFrame frame = new JFrame();
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

  void addElement(String id, ZPEObject element) {
    this.elements.put(new ZPEString(id), element);
  }

  public class _construct_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"title"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      frame.setSize(300, 300);
      frame.setLayout(new FlowLayout());
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

  public class set_size_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"x", "y"};
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      return new ZPEUIButtonObject(getRuntime(), parent, frame, parameters.get("text").toString());
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      return new ZPEUIListObject(getRuntime(), parent, frame);
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
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      return new ZPEUITurtleObject(getRuntime(), parent, frame);
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
      return new String[]{"text"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      JOptionPane.showMessageDialog(frame.getContentPane(), parameters.get("text").toString());

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

  public class ZPEUITurtleObject extends ZPEObject {
    private final JFrame frame;
    private int x = 0, y = 0;
    private final java.util.List<Point> path = new java.util.ArrayList<>();

    public ZPEUITurtleObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, JFrame f) {
      super(z, p, "ZPETurtle");
      this.frame = f;
      addNativeMethod("draw_to", new draw_to_Command());
    }

    public class draw_to_Command implements ZPEObjectNativeMethod {
      public String[] getParameterNames() { return new String[] {"x2", "y2"}; }

      public ZPEType MainMethod(BinarySearchTree<String, ZPEType> params, ZPEObject parent) {
        int x2 = HelperFunctions.stringToInteger(params.get("x2").toString());
        int y2 = HelperFunctions.stringToInteger(params.get("y2").toString());
        path.add(new Point(x, y));
        path.add(new Point(x2, y2));
        x = x2;
        y = y2;
        frame.repaint();
        return parent;
      }
      public int getRequiredPermissionLevel() { return 0; }
      public String getName() { return "draw_to"; }
    }
  }

  /*
   * To add a button, a button object is added
   */

  public class ZPEUIItemObject extends ZPEObject {

    private static final long serialVersionUID = 5573981895716569476L;
    protected JFrame ownerFrame;

    public ZPEUIItemObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, String name, JFrame owner) {
      super(z, p, name);
      ownerFrame = owner;
      addNativeMethod("set_id", new set_id_Command());
    }

    public class set_id_Command implements ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        return new String[]{"id"};
      }

      @Override
      public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

        String id = parameters.get("id").toString();
        UIBuilderObject p = (UIBuilderObject) (parent).getParentObject();
        p.addElement(id, parent);

        return parent;
      }

      @Override
      public int getRequiredPermissionLevel() {
        return 0;
      }

      public String getName() {
        return "set_id";
      }

    }

  }

  public class ZPEUIButtonObject extends ZPEUIItemObject {

    private final HashMap<String, ZPEFunction> actions = new HashMap<>();

    private static final long serialVersionUID = 13L;

    Button btn;

    public ZPEUIButtonObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, JFrame owner, String text) {
      super(z, p, "ZPEButton", owner);

      Button obj = new Button(text);
      obj.setSize(100, 100);
      obj.setLocation(new Point(100, 100));

      this.btn = obj;


      frame.add(obj);

			/*btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					respondToAction("click");
				}
			});*/
      btn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
          if (e.getComponent() == btn) {

            if (e.getButton() == 1) {
              respondToAction("click");
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

  public class ZPEUIListObject extends ZPEObject {

    private final HashMap<String, ZPEFunction> actions = new HashMap<>();

    private static final long serialVersionUID = 13L;

    List lst;
    JFrame frame;

    public ZPEUIListObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, JFrame owner) {
      super(z, p, "ZPEButton");
      frame = owner;

      List obj = new List();

      lst = obj;
      frame = owner;

      frame.add(obj);

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

        lst.add(parameters.get("text").toString());


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

    public class destroy_Command implements ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        return new String[]{};
      }

      @Override
      public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

        lst.getParent().remove(lst);


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


}
