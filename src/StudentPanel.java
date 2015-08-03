import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;


public class StudentPanel extends JPanel {
  private TeacherReportAssistant tra;
  private JLabel studentNameLabel;

  public StudentPanel(TeacherReportAssistant tra) {
    super();
    this.tra = tra;
    studentNameLabel = new JLabel("First: File-> Open Student Names", JLabel.CENTER);  
    
   BasicArrowButton backButton=new BasicArrowButton(BasicArrowButton.WEST);
    
    backButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        tra.backToPriorStudent();
      } 
     });
    
    BasicArrowButton fowardButton=new BasicArrowButton(BasicArrowButton.EAST);
    
    fowardButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        tra.advanceToNextStudent();
      } 
     });
    
    //studentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = .1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(backButton,gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = .8;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(studentNameLabel,gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weightx = .1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(fowardButton,gbc);

/*
    JFrame jf = new JFrame();
    jf.setContentPane(this);
    jf.setTitle("Test Student Panel");
    jf.setSize(800, 480);
    jf.setVisible(true);
  */  
  }
  
  
  public void updateStudentNameLabel(Student student)
  {
    String sName=student.getName();
    String sGender=student.getGender();
    studentNameLabel.setText(sName+" gender:"+sGender); 
  }

}
