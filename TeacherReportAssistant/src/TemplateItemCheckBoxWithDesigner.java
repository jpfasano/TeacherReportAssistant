import javax.swing.JPanel;
import javax.swing.JCheckBox;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class TemplateItemCheckBoxWithDesigner extends JPanel {

	
	 private String templateWoComment;
	 private String comment;
	 private JCheckBox checkBox;
	 
	/**
	 * Create the panel.
	 */
	public TemplateItemCheckBoxWithDesigner(String template) {
		
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
		gridBagLayout.columnWidths = new int[]{97, 0};
		gridBagLayout.rowHeights = new int[]{5,1, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		checkBox = new JCheckBox(template);
		checkBox.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.fill = GridBagConstraints.BOTH;
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.NORTHWEST;
		//gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 0;
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
