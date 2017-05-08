(function(app) { 
  app.TodoListComponent = TodoListComponent; 
  
  TodoListComponent.parameters = [ app.DataService ];

  TodoListComponent.annotations = [ 
    new ng.core.Component({ 
      selector: 'todo-list', 
      templateUrl: 'app/todo.list/todo.list.component.html', 
    }) 
  ]; 


  function TodoListComponent(dataService) {
    this.myList = dataService.getTODOs();
  }
//  function TodoListComponent() { 
//    this.myList = [{text: "make a list", done: true}, 
//                   {text: "print the list", done: false}, 
//                   {text: "add more functionality...", done: false}]; 
//  } 
})(window.app = window.app || {});
