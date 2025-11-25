package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Connection;
import dev.kochki.rolliki.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Transactional
    public Connection createConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    @Transactional
    public Connection updateConnection(Long id, Connection connectionDetails) {
        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        connection.setRole(connectionDetails.getRole());
        connection.setAccepted(connectionDetails.getAccepted());

        return connectionRepository.save(connection);
    }

    @Transactional
    public void deleteConnection(Long id) {
        connectionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Connection> getConnectionById(Long id) {
        return connectionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Connection> getConnectionsByProjectId(Long projectId) {
        return connectionRepository.findByProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public List<Connection> getPendingConnectionsByUserId(Long userId) {
        return connectionRepository.findByUserIdAndAcceptedFalse(userId);
    }

    @Transactional(readOnly = true)
    public List<Connection> getAcceptedConnectionsByUserId(Long userId) {
        return connectionRepository.findByUserIdAndAcceptedTrue(userId);
    }

    @Transactional(readOnly = true)
    public List<Connection> getPendingConnectionsByProjectId(Long projectId) {
        return connectionRepository.findByProjectIdAndAcceptedFalse(projectId);
    }

    @Transactional(readOnly = true)
    public List<Connection> getAcceptedConnectionsByProjectId(Long projectId) {
        return connectionRepository.findByProjectIdAndAcceptedTrue(projectId);
    }

    @Transactional
    public Connection acceptConnection(Long userId, Long projectId) {
        Connection connection = connectionRepository.findByUserIdAndProjectId(userId, projectId);
        if (connection == null) {
            throw new RuntimeException("Connection not found");
        }
        connection.setAccepted(true);
        return connectionRepository.save(connection);
    }
}