import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TemplateItemCheckBoxWithDesigner extends JPanel {

	
	 private String templateWoComment;
	 private String comment;
	 private JCheckBox checkBox;
	 
	/**
	 * Create the panel.
	 */
	public TemplateItemCheckBoxWithDesigner(String template) {
		
		super();
	    this.setMinimumSize(new Dimension(200,18));
		
	    // find comment character if present
	    int c=template.indexOf("#");
	    if (c==-1) {
	      comment="";
	      templateWoComment=template.trim();
	    }
	    else {
	      comment=template.substring(0,c+1);
	      templateWoComment=template.substring(c+1).trim();
	    }
	    
		
		GridBagLayout gridBagLayout = new GridBagLayout();
//		gridBagLayout.columnWidths = new int[]{97, 0};
//		gridBagLayout.rowHeights = new int[]{5,1, 0};
//		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
//		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//if (!template.trim().equals("")) {
		checkBox = new JCheckBox(template);
		//}
		//else {
		//	checkBox = new JLabel(" ");
		//}
		//checkBox.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.fill = GridBagConstraints.BOTH;
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.NORTHWEST;
		//gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 0;

		gbc_chckbxNewCheckBox.weightx = 1.0;
		gbc_chckbxNewCheckBox.weighty = 0.0001;

	    setMinimumSize(new Dimension(600,18));
		add(checkBox, gbc_chckbxNewCheckBox);
		
		

	}

	public void setSelected(boolean b) {
		checkBox.setSelected(b);
		
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}
	
	 public String getTemplateWoComment()
	  {
	    return templateWoComment;
	  }

	  public String getComment()
	  {
	    return comment;
	  }
	  
	  public String getTemplate() {
	    return getComment()+getTemplateWoComment();
	  }

}
