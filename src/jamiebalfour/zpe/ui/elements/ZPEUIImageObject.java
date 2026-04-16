package jamiebalfour.zpe.ui.elements;

import jamiebalfour.zpe.ui.ZPEUIFrameObject;
import jamiebalfour.zpe.core.YASSByteCodes;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.core.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.core.interfaces.ZPEType;
import jamiebalfour.zpe.core.objects.ImageObject;
import jamiebalfour.zpe.ui.core.ZPEUIItemObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ZPEUIImageObject extends ZPEUIItemObject {

  private final JLabel label;
  public BufferedImage image;
  private int width = -1;
  private int height = -1;

  public ZPEUIImageObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper p, ZPEUIFrameObject obj) {
    super(z, p, "image", obj);

    label = new JLabel();
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setVerticalAlignment(SwingConstants.CENTER);
    setComponent(label);

    addNativeMethod("set_image", new set_image_Command());
    addNativeMethod("set_size", new set_size_Command());
    addNativeMethod("set_visible", new set_visible_Command());
    addNativeMethod("clear", new clear_Command());

    super.setComponent(label);
  }

  public void refreshImage() {
    if (image == null) {
      label.setIcon(null);
      label.setText("");
      component.revalidate();
      component.repaint();
      return;
    }

    Image displayImage = image;

    if (width > 0 && height > 0) {
      displayImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      label.setPreferredSize(new Dimension(width, height));
    } else {
      label.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    label.setIcon(new ImageIcon(displayImage));
    label.setText("");
    component.revalidate();
    component.repaint();
  }

  public class set_image_Command implements ZPEObjectNativeMethod {

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
      if (parameters.get("image") instanceof ImageObject) {
        ImageObject imgObj = (ImageObject) parameters.get("image");
        image = imgObj.getImage();
        refreshImage();
      }
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_image";
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
      width = Integer.parseInt(parameters.get("width").toString());
      height = Integer.parseInt(parameters.get("height").toString());
      refreshImage();
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
      label.setVisible(visible);
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "set_visible";
    }

    @Override
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
      image = null;
      refreshImage();
      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "clear";
    }

    @Override
    public byte[] returnTypes() {
      return new byte[]{YASSByteCodes.OBJECT_TYPE};
    }
  }

  @Override
  public String toString() {
    return "ZPEUIImageObject{}";
  }
}