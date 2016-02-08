package View;
       
//package net.jcdev.utils;

//Import the GUI classes
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Controler.SetContainerControler;
import Controler.SetControler;

/**
 * A JOutlookBar provides a component that is similar to a JTabbedPane, but
 * instead of maintaining tabs, it uses Outlook-style bars to control the
 * visible component
 */
public class JAccordion extends JPanel implements ActionListener, MouseListener {
  private static final long serialVersionUID = -2760245005186933366L;
  
  
    /**
   * The top panel: contains the buttons displayed on the top of the
   * JOutlookBar
   */
  private JPanel topPanel = new JPanel(new GridLayout(1, 1));

  /**
   * The bottom panel: contains the buttons displayed on the bottom of the
   * JOutlookBar
   */
  private JPanel bottomPanel = new JPanel(new GridLayout(1, 1));

  /**
   * A LinkedHashMap of bars: we use a linked hash map to preserve the order
   * of the bars
   */
  private Map<String, BarInfo> bars = new LinkedHashMap<String, BarInfo>();

  /**
   * The currently visible bar (zero-based index)
   */
  private int visibleBar = 0;

  /**
   * A place-holder for the currently visible component
   */
  private JComponent visibleComponent = null;

  /**
   * Creates a new JOutlookBar; after which you should make repeated calls to
   * addBar() for each bar
   */
  public JAccordion() {
    this.setLayout(new BorderLayout());
    this.add(topPanel, BorderLayout.NORTH);
    this.add(bottomPanel, BorderLayout.SOUTH);
	
     }

  /**
   * Adds the specified component to the JOutlookBar and sets the bar's name
   * 
   * @param name
   *            The name of the outlook bar
   * @param componenet
   *            The component to add to the bar
   */
  public void addBar(String name, JComponent component, SetControler setControler) {
    BarInfo barInfo = new BarInfo(name, component,setControler);
    //barInfo.getButton().addActionListener(this);
    barInfo.getHeader().addMouseListener(this);
    this.bars.put(name, barInfo);
    render();
  }

  /**
   * Adds the specified component to the JOutlookBar and sets the bar's name
   * 
   * @param name
   *            The name of the outlook bar
   * @param icon
   *            An icon to display in the outlook bar
   * @param componenet
   *            The component to add to the bar
   */
  public void addBar(String name, Icon icon, JComponent component) {
    BarInfo barInfo = new BarInfo(name, icon, component);
    barInfo.getButton().addActionListener(this);
    this.bars.put(name, barInfo);
    render();
  }

  /**
   * Removes the specified bar from the JOutlookBar
   * 
   * @param name
   *            The name of the bar to remove
   */
  public void removeBar(String name) {
    this.bars.remove(name);
    render();
  }

  /**
   * Returns the index of the currently visible bar (zero-based)
   * 
   * @return The index of the currently visible bar
   */
  public int getVisibleBar() {
    return this.visibleBar;
  }

  /**
   * Programmatically sets the currently visible bar; the visible bar index
   * must be in the range of 0 to size() - 1
   * 
   * @param visibleBar
   *            The zero-based index of the component to make visible
   */
  public void setVisibleBar(int visibleBar) {
    if (visibleBar > 0 && visibleBar < this.bars.size() - 1) {
      this.visibleBar = visibleBar;
      render();
    }
  }

  /**
   * Causes the outlook bar component to rebuild itself; this means that it
   * rebuilds the top and bottom panels of bars as well as making the
   * currently selected bar's panel visible
   */
  public void render() {
    // Compute how many bars we are going to have where
    int totalBars = this.bars.size();
    int topBars = this.visibleBar + 1;
    int bottomBars = totalBars - topBars;

    // Get an iterator to walk through out bars with
    Iterator<String> itr = this.bars.keySet().iterator();

    // Render the top bars: remove all components, reset the GridLayout to
    // hold to correct number of bars, add the bars, and "validate" it to
    // cause it to re-layout its components
    this.topPanel.removeAll();
    GridLayout topLayout = (GridLayout) this.topPanel.getLayout();
    topLayout.setRows(topBars);
    BarInfo barInfo = null;
    for (int i = 0; i < topBars; i++) {
      String barName = (String) itr.next();
      barInfo = (BarInfo) this.bars.get(barName);
      this.topPanel.add(barInfo.getHeader());
    }
    this.topPanel.validate();

    // Render the center component: remove the current component (if there
    // is one) and then put the visible component in the center of this
    // panel
    if (this.visibleComponent != null) {
      this.remove(this.visibleComponent);
    }
    this.visibleComponent = barInfo.getComponent();
    this.add(visibleComponent, BorderLayout.CENTER);

    // Render the bottom bars: remove all components, reset the GridLayout
    // to
    // hold to correct number of bars, add the bars, and "validate" it to
    // cause it to re-layout its components
    this.bottomPanel.removeAll();
    GridLayout bottomLayout = (GridLayout) this.bottomPanel.getLayout();
    bottomLayout.setRows(bottomBars);
    for (int i = 0; i < bottomBars; i++) {
      String barName = (String) itr.next();
      barInfo = (BarInfo) this.bars.get(barName);
      this.bottomPanel.add(barInfo.getHeader());
    }
    this.bottomPanel.validate();

    // Validate all of our components: cause this container to re-layout its
    // subcomponents
    validate();
  }
  
  
  
  public void updateView(){
	  
	  for(String key : this.bars.keySet()){
		  
		  this.bars.get(key).updateUI();
	  }
  }

  /**
   * Invoked when one of our bars is selected
   */
  public void actionPerformed(ActionEvent e) {
    
	  
	  System.out.println("BT CLIck");
  }
 
  /**
   * Internal class that maintains information about individual Outlook bars;
   * specifically it maintains the following information:
   * 
   * name The name of the bar button The associated JButton for the bar
   * component The component maintained in the Outlook bar
   */
  class BarInfo implements ActionListener {
	  
	  
	  private SetControler setControler;
    /**
     * The name of this bar
     */
    private String name;

    /**
     * The JButton that implements the Outlook bar itself
     */
    private JButton button;
    private JPanel headerPane;
    private JLabel label;

    /**
     * The component that is the body of the Outlook bar
     */
    private JComponent component;

    /**
     * Creates a new BarInfo
     * 
     * @param name
     *            The name of the bar
     * @param component
     *            The component that is the body of the Outlook Bar
     */
    public BarInfo(String name, JComponent component, SetControler setControler) {
      this.name = name;
      this.component = component;
      
      this.setControler = setControler;
      
      this.headerPane = new JPanel();
      headerPane.setLayout(new BorderLayout());
      headerPane.setBorder(BorderFactory.createLineBorder(Color.lightGray));
      
      Color color = new Color(setControler.getSet().getColor_r(),setControler.getSet().getColor_g(),setControler.getSet().getColor_b());
      headerPane.setBackground(color);
      
      label = new JLabel(name);
      headerPane.add(label,BorderLayout.WEST);
      
      this.button = new JButton("+");
      this.button.addActionListener(this);
      
      headerPane.add(button,BorderLayout.EAST);
      
    }

    /**
     * Creates a new BarInfo
     * 
     * @param name
     *            The name of the bar
     * @param icon
     *            JButton icon
     * @param component
     *            The component that is the body of the Outlook Bar
     */
    public BarInfo(String name, Icon icon, JComponent component) {
      this.name = name;
      this.component = component;
      this.button = new JButton(name, icon);
    }

    /**
     * Returns the name of the bar
     * 
     * @return The name of the bar
     */
    public String getName() {
      return this.name;
    }

    /**
     * Sets the name of the bar
     * 
     * @param The
     *            name of the bar
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * Returns the outlook bar JButton implementation
     * 
     * @return The Outlook Bar JButton implementation
     */
    public JButton getButton() {
    	
    	
      return this.button;
    }
    
   
    public JPanel getHeader(){
    	
    	return this.headerPane;
    }

    /**
     * Returns the component that implements the body of this Outlook Bar
     * 
     * @return The component that implements the body of this Outlook Bar
     */
    public void updateUI(){
    	Color color = new Color(setControler.getSet().getColor_r(),setControler.getSet().getColor_g(),setControler.getSet().getColor_b());
        headerPane.setBackground(color);
        String newName = setControler.getSet().getName();
        label.setText(newName);
        
    }
    public JComponent getComponent() {
      return this.component;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.button){
			
			setControler.requestForHandle();
		}
		
	}
  }

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
	 int currentBar = 0;
	    for (Iterator<String> i = this.bars.keySet().iterator(); i.hasNext();) {
	      String barName = (String) i.next();
	      BarInfo barInfo = (BarInfo) this.bars.get(barName);
	      if (barInfo.getHeader() == e.getSource()) {
	        // Found the selected button
	        this.visibleBar = currentBar;
	        render();
	        return;
	      }
	      currentBar++;
	    }
	
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}
}

   