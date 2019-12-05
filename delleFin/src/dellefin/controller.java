package dellefin;

import java.io.IOException;
import static java.lang.System.exit;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.time.Year;
import java.util.logging.Level;
import java.util.logging.Logger;

public class controller {
//push
    void start() throws SQLException {

        while (true) {
            Scanner myScan = new Scanner(System.in);
            BrugerMeny();
            int answer = myScan.nextInt();

            switch (answer) {
                case 1:

                    seMedlemmer();

                    break;
                case 2:
                   // clearConsole();
                    System.out.println("Oprette medlem tryk 1\nSlem medlem tryk 2\nRedigere alder tryk 3\nRedigere stam oplysninger tryk 4\nRedigere medlems passiv/aktiv status tryk 5\nGør medlem til konkurrent eller skift et medlems træner - tryk 6 \n");
                    int XD = myScan.nextInt();
                    switch (XD) {
                        case 1:
                            lavMedlem();
                            break;
                        case 2:
                            sletMedlem();
                            break;
                        case 3:
                            setMedlemAlder();
                            break;
                        case 4:
                            setMedlemStamOpl();
                            break;
                        case 5:
                            setMedlemTilPassivAktiv();
                            break;
                        case 6:
                        setMedlemKonkurrent();
                        break;
                    }

                    break;
                case 3:

                    System.out.println("Tryk 1 for at se restance oversigt\nTryk 2 for at ærklere et medlem som betalt");
                    int XDXD = myScan.nextInt();
                    switch (XDXD) {

                        case 1:
                            seRestanceOversigt();
                            break;
                        case 2:
                            setBetalStatus();
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Top 5 senior 2019");
                    seTop5Senior();
                    System.out.println("");
                    System.out.println("Top 5 Junior 2019");
                    seTop5Junior();
                    break;
                case 5:
                    opretResultat();
                    break;

                default:
                    if (answer == 9) {

                        System.out.println("System ending ...");
                        exit(0);
                    }
                    break;
            }
        }
    }

//    public void clearConsole() {
//        for (int i = 0; i <= 1; i++) {
//            System.out.println("");
//        }



    public void lavMedlem() throws SQLException {

        Scanner myScan = new Scanner(System.in);
        String stamOpl = "";
        int alder = 0;
        boolean passivAktiv = false;

        
        System.out.println("Skriv navn, tlfnummer og adresse");
        stamOpl = myScan.nextLine();

        System.out.println("Skriv Årgang");
        alder = myScan.nextInt();

        //fanger nextInt for at forhindre scanner buggen
        myScan.nextLine();

        System.out.println("Skriv \"false\", hvis medlemmet er passivt eller \"true\" hvis medlemmet aktivt");
        passivAktiv = myScan.nextBoolean();

        System.out.println("Er medlemmet konkurrent? ja(1)/nej(2)");
        int trololo = myScan.nextInt();
        myScan.nextLine();
        if (trololo == 1) {
            System.out.println("Skriv medlemmets tilknyttede træners navn");
            String træner = "";
            træner = myScan.nextLine();
            try {
                Connection conn = DataConnector.getConnection();
                PreparedStatement stmt = conn.prepareStatement("insert into delfin.Medlem(stamOpl,alder,passivAktiv,træner) values(?,?,?,?)");

                stmt.setString(1, stamOpl);
                stmt.setInt(2, alder);
                stmt.setBoolean(3, passivAktiv);
                stmt.setString(4, træner);

                stmt.executeUpdate();
            } catch (SQLException se) {
                System.out.println("Det virkede ikke boiii");
            }
        } else if (trololo == 2) {
            try {
                Connection conn = DataConnector.getConnection();
                PreparedStatement stmt = conn.prepareStatement("insert into delfin.Medlem(stamOpl,alder,passivAktiv,træner) values(?,?,?,?)");

                stmt.setString(1, stamOpl);
                stmt.setInt(2, alder);
                stmt.setBoolean(3, passivAktiv);
                stmt.setString(4, null);

                stmt.executeUpdate();
            } catch (SQLException se) {
                System.out.println("Det virkede ikke boiii");
            }
        }
    }

    public void opretResultat() {
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

        System.out.println("Tid?(sek.msek)");
        Tid = myScan.nextLine();

        System.out.println("Hvilken svømmedisciplin? (Crawl, Rygcrawl, Brystsvømning, Butterfly)");
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

    public void seMedlemmer() {

        try {
            Connection conn = DataConnector.getConnection();

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM delfin.medlem");

            while (resultSet.next()) {

                System.out.println("Medlemmets ID: " + resultSet.getInt("ID") + "\n"
                        + "Stam oplysninger: " + resultSet.getString("stamOpl") + "\n"
                        + "Årgang: " + resultSet.getInt("alder") + "\n" + "True hvis aktiv, false hvis passiv: "
                        + resultSet.getBoolean("passivAktiv") + "\n" + "Træner: " + resultSet.getString("Træner") + "\n");

            }
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setMedlemTilPassivAktiv() {
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

    public void setMedlemKonkurrent() {
        System.out.println("Skriv medlemmets ID");
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();

        PreparedStatement statement = null;
        try {

            Connection conn = DataConnector.getConnection();

            System.out.println("Skriv navnet på medlemmets træner");

            String s;

            s = in.nextLine();

            

                String sql = "update delfin.medlem set træner = ? where ID = ?;";

                statement = conn.prepareStatement(sql);

                statement.setString(1, s);
                statement.setInt(2, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som konkurrent");
          
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setMedlemAlder() {
        System.out.println("Skriv medlemmets ID");
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();

        System.out.println("Skriv ny årgang");
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
            System.out.println("Medlem: " + ID + " er registreret som værende årgang " + s);

        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMedlemStamOpl() {
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

    public void sletMedlem() {
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

    public void seTop5Senior() {
        System.out.println("Hvilken svømmedisciplin vil du se top 5 for? (crawl, butterfly, brystsvømning, rygcrawl)");
        Scanner in = new Scanner(System.in);
        String s = "";
        ResultSet resultset = null;
        s = in.nextLine();

        PreparedStatement statement = null;
        try {

            Connection conn = DataConnector.getConnection();

            String sql = "select m.ID, m.stamOpl, m.alder, s.Stævne, s.medlem_id, s.tid, s.svømid,s.svømmedisciplin from medlem m, svømresultat s where s.Medlem_ID=m.id and alder < 2001 and s.Svømmedisciplin = ? order by Tid asc limit 5;";

            statement = conn.prepareStatement(sql);

            statement.setString(1, s);

            resultset = statement.executeQuery();

            while (resultset.next()) {

                System.out.println("Medlemmets ID: " + resultset.getInt("ID") + "\n"
                        + "Stam oplysninger: " + resultset.getString("stamOpl") + "\n"
                        + "Årgang: " + resultset.getInt("alder") + "\n"
                        + "Stævne: " + resultset.getString("Stævne") + "\n"
                        + "Tid: " + resultset.getString("Tid") + "\n"
                        + "Svømmedisciplinen: " + resultset.getString("Svømmedisciplin") + "\n");
            }

        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void seTop5Junior() {
        System.out.println("Hvilken svømmedisciplin vil du se top 5 for? (crawl, butterfly, brystsvømning, rygcrawl)");
        Scanner in = new Scanner(System.in);
        String s = "";
        ResultSet resultset = null;
        s = in.nextLine();

        PreparedStatement statement = null;
        try {

            Connection conn = DataConnector.getConnection();

            String sql = "select m.ID, m.stamOpl, m.alder, s.Stævne, s.medlem_id, s.tid, s.svømid,s.svømmedisciplin from medlem m, svømresultat s where s.Medlem_ID=m.id and alder > 2001 and s.Svømmedisciplin = ? order by Tid asc limit 5;";

            statement = conn.prepareStatement(sql);

            statement.setString(1, s);

            resultset = statement.executeQuery();

            while (resultset.next()) {

                System.out.println("Medlemmets ID: " + resultset.getInt("ID") + "\n"
                        + "Stam oplysninger: " + resultset.getString("stamOpl") + "\n"
                        + "Årgang: " + resultset.getInt("alder") + "\n"
                        + "Stævne: " + resultset.getString("Stævne") + "\n"
                        + "Tid: " + resultset.getString("Tid") + "\n"
                        + "Svømmedisciplinen: " + resultset.getString("Svømmedisciplin") + "\n");
            }

        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void setBetalStatus() {
        System.out.println("Skriv medlemmets ID"); // betalingstatus
        Scanner in = new Scanner(System.in);
        String ID = "";
        ID = in.nextLine();

        PreparedStatement statement = null;
        try {

            Connection conn = DataConnector.getConnection();

            System.out.println("Skriv \"1\" For at sætte medlemmets betalingstatus til betalt "
                    + "og \"0\", hvis medlemmet skal sættes i restance.");

            int s = 0;

            s = in.nextInt();

            if (s == 1) {

                String sql = "update delfin.medlem set betalStatus = 1 where ID = ?;";

                statement = conn.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som betalt");
            }
            if (s == 0) {

                String sql = "update delfin.medlem set betalStatus = 0 where ID = ?;";

                statement = conn.prepareStatement(sql);

                statement.setInt(1, Integer.parseInt(ID));

                statement.execute();
                System.out.println("Medlem: " + ID + " er registreret som værende i restance");
            }
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void seRestanceOversigt() {

        try {
            Connection conn = DataConnector.getConnection();

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM delfin.medlem where betalStatus = 0");

            while (resultSet.next()) {

                int årstal = Year.now().getValue();
                double pris = 1600;
                int alder = resultSet.getInt("alder");
                boolean passivAktivStatus = resultSet.getBoolean("passivAktiv");

                if (årstal - alder < 18) {
                    pris = 1000;

                } else if (årstal - alder > 60) {
                    pris = pris * 0.75;

                }
                if (!passivAktivStatus == true) {
                    pris = 500;

                }
                System.out.println("Medlemmets ID: " + resultSet.getInt("ID") + "\n"
                        + "Stam oplysninger: " + resultSet.getString("stamOpl") + "\n"
                        + "Årgang: " + resultSet.getInt("alder") + "\n" + "True hvis aktiv, false hvis passiv: "
                        + resultSet.getBoolean("passivAktiv") + "\n" +  "Mangler at betale: " + pris + "kr." + "\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void BrugerMeny() {
        System.out.println("Velkommen! \n \nTryk 1 for: Medlems oversigt\nTryk 2 for: Redigere medlem\nTryk 3 for: Se restance meny\nTryk 4 for: Se top 5\nTryk 5 for at oprette et ny svømmeresultat\nTryk 9 for at aflutte programmet");

    }
}
