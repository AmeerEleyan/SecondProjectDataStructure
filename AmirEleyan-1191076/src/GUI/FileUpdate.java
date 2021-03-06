/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 14/4/2021  10:14 AM
 */
package GUI;

import Shares.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUpdate {

    /**
     * To print queue to the file(update)
     */
    public static void update(String fileName, Object list) {
        try {
            File file = new File(fileName);
            PrintWriter writer = new PrintWriter(file);
            writer.println(list.toString());
            writer.close();
        } catch (IOException exception) {
            Message.displayMassage("Warning", exception.getMessage());
        }
    }
}
