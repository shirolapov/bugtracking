package org.bugtracking.tasks;

import java.lang.String;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //Create
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String task(@RequestBody Task task) throws Exception {
        if (task.getName() == null) throw new NameNotFoundException();
        if (task.getPriority() == null) { task.setPriority(0); }
        if (task.getPriority() < 0) throw new StatusNotPositiveNumber();
        taskRepository.save(task);
        TaskDTO taskDTO = new TaskDTO(task);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(taskDTO);
    }

    //Read
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public @ResponseBody String getProject(@PathVariable("id") int id) throws Exception {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.findById(id).get();
            TaskDTO taskDTO = new TaskDTO(task);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(taskDTO);
        } else {
            throw new TaskNotFoundException();
        }
    }

    //Update
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateProject(@PathVariable("id") int id, @RequestBody Task taskRequest) throws  Exception {
        if (taskRepository.existsById(id)) {

            Task task = taskRepository.findById(id).get();

            if (task.equalsAndChange(taskRequest)) {
                taskRepository.save(task);
                TaskDTO taskDTO = new TaskDTO(task);
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(taskDTO);
            } else {
                throw new ProjectNotChangedException();
            }
        } else {
            throw new TaskNotFoundException();
        }
    }

    //Delete
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteTask(@PathVariable("id") int id) {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.findById(id).get();
            taskRepository.delete(task);
            return null;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //FindAll
    @RequestMapping(path="/tasks", method = RequestMethod.GET)
    private  @ResponseBody Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //Custom exception
    @ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY, reason="No such name")  // 422
    private class NameNotFoundException extends RuntimeException {
        // ...
    }

    @ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY, reason="Status must be positive number")  // 422
    private class StatusNotPositiveNumber extends RuntimeException {
        // ...
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Project not found")  // 404
    private class TaskNotFoundException extends RuntimeException {
        // ...
    }

    @ResponseStatus(value=HttpStatus.NOT_MODIFIED, reason="Project not modified")  // 304
    private class ProjectNotChangedException extends RuntimeException {
        // ...
    }
}
