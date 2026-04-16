package jamiebalfour.zpe.ui.core;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ZPEFileChooser {

  public static String chooseFile(String[] extensions) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    if(extensions != null) {
      FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
          if (pathname.isDirectory()) {
            return true;
          }

          String name = pathname.getName().toLowerCase();

          for (String ext : extensions) {
            if (name.endsWith("." + ext)) {
              return true;
            }
          }

          return false;
        }

        @Override
        public String getDescription() {
          return "Supported files (" + String.join(", ", extensions) + ")";
        }
      };
      chooser.setFileFilter(fileFilter);
    }



    int result = chooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      return file.getAbsolutePath();
    }

    return null;
  }

  public static String chooseFolder() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    int result = chooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      return file.getAbsolutePath();
    }

    return null;
  }
}
