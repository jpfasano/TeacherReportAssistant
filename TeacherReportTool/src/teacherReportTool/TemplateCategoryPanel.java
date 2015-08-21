/*******************************************************************************
 * Copyright (c) 2015 JP Fasano.
 *
 * This file is part of the TeacherReportTool.
 *
 * TeacherReportTool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TeacherReportTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TeacherReportTool.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package teacherReportTool;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TemplateCategoryPanel extends JPanel {
   private TeacherReportTool trt;

   private ArrayList<TemplateSentencePanel> templateSentencePanels = new ArrayList<TemplateSentencePanel>();

   public TemplateCategoryPanel(TeacherReportTool trt, TemplateCategory tc) {

      super();

      this.trt = trt;

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
         TemplateSentencePanel tcb = new TemplateSentencePanel(t);

         checkBoxPanel.add(tcb, gbc);
         templateSentencePanels.add(tcb);

         gbc.gridy++;
         gbc.anchor = GridBagConstraints.WEST;

         // tcbp.addItemListener(this);
      }

      // Add additional empty space holders so all tabbed panels are similarly
      // sized. There should not be needed if I better understoon gridbaglayout.
      for (int i = ts.size(); i < trt.getTemplateCategories().maxTemplateCategorySize(); i++) {

         JLabel l = new JLabel(" ");

         checkBoxPanel.add(l, gbc);

         gbc.gridy++;
         gbc.anchor = GridBagConstraints.WEST;

      }

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(checkBoxPanel);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setPreferredSize(new Dimension(600, 240));

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

   public ArrayList<TemplateSentencePanel> getTemplateCheckBoxesInPanel() {
      return templateSentencePanels;
   }

   public void uncheckBoxes() {
      for (TemplateSentencePanel tcb : templateSentencePanels) {
         tcb.setSelected(false);
      }
   }

   public String apply() {
      String retVal = "";
      for (TemplateSentencePanel tcb : templateSentencePanels) {
         if (tcb.isSelected())
            retVal += (tcb.getTemplateWoComment() + " ");
      }
      retVal = retVal.trim();

      uncheckBoxes();

      // Substitute _NAME with student name.
      String sn = trt.getStudentName();
      retVal = retVal.replace("_NAME", sn);

      // Replace gender specific pronouns.
      Map<String, GenderWordPair> gwd = trt.getGenderWordsDict();
      String sg = trt.getStudentGender();
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
         String[] endSent = {".", "?", "!"};
         for (String es : endSent) {
            String needle1 = es + " " + needle;
            retVal = retVal.replace(needle1, es + " " + replacementUC);
            needle1 = es + "  " + needle;
            retVal = retVal.replace(needle1, es + "  " + replacementUC);
         }
         retVal = retVal.replace(needle, replacement);
      }

      trt.getContentPanel().insertEditableText(retVal);

      return retVal;
   }

}
