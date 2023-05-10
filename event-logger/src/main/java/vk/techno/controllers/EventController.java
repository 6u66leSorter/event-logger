package vk.techno.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vk.techno.models.Event;
import vk.techno.models.EventDto;
import vk.techno.services.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/event")
    public ResponseEntity<?> create(@RequestBody EventDto eventDto) {
        try {
            Event event = new Event();
            event.setEventName(eventDto.getName());
            event.setAuthorized(eventDto.getStatus());
            event.setEventDateTime(LocalDateTime.now());
            event.setIpAddress(eventDto.getUserIp());
            eventService.save(event);
            return ResponseEntity.ok("Event saved successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving event: " + ex.getMessage());
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getStatistics(@RequestParam(name = "eventName") String eventName,
                                                           @RequestParam(name = "startDate") String start,
                                                           @RequestParam(name = "endDate") String end,
                                                           @RequestParam(name = "aggregateBy") String aggregateBy) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(start, dateTimeFormatter);
            LocalDateTime endDate = LocalDateTime.parse(end, dateTimeFormatter);

            Map<String, Long> result;
            switch (aggregateBy) {
                case "event":
                    result = eventService.countEventsByEventNameAndDateRange(eventName, startDate, endDate);
                    break;
                case "user":
                    result = eventService.countEventsByUserIpAndDateRange(eventName, startDate, endDate);
                    break;
                case "status":
                    result = eventService.countEventsByStatusAndDateRange(eventName, startDate, endDate);
                    break;
                default:
                    return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getStatistics() {
        List<Event> events = eventService.readAll();

        return events != null && !events.isEmpty() ? new ResponseEntity<>(events, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
