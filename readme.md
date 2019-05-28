# Tasks

## Create

```
curl -v -H "Content-Type: application/json" -X POST -d '{"name": "New task", "priority": "5", "description":"Very good task"}' 127.0.0.1:8080/tasks
```

## Read

```
curl -v -H "Content-Type: application/json" 127.0.0.1:8080/tasks/1
```

## Update
```
curl -v -H "Content-Type: application/json" -X PATCH -d '{"status": "INPROGRESS"}' 127.0.0.1:8080/tasks/1
```

## Delete

```
curl -v -X DELETE 127.0.0.1:8080/tasks/1
```

##  Get all

```
curl -v 127.0.0.1:8080/tasks
```


# Projects

## Create

```
curl -v -H "Content-Type: application/json" -X POST -d '{"name": "New project", "description":"Just another project"}' 127.0.0.1:8080/projects
```

## Read

```
curl -v -H "Content-Type: application/json" 127.0.0.1:8080/projects/1
```

## Update

```
curl -v -H "Content-Type: application/json" -X PATCH -d '{"name": "New name for this project"}' 127.0.0.1:8080/projects/1
```

## Delete
```
curl -v -X DELETE 127.0.0.1:8080/projects/1
```

##  Get all

```
curl -v 127.0.0.1:8080/projects
```