package jamiebalfour.zpe.ui;

import jamiebalfour.HelperFunctions;
import jamiebalfour.ui.windows.BalfWindow;
import jamiebalfour.zpe.ui.core.ZPEFileChooser;
import jamiebalfour.zpe.core.*;
import jamiebalfour.zpe.core.exceptions.MissingParameterException;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.objects.ImageObject;
import jamiebalfour.zpe.core.types.ZPEList;
import jamiebalfour.zpe.core.types.ZPEMap;
import jamiebalfour.zpe.core.types.ZPEString;
import jamiebalfour.zpe.ui.core.ZPEUIContainer;
import jamiebalfour.zpe.ui.core.ZPEUIItemObject;
import jamiebalfour.zpe.ui.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

public class ZPEUIFrameObject extends ZPEUIActionable {

  private static final long serialVersionUID = 13L;
  private final ZPEMap elements = new ZPEMap();
  BalfWindow frame;
  TurtlePanel panel = new TurtlePanel();
  ZPEUIFrameObject _this = this;
  ZPEFunction closeFunction = null;

  public ZPEUIFrameObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p) {
    super(z, p, "ZPEFrame");

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      //Ignored
    }




    addNativeMethod("add", new add_Command());
    addNativeMethod("set_title", new set_title_Command());
    addNativeMethod("set_footer_text", new set_footer_text_Command());
    addNativeMethod("create_turtle", new create_turtle_Command());
    addNativeMethod("set_size", new set_size_Command());
    addNativeMethod("set_on_close", new set_on_close_Command());
    addNativeMethod("get_element_by_id", new get_element_by_id_Command());
    addNativeMethod("create_container", new create_container_Command());
    addNativeMethod("create_button", new create_button_Command());
    addNativeMethod("create_label", new create_label_Command());
    addNativeMethod("create_text_field", new create_text_field_Command());
    addNativeMethod("create_checkbox", new create_checkbox_Command());
    addNativeMethod("create_combo_box", new create_combo_box_Command());
    addNativeMethod("create_image", new create_image_Command());
    addNativeMethod("create_list", new create_list_Command());
    addNativeMethod("create_quadratic", new create_quadratic_Command());
    addNativeMethod("alert", new alert_Command());
    addNativeMethod("set_always_on_top", new set_always_on_top_Command());
    addNativeMethod("show", new show_Command());
    addNativeMethod("hide", new hide_Command());
    addNativeMethod("choose_file", new choose_file_Command());
    addNativeMethod("choose_folder", new choose_folder_Command());

  }

  public void addElement(String id, ZPEObject element, JComponent component) {
    panel.add(component);
    this.elements.put(new ZPEString(id), element);
  }

  public void changeId(String id, String newId, ZPEObject element) {
    elements.put(new ZPEString(newId), element);
    if (elements.containsKey(new ZPEString(id))) {
      elements.remove(new ZPEString(id));
    }
  }

  void attachActions(){
    MouseListener mouseListener = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          respondToAction("double_click");
        } else if (e.getButton() == 1) {
          respondToAction("click");
        } else if (e.getButton() == 2) {
          respondToAction("middle_click");
        } else if (e.getButton() == 3) {
          respondToAction("right_click");
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    };
    frame.addMouseListener(mouseListener);
    panel.addMouseListener(mouseListener);
    setSuitableActions(new String[]{"click", "double_click", "middle_click", "right_click"});
  }

  public static class TurtlePanel extends JPanel {
    private final java.util.List<Line2D> lines = new ArrayList<>();

    public void addLine(int x1, int y1, int x2, int y2) {
      synchronized (lines) {
        lines.add(new Line2D.Float(x1, y1, x2, y2));
      }
      repaint();
    }

    public void clear() {
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



  public class add_Command implements ZPEObjectNativeMethod {


    @Override
    public String[] getParameterNames() {
      return new String[]{"component"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"object"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      if (parameters.get("component") instanceof ZPEUIItemObject) {
        ZPEUIItemObject obj = (ZPEUIItemObject) parameters.get("component");
        addElement(obj.getId(), obj, obj.component);
      }


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "add";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      if(!parameters.containsKey("title")) {
        throw new MissingParameterException("title", "set_title");
      }

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

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public class set_footer_text_Command implements ZPEObjectNativeMethod {

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

      if(!parameters.containsKey("text")) {
        throw new MissingParameterException("text", "set_footer_text");
      }

      if(frame.getFooter() != null) {
        frame.getFooter().setText(parameters.get("text").toString());
      }


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_footer_text";
    }

    @Override
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

      if(!parameters.containsKey("width")) {
        throw new MissingParameterException("width", "set_size");
      }

      if(!parameters.containsKey("height")) {
        throw new MissingParameterException("height", "set_size");
      }

      frame.setSize(HelperFunctions.stringToInteger(parameters.get("width").toString()), HelperFunctions.stringToInteger(parameters.get("height").toString()));

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_size";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public class create_container_Command implements ZPEObjectNativeMethod {

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

      return new ZPEUIContainer(getRuntime(), parent, _this);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_container";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public class create_button_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"text", "arc"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string", "number"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      int arc = 4;

      if(parameters.containsKey("arc")) {
        arc = HelperFunctions.stringToInteger(parameters.get("arc").toString());
      }

      return new ZPEUIButtonObject(getRuntime(), parent, _this, parameters.get("text").toString(), arc);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_button";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public class create_label_Command implements ZPEObjectNativeMethod {

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
      return new ZPEUILabelObject(getRuntime(), parent, (ZPEUIFrameObject) parent);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_label";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class create_text_field_Command implements ZPEObjectNativeMethod {

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
      return new ZPEUITextFieldObject(getRuntime(), parent, (ZPEUIFrameObject) parent);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_text_field";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class create_checkbox_Command implements ZPEObjectNativeMethod {

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
      return new ZPEUICheckBoxObject(getRuntime(), parent, (ZPEUIFrameObject) parent);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_checkbox";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class create_combo_box_Command implements ZPEObjectNativeMethod {

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
      return new ZPEUIComboBoxObject(getRuntime(), parent, (ZPEUIFrameObject) parent);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_combo_box";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  public class create_image_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"image"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"object"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      ZPEUIImageObject i = new ZPEUIImageObject(getRuntime(), parent, _this);
      if (parameters.containsKey("image") && parameters.get("image") instanceof ImageObject) {
        ImageObject imgObj = (ImageObject) parameters.get("image");
        i.image = imgObj.getImage();
        i.refreshImage();
      }

      return i;

    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_image";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      return new ZPEUIListObject(getRuntime(), parent, _this);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_list";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

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

      return parent;

    }

    public String getName() {
      return "create_quadratic";
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public class set_on_close_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"func"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"function"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      if(parameters.containsKey("func") && parameters.get("func") instanceof ZPEFunction) {
        closeFunction = (ZPEFunction) parameters.get("func");
      }


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_on_close";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

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

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      return new ZPEUITurtleObject(getRuntime(), parent, _this);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "create_turtle";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      if(!parameters.containsKey("text")) {
        throw new MissingParameterException("text", "alert");
      }

      String title = frame.getTitle();
      if(parameters.containsKey("title")){
        title = parameters.get("title").toString();
      }

      JOptionPane.showMessageDialog(frame.getContentPane(), parameters.get("text").toString(), title, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ZPEHelperFunctions.getLogo().getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));

      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "alert";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

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

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
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

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
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
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {
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

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }

  }

  public static class choose_file_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"extensions"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"list"};
    }

    @Override
    public ZPEType run(HashMap<String, ZPEType> parameters, ZPEObject parent) {

      String[] exts = null;
      if(parameters.containsKey("extensions")) {
        ZPEList extensions = (ZPEList) parameters.get("extensions");
        exts = new String[extensions.size()];
        for(int i = 0; i < extensions.size(); i++) {
          exts[i] = extensions.get(i).toString();
        }
      }

      String path = ZPEFileChooser.chooseFile(exts);
      return new ZPEString(path == null ? "" : path);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "choose_file";
    }

    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.STRING_TYPE};
    }
  }

  public static class choose_folder_Command implements ZPEObjectNativeMethod {

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
      String path = ZPEFileChooser.chooseFolder();
      return new ZPEString(path == null ? "" : path);
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "choose_folder";
    }

    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.STRING_TYPE};
    }
  }


}
