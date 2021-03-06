package model;

import facade.Facade;
import facade.Starter;

import java.sql.SQLException;
import java.util.List;

public class Client extends User {

    private Facade facade = Starter.facade;

    public Client(List<Plant> plants, User user) throws SQLException, ClassNotFoundException {
        super(user.getUID(), user.getFirstName(), user.getSecondName(), user.getRole(), user.getAuthDataID());
        List<Plant> clientPlants = facade.filterPlantsByUserID(user.getUID());
    }

    @Override
    public Role getRole() {
        return Role.client;
    }

    public Client getClient() {
        return this;
    }

    // if a first one case, then plantID should be null else client need to pass a plantID
    public ClientRequest createClientReq(ClientRequest.Type type, Integer userID) throws SQLException, ClassNotFoundException {
        ClientRequest cReq = new ClientRequest(null, null, null, userID, null, null,
                null, type);
        cReq = facade.addCReq(cReq);
        return cReq;
    }

    //opinion true if client accepts landscaper`s work or false if not (will come somehow outside)
    public Feedback makeFeedback(ClientRequest clientRequest, boolean opinion) throws SQLException, ClassNotFoundException {
        Feedback feedback = new Feedback(clientRequest.getCReqID(), Feedback.Type.none, "");
        if (clientRequest.getStatus().equals(ClientRequest.Status.done)) {
            if (opinion) {
            feedback.setType(Feedback.Type.accepted);
            feedback.setText("Everything is great! Thanks.");
        }
            else {
                feedback.setText("I am not satisfied. Needs some more work to do here!");
                feedback.setType(Feedback.Type.declined);
                Integer plantID = facade.findCReqByID(clientRequest.getCReqID()).getPlantID();
                ClientRequest cReq = this.createClientReq(ClientRequest.Type.planned, plantID);
            }
        }
        else {
            feedback.setText("Work is not finished, can`t be accepted!");
            feedback.setType(Feedback.Type.none);
            Integer plantID = facade.findCReqByID(clientRequest.getCReqID()).getPlantID();
            ClientRequest cReq = this.createClientReq(ClientRequest.Type.planned, plantID);
        }
    return feedback;
    }
}
