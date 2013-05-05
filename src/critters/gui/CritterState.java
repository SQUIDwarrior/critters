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

import java.util.logging.Level;
import java.util.logging.Logger;

import critters.Critter;

/**
 *
 * @author  mike
 */
public class CritterState extends javax.swing.JPanel {
    
    private String name;
    private int initialPopulation;
    private Class<? extends Critter>critterType;
    /** Creates new form CritterState */
    public CritterState() {
        initComponents();
    }
    
    public CritterState(Class<? extends Critter>critterType, int initialPopulation, int totaPopulation)
    {
        try {
            initComponents();
            this.critterType = critterType;
            Critter critter = critterType.newInstance();
            name = critterType.getSimpleName() + " '" + critter.getSymbol() + "'";

            this.initialPopulation = initialPopulation;

            critterName.setText(name);
            critterCountBar.setString("" + initialPopulation);
            critterCountBar.setMaximum(totaPopulation);
            critterCountBar.setValue(initialPopulation);
            critterCountBar.setMinimum(0);
            critterCountBar.setStringPainted(true);
        } catch (InstantiationException ex) {
            Logger.getLogger(CritterState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CritterState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(int count, int total)
    {
        critterCountBar.setMaximum(total);
        critterCountBar.setValue(count);
        critterCountBar.setString("" + count);
    }
    
    public Class<? extends Critter> getType()
    {
        return critterType;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        critterName = new javax.swing.JLabel();
        critterCountBar = new javax.swing.JProgressBar();

        critterName.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(critterName, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(critterCountBar, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(critterName)
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addComponent(critterCountBar, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar critterCountBar;
    private javax.swing.JLabel critterName;
    // End of variables declaration//GEN-END:variables
    
}
