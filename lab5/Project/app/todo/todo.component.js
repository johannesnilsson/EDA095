(function(app) { 
  app.TodoComponent = TodoComponent; 

  TodoComponent.parameters = [ app.DataService ];

  TodoComponent.annotations = [ 
    new ng.core.Component({ 
      selector: 'todo', 
      templateUrl: 'app/todo/todo.component.html', 
    }) 
  ]; 

  function TodoComponent() { 
  }; 

  function newTodo() {
  };

  
newTodo();

})(window.app = window.app || {});

