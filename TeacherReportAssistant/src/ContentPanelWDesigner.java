import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class ContentPanelWDesigner extends JPanel
{
  
  //private TeacherReportAssistant tra;
  private StudentPanel studentPanel;
  private EditableTextPanel editableTextPanel;
  private TabbedTemplatePanel2WDesigner tabbedTemplatePanel2WDesigner;
  
 

  public ContentPanelWDesigner(TeacherReportAssistant tra)
  {
    super();
    //this.tra=tra;

    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.rowWeights = new double[]{0.0};
    gridBagLayout.columnWeights = new double[]{1.0};
    this.setLayout(gridBagLayout);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 0, 0, 0);
    
    
    // There are 3 parts to the content panel: Students, Templates, and Editable text.
    // Create each one and add to the panel.

    // Student Panel
    studentPanel=new StudentPanel(tra);
//    GridBagLayout gridBagLayout_1 = (GridBagLayout) studentPanel.getLayout();
//    gridBagLayout_1.rowWeights = new double[]{0.0, 1.0};
//    gridBagLayout_1.columnWeights = new double[]{0.0, 1.0, 0.0};
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = .0;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(studentPanel,gbc);
    
    JSplitPane splitPane = new JSplitPane();
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    GridBagConstraints gbc_splitPane = new GridBagConstraints();
    gbc_splitPane.gridwidth = 1;
    //gbc_splitPane.insets = new Insets(0, 0, 0, 5);
    gbc_splitPane.fill = GridBagConstraints.BOTH;
    gbc_splitPane.gridx = 0;
    gbc_splitPane.gridy = 1;
    gbc_splitPane.weightx = 1.0;
    gbc_splitPane.weighty = .9;
    this.add(splitPane, gbc_splitPane);
    
    editableTextPanel = new EditableTextPanel(tra);
    splitPane.setRightComponent(editableTextPanel);
    
    tabbedTemplatePanel2WDesigner = new TabbedTemplatePanel2WDesigner(tra);
    splitPane.setLeftComponent(tabbedTemplatePanel2WDesigner);
  
   
   
  }

  public void clearEditableText()
  {
	  getEditableTextPanel().clearEditableText();
  }
  public void insertEditableText(String t)
  {
	  getEditableTextPanel().insert(t) ;    
  }

  public void setEditableText(String t)
  {
	  getEditableTextPanel().setText(t) ;    
  }
  
  public String getEditableTest()
  {
    // First update editable text to contain any current checked templates
	  getTabbedTemplatePanel().apply();
    return getEditableTextPanel().getText();
  }
  
  public void setFocusToFirstTab()
  {
    if(getTabbedTemplatePanel()!=null)
    	getTabbedTemplatePanel().setFocusToFirstTab();
  }

  
  public void updateStudentNameLabel(Student student)
  {
    studentPanel.updateStudentNameLabel(student);
  }

	public EditableTextPanel getEditableTextPanel() {
		return editableTextPanel;
	}
	public TabbedTemplatePanel2WDesigner getTabbedTemplatePanel() {
		return tabbedTemplatePanel2WDesigner;
	}
}
