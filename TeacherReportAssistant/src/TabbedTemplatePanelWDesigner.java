
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabbedTemplatePanelWDesigner extends JPanel
// implements ItemListener
{
  private ArrayList<TemplatesPanelIncApplyWDesigner> templatePanels = new ArrayList<TemplatesPanelIncApplyWDesigner>();
  private TeacherReportAssistant tra;
  JTabbedPane jTabbedPane;

  public TabbedTemplatePanelWDesigner(TeacherReportAssistant tra)
  {
    super();
    this.tra = tra;
    ArrayList<TemplateCategory> templates = tra.getTemplateCategories();
    
    jTabbedPane = new JTabbedPane();
    GridBagConstraints gbc = new GridBagConstraints();

    for (TemplateCategory tc: templates)
    {
      
      //JPanel p = new JPanel(new GridBagLayout());
            
      gbc.gridx=0;
      gbc.gridy=0;
      gbc.weightx=1.0;
      gbc.weighty=1.0;
      gbc.fill = GridBagConstraints.BOTH;

      // Create panel to contain template check boxes
      TemplatesPanelIncApplyWDesigner tpl = new TemplatesPanelIncApplyWDesigner(tra,tc);
      templatePanels.add(tpl);
      
      JScrollPane sp = new JScrollPane(tpl);

      
//      GridBagLayout gridbag = new GridBagLayout() ;
//      gridbag.setConstraints(sp,gbc);
      
      jTabbedPane.add(sp,tc.getName());
    }
    
    // Call apply() when tab is changed;
    ChangeListener changeListner = new ChangeListener() {
      public void stateChanged(ChangeEvent changeEvent) {
        apply();
      }
    };
    jTabbedPane.addChangeListener(changeListner);
    
    gbc.gridx=0;
    gbc.gridy=0;
    gbc.weightx=1.0;
    gbc.weighty=1.0;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(jTabbedPane/*,gbc*/);
  }

  public void uncheckBoxes()
  {
    for (TemplatesPanelIncApplyWDesigner tp : templatePanels)
      for (TemplateItemCheckBoxWithDesigner tcb : tp.getTemplateCheckBoxesInPanel())
        tcb.setSelected(false);
  }
  
  public void setFocusToFirstTab()
  {
    if (jTabbedPane != null) {
      int cnt = jTabbedPane.getTabCount();    
      if (cnt > 0)
          jTabbedPane.setSelectedIndex(0);
    }
  }
  
  
  public String apply()
  {
    // Get text associated with checked boxes
    String retVal="";
    for (TemplatesPanelIncApplyWDesigner tp : templatePanels)
    {
      String paneText=tp.apply();
       retVal+=paneText;
    }
    uncheckBoxes();
    return retVal;
  }
  
  
}



