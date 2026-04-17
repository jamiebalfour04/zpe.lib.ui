package jamiebalfour.zpe.ui;

import jamiebalfour.zpe.core.ZPEModule;
import jamiebalfour.zpe.ui.elements.ZPEUIButtonObject;

public class ZPEUIModule extends ZPEModule {
  public ZPEUIModule() {
    super("UI");

    addStructure("frame", ZPEUIFrameObject.class);
    addStructure("button", new ZPEUIButtonObject(getRuntime(), this));
  }
}
