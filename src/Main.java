import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        String ip = "192.168.98.215";
        String ip = "10.51.160.101";
        String community = "adsl";
        String targetOid = ".1.3.6.1.2.1.2.2.1.3";

        //snmpifType.main(ip, targetOid,community);

        //System.out.println("");
        System.out.println("Hello World!");
        System.out.println("xx");

       // SnmpWalk.snmpWalk(ip, community, targetOid);
        SnmpWalkAsyn.main(args);

//        zte.zteTest(2416967936L);
    }
}
