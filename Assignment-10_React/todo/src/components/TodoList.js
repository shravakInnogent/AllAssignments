import TodoItem from './TodoItem';

function TodoList({ 
  todos, 
  onDeleteTodo, 
  onToggleComplete,
  onStartEdit,
  onSaveEdit,
  onCancelEdit,
  editingId,
  editText,
  setEditText
}) {
  if (todos.length === 0) {
    return <div className="empty-state">✨ No tasks yet. Add one to get started!</div>;
  }

  return (
    <div className="todo-list-container">
      <ul className="todo-list">
        {todos.map((todo) => (
          <TodoItem
            key={todo.id}
            todo={todo}
            onDeleteTodo={onDeleteTodo}
            onToggleComplete={onToggleComplete}
            onStartEdit={onStartEdit}
            onSaveEdit={onSaveEdit}
            onCancelEdit={onCancelEdit}
            isEditing={editingId === todo.id}
            editText={editText}
            setEditText={setEditText}
          />
        ))}
      </ul>
    </div>
  );
}


export default TodoList;
