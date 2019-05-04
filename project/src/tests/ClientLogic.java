package tests;

import model.*;
import org.junit.Test;
import repo.PlantRepoImpl;
import repo.UserRepoImpl;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

public class ClientLogic {

    private UserRepoImpl users = new UserRepoImpl();
    private PlantRepoImpl plantsBase = new PlantRepoImpl();

    public ClientLogic() throws ParseException {}

    @Test
    public void ClientPlantsAmountTest() throws ParseException {
        Integer UID = 2;
        Client client = users.getClientByUserID(UID);
        List<Plant> plants = client.clientPlants(client.getUID());
        Integer expected = 3;
        Integer res = plants.size();
        assertEquals(expected, res);
    }

    //createClientReq method tests

    @Test
    public void CReqCreatedTest() throws ParseException {
        Integer UID = 2;
        Integer plantID = 2;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, plantID);
        ClientRequest.Status res = cReq.getStatus();
        ClientRequest.Status expected = ClientRequest.Status.newOne;
        assertEquals(res, expected);
    }

    @Test
    public void FirstCReqPlantNullTest() throws ParseException {
        Integer UID = 2;
        Integer plantID = 7;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.firstOne, plantID);
        Plant res = plantsBase.findItemByPlantID(cReq.getPlantID());
        Plant expected = null;
        assertEquals(res, expected);
    }

    @Test
    public void PlannedCReqPlantNotNullTest() throws ParseException {
        Integer UID = 2;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, 2);
        Plant res = plantsBase.findItemByPlantID(cReq.getPlantID());
        Plant unexpected = null;
        assertNotEquals(res, unexpected);
    }

    //makeFeedback method tests

    @Test
    public void PositiveFeedbackTest() throws ParseException {
        Integer UID = 2;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, 2);
        Feedback feedback = client.makeFeedback(cReq, true);
        Feedback.Type res = feedback.getType();
        Feedback.Type expected = Feedback.Type.accepted;
        System.out.println(feedback.getText());
        assertEquals(res, expected);
    }

    @Test
    public void NegativeFeedbackTest() throws ParseException {
        Integer UID = 2;
        Client client = users.getClientByUserID(UID);
        ClientRequest cReq = client.createClientReq(ClientRequest.Type.planned, 2);
        Feedback feedback = client.makeFeedback(cReq, false);
        Feedback.Type res = feedback.getType();
        Feedback.Type expected = Feedback.Type.declined;
        System.out.println(feedback.getText());
        assertEquals(res, expected);
    }

    @Test
    public void allPlants() {
        List<Plant> allPlants = plantsBase.allPlants();
        System.out.println(allPlants);
    }

    @Test
    public void allClients() throws ParseException {
        List<Client> allClients = users.allClients();
        System.out.println(allClients);
    }

}