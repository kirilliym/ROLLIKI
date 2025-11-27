package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.Connection;
import dev.kochki.rolliki.model.entity.Role;
import dev.kochki.rolliki.model.request.CreateConnectionRequest;
import dev.kochki.rolliki.model.request.UpdateConnectionRequest;
import dev.kochki.rolliki.model.response.ConnectionResponse;
import dev.kochki.rolliki.repository.ConnectionRepository;
import dev.kochki.rolliki.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/connection")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;
    private final ConnectionRepository connectionRepository;

    @PostMapping
    public ResponseEntity<ConnectionResponse> createConnection(@RequestBody CreateConnectionRequest request) {
        Connection connection = new Connection();
        connection.setProjectId(request.getProjectId());
        connection.setUserId(request.getUserId());
        connection.setRole(request.getRole());
        connection.setAccepted(false);

        Connection createdConnection = connectionService.createConnection(connection);
        ConnectionResponse response = new ConnectionResponse(
                createdConnection.getId(),
                createdConnection.getProjectId(),
                createdConnection.getUserId(),
                createdConnection.getRole(),
                createdConnection.getAccepted()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConnectionResponse> updateConnection(@PathVariable Long id, @RequestBody UpdateConnectionRequest request) {
        Connection connectionDetails = new Connection();
        connectionDetails.setRole(request.getRole());
        connectionDetails.setAccepted(request.getAccepted());

        Connection updatedConnection = connectionService.updateConnection(id, connectionDetails);
        ConnectionResponse response = new ConnectionResponse(
                updatedConnection.getId(),
                updatedConnection.getProjectId(),
                updatedConnection.getUserId(),
                updatedConnection.getRole(),
                updatedConnection.getAccepted()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Long id) {
        connectionService.deleteConnection(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionResponse> getConnectionById(@PathVariable Long id) {
        return connectionService.getConnectionById(id)
                .map(connection -> new ConnectionResponse(
                        connection.getId(),
                        connection.getProjectId(),
                        connection.getUserId(),
                        connection.getRole(),
                        connection.getAccepted()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ConnectionResponse>> getConnectionsByProjectId(@PathVariable Long projectId) {
        List<Connection> connections = connectionService.getConnectionsByProjectId(projectId);
        List<ConnectionResponse> responses = connections.stream()
                .map(connection -> new ConnectionResponse(
                        connection.getId(),
                        connection.getProjectId(),
                        connection.getUserId(),
                        connection.getRole(),
                        connection.getAccepted()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<ConnectionResponse>> getPendingConnectionsByUserId(@PathVariable Long userId) {
        List<Connection> connections = connectionService.getPendingConnectionsByUserId(userId);
        List<ConnectionResponse> responses = connections.stream()
                .map(connection -> new ConnectionResponse(
                        connection.getId(),
                        connection.getProjectId(),
                        connection.getUserId(),
                        connection.getRole(),
                        connection.getAccepted()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}/accepted")
    public ResponseEntity<List<ConnectionResponse>> getAcceptedConnectionsByUserId(@PathVariable Long userId) {
        List<Connection> connections = connectionService.getAcceptedConnectionsByUserId(userId);
        List<ConnectionResponse> responses = connections.stream()
                .map(connection -> new ConnectionResponse(
                        connection.getId(),
                        connection.getProjectId(),
                        connection.getUserId(),
                        connection.getRole(),
                        connection.getAccepted()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/project/{projectId}/pending")
    public ResponseEntity<List<ConnectionResponse>> getPendingConnectionsByProjectId(@PathVariable Long projectId) {
        List<Connection> connections = connectionService.getPendingConnectionsByProjectId(projectId);
        List<ConnectionResponse> responses = connections.stream()
                .map(connection -> new ConnectionResponse(
                        connection.getId(),
                        connection.getProjectId(),
                        connection.getUserId(),
                        connection.getRole(),
                        connection.getAccepted()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/project/{projectId}/accepted")
    public ResponseEntity<List<ConnectionResponse>> getAcceptedConnectionsByProjectId(@PathVariable Long projectId) {
        List<Connection> connections = connectionService.getAcceptedConnectionsByProjectId(projectId);
        List<ConnectionResponse> responses = connections.stream()
                .map(connection -> new ConnectionResponse(
                        connection.getId(),
                        connection.getProjectId(),
                        connection.getUserId(),
                        connection.getRole(),
                        connection.getAccepted()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/accept/{userId}/{projectId}")
    public ResponseEntity<ConnectionResponse> acceptConnection(@PathVariable Long userId, @PathVariable Long projectId) {
        Connection acceptedConnection = connectionService.acceptConnection(userId, projectId);
        ConnectionResponse response = new ConnectionResponse(
                acceptedConnection.getId(),
                acceptedConnection.getProjectId(),
                acceptedConnection.getUserId(),
                acceptedConnection.getRole(),
                acceptedConnection.getAccepted()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getrole/{projectId}/{userId}")
    public ResponseEntity<Role> getRole(@PathVariable Long projectId, @PathVariable Long userId) {
        Connection connection = connectionRepository.findByUserIdAndProjectId(userId, projectId);
        if (connection != null && connection.getAccepted()) {
            return ResponseEntity.ok(connection.getRole());
        }
        return ResponseEntity.notFound().build();
    }
}