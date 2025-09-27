package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.model.Logs;
import com.MaFederation.MaFederation.repository.LogsRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogsService
{
    private  final LogsRepository logsRepository;
    public LogsService(LogsRepository logsRepository)
    {
        this.logsRepository = logsRepository;
    }

    public void log(String action, String userId) {
        Logs logs = Logs.builder()
                .action(action)
                .userId(userId)
                .actionTime(LocalDateTime.now())
                .build();
        logsRepository.save(logs);
    }
    public List<Logs> getLogs()
    {
        return logsRepository.findAll();
    }
}
