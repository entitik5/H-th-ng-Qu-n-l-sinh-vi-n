package view;

import controller.DiemSoController;
import dao.MonHocDAO;
import dao.SinhVienDAO;
import model.MonHoc;
import model.SinhVien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class DiemSoPanel extends JPanel {

    private JTable              table;
    private DefaultTableModel   tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JComboBox<SinhVien> cmbSinhVien;
    private JComboBox<MonHoc>   cmbMonHoc;
    private JTextField          txtDiem;
    private JTextField          txtTimKiem;
    private JLabel              lblKetQua;
    private DiemSoController    controller;

    public DiemSoPanel() {
        String[] columns = {"Mã điểm", "Mã SV", "Mã MH", "Điểm số", "Xếp loại"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        controller = new DiemSoController(this, tableModel);
        initUI();
        controller.taiDuLieu();
        capNhatSoLuong();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // ── Tiêu đề ──────────────────────────────────
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐIỂM SỐ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(30, 80, 160));

        // ── Thanh tìm kiếm ───────────────────────────
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(new Color(245, 247, 250));

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchBar.setBackground(new Color(245, 247, 250));

        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 13));

        txtTimKiem = new JTextField(22);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTimKiem.setToolTipText("Tìm theo mã SV, mã môn học, xếp loại...");

        JButton btnXoaTK = new JButton("X");
        btnXoaTK.setFont(new Font("Arial", Font.BOLD, 12));
        btnXoaTK.setBackground(new Color(220, 53, 69));
        btnXoaTK.setForeground(Color.WHITE);
        btnXoaTK.setFocusPainted(false);
        btnXoaTK.setBorderPainted(false);
        btnXoaTK.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXoaTK.setToolTipText("Xóa tìm kiếm");
        btnXoaTK.addActionListener(e -> txtTimKiem.setText(""));

        lblKetQua = new JLabel("Tổng: 0 bản ghi");
        lblKetQua.setFont(new Font("Arial", Font.ITALIC, 12));
        lblKetQua.setForeground(Color.GRAY);

        searchBar.add(lblSearch);
        searchBar.add(txtTimKiem);
        searchBar.add(btnXoaTK);
        searchBar.add(Box.createHorizontalStrut(10));
        searchBar.add(lblKetQua);

        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(searchBar, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        // ── Bảng dữ liệu ─────────────────────────────
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(30, 80, 160));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(190, 215, 255));
        table.setGridColor(new Color(220, 225, 235));
        table.setShowGrid(true);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Lắng nghe tìm kiếm real-time
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { applyFilter(); }
            @Override public void removeUpdate(DocumentEvent e)  { applyFilter(); }
            @Override public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        // ── Form nhập điểm ───────────────────────────
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(280, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230)),
            "Nhập điểm", 0, 0,
            new Font("Arial", Font.BOLD, 13), new Color(30, 80, 160)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cmbSinhVien = new JComboBox<>();
        new SinhVienDAO().layTatCa().forEach(sv -> cmbSinhVien.addItem(sv));

        cmbMonHoc = new JComboBox<>();
        new MonHocDAO().layTatCa().forEach(mh -> cmbMonHoc.addItem(mh));

        txtDiem = new JTextField(12);
        txtDiem.setToolTipText("Nhập điểm từ 0.0 đến 10.0");

        String[] labels = {"Sinh viên *", "Môn học *", "Điểm số *"};
        Component[] inputs = {cmbSinhVien, cmbMonHoc, txtDiem};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            formPanel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            inputs[i].setFont(new Font("Arial", Font.PLAIN, 13));
            formPanel.add(inputs[i], gbc);
        }

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 6, 6));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(10, 8, 8, 8));
        JButton btnThem = taoNut("Nhập điểm", new Color(40, 160, 80));
        JButton btnXoa  = taoNut("Xóa",       new Color(200, 50, 50));
        btnPanel.add(btnThem);
        btnPanel.add(btnXoa);

        gbc.gridx = 0; gbc.gridy = labels.length;
        gbc.gridwidth = 2; gbc.insets = new Insets(10, 0, 0, 0);
        formPanel.add(btnPanel, gbc);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(formPanel, BorderLayout.EAST);

        // ── Sự kiện nút ──────────────────────────────
        btnThem.addActionListener(e -> {
            SinhVien sv = (SinhVien) cmbSinhVien.getSelectedItem();
            MonHoc   mh = (MonHoc)   cmbMonHoc.getSelectedItem();
            if (controller.nhapDiem(
                    sv != null ? sv.getMaSV() : null,
                    mh != null ? mh.getMaMH() : null,
                    txtDiem.getText().trim())) {
                txtDiem.setText("");
                capNhatSoLuong();
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn điểm cần xóa!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Lấy index thực trong model (vì bảng có thể đang lọc)
            int modelRow = table.convertRowIndexToModel(row);
            controller.xoaDiem((int) tableModel.getValueAt(modelRow, 0));
            capNhatSoLuong();
        });
    }

    /** Lọc bảng theo nội dung txtTimKiem */
    private void applyFilter() {
        String text = txtTimKiem.getText().trim();
        if (text.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            // Lọc trên tất cả các cột (case-insensitive)
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
        capNhatSoLuong();
    }

    /** Cập nhật nhãn số lượng bản ghi đang hiển thị */
    public void capNhatSoLuong() {
        int hienThi = table.getRowCount();
        int tongCong = tableModel.getRowCount();
        if (hienThi == tongCong) {
            lblKetQua.setText("Tổng: " + tongCong + " bản ghi");
        } else {
            lblKetQua.setText("Hiển thị: " + hienThi + " / " + tongCong + " bản ghi");
        }
    }

    private JButton taoNut(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}