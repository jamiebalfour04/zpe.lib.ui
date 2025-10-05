package jamiebalfour.zpe;

import jamiebalfour.HelperFunctions;
import jamiebalfour.generic.BinarySearchTree;
import jamiebalfour.zpe.core.ZPEHelperFunctions;
import jamiebalfour.zpe.core.ZPEObject;
import jamiebalfour.zpe.core.ZPERuntimeEnvironment;
import jamiebalfour.zpe.core.ZPEStructure;
import jamiebalfour.zpe.exceptions.BreakPointHalt;
import jamiebalfour.zpe.exceptions.ExitHalt;
import jamiebalfour.zpe.interfaces.ZPEObjectNativeMethod;
import jamiebalfour.zpe.interfaces.ZPEPropertyWrapper;
import jamiebalfour.zpe.interfaces.ZPEType;


public class ZPEUIBuilderObject extends ZPEStructure {

  protected ZPEUIBuilderObject(ZPERuntimeEnvironment z, ZPEPropertyWrapper parent) {
    super(z, parent, "ZPEUIBuilder");
    if (jamiebalfour.zpe.core.ZPEHelperFunctions.isHeadless()) {
      if(ZPEHelperFunctions.isNativeImage()){
        System.err.println("UIBuilder object cannot be created because it is being run in ZPE Native.");
      } else{
        System.err.println("UIBuilder object cannot be created because it is being run in a headless (non-GUI supporting) environment.");
      }
    } else{
      addNativeMethod("new_frame", new new_frame_Command());
    }


  }


  public class new_frame_Command implements ZPEObjectNativeMethod {

    @Override
    public String[] getParameterNames() {
      return new String[]{"title", "arc"};
    }

    @Override
    public String[] getParameterTypes() {
      return new String[]{"string", "number"};
    }

    @Override
    public ZPEType MainMethod(BinarySearchTree<String, ZPEType> parameters, ZPEObject parent) {

      String title = "";
      int arc = 0;

      if (parameters.containsKey("title"))
        title = parameters.get("title").toString();

      if (parameters.containsKey("arc"))
        arc = HelperFunctions.stringToInteger(parameters.get("arc").toString());


      ZPEUIFrameObject f = new ZPEUIFrameObject(getRuntime(), getParent());

      f.frame = new jamiebalfour.ui.windows.BalfWindow(title, arc, java.awt.Color.white, java.awt.Color.black, null);

      f.frame.setSize(300, 300);




      f.frame.initialise();

      f.frame.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));


      if(f.frame.getFooter() != null){
        f.frame.getFooter().setText(title);
      }

      f.frame.add(f.panel);
      f.panel.setOpaque(false);
      f.panel.setLayout(new java.awt.FlowLayout());


      f.frame.getTitleBar().setCloseListener(e -> {
        if(f.closeFunction != null) {
          try {
            f.closeFunction.run();
            f.frame.dispose();
          } catch (ExitHalt ex) {
            System.exit(0);
          } catch (BreakPointHalt ex) {
            throw new RuntimeException(ex);
          }
        }
      });


      return parent;
    }

    @Override
    public int getRequiredPermissionLevel() {
      return 0;
    }

    public String getName() {
      return "new_frame";
    }

  }
}
