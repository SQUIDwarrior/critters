/*
 * Copyright (c) 2013 Mike Deats
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created on May 4, 2013
 */

package critters.gui;

import critters.Critter;
import critters.type.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author  mike
 */
public class CritterConfigurationPanel extends javax.swing.JPanel {
    
    private static String[] bleh = {"D"};

    private Class<? extends Critter>critterType;
    private int pop;

		private int max;
    /** Creates new form CritterConfigurationPanel */
    public CritterConfigurationPanel() {
        initComponents();
    }
    
    public CritterConfigurationPanel(Collection<Class<? extends Critter>> types)
    {
        initComponents();
        typeChooser.removeAllItems();
        for(Class<? extends Critter>type : types)
        { 
            typeChooser.addItem(type);
        }
        typeChooser.setSelectedIndex(0);
        
        typeChooser.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						setType((Class<? extends Critter>) typeChooser.getSelectedItem(), pop, max);
						
					}});
        
        populationSlider.addChangeListener(new ChangeListener(){

					public void stateChanged(ChangeEvent e) {
						setPopulation(populationSlider.getValue());
						
					}});
        
    }
    
    public void setType(Class<? extends Critter> critterType, int pop, int max)
    {
    	this.critterType = critterType;
    	this.max = max;
      try {

        Critter critter = critterType.newInstance();
        descriptionArea.setText(critter.getDescription());
        
        
    } catch (InstantiationException ex) {
    	ex.printStackTrace();
        Logger.getLogger(CritterConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
    	ex.printStackTrace();
        Logger.getLogger(CritterConfigurationPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    populationSlider.setMaximum(max);

    typeLabel.setText(critterType.getSimpleName());
    
    typeChooser.setSelectedItem(critterType);
    
    setPopulation(pop);
    }
    
    public void setPopulation(int pop)
    {
    	System.out.println(pop);
    	this.pop = pop;
      populationSlider.setValue(pop);
      populationCountLabel.setText("Count: " + pop + ", Max: " + max);
      
    }
    
    public Class<? extends Critter> getCritterType()
    {
    	return critterType;
    }
    
    public int getPopulation()
    {
    	return pop;
    }
    
    public int getMaxPopulation()
    {
    	return max;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descriptionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        configPanel = new javax.swing.JPanel();
        typeLabel = new javax.swing.JLabel();
        populationSlider = new javax.swing.JSlider();
        typeChooser = new javax.swing.JComboBox();
        populationCountLabel = new javax.swing.JLabel();

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));

        descriptionArea.setColumns(20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setRows(5);
        jScrollPane1.setViewportView(descriptionArea);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );

        configPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));

        typeLabel.setText("Type");

        typeChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addComponent(typeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(populationCountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
            .addComponent(populationSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(typeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(populationCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(populationSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(configPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(configPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel configPanel;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel populationCountLabel;
    private javax.swing.JSlider populationSlider;
    private javax.swing.JComboBox typeChooser;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
    
}
