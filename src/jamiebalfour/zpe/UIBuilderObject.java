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
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.ZPEFunction;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPEStructure;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.types.ZPEMap;

public class UIBuilderObject extends ZPEStructure {

  private static final long serialVersionUID = 13L;

  JFrame frame = new JFrame();
  boolean shutdownOnClose = false;
  private ZPEMap elements = new ZPEMap();

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
    addNativeMethod("show", new show_Command());
    addNativeMethod("hide", new hide_Command());
  }

  void addElement(String id, ZPEObject element) {
    this.elements.put(id, element);
  }

  public class _construct_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {"title"};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

  public class set_size_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {"x", "y"};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

      frame.setSize(HelperFunctions.StringToInteger(parameters.get("x").toString()), HelperFunctions.StringToInteger(parameters.get("y").toString()));

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

  public class create_button_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {"text"};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

      ZPEUIButtonObject obj = new ZPEUIButtonObject(getRuntime(), parent, frame, parameters.get("text").toString());
      return obj;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_button";
    }

  }

  public class create_list_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

      ZPEUIListObject obj = new ZPEUIListObject(getRuntime(), parent, frame);
      return obj;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_list";
    }

  }

  public class create_quadratic_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

      int y = 0;
      int a = 2;
      int b = 3;
      int c = 5;

      Graphics g = frame.getGraphics();
      //int lastY = -99;
      //int lastX = -99;
      for (int x = 0; x < 100; x++) {
        //The quadratic equation, so sexy
        y = a * (x ^ 2) + b * x + c;

				/*if(lastY == -99) {
					g.drawLine(x, y, x, y);
				} else {
					g.drawLine(lastX, lastY, x, y);
				}*/
        g.drawOval(x, y, 1, 1);

        //lastX = x;
        //lastY = y;
      }

      return "";

    }

    public String getName() {
      return "create_quadratic";
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

  }

  public class set_on_close_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {"value"};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

  public class get_element_by_id_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {"id"};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

      String id = parameters.get("id").toString();
      return elements.get(id);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "get_element_by_id";
    }

  }

  public class create_turtle_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

  public class alert_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {"text"};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

  public class show_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {
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

  public class hide_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      String l[] = {};
      return l;
    }

    @Override
    public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {
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

    HashMap<String, ZPEFunction> actions = new HashMap<String, ZPEFunction>();

    private static final long serialVersionUID = 13L;

    JFrame frame = null;

    int xcur = 0;
    int ycur = 0;

    public ZPEUITurtleObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, JFrame owner) {
      super(z, p, "ZPETurtle");
      frame = owner;

      addNativeMethod("draw_to", new draw_to_Command());
    }

    public void respondToAction(String a) {
      if (actions.containsKey(a)) {
        ZPEFunction f = actions.get(a);
        getRuntime().RunZPEFunction(f);
      }
    }

    public class draw_to_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {"x2", "y2"};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {


        frame.getGraphics().drawLine(xcur, ycur, HelperFunctions.StringToInteger(parameters.get("x2").toString()), HelperFunctions.StringToInteger(parameters.get("y2").toString()));
        xcur = HelperFunctions.StringToInteger(parameters.get("x2").toString());
        ycur = HelperFunctions.StringToInteger(parameters.get("y2").toString());
        return parent;
      }

      @Override
      public int getRequiredPermissionLevel() {
        return 0;
      }

      public String getName() {
        return "draw_to";
      }

    }
  }

  /*
   * To add a button, a button object is added
   */

  public class ZPEUIItemObject extends ZPEObject {

    private static final long serialVersionUID = 5573981895716569476L;
    protected JFrame ownerFrame;

    public ZPEUIItemObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, String name, JFrame owner) {
      super(z, p, "");
      ownerFrame = owner;
      addNativeMethod("set_id", new set_id_Command());
    }

    public class set_id_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {"id"};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

        String id = parameters.get("id").toString();
        UIBuilderObject p = (UIBuilderObject) ((UIBuilderObject.ZPEUIItemObject) parent).getParentObject();
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

    private HashMap<String, ZPEFunction> actions = new HashMap<String, ZPEFunction>();

    private static final long serialVersionUID = 13L;

    Button btn = null;

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
        getRuntime().RunZPEFunction(f);
      }
    }

    public class on_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {"action", "method"};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

    public class set_text_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {"text"};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

    public class destroy_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

    private HashMap<String, ZPEFunction> actions = new HashMap<String, ZPEFunction>();

    private static final long serialVersionUID = 13L;

    List lst = null;
    JFrame frame = null;

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
        getRuntime().RunZPEFunction(f);
      }
    }

    public class on_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {"action", "method"};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

    public class add_item_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {"text"};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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

    public class destroy_Command implements jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod {


      @Override
      public String[] getParameterNames() {
        String l[] = {};
        return l;
      }

      @Override
      public Object MainMethod(HashMap<String, Object> parameters, ZPEObject parent) {

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
