import { useState } from 'react';

function TodoInput({ onAddTodo }) {
  const [inputValue, setInputValue] = useState("");

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleAddClick = () => {
    if (inputValue.trim() !== '') {
      onAddTodo(inputValue);
      setInputValue("");
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleAddClick();
    }
  };

  return (
    <div className="todo-input-container">
      <input 
        type="text"
        value={inputValue}
        onChange={handleInputChange}
        onKeyPress={handleKeyPress}
        placeholder="Add a new task..."
        className="todo-input"
      />
      <button onClick={handleAddClick} className="add-btn">
        + Add Task
      </button>
    </div>
  );
}


export default TodoInput;
