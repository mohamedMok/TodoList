TodoList Application
========
This API provides a todo list. Each todo item is called a task and has the following attributes:

**id**    : (String)                                                                                                              
**title** : (String)                                                                                                          
**body**  : (String)                                                                                                           
**done**  : (boolean)  

## Consuming the API 
The API needs JSON for input and return JSON for output

The base endpoint URI : `/todolist/tasks`

### Create new task 
Tasks can be create with `POST` HTTP method.                                                                        
**URI** : `/todolist/tasks`        

JSON exemple expected : 
```
  {
      "title" : "Shopping",
      "body" : "Buy a new suit",
      "done" : false
  }
```
Only one task can be create at time. URI of the taks created is return on "location" of HTTP HEAD 

### Get Task(s)
#### Get all tasks
Tasks can be get with `GET` HTTP method.                                                                  
**URI** : `/todolist/tasks`                                                                             
Output expected : A list of tasks at least one.                                                                       
If any task are retrieve an 404 HTTP ERROR will be returned

#### Get task by id 
Only one task can be get with `GET` HTTP method.                                                                  
**URI** : `/todolist/tasks/id` (on exemple id = "546ce451300445f6d74fffb0")                                     

Output expected (JSON Format):                                            
```
  {
      id    : "546ce451300445f6d74fffb0"
      title : "Running"
      body  : "Monday 22/10 - Running at least 1km"
      done  : false
  }

```
If any task are retrieve an 404 HTTP ERROR will be returned

### Update Task
Task can be updated with `PUT`HTTP method.                                                                
**URI** : `/todolist/tasks/id`                                                                                      
Input expected (JSON FORMAT) : Complete Task JSON object.                                                           
If the JSON object contain some syntax error, an 400 HTTP ERROR will be returned.

### Mark as done/undone
Task can be marked as done or undone with `PUT`HTTP method.                                                     
**URI** = `/todolist/tasks/id/done`                                                           
Input expected (JSON FORMAT) :              
```
  {
    "done" : true/false
  }
```
If task marked as done (done == true), the app send an SMS notification with Twilio API.    
BTW we need to change the value of properties of twilio account into properties/todolist.properties.
--(ACCOUNT_SID, AUTH_TOKEN, FromNumber, ToNumber)--

### Delete Task 
A task can be deleted with `DELETE` HTTP method.                                                        
**URI** : '/todolist/tasks/id/`

### Search 
We can search tasks with `GET` HTTP method. Titles and bodies will be searched and the results will be ranked by pertinence.
**URI** : '/todolist/tasks?query="something"`                                                                     
Output expected : all tasks contains the query string.                                                              



