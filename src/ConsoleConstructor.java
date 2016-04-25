import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/**
 * Creates the JPanel that will display all System.ou.println() statements
 *
 * @author Chris
 * @see ConsoleStream
 */
public class ConsoleConstructor extends JPanel {

    JTextArea textArea = new JTextArea(15, 30);

    ConsoleStream cStream = new ConsoleStream(textArea, "☠");

    public ConsoleConstructor() {
        setLayout(new BorderLayout());
        JScrollPane p= new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar bar= new JScrollBar();
        bar.setBackground(Color.decode("#424242"));
        bar.setForeground(Color.decode("#E0E0E0"));
        bar.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        p.setVerticalScrollBar(bar);
        p.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        add(p);
        System.setOut(new PrintStream(cStream));
        System.setErr(new PrintStream(cStream));
    }

    /**
     * Clears the text within textArea.
     * Is used when the start button is clicked on the UI.
     */
    public void clear()
    {
        textArea.setText("");
    }
}
