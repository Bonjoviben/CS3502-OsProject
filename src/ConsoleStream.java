import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Captures all System.out and System.err
 * Controls the colors of the text.
 *
 * @author Chris
 */
public class ConsoleStream extends OutputStream {

    private final JTextArea console;
    private final StringBuilder builder = new StringBuilder();
    private String osName;

    public ConsoleStream(final JTextArea text, String name)
    {
        this.console = text;
        text.setForeground(Color.decode("#8BC34A"));
        text.setBackground(Color.decode("#303030"));
        this.osName = name;
        builder.append(name + ">> ");
    }

    @Override
    public void flush() {}

    @Override
    public void close() {}

    @Override
    public void write(int a) throws IOException {

        if (a == '\r')
            return;

        if (a == '\n') {
            final String text = builder.toString() + "\n";
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    console.append(text);
                }
            });
            builder.setLength(0);
            builder.append(osName + ">> ");
            return;
        }

        builder.append((char) a);
    }
}
