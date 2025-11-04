function TodoItem({
  todo,
  onDeleteTodo,
  onToggleComplete,
  onStartEdit,
  onSaveEdit,
  onCancelEdit,
  isEditing,
  editText,
  setEditText
}) {
  return (
    <li className={`todo-item ${todo.completed ? 'completed' : ''}`}>
      <div className="todo-content">
        <input
          type="checkbox"
          checked={todo.completed}
          onChange={() => onToggleComplete(todo.id)}
          className="todo-checkbox"
        />
        
        {isEditing ? (
          <input
            type="text"
            value={editText}
            onChange={(e) => setEditText(e.target.value)}
            className="edit-input"
            autoFocus
          />
        ) : (
          <span className="todo-text">{todo.text}</span>
        )}
      </div>

      <div className="todo-actions">
        {isEditing ? (
          <>
            <button 
              onClick={() => onSaveEdit(todo.id)} 
              className="btn btn-save"
            >
              ✓ Save
            </button>
            <button 
              onClick={onCancelEdit} 
              className="btn btn-cancel"
            >
              ✕ Cancel
            </button>
          </>
        ) : (
          <>
            <button 
              onClick={() => onStartEdit(todo.id, todo.text)} 
              className="btn btn-edit"
            >
               Edit
            </button>
            <button 
              onClick={() => onDeleteTodo(todo.id)} 
              className="btn btn-delete"
            >
               Delete
            </button>
          </>
        )}
      </div>
    </li>
  );
}


export default TodoItem;
