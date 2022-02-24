package progettofinale.sailingclub;

import java.io.*;
import java.net.Socket;
import progettofinale.sailingclub.communication.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * The class {@code ServerThread} is used to handles the communication
 * between client and server thread.
 *
 **/
public class ServerThread implements Runnable {

    /**
     * Class fields.
     *
     * socket - It is the socket used by server thread to communicates with the client.
     * SlEEPTIME - It is a constant that defines the rest time for each thread.
     * monitor - It is the shared resource between server threads. It is used to access database that is the shared resource.
     */

    private final Socket socket;
    private static final long SLEEPTIME = 200;
    private static final ManagementDB manager = new ManagementDB();


    /**
     * Class constructor.
     *
     * @param c it is the client socket.
     **/

    public ServerThread(final Socket c) { this.socket = c; }


    /** {@inheritDoc} **/
    @Override
    public void run() {

        ObjectInputStream is;

        ObjectOutputStream os = null;

        try {
            is = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        while (true) {

            try {

                Object i = is.readObject();

                if(i instanceof Request && ((Request) i).getType().equals(RequestType.LOGIN)){
                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    Person user = (Person) manager.queryLogin(((Request) i).getPerson());

                    os.writeObject(Objects.requireNonNullElse(user, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.REGISTER)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType username = manager.verifyUsername(((Request) i).getMember().getUsername());
                    String verifyCf = manager.queryVerifyCF(((Request) i).getMember().getCf());


                    if (username.equals(ResponseType.NOT_FOUND) && verifyCf == null){

                        ResponseType member =  manager.queryAddMember(((Request) i).getMember());
                        os.writeObject(member);

                    }
                    else if (verifyCf != null) {

                        os.writeObject(ResponseType.NOT_AUTHORIZED);

                    }
                    else if (username.equals(ResponseType.ALREADY_EXISTING) ) {

                        os.writeObject(username);

                    }

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.LIST_BOATS)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Boat> boats = manager.queryBoat(((Request) i).getUsername());

                    os.writeObject(boats);

                    os.flush();
                    this.socket.close();
                    return;
                }

                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.ADD_BOAT)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType addBoat = manager.queryAddBoat(((Request) i).getBoat().getOwner(),((Request) i).getBoat().getName(),((Request) i).getBoat().getLength());


                    if (addBoat.equals(ResponseType.ADD_BOAT_SUCCESS)) os.writeObject(addBoat);

                    else os.writeObject(ResponseType.ERROR_ADD_BOAT);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.RMV_BOAT)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType rmvBoat = manager.queryRmvBoat(((Request) i).getUsername(),((Request) i).getId());


                    if (rmvBoat.equals(ResponseType.RMV_BOAT_SUCCESS)) os.writeObject(rmvBoat);

                    else os.writeObject(ResponseType.NOT_FOUND);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.LIST_USERNAME)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<String> username = manager.querySearchMember();

                    os.writeObject(Objects.requireNonNullElse(username, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VIEW_MEMBERS)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Member> members = manager.queryMember();

                    os.writeObject(Objects.requireNonNullElse(members, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.ADD_MEMBER)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType addMember = manager.queryAddMember(((Request) i).getMember());

                    if(addMember.equals(ResponseType.ADD_MEMBER_SUCCESS)) os.writeObject(addMember);

                    else os.writeObject(ResponseType.ERROR_ADD_MEMBER);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.RMV_MEMBER)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType rmvMember = manager.queryRmvMember(((Request) i).getUsername());

                    if (rmvMember.equals(ResponseType.RMV_MEMBER_SUCCESS)) os.writeObject(rmvMember);

                    else os.writeObject(ResponseType.NOT_FOUND);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.LIST_RACE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Race> race = manager.queryRace();

                    os.writeObject(Objects.requireNonNullElse(race, ResponseType.NOT_RACE));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.LIST_INSCRIPTION)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Participant> inscription = manager.queryParticipant(((Request) i).getUsername());

                    os.writeObject(Objects.requireNonNullElse(inscription, ResponseType.NOT_INSCRIPTION));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.ADD_RACE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType addRace = manager.queryAddRace(((Request) i).getRace());

                    if(addRace.equals(ResponseType.ADD_RACE_SUCCESS)) os.writeObject(addRace);

                    else os.writeObject(ResponseType.ERROR_ADD_RACE);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.RMV_RACE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType rmvRace = manager.queryRmvRace(((Request) i).getId());

                    if (rmvRace.equals(ResponseType.RMV_RACE_SUCCESS)) os.writeObject(rmvRace);

                    else os.writeObject(ResponseType.NOT_FOUND);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.LIST_INSCRIPTION_S)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Participant> inscription = manager.queryParticipantS(((Request) i).getUsername());

                    os.writeObject(Objects.requireNonNullElse(inscription, ResponseType.NOT_INSCRIPTION));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VERIFY_ID_RACE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    Float verifyRace = manager.queryVerifyRace(((Request) i).getId());

                    os.writeObject(Objects.requireNonNullElse(verifyRace, ResponseType.NOT_RACE));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.ADD_INSCRIPTION)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    int idBoat = manager.getIdBoat(((Request) i).getParticipant(),((Request) i).getUsername());

                    if (idBoat != 0){
                        ResponseType addParticipant = manager.queryAddParticipation(((Request) i).getParticipant().getIdRace(),idBoat);

                        os.writeObject(addParticipant);
                    }
                    else os.writeObject(ResponseType.NOT_BOAT);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.LIST_BOAT_Q)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Payment> boat = manager.queryQuoteBoat1(((Request) i).getUsername());

                    os.writeObject(Objects.requireNonNullElse(boat, ResponseType.NOT_BOAT));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.GET_MEMBERSHIP_FEE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    float membershipFee = manager.queryMemberShipFee();

                    if (membershipFee != 0) os.writeObject(membershipFee);

                    else os.writeObject(ResponseType.ERROR);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VERIFY_ID_BOAT)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType boatId = manager.queryVerifyBoat(((Request) i).getUsername(),((Request) i).getId());

                    if (boatId.equals(ResponseType.OK)) os.writeObject(boatId);

                    else os.writeObject(boatId);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VERIFY_STORAGE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType verifyStorage = manager.queryVerifyStorage(((Request) i).getId());

                    os.writeObject(verifyStorage);
                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.PAY_STORAGE)) {

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType payStorage = manager.queryPayStorage(((Request) i).getId(), ((Request) i).getUsername());

                    os.writeObject(payStorage);
                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VERIFY_EXPIRE_DATE)) {

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    Object obj  = manager.queryVerifyDateExp(((Request) i).getUsername());

                    os.writeObject(obj);
                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.PAY_MEMBERSHIP)) {

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    Object obj = manager.queryPayMembership(((Request) i).getUsername());

                    os.writeObject(obj);
                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VIEW_STORAGE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Payment> listStorage = manager.queryViewStorage();

                    os.writeObject(Objects.requireNonNullElse(listStorage, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.SEARCH_STORAGE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Payment> listStorage = manager.querySearchStorage(((Request) i).getUsername(),((Request) i).getIdBoat());

                    os.writeObject(Objects.requireNonNullElse(listStorage, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VIEW_FEE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Payment> listFee = manager.queryViewMembership();

                    os.writeObject(Objects.requireNonNullElse(listFee, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.UPDATE_FEE)) {

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    Object obj  = manager.queryUpdateMembership(((Request) i).getQuote());

                    os.writeObject(obj);
                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.SEARCH_MEMBER_FEE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Payment> memberFee = manager.querySearchMemberFee(((Request) i).getUsername());

                    os.writeObject(Objects.requireNonNullElse(memberFee, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.VIEW_PARTICIPATION_RACE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Payment> listInscription = manager.queryRaceInscription();

                    os.writeObject(Objects.requireNonNullElse(listInscription, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.SEARCH_PARTICIPATION)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    List<Payment> listParticipation = manager.querySearchRaceParticipant(((Request) i).getUsername(),((Request) i).getIdRace(),((Request) i).getIdBoat());

                    os.writeObject(Objects.requireNonNullElse(listParticipation, ResponseType.NOT_FOUND));

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.NOTICE_STORAGE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType type = manager.queryAddNotice(((Request) i).getUsername(),"storage");

                    os.writeObject(type);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.NOTICE_MEMBERSHIP)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType type = manager.queryAddNotice(((Request) i).getUsername(),"membership");

                    os.writeObject(type);

                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.CHECK_NOTICE)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    ResponseType rs = manager.queryVerifyNotice(((Request) i).getUsername());

                    os.writeObject(rs);
                    os.flush();
                    this.socket.close();
                    return;
                }
                else if(i instanceof Request && ((Request) i).getType().equals(RequestType.CHECK_PAY)){

                    Thread.sleep(SLEEPTIME);

                    if (os == null) {

                        os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    }

                    manager.queryVerifyPayment(((Request) i).getUsername());

                    os.writeObject(ResponseType.OK);

                    os.flush();
                    this.socket.close();
                    return;
                }

                this.socket.close();
                return;

            }
            catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }

            catch (ClassNotFoundException | InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
