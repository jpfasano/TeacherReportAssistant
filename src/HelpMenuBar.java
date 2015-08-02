/** Help for the TeacherReportAssistant game
 */

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class HelpMenuBar {
  private JFrame jf;

  public void showHelp() {
    String longMessage = "";

    // Read help text from a resource file
    try {

      ClassLoader classLoader = getClass().getClassLoader();
      // File file = new
      // File(classLoader.getResource(".."+pathSeparator+"resources"+pathSeparator+"genderWordPairs.tra").getFile());
      File file = new File(classLoader.getResource(
          "resources/helpText.html").getFile());
      FileInputStream fstream = new FileInputStream(file);
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

      String strLine;

      // Read File Line By Line
      while ((strLine = br.readLine()) != null) {
        longMessage += strLine;
      }
      br.close();
    }
    catch (Exception e) {
      System.err.println(e.getMessage());
    }

    // JTextArea textArea = new JTextArea();
    JTextPane textArea = new JTextPane();
    textArea.setContentType("text/html");
    textArea.setText(longMessage);
    textArea.setEditable(false);

    // wrap a scrollpane around it
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane
        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    // Button to dismiss dialog
    JButton jb = new JButton("OK");
    jb.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jf.dispose();
      }
    });

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    JPanel jp = new JPanel();
    jp.setLayout(gbl);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = .9;
    gbc.weighty = .9;
    gbc.fill = GridBagConstraints.BOTH;
    jp.add(scrollPane, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = .0;
    gbc.weighty = .0;
    gbc.fill = GridBagConstraints.NONE;
    jp.add(jb, gbc);

    jf = new JFrame();
    jf.setContentPane(jp);
    jf.setTitle("TeacherReportAssistant Help");
    jf.setSize(480, 480);
    jf.setVisible(true);

  }

  public static void showAbout() {
    JOptionPane.showMessageDialog(null, "Teacher Report Assistant.\n"
        + "Java Source Code is available on GitHub", "About", // Dialog title
        JOptionPane.PLAIN_MESSAGE);
  }
}
