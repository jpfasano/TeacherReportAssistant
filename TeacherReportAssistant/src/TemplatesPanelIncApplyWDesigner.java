
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TemplatesPanelIncApplyWDesigner extends JPanel {
	private TeacherReportAssistant tra;

	private ArrayList<TemplateItemCheckBoxWithDesigner> templateCheckBoxesInPanel = new ArrayList<TemplateItemCheckBoxWithDesigner>();

	/*
	 * public TemplatePanel() { super(); tra=null; }
	 */
	public TemplatesPanelIncApplyWDesigner(TeacherReportAssistant tra, TemplateCategory tc) {

		super();

		this.tra = tra;

		ArrayList<String> ts = tc.getTemplates();

		JPanel checkBoxPanel = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.001;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;

		// Loop once for each template in category tc
		// for (String t : ts){
		for (int i = 0; i < ts.size(); i++) {
			String t = ts.get(i);
			TemplateItemCheckBoxWithDesigner tcb = new TemplateItemCheckBoxWithDesigner(t);

			// System.out.println(t+" "+i);
			// {
			// JFrame jf = new JFrame();
			//
			// jf.getContentPane().add(new TemplateItemCheckBoxWithDesigner(t));
			//
			// jf.setTitle("Test TemplateItemCheckBoxWithDesigner Panel");
			// jf.setSize(620, 750);
			// jf.pack();
			// jf.setVisible(true);
			// }

			// if last checkbox being added then allow space below it
			// if (i == ts.size() - 1)
			// gbc.weightx = 1.0;

			checkBoxPanel.add(tcb, gbc);
			templateCheckBoxesInPanel.add(tcb);

			gbc.gridy++;
			gbc.anchor = GridBagConstraints.WEST;

			// tcbp.addItemListener(this);
		}

		// Add additional empty space holders so all tabbed panels are similarly
		// sized.  There should not be needed if I better understoon gridbaglayout.
		for (int i = ts.size(); i < tra.getTemplateCategories().maxTemplateCategorySize(); i++) {
			
			JLabel  l = new JLabel(" ");

			checkBoxPanel.add(l, gbc);

			gbc.gridy++;
			gbc.anchor = GridBagConstraints.WEST;

		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(checkBoxPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JButton applyButton = new JButton("Apply");

		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Perform tasks associated with pressing apply button.
				apply();
			}
		});

		this.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		this.add(scrollPane, gbc);
		// this.add(checkBoxPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(applyButton, gbc);

	}

	public ArrayList<TemplateItemCheckBoxWithDesigner> getTemplateCheckBoxesInPanel() {
		return templateCheckBoxesInPanel;
	}

	public void uncheckBoxes() {
		for (TemplateItemCheckBoxWithDesigner tcb : templateCheckBoxesInPanel) {
			tcb.setSelected(false);
		}
	}

	public String apply() {
		String retVal = "";
		for (TemplateItemCheckBoxWithDesigner tcb : templateCheckBoxesInPanel) {
			if (tcb.isSelected())
				retVal += (tcb.getTemplateWoComment() + " ");
		}
		retVal = retVal.trim();

		uncheckBoxes();

		// Substitute _NAME with student name.
		String sn = tra.getStudentName();
		retVal = retVal.replace("_NAME", sn);

		// Replace gender specific pronouns.
		Map<String, GenderWordPair> gwd = tra.getGenderWordsDict();
		String sg = tra.getStudentGender();
		for (Map.Entry<String, GenderWordPair> entry : gwd.entrySet()) {
			String needle = "_" + entry.getKey().toUpperCase().trim();
			String replacement = entry.getValue().getGenderWord(sg).trim();

			// Create upper case first character replacement
			char[] r1 = replacement.toCharArray();
			r1[0] = Character.toUpperCase(r1[0]);
			String replacementUC = new String(r1);

			// If needle is at beginning of sentence then replacement must start
			// with an uppercase character
			if (retVal.indexOf(needle) == 0) {
				retVal = replacementUC + retVal.substring(needle.length());
			}

			// Look for the needle at the beginning of a sentence.
			String[] endSent = { ".", "?", "!" };
			for (String es : endSent) {
				String needle1 = es + " " + needle;
				retVal = retVal.replace(needle1, es + " " + replacementUC);
				needle1 = es + "  " + needle;
				retVal = retVal.replace(needle1, es + "  " + replacementUC);
			}
			retVal = retVal.replace(needle, replacement);
		}

		tra.getContentPanel().insertEditableText(retVal);

		return retVal;
	}

}
