package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

public class Editor extends javax.swing.JPanel {

    private AlgorithmManager tsp, mm, mis, mds, meds;
    private static JRadioButton radioSelected = null;
    private static Thread calculatingThread = null;
    private String log;

    public Editor() {
        initComponents();

        //Imposto il logger
        this.log = new String();
        this.graphArea.setLogger(new Logger() {

            @Override
            public void log(Object s) {
                String str = String.valueOf(s);
                System.out.println(str);

                log = log.replace("<b>", "");
                log = log.replace("</b>", "");
                log = log.replace("&nbsp", "");

                int a = str.indexOf(':') + 1;
                int b = str.indexOf('(');

                log += "<b>" + str.substring(0, a);
                log += "<font color=\"red\">" + str.substring(a, b) + "</font>";
                log += str.substring(b) + "</b><br>&nbsp";

                logArea.setText("<font face=\"Trebuchet MS\">" + log + "</font>");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
        });

        //Configuro il listener per gli strumenti
        ActionListener action = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selectTool((JToggleButton) e.getSource());
            }
        };
        this.selectionButton.addActionListener(action);
        this.newVerticeButton.addActionListener(action);
        this.newEdgeButton.addActionListener(action);

        //Configuro il listener per i radio button
        ActionListener radioListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton radio = (JRadioButton) e.getSource();
                if (radio != radioSelected) {
                    radioSelected = radio;

                    graphArea.clearAlgorithmSolutions();
                    setGeneticButtonText(false);
                    String text = radio == tspRadio ? "Travelling Salesman" : radio.getText();
                    geneticButton.setToolTipText("<html>Finds a good solution for the "
                            + "<b>" + text + " Problem</b> using a <b>Genetic Algorithm</b></html>");
                    bruteforceButton.setToolTipText("<html>Finds the optimal solution for the "
                            + "<b>" + text + " Problem</b> using the <b>Brute-Force Search</b></html>");
                }
            }
        };
        this.tspRadio.addActionListener(radioListener);
        this.tspRadio.doClick();
        this.mmRadio.addActionListener(radioListener);
        this.misRadio.addActionListener(radioListener);
        this.mdsRadio.addActionListener(radioListener);
        this.medsRadio.addActionListener(radioListener);

        //Configuro i thread di calcolo degli algoritmi
        this.tsp = new AlgorithmManager() {

            @Override
            public boolean algorithm(boolean genetic) throws InterruptedException {
                graphArea.findTSPSolution(genetic);
                return graphArea.isTSPCalculated();
            }
        };
        this.mm = new AlgorithmManager() {

            @Override
            public boolean algorithm(boolean genetic) throws InterruptedException {
                graphArea.findMMSolution(genetic);
                return graphArea.isMMCalculated();
            }
        };
        this.mis = new AlgorithmManager() {

            @Override
            public boolean algorithm(boolean genetic) throws InterruptedException {
                graphArea.findMISSolution(genetic);
                return graphArea.isMISCalculated();
            }
        };
        this.mds = new AlgorithmManager() {

            @Override
            public boolean algorithm(boolean genetic) throws InterruptedException {
                graphArea.findMDSSolution(genetic);
                return graphArea.isMDSCalculated();
            }
        };
        this.meds = new AlgorithmManager() {

            @Override
            public boolean algorithm(boolean genetic) throws InterruptedException {
                graphArea.findMEDSSolution(genetic);
                return graphArea.isMEDSCalculated();
            }
        };
    }

    private void selectTool(JToggleButton button) {
        this.selectionButton.setSelected(this.selectionButton == button);
        this.newVerticeButton.setSelected(this.newVerticeButton == button);
        this.newEdgeButton.setSelected(this.newEdgeButton == button);

        if (this.selectionButton == button) {
            graphArea.setTool(GraphArea.TOOL_SELECT);
        } else if (this.newVerticeButton == button) {
            graphArea.setTool(GraphArea.TOOL_NEW_VERTICE);
        } else if (this.newEdgeButton == button) {
            graphArea.setTool(GraphArea.TOOL_NEW_EDGE);
        }
        this.setGeneticButtonText(false);
    }

    private void setGeneticButtonText(boolean b) {
        this.geneticButton.setText(b ? "Raffinate Solution"
                : "<html><center>Solve with a<br>Genetic Algorithm</html>");
    }

    private void setButtonsEnabled(boolean b) {
        this.selectionButton.setEnabled(b);
        this.newVerticeButton.setEnabled(b);
        this.newEdgeButton.setEnabled(b);
        this.clearEdgesButton.setEnabled(b);
        this.clearGraphButton.setEnabled(b);
        this.completeButton.setEnabled(b);
        this.autolinkButton.setEnabled(b);
        this.geneticButton.setEnabled(b);
        this.bruteforceButton.setEnabled(b);
    }

    private void deselectTools() {
        this.selectionButton.setSelected(false);
        this.newVerticeButton.setSelected(false);
        this.newEdgeButton.setSelected(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.ButtonGroup npcRadioGroup = new javax.swing.ButtonGroup();
        javax.swing.JPanel settingsPanel = new javax.swing.JPanel();
        javax.swing.JLabel toolsLabel = new javax.swing.JLabel();
        selectionButton = new javax.swing.JToggleButton();
        newVerticeButton = new javax.swing.JToggleButton();
        newEdgeButton = new javax.swing.JToggleButton();
        clearEdgesButton = new javax.swing.JButton();
        clearGraphButton = new javax.swing.JButton();
        completeButton = new javax.swing.JButton();
        autolinkButton = new javax.swing.JButton();
        javax.swing.JLabel npcLabel = new javax.swing.JLabel();
        javax.swing.JPanel radioPanel = new javax.swing.JPanel();
        tspRadio = new javax.swing.JRadioButton();
        mmRadio = new javax.swing.JRadioButton();
        misRadio = new javax.swing.JRadioButton();
        mdsRadio = new javax.swing.JRadioButton();
        medsRadio = new javax.swing.JRadioButton();
        geneticButton = new javax.swing.JButton();
        bruteforceButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        javax.swing.JSplitPane splitPane = new javax.swing.JSplitPane();
        graphArea = new gui.GraphArea();
        javax.swing.JScrollPane logScrollPane = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(0, 0, 0));

        settingsPanel.setBackground(new java.awt.Color(232, 211, 140));

        toolsLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        toolsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toolsLabel.setText("Tools");
        toolsLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        selectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cursor.png"))); // NOI18N
        selectionButton.setToolTipText("Select a graph element");
        selectionButton.setFocusable(false);

        newVerticeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vertice.png"))); // NOI18N
        newVerticeButton.setToolTipText("<html>Creates a new <b>vertice</b></html>");
        newVerticeButton.setFocusable(false);

        newEdgeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edge.png"))); // NOI18N
        newEdgeButton.setToolTipText("<html>Creates a new <b>edge</b></html>");
        newEdgeButton.setFocusable(false);

        clearEdgesButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        clearEdgesButton.setText("<html><center>Remove<br>Edges</html>");
        clearEdgesButton.setToolTipText("Removes every edges in the graph");
        clearEdgesButton.setFocusable(false);
        clearEdgesButton.setOpaque(false);
        clearEdgesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearEdgesButtonActionPerformed(evt);
            }
        });

        clearGraphButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        clearGraphButton.setText("<html><center>Clear<br>Graph</html>");
        clearGraphButton.setToolTipText("Clears the graph");
        clearGraphButton.setFocusable(false);
        clearGraphButton.setOpaque(false);
        clearGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearGraphButtonActionPerformed(evt);
            }
        });

        completeButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        completeButton.setText("<html><center>Complete<br>Graph</html>");
        completeButton.setToolTipText("Completes the graph");
        completeButton.setFocusable(false);
        completeButton.setOpaque(false);
        completeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completeButtonActionPerformed(evt);
            }
        });

        autolinkButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        autolinkButton.setText("<html><center>Autolink</html>");
        autolinkButton.setToolTipText("Links every vertices to the closest four");
        autolinkButton.setFocusable(false);
        autolinkButton.setOpaque(false);
        autolinkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autolinkButtonActionPerformed(evt);
            }
        });

        npcLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        npcLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        npcLabel.setText("NP-C Problems");
        npcLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        radioPanel.setOpaque(false);
        radioPanel.setLayout(new java.awt.GridLayout(5, 1));

        npcRadioGroup.add(tspRadio);
        tspRadio.setFont(new java.awt.Font("Trebuchet MS", 0, 12));
        tspRadio.setText("<html>Travelling Salesman Problem</html>");
        tspRadio.setToolTipText("Shortest hamiltonian cycle");
        tspRadio.setFocusable(false);
        tspRadio.setIconTextGap(7);
        tspRadio.setOpaque(false);
        radioPanel.add(tspRadio);

        npcRadioGroup.add(mmRadio);
        mmRadio.setFont(new java.awt.Font("Trebuchet MS", 0, 12));
        mmRadio.setText("Maximum Matching");
        mmRadio.setToolTipText("Biggest subset of non-adjacent edges");
        mmRadio.setFocusable(false);
        mmRadio.setIconTextGap(7);
        mmRadio.setOpaque(false);
        radioPanel.add(mmRadio);

        npcRadioGroup.add(misRadio);
        misRadio.setFont(new java.awt.Font("Trebuchet MS", 0, 12));
        misRadio.setText("Maximum Indipendent Set");
        misRadio.setToolTipText("Biggest subset of non-linked vertices");
        misRadio.setFocusable(false);
        misRadio.setIconTextGap(7);
        misRadio.setOpaque(false);
        radioPanel.add(misRadio);

        npcRadioGroup.add(mdsRadio);
        mdsRadio.setFont(new java.awt.Font("Trebuchet MS", 0, 12));
        mdsRadio.setText("Minimum Dominating Set");
        mdsRadio.setToolTipText("Smallest subset of dominating vertices");
        mdsRadio.setFocusable(false);
        mdsRadio.setIconTextGap(7);
        mdsRadio.setOpaque(false);
        radioPanel.add(mdsRadio);

        npcRadioGroup.add(medsRadio);
        medsRadio.setFont(new java.awt.Font("Trebuchet MS", 0, 12));
        medsRadio.setText("Minimum Edge Dominating Set");
        medsRadio.setToolTipText("Smallest subset of dominating edges");
        medsRadio.setFocusable(false);
        medsRadio.setIconTextGap(7);
        medsRadio.setOpaque(false);
        radioPanel.add(medsRadio);

        geneticButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        geneticButton.setText("<html><center>Solve with a<br>Genetic Algorithm</html>");
        geneticButton.setFocusable(false);
        geneticButton.setOpaque(false);
        geneticButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geneticButtonActionPerformed(evt);
            }
        });

        bruteforceButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        bruteforceButton.setText("<html><center>Solve with the<br>Brute-Force Search</html>");
        bruteforceButton.setFocusable(false);
        bruteforceButton.setOpaque(false);
        bruteforceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bruteforceButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.setOpaque(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(npcLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(toolsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsPanelLayout.createSequentialGroup()
                                .addComponent(selectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newVerticeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newEdgeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsPanelLayout.createSequentialGroup()
                                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(completeButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(clearEdgesButton, 0, 0, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(autolinkButton, 0, 0, Short.MAX_VALUE)
                                    .addComponent(clearGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(geneticButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                            .addComponent(bruteforceButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                .addContainerGap())
        );

        settingsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {autolinkButton, clearEdgesButton, clearGraphButton, completeButton});

        settingsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bruteforceButton, geneticButton});

        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newVerticeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newEdgeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearEdgesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(clearGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(autolinkButton)
                    .addComponent(completeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addComponent(npcLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(geneticButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bruteforceButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelButton)
                .addGap(29, 29, 29))
        );

        settingsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {autolinkButton, clearEdgesButton, clearGraphButton, completeButton});

        settingsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bruteforceButton, geneticButton});

        splitPane.setDividerLocation(550);
        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        BasicSplitPaneDivider dividerContainer = (BasicSplitPaneDivider) splitPane.getComponent(2);
        dividerContainer.setBackground(Color.black);
        splitPane.setResizeWeight(1.0);

        javax.swing.GroupLayout graphAreaLayout = new javax.swing.GroupLayout(graphArea);
        graphArea.setLayout(graphAreaLayout);
        graphAreaLayout.setHorizontalGroup(
            graphAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );
        graphAreaLayout.setVerticalGroup(
            graphAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
        );

        splitPane.setTopComponent(graphArea);

        logScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setAutoscrolls(true);

        logArea.setBackground(new java.awt.Color(232, 211, 140));
        logArea.setContentType("text/html");
        logArea.setEditable(false);
        logScrollPane.setViewportView(logArea);

        splitPane.setBottomComponent(logScrollPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearEdgesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearEdgesButtonActionPerformed
        this.graphArea.removeEdges();
        this.setGeneticButtonText(false);
}//GEN-LAST:event_clearEdgesButtonActionPerformed

    private void clearGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearGraphButtonActionPerformed
        this.graphArea.clearGraph();
        this.log = "";
        this.logArea.setText(null);
        this.setGeneticButtonText(false);
}//GEN-LAST:event_clearGraphButtonActionPerformed

    private void completeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completeButtonActionPerformed
        this.graphArea.complete();
        this.setGeneticButtonText(false);
}//GEN-LAST:event_completeButtonActionPerformed

    private void autolinkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autolinkButtonActionPerformed
        this.graphArea.autolink();
        this.setGeneticButtonText(false);
    }//GEN-LAST:event_autolinkButtonActionPerformed

    private AlgorithmManager getAlgoManagerFromRadio() {
        if (this.tspRadio.isSelected()) {
            return this.tsp;
        } else if (this.mmRadio.isSelected()) {
            return this.mm;
        } else if (this.misRadio.isSelected()) {
            return this.mis;
        } else if (this.mdsRadio.isSelected()) {
            return this.mds;
        } else if (this.medsRadio.isSelected()) {
            return this.meds;
        }
        return null;
    }

    private void geneticButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geneticButtonActionPerformed
        this.getAlgoManagerFromRadio().startGA();
    }//GEN-LAST:event_geneticButtonActionPerformed

    private void bruteforceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bruteforceButtonActionPerformed
        this.getAlgoManagerFromRadio().startBFS();
    }//GEN-LAST:event_bruteforceButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (calculatingThread.isAlive()) {
            calculatingThread.interrupt();
        }
        this.graphArea.clearAlgorithmSolutions();
        this.setGeneticButtonText(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private abstract class AlgorithmManager implements Runnable {

        private boolean genetic;

        public void startGA() {
            this.genetic = true;
            calculatingThread = new Thread(this);
            calculatingThread.start();
        }

        public void startBFS() {
            this.genetic = false;
            calculatingThread = new Thread(this);
            calculatingThread.start();
        }

        @Override
        public void run() {
            boolean calculated = false;
            try {
                graphArea.setBackground(Color.gray);

                if (!genetic) {
                    setGeneticButtonText(false);
                }
                deselectTools();
                setButtonsEnabled(false);

                calculated = algorithm(genetic);

            } catch (InterruptedException ex) {
            } finally {
                graphArea.setBackground(Color.white);
                setButtonsEnabled(true);

                if (genetic) {
                    setGeneticButtonText(calculated);
                }
            }
        }

        public abstract boolean algorithm(boolean genetic) throws InterruptedException;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton autolinkButton;
    private javax.swing.JButton bruteforceButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton clearEdgesButton;
    private javax.swing.JButton clearGraphButton;
    private javax.swing.JButton completeButton;
    private javax.swing.JButton geneticButton;
    protected gui.GraphArea graphArea;
    private javax.swing.JTextPane logArea;
    private javax.swing.JRadioButton mdsRadio;
    private javax.swing.JRadioButton medsRadio;
    private javax.swing.JRadioButton misRadio;
    private javax.swing.JRadioButton mmRadio;
    private javax.swing.JToggleButton newEdgeButton;
    private javax.swing.JToggleButton newVerticeButton;
    private javax.swing.JToggleButton selectionButton;
    private javax.swing.JRadioButton tspRadio;
    // End of variables declaration//GEN-END:variables
}