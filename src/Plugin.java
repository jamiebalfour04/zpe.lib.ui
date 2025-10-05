import jamiebalfour.zpe.ZPEUIBuilderObject;
import jamiebalfour.zpe.core.ZPEStructure;
import jamiebalfour.zpe.interfaces.ZPECustomFunction;
import jamiebalfour.zpe.interfaces.ZPELibrary;

import java.util.HashMap;
import java.util.Map;

public class Plugin implements ZPELibrary {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }

  @Override
  public Map<String, ZPECustomFunction> getFunctions() {
    return null;
  }

  @Override
  public Map<String, Class<? extends ZPEStructure>> getObjects() {
    Map<String, Class<? extends ZPEStructure>> m = new HashMap<>();
    m.put("UIBuilder", ZPEUIBuilderObject.class);
    return m;
  }

  @Override
  public boolean supportsWindows() {
    return true;
  }

  @Override
  public String getName() {
    return "libUI";
  }

  @Override
  public String getVersionInfo() {
    return "1.0";
  }

  @Override
  public boolean supportsMacOs(){
    return true;
  }

  @Override
  public boolean supportsLinux() {
    return true;
  }


}