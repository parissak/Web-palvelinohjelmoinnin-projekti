package projekti.services;

import java.sql.Connection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.models.ConnectionRequest;
import projekti.repositories.ConnectionRequestRepository;
import projekti.models.Account;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRequestRepository connectionRequestRepository;

    public void saveRequest(ConnectionRequest connectionRequest) {
        connectionRequestRepository.save(connectionRequest);
    }

    public List<ConnectionRequest> getConnectionRequests(Account account) {
        return connectionRequestRepository.findByaccountTo(account);
    }

    public void deleteRequest(Account to, Account from) {
        ConnectionRequest request = connectionRequestRepository.findByAccountToAndAccountFrom(to, from);
        connectionRequestRepository.delete(request);
    }

    public void sendRequest(Account from, Account to) {
        ConnectionRequest request = new ConnectionRequest();
        request.setAccountFrom(from);
        request.setAccountTo(to);
        connectionRequestRepository.save(request);
    }

    public void connectAccounts(Account from, Account to) {
        to.getConnections().add(from);
        from.getConnections().add(to);
    }

    public void disconnectAccounts(Account from, Account to) {
        to.getConnections().remove(from);
        from.getConnections().remove(to);
    }

}
