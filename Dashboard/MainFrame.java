package Dashboard;
import javax.swing.*;
import StartPage.SignInpanel;
import StartPage.SignUpPanel;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;
    private SidebarPanel sidebar;
    private JPanel contentArea;
    private CardLayout contentLayout;
    private InventorySystem inventorySystem; // Shared inventory system
    private AllBooksPanel allBooksPanel; // Reference to AllBooksPanel
    private AddBookPanel addBookPanel; // Reference to AddBookPanel
    private UpdateStockPanel updateStockPanel; // Reference to UpdateStockPanel
    private SellBookPanel sellBookPanel; // Reference to SellBookPanel
    private SalesReportPanel salesReportPanel; // Reference to SalesReportPanel

    public MainFrame() {
        super("Shelf Wise");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(new SignInpanel(this), "SignIn");
        cards.add(new SignUpPanel(this), "SignUp");
        getContentPane().add(cards);
        cardLayout.show(cards, "SignIn");
    }

    public void initMainUI() {
        cards.removeAll();
        
        // Initialize shared inventory system
        inventorySystem = new InventorySystem();
        
        // Split pane for sidebar + content
        JSplitPane split = new JSplitPane();
        split.setDividerLocation(310);
        split.setEnabled(false);

        sidebar = new SidebarPanel();
        
        // Content area with CardLayout for different panels
        contentLayout = new CardLayout();
        contentArea = new JPanel(contentLayout);
        contentArea.setBackground(Color.WHITE);

        // Create AllBooksPanel first with shared inventory system
        allBooksPanel = new AllBooksPanel(inventorySystem);
        
        // Create AddBookPanel with shared inventory system and AllBooksPanel reference
        addBookPanel = new AddBookPanel(inventorySystem, allBooksPanel);
        
        // Create UpdateStockPanel with shared inventory system and AllBooksPanel reference
        updateStockPanel = new UpdateStockPanel(inventorySystem, allBooksPanel);
        
        // Create SellBookPanel with shared inventory system and AllBooksPanel reference
        sellBookPanel = new SellBookPanel(inventorySystem, allBooksPanel);
        
        // Create SalesReportPanel with shared inventory system
        salesReportPanel = new SalesReportPanel(inventorySystem);

        // Add all feature panels
        contentArea.add(new DashboardPanel(), "Dashboard");
        contentArea.add(allBooksPanel, "AllBooks");
        contentArea.add(addBookPanel, "AddBooks");
        contentArea.add(updateStockPanel, "UpdateStock");
        contentArea.add(sellBookPanel, "SellBook");
        contentArea.add(salesReportPanel, "SalesReport"); // Use the properly initialized salesReportPanel

        // Add action listeners to sidebar buttons
        sidebar.getShowBooksBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Refresh the table when switching to AllBooks panel
                allBooksPanel.refreshBookTable();
                contentLayout.show(contentArea, "AllBooks");
            }
        });

        sidebar.getAddBookBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentLayout.show(contentArea, "AddBooks");
            }
        });

        sidebar.getUpdateBookBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentLayout.show(contentArea, "UpdateStock");
            }
        });

        sidebar.getSellBookBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentLayout.show(contentArea, "SellBook");
            }
        });

        sidebar.getSalesReportBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Refresh the sales report when switching to it
                salesReportPanel.refreshReport();
                contentLayout.show(contentArea, "SalesReport");
            }
        });

        split.setLeftComponent(sidebar);
        split.setRightComponent(contentArea);
        cards.removeAll();
        cards.add(split);
        revalidate();
        repaint();

        // Show dashboard by default
        contentLayout.show(contentArea, "Dashboard");
    }
    
    public void showSignUp() { 
        cardLayout.show(cards, "SignUp"); 
    }
    
    public void showSignIn() { 
        cardLayout.show(cards, "SignIn"); 
    }
}