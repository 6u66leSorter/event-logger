package vk.techno.services;

import org.springframework.stereotype.Service;
import vk.techno.models.Event;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface EventService {
    void save(Event event);
    List<Event> readAll();
    Map<String, Long> countEventsByEventNameAndDateRange(String eventName, LocalDateTime startDate, LocalDateTime endDate);
    Map<String, Long> countEventsByUserIpAndDateRange(String eventName, LocalDateTime startDate, LocalDateTime endDate);
    Map<String, Long> countEventsByStatusAndDateRange(String eventName, LocalDateTime startDate, LocalDateTime endDate);
}