package org.bugtracking.projects;

import java.lang.reflect.Array;
import java.util.List;
import java.lang.String;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.bugtracking.tasks.TaskRepository;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.crypto.Data;
import org.bugtracking.tasks.Task;

@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    //Create
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/projects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String project(@RequestBody Project project) throws Exception {
        if (project.getName() == null) throw new NameNotFoundException();
        projectRepository.save(project);
        ProjectDTO projectDTO = new ProjectDTO(project);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(projectDTO);
    }

    //Read
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public @ResponseBody String getProject(@PathVariable("id") int id) throws Exception {
        if (projectRepository.existsById(id)) {
            Project project = projectRepository.findById(id).get();
            ProjectDTO projectDTO = new ProjectDTO(project);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(projectDTO);
        } else {
            throw new ProjectNotFoundException();
        }
    }

    //Add many task to project
    @RequestMapping(value = "/projects/{id}/relationships/tasks", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String addTasksToProject(@PathVariable("id") int id, @RequestBody String jsonString) throws Exception {
        if (projectRepository.existsById(id)) {
            Project project = projectRepository.findById(id).get();

            Gson gson = new Gson();
            DataTask dataTask = new DataTask();

            dataTask = gson.fromJson(jsonString, DataTask.class);

            for (int i = 0; i < dataTask.data.size(); i++) {
                Integer taskId = dataTask.data.get(i).id;
                if (taskRepository.existsById(taskId)) {
                    Task task = taskRepository.findById(taskId).get();
                    task.setProject(project);
                    taskRepository.save(task);
                }
            }

            ProjectDTO projectDTO = new ProjectDTO(project);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(projectDTO);
        } else {
            throw new ProjectNotFoundException();
        }
    }

    //Update
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateProject(@PathVariable("id") int id, @RequestBody Project projectRequest) throws  Exception {
        if (projectRepository.existsById(id)) {

            Project project = projectRepository.findById(id).get();

            if (project.equalsAndChange(projectRequest)) {
                projectRepository.save(project);
                ProjectDTO projectDTO = new ProjectDTO(project);
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(projectDTO);
            } else {
                throw new ProjectNotChangedException();
            }
        } else {
            throw new ProjectNotFoundException();
        }
    }

    //Delete
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteProject(@PathVariable("id") int id) {
        if (projectRepository.existsById(id)) {
            Project project = projectRepository.findById(id).get();
            projectRepository.delete(project);
            return null;
        } else {
            throw new ProjectNotFoundException();
        }
    }

    //FindAll
    @RequestMapping(path="/projects", method = RequestMethod.GET)
    private  @ResponseBody Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    //Custom exception
    @ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY, reason="No such name")  // 422
    private class NameNotFoundException extends RuntimeException {
        // ...
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Project not found")  // 404
    private class ProjectNotFoundException extends RuntimeException {
        // ...
    }

    @ResponseStatus(value=HttpStatus.NOT_MODIFIED, reason="Project not modified")  // 304
    private class ProjectNotChangedException extends RuntimeException {
        // ...
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Project not found")  // 404
    private class TaskNotFoundException extends RuntimeException {
        // ...
    }
}
