package vk.techno.services;

import org.springframework.stereotype.Service;
import vk.techno.models.Event;
import vk.techno.repositories.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public List<Event> readAll() {
        return eventRepository.findAll();
    }

    public Map<String, Long> countEventsByEventNameAndDateRange(String eventName, LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> events = eventRepository.findAllByEventNameAndEventDateTimeBetween(eventName, startDate, endDate);
        return events.stream()
                .collect(Collectors.groupingBy(Event::getEventName, Collectors.counting()));
    }

    public Map<String, Long> countEventsByUserIpAndDateRange(String eventName, LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> events = eventRepository.findAllByEventNameAndEventDateTimeBetween(eventName, startDate, endDate);
        return events.stream()
                .collect(Collectors.groupingBy(Event::getIpAddress, Collectors.counting()));
    }

    public Map<String, Long> countEventsByStatusAndDateRange(String eventName, LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> events = eventRepository.findAllByEventNameAndEventDateTimeBetween(eventName, startDate, endDate);
        return events.stream()
                .collect(Collectors.groupingBy(Event::getAuthorized, Collectors.counting()));
    }
}
