/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.PhoneController;
import model.Phone;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 *
 * @author admin
 */
public class PhoneCatalogUI extends javax.swing.JFrame {
    
    private final PhoneController controller = new PhoneController();

    private DefaultTableModel tableModel;

    private List<Phone> displayedPhones = new ArrayList<>(); static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PhoneCatalogUI.class.getName());

    /**
     * Creates new form PhoneCatalogUI
     */
    public PhoneCatalogUI() {
        initComponents();
        setLocationRelativeTo(null);
        initializeTable();
        initializeComboBox();
        initializeListeners();
        btnAdd.setBackground(new java.awt.Color(22, 163, 74));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnRemove.setBackground(new java.awt.Color(220, 38, 38));
        btnRemove.setForeground(java.awt.Color.WHITE);
        btnUpdate.setBackground(new java.awt.Color(37, 99, 235));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        
        
        
    controller.addPhone(new Phone(
        "Samsung",
        "Galaxy S24 Ultra",
        LocalDate.of(2024, 1, 17),
        69999
    ));
    controller.addPhone(new Phone(
        "Apple",
        "iPhone 16 Pro",
        LocalDate.of(2024, 9, 20),
        79990
    ));
    controller.addPhone(new Phone(
        "Xiaomi",
        "14 Ultra",
        LocalDate.of(2024, 2, 22),
        28999
    ));
    controller.addPhone(new Phone(
        "Google",
        "Pixel 9 Pro",
        LocalDate.of(2024, 9, 9),
        54999
    ));
    controller.addPhone(new Phone(
        "Asus",
        "Zenfone 11 ultra",
        LocalDate.of(2024, 4, 7),
        69999
    ));
    controller.addPhone(new Phone(
        "Motorola",
        "Razr V3",
        LocalDate.of(2004, 11, 2),
        34999
    ));

refreshTable();
        
    }
    
    private void initializeTable() {

    tableModel = new DefaultTableModel(
        new Object[][]{},
        new String[]{
            "Brand",
            "Model",
            "Date released",
            "Price (PHP)"
        }
                        
    ) 
    
    {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int column) {

            switch (column) {
                case 2:
                    return LocalDate.class;

                case 3:
                    return Double.class;

                default:
                    return String.class;
            }
        }
    };

    tblPhones.setModel(tableModel);
    
    tblPhones.setForeground(
    new java.awt.Color(31, 41, 55)
    );
    tblPhones.setSelectionBackground(
    new java.awt.Color(191, 219, 254)
    );
    tblPhones.setSelectionForeground(
    new java.awt.Color(30, 58, 95)
    );
    tblPhones.setGridColor(
    new java.awt.Color(226, 232, 240)
    );
    tblPhones.setRowHeight(30);
    
    tblPhones.setDefaultRenderer(
    Object.class,
    new javax.swing.table.DefaultTableCellRenderer() {

        @Override
        public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

            java.awt.Component component =
                super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column
                );

            if (isSelected) {
                component.setBackground(
                    new java.awt.Color(191, 219, 254)
                );
            } else if (row % 2 == 0) {
                component.setBackground(
                    new java.awt.Color(239, 246, 255)
                );
            } else {
                component.setBackground(
                    java.awt.Color.WHITE
                );
            }

            component.setForeground(
                new java.awt.Color(31, 41, 55)
            );

            return component;
        }
    }
);
}
    
    private void initializeComboBox() {

    cmbSort.removeAllItems();

    cmbSort.addItem("Default Order");
    cmbSort.addItem("Date released");

    cmbSort.setSelectedIndex(0);
}
    
    private void refreshTable() {

    tableModel.setRowCount(0);

    String search = txtSearch.getText().trim().toLowerCase();

    List<Phone> phones;

    // Check selected sorting option
    if ("Date released".equals(cmbSort.getSelectedItem())) {

        phones = controller.sortByReleaseDate();

    } else {

        phones = controller.getAllPhones();
    }

    displayedPhones.clear();

    for (Phone phone : phones) {

        boolean matches =
                phone.getBrand().toLowerCase().contains(search)
                ||
                phone.getModel().toLowerCase().contains(search);

        if (matches) {

            displayedPhones.add(phone);

            tableModel.addRow(new Object[]{
                phone.getBrand(),
                phone.getModel(),
                phone.getReleaseDate(),
                phone.getPrice()
            });
        }
    }
}
    
    private void initializeListeners() {  
    btnAdd.addActionListener(e -> addPhone());
    btnRemove.addActionListener(e -> removePhone());
    btnUpdate.addActionListener(e -> updatePhone());
    cmbSort.addActionListener(e -> refreshTable());
    txtSearch.getDocument().addDocumentListener(
        new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshTable();
            }
        }
    );
}
    
    private void addPhone() {

    Phone phone = getPhoneDetails(null);

    if (phone == null) {
        return;
    }

    controller.addPhone(phone);

    refreshTable();

    JOptionPane.showMessageDialog(
        this,
        "Phone added successfully!"
    );
}
    private void removePhone() {

    int selectedRow = tblPhones.getSelectedRow();

    if (selectedRow == -1) {

        JOptionPane.showMessageDialog(
            this,
            "Please select a phone to remove."
        );

        return;
    }

    Phone selectedPhone = displayedPhones.get(selectedRow);

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to remove this phone?",
        "Confirm Removal",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {

        controller.removePhone(selectedPhone);

        refreshTable();

        JOptionPane.showMessageDialog(
            this,
            "Phone removed successfully!"
        );
    }
}
    
    private void updatePhone() {

    int selectedRow = tblPhones.getSelectedRow();

    if (selectedRow == -1) {

        JOptionPane.showMessageDialog(
            this,
            "Please select a phone to update."
        );

        return;
    }

    Phone oldPhone = displayedPhones.get(selectedRow);

    Phone newPhone = getPhoneDetails(oldPhone);

    if (newPhone == null) {
        return;
    }

    controller.updatePhone(oldPhone, newPhone);

    refreshTable();

    JOptionPane.showMessageDialog(
        this,
        "Phone updated successfully!"
    );
}
    
    private Phone getPhoneDetails(Phone existingPhone) {

    String brand = JOptionPane.showInputDialog(
        this,
        "Enter Phone Brand:",
        existingPhone == null ? "" : existingPhone.getBrand()
    );

    if (brand == null) return null;

    String model = JOptionPane.showInputDialog(
        this,
        "Enter Phone Model:",
        existingPhone == null ? "" : existingPhone.getModel()
    );

    if (model == null) return null;

    String date = JOptionPane.showInputDialog(
        this,
        "Enter Release Date (YYYY-MM-DD):",
        existingPhone == null
            ? ""
            : existingPhone.getReleaseDate().toString()
    );

    if (date == null) return null;

    String price = JOptionPane.showInputDialog(
        this,
        "Enter Price (PHP):",
        existingPhone == null
            ? ""
            : String.valueOf(existingPhone.getPrice())
    );

    if (price == null) return null;

    try {

        brand = brand.trim();
        model = model.trim();

        if (brand.isEmpty() || model.isEmpty()
                || date.trim().isEmpty()
                || price.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "All fields are required."
            );

            return null;
        }

        LocalDate releaseDate = LocalDate.parse(date.trim());

        double phonePrice = Double.parseDouble(price.trim());

        if (!Double.isFinite(phonePrice) || phonePrice < 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid non-negative price."
            );

            return null;
        }

        return new Phone(
            brand,
            model,
            releaseDate,
            phonePrice
        );

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
            this,
            "Invalid input!\n"
            + "Date format: YYYY-MM-DD\n"
            + "Price must be a valid number."
        );

        return null;
    }
}
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        cmbSort = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhones = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PHONE INVENTORY SYSTEM");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("POCKET CATALOG");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("BROWSE, SEARCH, ADD, AND SORT SMARTPHONES RECORD AND PRICES");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("SEARCH");

        txtSearch.addActionListener(this::txtSearchActionPerformed);

        cmbSort.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default Order", "Date released" }));
        cmbSort.addActionListener(this::cmbSortActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("SORT");

        tblPhones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblPhones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Brand", "Model", "Date released", "Price (PHP)"
            }
        ));
        jScrollPane1.setViewportView(tblPhones);

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdd.setText("ADD");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRemove.setText("REMOVE");

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setText("UPDATE");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("------------------------------------------------------------------------");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdate))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 154, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSortActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PhoneCatalogUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPhones;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
