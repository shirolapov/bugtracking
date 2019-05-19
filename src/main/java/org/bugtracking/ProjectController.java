package org.bugtracking;

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
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

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

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateProject(@PathVariable("id") int id, @RequestBody Project projectRequest) throws  Exception {
        Boolean wasChange = false;
        if (projectRepository.existsById(id)) {

            Project project = projectRepository.findById(id).get();

            if  (!project.getName().equals(projectRequest.getName())) {
                if (projectRequest.getName() != null) {
                    wasChange = true;
                    project.setName(projectRequest.getName());
                }
            };

            if (!project.getDescription().equals(projectRequest.getDescription())) {
                wasChange = true;
                project.setDescription(projectRequest.getDescription());
            };

            if ( wasChange ) {
                projectRepository.save(project);
            } else {
                throw new ProjectNotChangedException();
            }

            ProjectDTO projectDTO = new ProjectDTO(project);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(projectDTO);
        } else {
            throw new ProjectNotFoundException();
        }
    }

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

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/projects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String project(@RequestBody Project project) throws Exception {
        if (project.getName() == null) throw new NameNotFoundException();
        projectRepository.save(project);
        ProjectDTO projectDTO = new ProjectDTO(project);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(projectDTO);
    }

    @RequestMapping(path="/projects", method = RequestMethod.GET)
    private  @ResponseBody Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }

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
}
