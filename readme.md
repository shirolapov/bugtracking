#Task

##Create

```
curl -v -H "Content-Type: application/json" -X POST -d '{"name": "New task"}' 127.0.0.1:8080/tasks
```

#Project

##Create

```
curl -v -H "Content-Type: application/json" -X POST -d '{"name": "New project"}' 127.0.0.1:8080/projects
```

##Add task

```
curl -v -H "Content-Type: application/json" -X PATCH -d '{"data": [ {"type": "task", "id":1} ] }' 127.0.0.1:8080/projects/2/relationships/tasks | jq
```
