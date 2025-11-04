import './App.css';
import { useState, useEffect } from 'react';
import TodoInput from './components/TodoInput';
import TodoList from './components/TodoList';

function App() {
  const [todos, setTodos] = useState(
    JSON.parse(localStorage.getItem('todos') || [])
  );
  const [editingId, setEditingId] = useState(null); 
  const [editText, setEditText] = useState('');

  // Save to localStorage whenever todos change
  useEffect(() => {
    localStorage.setItem('todos', JSON.stringify(todos));
  }, [todos]);

  const addTodo = (text) => {
    if (text.trim() === '') return;
    let newTodo = { id: Date.now(), text: text, completed: false };
    setTodos([...todos, newTodo]);
  };

  const deleteTodo = (id) => {
    setTodos(todos.filter((todo) => todo.id !== id));
  };

  const toggleComplete = (id) => {
    setTodos(todos.map((todo) =>
      todo.id === id ? { ...todo, completed: !todo.completed } : todo
    ));
  };

  const startEdit = (id, text) => {
    setEditingId(id);
    setEditText(text);
  };

  const saveEdit = (id) => {
    if (editText.trim() === '') return;
    setTodos(todos.map((todo) =>
      todo.id === id ? { ...todo, text: editText } : todo
    ));
    setEditingId(null);
    setEditText('');
  };

  const cancelEdit = () => {
    setEditingId(null);
    setEditText('');
  };

  return (
    <div className="App">
      <div className="container">
        <header className="header">
          <h1>I Have To-Do</h1>
          <p className="subtitle">Stay organized and productive</p>
        </header>
        
        <TodoInput onAddTodo={addTodo} />
        
        <TodoList 
          todos={todos} 
          onDeleteTodo={deleteTodo} 
          onToggleComplete={toggleComplete}
          onStartEdit={startEdit}
          onSaveEdit={saveEdit}
          onCancelEdit={cancelEdit}
          editingId={editingId}
          editText={editText}
          setEditText={setEditText}
        />
      </div>
    </div>
  );
  
}

export default App;
