package dellefin;

import java.io.IOException;
import static java.lang.System.exit;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class controller 
{

    void start() throws SQLException 
    {
        //lavMedlem();

        //opretResultat();
        
        //seMedlemmer();

        //setMedlemTilPassivAktiv();
        
        //setMedlemTilMotionistKonkurrent();
        
        //setMedlemAlder();
        
        //setMedlemStamOpl();
        
        //sletMedlem();
        
        // virker ikke endnu seTop5(); 
    }

    public void lavMedlem() throws SQLException 
    {

        Scanner myScan = new Scanner(System.in);
        String stamOpl = "";
        int alder = 0;
        boolean passivAktiv = false;
        boolean MotionKonkurant = false;

        System.out.println("Skriv navn, tlfnummer og adresse");
        stamOpl = myScan.nextLine();

        System.out.println("Skriv alder (med tal)");
        alder = myScan.nextInt();

        //fanger nextInt
        myScan.nextLine();

        System.out.println("Skriv \"false\", hvis medlemmet er passivt eller \"true\" hvis medlemmet aktivt");
        passivAktiv = myScan.nextBoolean();

        System.out.println("Skriv \"false\", hvis medlemmet er motionist eller \"true\" hvis medlemmet er konkurrent");
        MotionKonkurant = myScan.nextBoolean();

        //medlem medlem = new medlem(stamOpl, alder, passivAktiv, MotionKonkurant);
        try {
            Connection conn = DataConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into delfin.Medlem(stamOpl,alder,passivAktiv,MotionKonkurant) values(?,?,?,?)");

            stmt.setString(1, stamOpl);
            stmt.setInt(2, alder);
            stmt.setBoolean(3, passivAktiv);
            stmt.setBoolean(4, MotionKonkurant);

            stmt.executeUpdate();
        } catch (SQLException se) {
            System.out.println("Det virkede ikke boiii");
        }
    }

    public void opretResultat() 
    {
        Scanner myScan = new Scanner(System.in);

        int Medlem_ID = 0;
        String Stævne = "";
        String Placering = "";
        String Tid = "";
        String Svømmedisciplin = "";

        System.out.println("Skriv id");
        Medlem_ID = myScan.nextInt();
        //fanger nextInt
        myScan.nextLine();
        System.out.println("Navnet på stævnet");
        Stævne = myScan.nextLine();

        System.out.println("Placering");
        Placering = myScan.nextLine();

        System.out.println("TId`?");
        Tid = myScan.nextLine();

        System.out.println("Hvilken svømmedisciplin?");
        Svømmedisciplin = myScan.nextLine();

        try {
            Connection conn = DataConnector.getConnection();
            PreparedStatement stmt;
            stmt = conn.prepareStatement("insert into delfin.svømresultat(Medlem_ID,Stævne,Placering,Tid,Svømmedisciplin) values(?,?,?,?,?)");
            stmt.setInt(1, Medlem_ID);
            stmt.setString(2, Stævne);
            stmt.setString(3, Placering);
            stmt.setString(4, Tid);
            stmt.setString(5, Svømmedisciplin);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SvømmeResultat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void seMedlemmer() 
    {
        
        try {
            Connection conn = DataConnector.getConnection();

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM delfin.medlem");

            while (resultSet.next()) {

                System.out.println("Medlemmets ID: " + resultSet.getInt("ID") + "\n"
                        + "Stam oplysninger: " + resultSet.getString("stamOpl") + "\n"
                        + "Alder: " + resultSet.getInt("alder") + "\n" + "True hvis aktiv, false hvis passiv: "
                        + resultSet.getBoolean("passivAktiv") + "\n" + "True hvis konkurrent, false hvis motionist: " + resultSet.getBoolean("MotionKonkurant") + "\n");

            }
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setMedlemTilPassivAktiv() 
    {
        System.out.println("Skriv medlemmets ID");
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();

        PreparedStatement statement = null;
        try {
            

            Connection conn = DataConnector.getConnection();

            System.out.println("Skriv \"1\" hvis hvis du vil gøre medlemmet aktivt og \"0\", hvis medlemmet skal gøres passivt.");

            int s = 0;

            s = in.nextInt();

            if (s == 1) {

                String sql = "update delfin.medlem set passivAktiv = 1 where ID = ?;";

                statement = conn.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som aktivt");
            }
            if (s == 0) {

                String sql = "update delfin.medlem set passivAktiv = 0 where ID = ?;";

                statement = conn.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som passivt");
            }
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setMedlemTilMotionistKonkurrent() 
    {
        System.out.println("Skriv medlemmets ID");
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();

        PreparedStatement statement = null;
        try {
            

            Connection conn = DataConnector.getConnection();

            System.out.println("Skriv \"1\" hvis hvis du vil gøre medlemmet til en konkurrent og \"0\", hvis medlemmet skal gøres til motionist.");

            int s;

            s = in.nextInt();

            if (s == 1) 
            {

                String sql = "update delfin.medlem set MotionKonkurant = 1 where ID = ?;";

                statement = conn.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som konkurrent");
            }
            if(s == 0) 
            {

                String sql = "update delfin.medlem set MotionKonkurant = 0 where ID = ?;";

                statement = conn.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som motionist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     public void setMedlemAlder() 
    {
        System.out.println("Skriv medlemmets ID");
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();  
        
            System.out.println("Skriv ny alder");
            String s;

            s = in.nextLine();
            
        PreparedStatement statement = null;
        try {
            

            Connection conn = DataConnector.getConnection();

          



                String sql = "update delfin.medlem set Alder = ? where ID = ?;";

                statement = conn.prepareStatement(sql);
                
                statement.setInt(1, Integer.parseInt(s));
                statement.setInt(2, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som " + s + " år gammel");
            

        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void setMedlemStamOpl() 
    {
        System.out.println("Skriv medlemmets ID");
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();  
        
            System.out.println("Skriv nyt navn, telefon nummer og adresse");
            String s;

            s = in.nextLine();
            
        PreparedStatement statement = null;
        try {
            

            Connection conn = DataConnector.getConnection();

          



                String sql = "update delfin.medlem set stamOpl = ? where ID = ?;";

                statement = conn.prepareStatement(sql);
                
                statement.setString(1, s);
                statement.setInt(2, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + ", har fået disse nye stam oplysninger: " + s);
            

        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void sletMedlem() 
    {
        System.out.println("Skriv ID'et på medlemmet som du vil slette (Dette kan ikke fortrydes!)");
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();  
     
        PreparedStatement statement = null;
        try {
            

            Connection conn = DataConnector.getConnection();

          
                String sql = "Delete from delfin.Medlem where ID = ?;";

                statement = conn.prepareStatement(sql);
                
                
                statement.setInt(1, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er slettet fra databasen");
            

        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void seTop5() 
    {
        System.out.println("Hvilken svømmedisciplin vil du se top 5 for? (crawl, butterfly, brystsvømning, rygcrawl)");
         Scanner in = new Scanner(System.in);
         String s = "";
         s = in.nextLine();  
        
                 PreparedStatement statement = null;
        try {
            

            Connection conn = DataConnector.getConnection();

                String sql = "select * from delfin.svømresultat where Svømmedisciplin = ? order by Tid asc limit 5;";
              

                statement = conn.prepareStatement(sql);
                
                
                statement.setString(1, s);

                statement.execute();
                System.out.println(sql);
                
               
            

        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
}
/*
    public void getUserInput() throws IOException {
        answer = myScan.nextInt();
        switch (answer) {
            case 1:
                myUtils.clearConsole();
                ;
                System.out.println(menuCard.toString());
                System.out.println("Press 0 to return to main menu");
                myUtils.splitDisplay();
                break;
            case 2:
                listPizzas = new ArrayList<>();
                this.currentOrder = null;
                listPizzas.clear();
                while (true) {
                    myUtils.clearConsole();
                    System.out.println(menuCard.toString());
                    System.out.println("Press 0 to return to main menu\n");
                    System.out.println("Which pizza from the menu card would you like to add as a order, enter a number 1-14, ");
                    myUtils.splitDisplay();
                    System.out.print("Enter pizza number: ");
                    
                    pizzaNo = myScan.nextInt();
                    listPizzas.add(pizzaNo);
                    myUtils.clearConsole();
                    if (pizzaNo >= 1 && pizzaNo <= 14) {
                        if (currentOrder == null) {
                            this.currentOrder = new Order(new ArrayList<>(), pickUpTime, customerId);
                        }
                        this.currentOrder.getPizzas().add(menuCard.getMenuCard().get(pizzaNo));
                    } else {
                        System.out.println("Pizza does not exist in menu card, please press 0 to return to main menu and try again ...");
                        break;
                    }
                    if (pizzaNo == 0) {
                        myUtils.clearConsole();
                        break;
                    }
                    // lave database kald, hvor jeg gemmer orderen med pizzaer før jeg nulstiller ordren.
                    // this.currentOrder = null; nulstil orderen
                }
                if (this.currentOrder != null) {
                    myUtils.clearConsole();
                    System.out.print("Enter a customer id for this order: ");
                    customerId = myScan.nextInt();
                    myUtils.clearConsole();
                    myScan.nextLine();
                    System.out.print("Enter a pick up time: ");
                    pickUpTime = myScan.nextLine();
                    myUtils.clearConsole();
                }
                for (int i = 0; i < currentOrder.getPizzas().size(); i++) {
                    connectOrderDatabase.insertOrder(customerId, listPizzas.get(i), pickUpTime);
                }
                myUtils.clearConsole();
                myUtils.printMainMenu();
                break;
            case 3:
                myUtils.clearConsole();
                System.out.println("Customer ID     PizzaName    PickUpTime");
                connectOrderDatabase.getCurrentOrders();
                System.out.println("\nPress 0 to return to main menu");
                myUtils.splitDisplay();
                System.out.println("Would you like to remove a order? Press 1");
                int remove = myScan.nextInt();
                myUtils.clearConsole();
                if (remove == 1) {
                    connectOrderDatabase.getCurrentOrders();
                    myUtils.splitDisplay();
                    System.out.println("Enter a customer id");
                    int customerId = myScan.nextInt();
                    connectOrderDatabase.updateOrdner(customerId);
                    myUtils.clearConsole();
                    myUtils.printMainMenu();
                } else {
                    myUtils.clearConsole();
                    myUtils.printMainMenu();
                }
                break;
            case 4:
                myUtils.clearConsole();
                System.out.println("Customer ID     PizzaName    PickUpTime");
                connectOrderDatabase.showOrderHistory();
                System.out.println("\nPress 0 to return to main menu");
                myUtils.splitDisplay();
                break;
            case 5:
                myUtils.clearConsole();
                System.out.println("Pizza Amount       PizzaName      AmountSold");
                connectOrderDatabase.Statistics();
                System.out.println("\nPress 0 to return to main menu");
                myUtils.splitDisplay();
                break;
            case 0:
                myUtils.clearConsole();
                myUtils.printMainMenu();
                break;
            default:
                if (answer == 9) {
                    myUtils.clearConsole();
                    System.out.println("System ending ...");
                    exit(0);
                }
                myUtils.clearConsole();
                myUtils.printMainMenu();
                break;
        }*/
